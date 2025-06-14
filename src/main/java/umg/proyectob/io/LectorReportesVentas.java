package umg.proyectob.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import umg.proyectob.CompiladoVenta;
import umg.proyectob.Cupon;
import umg.proyectob.Usuario;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LectorReportesVentas {

    public static List<CompiladoVenta> leerConSelector() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de reporte de ventas");

        selector.setAcceptAllFileFilterUsed(false);
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos CSV (.csv)", "csv"));
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos JSON (.json)", "json"));
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos XML (.xml)", "xml"));

        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            String ruta = archivo.getAbsolutePath().toLowerCase();

            try {
                if (ruta.endsWith(".csv")) {
                    return leerDesdeCSV(ruta);
                } else if (ruta.endsWith(".json")) {
                    return leerDesdeJSON(ruta);
                } else if (ruta.endsWith(".xml")) {
                    return leerDesdeXML(ruta);
                } else {
                    JOptionPane.showMessageDialog(null, "Formato no soportado.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al importar: " + e.getMessage());
            }
        }

        return new ArrayList<>();
    }

    private static List<CompiladoVenta> leerDesdeCSV(String ruta) {
        List<CompiladoVenta> ventas = new ArrayList<>();
        try (Stream<String> lineas = Files.lines(Paths.get(ruta))) {
            lineas.skip(1).forEach(linea -> {
                String[] partes = linea.split(",");
                try {
                    String id = partes[0];
                    String cliente = partes[1];
                    String nit = partes[2];
                    String direccion = partes[3];
                    double totalSinIva = Double.parseDouble(partes[4]);
                    double total = Double.parseDouble(partes[5]);
                    String nombreVendedor = partes[6];
                    LocalDate fecha = LocalDate.parse(partes[7]);

                    Usuario vendedor = new Usuario(nombreVendedor, "", "", 2);
                    ventas.add(new CompiladoVenta(id, cliente, nit, direccion, totalSinIva, total, vendedor, fecha, new ArrayList<>()));
                } catch (Exception e) {
                    System.err.println("Error en línea CSV: " + linea);
                }
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer CSV: " + e.getMessage());
        }
        return ventas;
    }

    private static List<CompiladoVenta> leerDesdeJSON(String ruta) {
        try (Reader reader = new FileReader(ruta)) {
            Type tipoLista = new TypeToken<List<CompiladoVenta>>() {}.getType();
            return new Gson().fromJson(reader, tipoLista);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static List<CompiladoVenta> leerDesdeXML(String ruta) {
        List<CompiladoVenta> ventas = new ArrayList<>();

        try {
            File archivo = new File(ruta);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaVentas = doc.getElementsByTagName("venta");

            for (int i = 0; i < listaVentas.getLength(); i++) {
                Node nodo = listaVentas.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    String id = getValorElemento("factura", elemento);
                    String cliente = getValorElemento("cliente", elemento);
                    String nit = getValorElemento("nit", elemento);
                    String direccion = getValorElemento("direccion", elemento);
                    double valorCupon = Double.parseDouble(getValorElemento("valor_cupon", elemento));
                    double totalSinIva = Double.parseDouble(getValorElemento("total_sin_iva", elemento));
                    double total = Double.parseDouble(getValorElemento("total", elemento));
                    String nombreVendedor = getValorElemento("vendedor", elemento);
                    LocalDate fecha = LocalDate.parse(getValorElemento("fecha", elemento));
                    Usuario vendedor = new Usuario(nombreVendedor, "", "", 2);

                    Cupon cupon = new Cupon();
                    cupon.setCodigo("ImportadoXML");
                    cupon.setDescripcion("Cupón cargado desde XML");
                    cupon.setValor(valorCupon);
                    cupon.setEsPorcentaje(false);
                    cupon.setActivo(true);
                    cupon.setFechaVencimiento(null);

                    CompiladoVenta venta = new CompiladoVenta(id, cliente, nit, direccion, totalSinIva, total, vendedor, fecha, new ArrayList<>());
                    venta.setCupon(cupon);
                    ventas.add(venta);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer XML: " + e.getMessage());
        }

        return ventas;
    }

    private static String getValorElemento(String etiqueta, Element elemento) {
        NodeList lista = elemento.getElementsByTagName(etiqueta);
        if (lista != null && lista.getLength() > 0) {
            return lista.item(0).getTextContent();
        }
        return "";
    }
}
