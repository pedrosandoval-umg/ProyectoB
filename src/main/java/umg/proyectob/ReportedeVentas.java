package umg.proyectob;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReportedeVentas extends javax.swing.JFrame {

    public ReportedeVentas() {
        initComponents();
        cargarReporte();
    }

    private void cargarReporte() {
        String[] columnas = {"Factura", "Cliente", "NIT", "Dirección", "Valor Cupon", "Total sin IVA", "Total", "Vendedor", "Fecha"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0);

        for (CompiladoVenta venta : Proyectob.ventas) {         // ✅ Si la venta tiene cupón, obtenemos su valor; si no, usamos 0
            double valorCupon = (venta.getCupon() != null) ? venta.getCupon().getValor() : 0;

            Object[] fila = {
                venta.getNumeroFactura(),
                venta.getNombreCliente(),
                venta.getNit(),
                venta.getDireccion(),
                String.format("Q.%.2f", valorCupon),
                String.format("Q%.2f", venta.getTotalSinIva()),
                String.format("Q%.2f", venta.getTotal()),
                venta.getVendedor().getNombre(),
                venta.getFecha().toString()
            };
            modelo.addRow(fila);
        }

        tblReporteVentas.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblReporteVentas = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblReporteVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblReporteVentas);

        btnClose.setText("Regresar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnExport.setText("Exportar");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setText("Importar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnImport)
                        .addGap(18, 18, 18)
                        .addComponent(btnExport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnExport)
                    .addComponent(btnImport))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // Opciones de formato disponibles
        String[] formatos = {"CSV", "JSON", "XML"};

        // Diálogo para que el usuario elija el formato
        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el formato de exportación:",
                "Exportar Reporte",
                JOptionPane.PLAIN_MESSAGE,
                null,
                formatos,
                "CSV" // opción por defecto
        );

        // Si el usuario seleccionó una opción válida
        if (seleccion != null) {
            switch (seleccion) {
                case "CSV":
                    ExportadorReportes.exportarComoCSV(Proyectob.ventas);
                    break;
                case "JSON":
                    ExportadorReportes.exportarComoJSON(Proyectob.ventas);
                    break;
                case "XML":
                    ExportadorReportes.exportarComoXML(Proyectob.ventas);
                    break;
            }
            JOptionPane.showMessageDialog(this, "Reporte exportado como " + seleccion + " correctamente.");

        }
    }//GEN-LAST:event_btnExportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImport;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReporteVentas;
    // End of variables declaration//GEN-END:variables
}
