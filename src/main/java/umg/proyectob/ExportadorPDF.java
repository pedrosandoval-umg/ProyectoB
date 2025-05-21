package umg.proyectob;

import umg.proyectob.CompiladoVenta;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ExportadorPDF {
    
    public static void exportarComoPDF(List<CompiladoVenta> ventas) {
    JFileChooser selector = new JFileChooser(); // Diálogo para seleccionar ruta
    selector.setDialogTitle("Guardar reporte como PDF");

    int resultado = selector.showSaveDialog(null); // Mostrar diálogo
    if (resultado == JFileChooser.APPROVE_OPTION) { // Verifica si se presionó Guardar
        String ruta = selector.getSelectedFile().getAbsolutePath(); // Obtiene ruta
        if (!ruta.endsWith(".pdf")) {
            ruta += ".pdf"; // Asegura la extensión .pdf
        }

        Document doc = new Document(); // Crear documento PDF

        try {
            PdfWriter.getInstance(doc, new java.io.FileOutputStream(ruta)); // Asociar escritor y ruta
            doc.open(); // Abrir documento para escribir

            doc.add(new Paragraph("REPORTE DE VENTAS"));
            doc.add(new Paragraph(" ")); // Línea en blanco

            PdfPTable tabla = new PdfPTable(9); // Crear tabla con 9 columnas
            tabla.addCell("Factura");
            tabla.addCell("Cliente");
            tabla.addCell("NIT");
            tabla.addCell("Dirección");
            tabla.addCell("Valor Cupón");
            tabla.addCell("Total sin IVA");
            tabla.addCell("Total");
            tabla.addCell("Vendedor");
            tabla.addCell("Fecha");

            for (CompiladoVenta venta : ventas) {
                double valorCupon = (venta.getCupon() != null) ? venta.getCupon().getValor() : 0;

                tabla.addCell(venta.getNumeroFactura());
                tabla.addCell(venta.getNombreCliente());
                tabla.addCell(venta.getNit());
                tabla.addCell(venta.getDireccion());
                tabla.addCell(String.format("Q%.2f", valorCupon));
                tabla.addCell(String.format("Q%.2f", venta.getTotalSinIva()));
                tabla.addCell(String.format("Q%.2f", venta.getTotal()));
                tabla.addCell(venta.getVendedor().getNombre());
                tabla.addCell(venta.getFecha().toString());
            }

            doc.add(tabla); // Agregar tabla al documento
            doc.close(); // Cerrar el documento

            JOptionPane.showMessageDialog(null, "Reporte exportado como PDF exitosamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al exportar PDF: " + e.getMessage());
        }
    }
}

    
}
