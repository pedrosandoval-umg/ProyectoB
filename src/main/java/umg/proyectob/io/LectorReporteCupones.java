package umg.proyectob.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import umg.proyectob.CuponUsado;

public class LectorReporteCupones {

    public static List<CuponUsado> leerConSelector() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de cupones usados");

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

    private static List<CuponUsado> leerDesdeCSV(String ruta) {
        List<CuponUsado> lista = new ArrayList<>();

        try (Stream<String> lineas = Files.lines(Paths.get(ruta))) {
            lineas.skip(1).forEach(linea -> {
                try {
                    String[] partes = linea.split(",");
                    if (partes.length >= 5) {
                        LocalDate fecha = LocalDate.parse(partes[0].trim());
                        String codigo = partes[1].trim();
                        String tipo = partes[2].trim();
                        double valor = Double.parseDouble(partes[3].trim());
                        String vendedor = partes[4].trim();

                        CuponUsado cupon = new CuponUsado(fecha, codigo, tipo, valor, vendedor);
                        lista.add(cupon);
                    }
                } catch (Exception e) {
                    System.err.println("Error en línea CSV: " + linea);
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer CSV: " + e.getMessage());
        }

        return lista;
    }

    private static List<CuponUsado> leerDesdeJSON(String ruta) {
        List<CuponUsado> lista = new ArrayList<>();

        try (Reader reader = new FileReader(ruta)) {
            Type tipoLista = new TypeToken<List<CuponUsado>>() {
            }.getType();
            Gson gson = new Gson();
            lista = gson.fromJson(reader, tipoLista);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer JSON: " + e.getMessage());
        }

        return lista;
    }

    private static List<CuponUsado> leerDesdeXML(String ruta) {
        List<CuponUsado> lista = new ArrayList<>();

        try {
            File archivo = new File(ruta);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList cupones = doc.getElementsByTagName("CuponUsado");

            for (int i = 0; i < cupones.getLength(); i++) {
                Node nodo = cupones.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nodo;

                    LocalDate fecha = LocalDate.parse(elem.getElementsByTagName("fecha").item(0).getTextContent());
                    String codigo = elem.getElementsByTagName("codigo").item(0).getTextContent();
                    String tipo = elem.getElementsByTagName("tipo").item(0).getTextContent();
                    double valor = Double.parseDouble(elem.getElementsByTagName("valor").item(0).getTextContent());
                    String vendedor = elem.getElementsByTagName("vendedor").item(0).getTextContent();

                    CuponUsado cupon = new CuponUsado(fecha, codigo, tipo, valor, vendedor);
                    lista.add(cupon);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer XML: " + e.getMessage());
        }

        return lista;
    }
}
