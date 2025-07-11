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
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javax.xml.transform.OutputKeys;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import umg.proyectob.CuponUsado;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



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

    public static void exportarLibrosVendidosComoCSV(List<DetalleVenta> lista) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de libros vendidos como CSV");

        int resultado = selector.showSaveDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".csv")) {
            archivo = new File(archivo.getAbsolutePath() + ".csv");
        }

        try (FileWriter writer = new FileWriter(archivo)) {
            // Encabezado
            writer.write("Fecha,Libro,Cantidad,Precio Unitario,Total\n");

            for (DetalleVenta detalle : lista) {
                String fecha = detalle.getFechaVenta().toString();
                String titulo = detalle.getLibro().getTitulo().replace(",", ""); // por seguridad
                int cantidad = detalle.getCantidad();
                double precio = detalle.getLibro().getPrecio();
                double total = cantidad * precio;

                writer.write(String.format("%s,%s,%d,%.2f,%.2f\n", fecha, titulo, cantidad, precio, total));
            }

            JOptionPane.showMessageDialog(null, "Reporte de libros vendidos exportado correctamente como CSV.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar CSV: " + e.getMessage());
        }
    }

    public static void exportarLibrosVendidosComoJSON(List<DetalleVenta> lista) {

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de libros vendidos como JSON");

        int resultado = selector.showSaveDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".json")) {
            archivo = new File(archivo.getAbsolutePath() + ".json");
        }

        try (FileWriter writer = new FileWriter(archivo)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>) (date, type, context) -> new com.google.gson.JsonPrimitive(date.toString()))
                    .create();
            gson.toJson(lista, writer);
            JOptionPane.showMessageDialog(null, "Reporte de libros vendidos exportado correctamente como JSON.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar JSON: " + e.getMessage());
        }
    }

    public static void exportarLibrosVendidosComoXML(List<DetalleVenta> lista) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de libros vendidos como XML");

        int resultado = selector.showSaveDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".xml")) {
            archivo = new File(archivo.getAbsolutePath() + ".xml");
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("librosVendidos");
            doc.appendChild(rootElement);

            for (DetalleVenta detalle : lista) {
                Element libroElem = doc.createElement("detalle");

                Element fecha = doc.createElement("fecha");
                fecha.appendChild(doc.createTextNode(detalle.getFechaVenta().toString()));
                libroElem.appendChild(fecha);

                Element titulo = doc.createElement("titulo");
                titulo.appendChild(doc.createTextNode(detalle.getLibro().getTitulo()));
                libroElem.appendChild(titulo);

                Element cantidad = doc.createElement("cantidad");
                cantidad.appendChild(doc.createTextNode(String.valueOf(detalle.getCantidad())));
                libroElem.appendChild(cantidad);

                Element precioUnitario = doc.createElement("precioUnitario");
                precioUnitario.appendChild(doc.createTextNode(String.format("%.2f", detalle.getLibro().getPrecio())));
                libroElem.appendChild(precioUnitario);

                Element total = doc.createElement("total");
                double subtotal = detalle.getLibro().getPrecio() * detalle.getCantidad();
                total.appendChild(doc.createTextNode(String.format("%.2f", subtotal)));
                libroElem.appendChild(total);

                rootElement.appendChild(libroElem);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivo);

            transformer.transform(source, result);

            JOptionPane.showMessageDialog(null, "Reporte de libros vendidos exportado correctamente como XML.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al exportar XML: " + e.getMessage());
        }
    }
    
    public static void exportarCuponesUsadosComoCSV(List<CuponUsado> lista) {
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Guardar reporte de cupones usados como CSV");

    int resultado = selector.showSaveDialog(null);
    if (resultado != JFileChooser.APPROVE_OPTION) return;

    File archivo = selector.getSelectedFile();
    if (!archivo.getName().toLowerCase().endsWith(".csv")) {
        archivo = new File(archivo.getAbsolutePath() + ".csv");
    }

    try (FileWriter writer = new FileWriter(archivo)) {
        writer.write("Fecha,Código,Tipo,Valor,Vendedor\n");

        for (CuponUsado cupon : lista) {
            writer.write(String.format(
                "%s,%s,%s,%.2f,%s\n",
                cupon.getFecha().toString(),
                cupon.getCodigo(),
                cupon.getTipo(),
                cupon.getValor(),
                cupon.getVendedor()
            ));
        }

        JOptionPane.showMessageDialog(null, "Cupones exportados correctamente como CSV.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al exportar CSV: " + e.getMessage());
    }
}

    public static void exportarCuponesUsadosComoJSON(List<CuponUsado> lista) {
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Guardar reporte de cupones usados como JSON");

    int resultado = selector.showSaveDialog(null);
    if (resultado != JFileChooser.APPROVE_OPTION) return;

    File archivo = selector.getSelectedFile();
    if (!archivo.getName().toLowerCase().endsWith(".json")) {
        archivo = new File(archivo.getAbsolutePath() + ".json");
    }

    try (FileWriter writer = new FileWriter(archivo)) {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class,
                (com.google.gson.JsonSerializer<LocalDate>)
                (date, type, context) -> new com.google.gson.JsonPrimitive(date.toString()))
            .create();

        gson.toJson(lista, writer);

        JOptionPane.showMessageDialog(null, "Cupones exportados correctamente como JSON.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al exportar JSON: " + e.getMessage());
    }
}
    public static void exportarCuponesUsadosComoXML(List<CuponUsado> lista) {
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Guardar reporte de cupones usados como XML");

    int resultado = selector.showSaveDialog(null);
    if (resultado != JFileChooser.APPROVE_OPTION) return;

    File archivo = selector.getSelectedFile();
    if (!archivo.getName().toLowerCase().endsWith(".xml")) {
        archivo = new File(archivo.getAbsolutePath() + ".xml");
    }

    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element root = doc.createElement("cuponesUsados");
        doc.appendChild(root);

        for (CuponUsado cupon : lista) {
            Element elemento = doc.createElement("cupon");

            Element fecha = doc.createElement("fecha");
            fecha.appendChild(doc.createTextNode(cupon.getFecha().toString()));
            elemento.appendChild(fecha);

            Element codigo = doc.createElement("codigo");
            codigo.appendChild(doc.createTextNode(cupon.getCodigo()));
            elemento.appendChild(codigo);

            Element tipo = doc.createElement("tipo");
            tipo.appendChild(doc.createTextNode(cupon.getTipo()));
            elemento.appendChild(tipo);

            Element valor = doc.createElement("valor");
            valor.appendChild(doc.createTextNode(String.format("%.2f", cupon.getValor())));
            elemento.appendChild(valor);

            Element vendedor = doc.createElement("vendedor");
            vendedor.appendChild(doc.createTextNode(cupon.getVendedor()));
            elemento.appendChild(vendedor);

            root.appendChild(elemento);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivo);
        transformer.transform(source, result);

        JOptionPane.showMessageDialog(null, "Cupones exportados correctamente como XML.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al exportar XML: " + e.getMessage());
    }
}


}
