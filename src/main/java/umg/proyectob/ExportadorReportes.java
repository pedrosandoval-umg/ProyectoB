package umg.proyectob;

import umg.proyectob.CompiladoVenta;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;




public class ExportadorReportes {

    public static void exportarComoCSV(List<CompiladoVenta> ventas) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte como CSV"); // Se crea un diálogo para que el usuario elija dónde guardar el archivo. Se muestra el título personalizado.

        int resultado = selector.showSaveDialog(null); // Abre el diálogo de guardado. Si el usuario presiona "Guardar", retorna APPROVE_OPTION.
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath(); // Si el usuario elige una ruta válida, se guarda esa ruta como string.
            if (!ruta.endsWith(".csv")) {
                ruta += ".csv"; // Si el usuario no escribió .csv al final, se agrega automáticamente.
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) { // Se abre un PrintWriter dentro de un bloque try-with-resources, que cierra el archivo automáticamente.
                // Se escribe la primera línea del archivo CSV: los nombres de las columnas.
                writer.println("Factura,Cliente,NIT,Dirección,Valor Cupón,Total sin IVA,Total,Vendedor,Fecha");

                // Se recorre cada venta en la lista.
                for (CompiladoVenta venta : ventas) {
                    double valorCupon = (venta.getCupon() != null) ? venta.getCupon().getValor() : 0; // Si la venta tiene cupón, se usa su valor; si no, se escribe 0.
                    writer.printf("%s,%s,%s,%s,%.2f,%.2f,%.2f,%s,%s\n", // Se imprimen los datos de la venta en formato CSV
                        venta.getNumeroFactura(),
                        venta.getNombreCliente(),
                        venta.getNit(),
                        venta.getDireccion().replace(",", " "), // Se reemplazan comas en la dirección para no romper el formato CSV.
                        valorCupon,
                        venta.getTotalSinIva(),
                        venta.getTotal(),
                        venta.getVendedor().getNombre(),
                        venta.getFecha().toString()
                    );
                }

                writer.flush();
                JOptionPane.showMessageDialog(null, "Reporte exportado como CSV exitosamente."); // Se vacía el buffer de escritura y se informa al usuario que todo salió bien.

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al exportar CSV: " + e.getMessage()); // Si ocurre cualquier error, se muestra un mensaje de error.
            }
        }
    }
    
    public static void exportarComoTXT(List<CompiladoVenta> ventas) {
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Guardar reporte como TXT (formato tabla)");

    int resultado = selector.showSaveDialog(null);
    if (resultado == JFileChooser.APPROVE_OPTION) {
        String ruta = selector.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".txt")) {
            ruta += ".txt";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            // Encabezado tipo tabla
            writer.println("factura|cliente|nit|direccion|valor_cupon|total_sin_iva|total|vendedor|fecha");

            // Datos de cada venta

            for (CompiladoVenta venta : ventas) {
                double valorCupon = (venta.getCupon() != null) ? venta.getCupon().getValor() : 0;

                // Construir línea de datos separada por pipes
                String linea = String.join("|",
                    venta.getNumeroFactura(),
                    venta.getNombreCliente(),
                    venta.getNit(),
                    venta.getDireccion().replace("|", " "), // evitar conflicto de separador
                    String.format("%.2f", valorCupon),
                    String.format("%.2f", venta.getTotalSinIva()),
                    String.format("%.2f", venta.getTotal()),
                    venta.getVendedor().getNombre(),
                    venta.getFecha().toString()
                                            );
                    writer.println(linea);
            }

            writer.flush();
            JOptionPane.showMessageDialog(null, "Reporte exportado como TXT exitosamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al exportar TXT: " + e.getMessage());
                                }
        }
    }
    
    public static void exportarComoXML(List<CompiladoVenta> ventas) {
    JFileChooser selector = new JFileChooser(); // Crear el selector para que el usuario elija dónde guardar
    selector.setDialogTitle("Guardar reporte como XML");

    int resultado = selector.showSaveDialog(null); // Muestra el diálogo de guardar.
    if (resultado == JFileChooser.APPROVE_OPTION) { // Si el usuario presiona "Guardar", retorna APPROVE_OPTION.
        String ruta = selector.getSelectedFile().getAbsolutePath(); // Se obtiene la ruta completa del archivo que el usuario eligió.
        if (!ruta.endsWith(".xml")) {
            ruta += ".xml"; // Si el usuario no puso la extensión .xml, se la agregamos automáticamente.
        }

        try {
            // Crear el documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Fábrica para construir documentos XML
            DocumentBuilder builder = factory.newDocumentBuilder(); // Creador de documentos
            Document doc = builder.newDocument(); // Documento XML en blanco

            Element raiz = doc.createElement("ventas"); // Nodo raíz <ventas>
            doc.appendChild(raiz); // Se adjunta el nodo raíz al documento

            for (CompiladoVenta venta : ventas) {
                Element ventaElem = doc.createElement("venta"); // Se crea un nodo <venta> por cada venta

                // Crear subelementos para cada campo
                Element factura = doc.createElement("factura"); //subelemento factura
                factura.appendChild(doc.createTextNode(venta.getNumeroFactura()));
                ventaElem.appendChild(factura);

                Element cliente = doc.createElement("cliente"); //subelemento cliente
                cliente.appendChild(doc.createTextNode(venta.getNombreCliente()));
                ventaElem.appendChild(cliente);

                Element nit = doc.createElement("nit"); //subelemento nit
                nit.appendChild(doc.createTextNode(venta.getNit()));
                ventaElem.appendChild(nit);

                Element direccion = doc.createElement("direccion"); //subelemento dirreccion
                direccion.appendChild(doc.createTextNode(venta.getDireccion()));
                ventaElem.appendChild(direccion);

                Element cupon = doc.createElement("valor_cupon"); //subelemento cupon
                cupon.appendChild(doc.createTextNode(
                    String.format("%.2f", (venta.getCupon() != null) ? venta.getCupon().getValor() : 0) // si no hay, pone valor 0
                ));
                ventaElem.appendChild(cupon);

                Element sinIVA = doc.createElement("total_sin_iva"); //subelemento total sin iva
                sinIVA.appendChild(doc.createTextNode(String.format("%.2f", venta.getTotalSinIva())));
                ventaElem.appendChild(sinIVA);

                Element total = doc.createElement("total"); //subelemento total
                total.appendChild(doc.createTextNode(String.format("%.2f", venta.getTotal())));
                ventaElem.appendChild(total);

                Element vendedor = doc.createElement("vendedor"); //subelemento vendedor
                vendedor.appendChild(doc.createTextNode(venta.getVendedor().getNombre()));
                ventaElem.appendChild(vendedor);

                Element fecha = doc.createElement("fecha"); //subelemento fecha
                fecha.appendChild(doc.createTextNode(venta.getFecha().toString()));
                ventaElem.appendChild(fecha);

                // Agregar la venta completa al nodo raíz
                raiz.appendChild(ventaElem);
            }

            // Escribir el documento a un archivo físico
            TransformerFactory transformerFactory = TransformerFactory.newInstance(); // Fábrica de transformadores XML
            Transformer transformer = transformerFactory.newTransformer(); // Crea transformador
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); // Formato legible
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc); // Fuente: el documento creado
            StreamResult result = new StreamResult(new java.io.File(ruta)); // Resultado: el archivo XML de salida

            transformer.transform(source, result); // Realiza la transformación: escribe el archivo
            JOptionPane.showMessageDialog(null, "Reporte exportado como XML exitosamente."); // Mensaje de éxito

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al exportar XML: " + e.getMessage()); // Si ocurre un error, se informa al usuario
        }
    }
}
    
    public static void exportarComoJSON(List<CompiladoVenta> ventas) {
        JFileChooser selector = new JFileChooser(); // Crear el selector para que el usuario elija dónde guardar
        selector.setDialogTitle("Guardar reporte como JSON");

        int resultado = selector.showSaveDialog(null); // Muestra el diálogo de guardar.
        if (resultado == JFileChooser.APPROVE_OPTION) { // Si el usuario presiona "Guardar", retorna APPROVE_OPTION.
            String ruta = selector.getSelectedFile().getAbsolutePath(); // Se obtiene la ruta completa del archivo que el usuario eligió.
        if (!ruta.endsWith(".json")) {
            ruta += ".json"; // Si el usuario no puso la extensión .json, se la agregamos automáticamente.
        }

        try (FileWriter writer = new FileWriter(ruta)) { // Se abre el archivo para escritura. try-with-resources asegura que se cierre correctamente.
            Gson gson = new GsonBuilder()
                .setPrettyPrinting() // Activa la indentación para que el JSON se vea bonito (legible)
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Le dice a Gson cómo manejar objetos de tipo LocalDate usando un adaptador personalizado
                .create(); // Construye la instancia final de Gson con la configuración aplicada

            gson.toJson(ventas, writer); // // Convertimos la lista de ventas a JSON y la escribimos al archivo, es decir Aquí se transforma todo el objeto List<CompiladoVenta> en JSON.
            writer.flush(); // Se vacía el buffer de escritura
            JOptionPane.showMessageDialog(null, "Reporte exportado como JSON exitosamente."); // Se notifica al usuario que todo fue exitoso.

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al exportar JSON: " + e.getMessage()); //Si ocurre un error durante el proceso (archivo bloqueado, ruta inválida, etc.), se muestra el mensaje correspondiente.
        }
    }
}
    
    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {         // Clase adaptadora que indica cómo Gson debe manejar objetos de tipo LocalDate

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException { // Método que define cómo escribir una fecha (LocalDate) en el archivo JSON.
            out.value(value.toString()); // Convierte el objeto LocalDate en texto con formato ISO (yyyy-MM-dd), que es legible y estándar. Es decir escribe como "2025-05-20"
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException { // Método que define cómo leer una fecha desde JSON (cuando hagas deserialización, si se implementa).
            return LocalDate.parse(in.nextString()); // Lee un string como "2025-05-20" y lo convierte en LocalDate
        }
    }
}
