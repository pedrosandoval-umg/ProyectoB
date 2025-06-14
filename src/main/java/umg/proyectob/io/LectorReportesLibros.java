package umg.proyectob.io;

import umg.proyectob.CompiladoVenta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import umg.proyectob.Cupon;
import java.time.LocalDate;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.Type;
import umg.proyectob.Usuario;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import umg.proyectob.DetalleVenta;
import umg.proyectob.LibroenInventario;

public class LectorReportesLibros {

    public static List<DetalleVenta> leerConSelector() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de libros vendidos");

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

    private static List<DetalleVenta> leerDesdeCSV(String ruta) {
        List<DetalleVenta> lista = new ArrayList<>();

        try (Stream<String> lineas = Files.lines(Paths.get(ruta))) {
            lineas.skip(1).forEach(linea -> {
                try {
                    String[] partes = linea.split(",");

                    LocalDate fecha = LocalDate.parse(partes[0].trim());
                    String titulo = partes[1].trim();
                    int cantidad = Integer.parseInt(partes[2].trim());
                    double precio = Double.parseDouble(partes[3].trim());

                    LibroenInventario libro = new LibroenInventario();
                    libro.setTitulo(titulo);
                    libro.setAutor("Autor desconocido");
                    libro.setGenero("Género desconocido");
                    libro.setPrecio(precio);
                    libro.setCantidad(0);

                    DetalleVenta detalle = new DetalleVenta(
                            "CF", // NIT
                            "CF", // Cliente
                            "Ciudad", // Dirección
                            0, // Item
                            libro,
                            cantidad,
                            fecha,
                            "N/A" // Vendedor
                    );

                    lista.add(detalle);

                } catch (Exception e) {
                    System.err.println("Error en línea CSV: " + linea);
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer CSV: " + e.getMessage());
        }

        return lista;
    }

    private static List<DetalleVenta> leerDesdeJSON(String ruta) {
        List<DetalleVenta> lista = new ArrayList<>();

        try (Reader reader = new FileReader(ruta)) {
            Type tipoLista = new TypeToken<List<DetalleVenta>>() {
            }.getType();
            Gson gson = new Gson();
            lista = gson.fromJson(reader, tipoLista);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer JSON: " + e.getMessage());
        }

        return lista;
    }

    private static List<DetalleVenta> leerDesdeXML(String ruta) {
        List<DetalleVenta> lista = new ArrayList<>();

        try {
            File archivo = new File(ruta);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList detalles = doc.getElementsByTagName("detalle");

            for (int i = 0; i < detalles.getLength(); i++) {
                Node nodo = detalles.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nodo;

                    LocalDate fecha = LocalDate.parse(elem.getElementsByTagName("fecha").item(0).getTextContent());
                    String titulo = elem.getElementsByTagName("titulo").item(0).getTextContent();
                    int cantidad = Integer.parseInt(elem.getElementsByTagName("cantidad").item(0).getTextContent());
                    double precio = Double.parseDouble(elem.getElementsByTagName("precioUnitario").item(0).getTextContent());

                    LibroenInventario libro = new LibroenInventario();
                    libro.setTitulo(titulo);
                    libro.setAutor("Autor desconocido");
                    libro.setGenero("Género desconocido");
                    libro.setPrecio(precio);
                    libro.setCantidad(0);

                    DetalleVenta detalle = new DetalleVenta(
                            "CF", "CF", "Ciudad", 0, libro, cantidad, fecha, "N/A"
                    );

                    lista.add(detalle);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer XML: " + e.getMessage());
        }

        return lista;
    }


}
