package umg.proyectob;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JOptionPane;

public class ConsultaLibros extends javax.swing.JFrame {

    public ConsultaLibros() {
        initComponents();
        cargarTabla();
    }

    private void cargarTabla() {
        String[] encabezados = {"Titulo", "Autor", "Genero", "Precio", "Stock"};
        DefaultTableModel tblbooks = new DefaultTableModel(encabezados, Proyectob.libros.size());
        tblBooks.setModel(tblbooks);

        TableModel tabla = tblBooks.getModel();
        for (int i = 0; i < Proyectob.libros.size(); i++) {
            LibroenInventario l = Proyectob.libros.get(i);
            tabla.setValueAt(l.getTitulo(), i, 0);
            tabla.setValueAt(l.getAutor(), i, 1);
            tabla.setValueAt(l.getGenero(), i, 2);
            String precio2 = String.format("Q.%.2f", l.getPrecio());
            tabla.setValueAt(precio2, i, 3);
            tabla.setValueAt(l.getStock(), i, 4);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblBooks = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblBooks.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblBooks);

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete)
                            .addComponent(btnEdit))
                        .addContainerGap(19, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int editar = tblBooks.getSelectedRow();
        if (editar > -1) {
            LibroenInventario l = Proyectob.libros.get(editar);

            String titulo = l.getTitulo();
            String autor = l.getAutor();
            String genero = l.getGenero();
            double precio = l.getPrecio();
            int stock = l.getStock();

            String nuevoTitulo = JOptionPane.showInputDialog(this, "Nuevo titulo:", titulo);
            if (nuevoTitulo == null || nuevoTitulo.trim().isEmpty()) {
                return;
            }

            nuevoTitulo = nuevoTitulo.trim();

            if (!nuevoTitulo.equalsIgnoreCase(titulo)) {
                for (LibroenInventario libro : Proyectob.libros) {
                    if (libro != l && libro.getTitulo().trim().equalsIgnoreCase(nuevoTitulo)) {
                        JOptionPane.showMessageDialog(this, "Ya existe un libro con ese título.");
                        return;
                    }
                }
            }

            String nuevoAutor = JOptionPane.showInputDialog(this, "Nuevo Autor:", autor);
            if (nuevoAutor == null || nuevoAutor.trim().isEmpty()) {
                return;
            }

            String nuevoGenero = JOptionPane.showInputDialog(this, "Nuevo Genero:", genero);
            if (nuevoGenero == null || nuevoGenero.trim().isEmpty()) {
                return;
            }

            String nuevoPrecioStr = JOptionPane.showInputDialog(this, "Nuevo valor:", String.valueOf(precio));
            if (nuevoPrecioStr == null) {
                return;
            }

            double nuevoPrecio;
            try {
                nuevoPrecio = Double.parseDouble(nuevoPrecioStr.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");
                return;
            }

            String nuevoStockStr = JOptionPane.showInputDialog(this, "Nuevo Stock:", String.valueOf(stock));
            if (nuevoStockStr == null) {
                return;
            }

            int nuevoStock;
            try {
                nuevoStock = Integer.parseInt(nuevoStockStr.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");
                return;
            }

            // Actualizar Tabla
            l.setTitulo(nuevoTitulo.trim());
            l.setAutor(nuevoAutor.trim());
            l.setGenero(nuevoGenero.trim());
            l.setPrecio(nuevoPrecio);
            l.setStock(nuevoStock);

            PuntosExtra.guardarTodo(); // ✅ guardar los cambios editados
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.");

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro paraa editar");
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int filaSeleccionada = tblBooks.getSelectedRow();
        if (filaSeleccionada != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar este libro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                Proyectob.libros.remove(filaSeleccionada);
                PuntosExtra.guardarTodo();
                cargarTabla();
                JOptionPane.showMessageDialog(null, "Libro eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor selecciona un libro para eliminar.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBooks;
    // End of variables declaration//GEN-END:variables
}
