package umg.proyectob;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JOptionPane;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

public class ConsultaCupon extends javax.swing.JFrame {

    public ConsultaCupon() {
        initComponents();
        cargarTabla();
    }

    private void cargarTabla() {
        String[] encabezados = {"Codigo", "Descripcion", "Valor", "Tipo", "Activo", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(encabezados, Proyectob.cupones.size());
        tblCupons.setModel(modelo);

        TableModel tabla = tblCupons.getModel();
        for (int i = 0; i < Proyectob.cupones.size(); i++) {
            Cupon cpn = Proyectob.cupones.get(i);
            tabla.setValueAt(cpn.getCodigo(), i, 0);
            tabla.setValueAt(cpn.getDescripcion(), i, 1);
            tabla.setValueAt(String.format("Q.%.2f", cpn.getValor()), i, 2);
            tabla.setValueAt(cpn.isEsPorcentaje() ? "Porcentaje" : "Monto fijo", i, 3);
            tabla.setValueAt(cpn.isActivo() ? "Sí" : "No", i, 4);
            tabla.setValueAt(cpn.getFechaVencimiento() != null ? cpn.getFechaVencimiento().toString() : "Sin fecha", i, 5);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCupons = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblCupons.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCupons);

        btnEdit.setText("Editar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Borrar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnClose))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addGap(30, 30, 30)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int modificar = tblCupons.getSelectedRow();
        if (modificar > -1) {
        Cupon cpn = Proyectob.cupones.get(modificar);

        String codigo = cpn.getCodigo();
        String descripcion = cpn.getDescripcion();
        double valor = cpn.getValor();
        boolean porcentaje = cpn.isEsPorcentaje();
        boolean activo = cpn.isActivo();
        String fechaActual = cpn.getFechaVencimiento() != null ? cpn.getFechaVencimiento().toString() : "";


        
        String nuevoCodigo = JOptionPane.showInputDialog(this, "Nuevo código:", codigo);
        if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) return;

        // Validar que el nuevo código no exista en otro cupón
        for (int i = 0; i < Proyectob.cupones.size(); i++) {
            if (i != modificar && Proyectob.cupones.get(i).getCodigo().equalsIgnoreCase(nuevoCodigo.trim())) {
                JOptionPane.showMessageDialog(this, "El código ingresado ya existe en otro cupón.");
                return;
            }
        }

        String nuevaDescripcion = JOptionPane.showInputDialog(this, "Nueva descripción:", descripcion);
        if (nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) return;

        String nuevoValorStr = JOptionPane.showInputDialog(this, "Nuevo valor:", String.valueOf(valor));
        if (nuevoValorStr == null) return;

        double nuevoValor;
        try {
            nuevoValor = Double.parseDouble(nuevoValorStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
            return;
        }

        String[] opcionesTipo = {"Porcentaje", "Monto fijo"};
        String nuevoTipo = (String) JOptionPane.showInputDialog(this, "Tipo:", "Editar tipo", JOptionPane.PLAIN_MESSAGE, null, opcionesTipo, porcentaje ? "Porcentaje" : "Monto fijo");
        if (nuevoTipo == null) return;

        int confirmActivo = JOptionPane.showConfirmDialog(this, "¿El cupón esta activo?", "Activo", JOptionPane.YES_NO_OPTION);
        
        String nuevaFechaStr = JOptionPane.showInputDialog(this, "Nueva fecha (yyyy-MM-dd):", fechaActual);
            if (nuevaFechaStr == null || nuevaFechaStr.trim().isEmpty()) return;

            try {
                cpn.setFechaVencimiento(LocalDate.parse(nuevaFechaStr.trim()));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa yyyy-MM-dd");
                return;
            }


        // Actualizar el cupón
        cpn.setCodigo(nuevoCodigo.trim());
        cpn.setDescripcion(nuevaDescripcion.trim());
        cpn.setValor(nuevoValor);
        cpn.setEsPorcentaje(nuevoTipo.equals("Porcentaje"));
        cpn.setActivo(confirmActivo == JOptionPane.YES_OPTION);
        cpn.setFechaVencimiento(LocalDate.parse(nuevaFechaStr.trim()));

        cargarTabla();
        JOptionPane.showMessageDialog(this, "Cupón actualizado correctamente.");

    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un cupón a modificar");
    }
    
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int filaSeleccionada = tblCupons.getSelectedRow();
        if (filaSeleccionada != -1) {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar este cupón?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            Proyectob.cupones.remove(filaSeleccionada);
            PuntosExtra.guardarTodo();
            cargarTabla();
            JOptionPane.showMessageDialog(null, "Cupón eliminado correctamente.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Por favor selecciona un cupón para eliminar.");
    }
    }//GEN-LAST:event_btnDeleteActionPerformed
  


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCupons;
    // End of variables declaration//GEN-END:variables
}
