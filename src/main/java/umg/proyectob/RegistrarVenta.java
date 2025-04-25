package umg.proyectob;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class RegistrarVenta extends javax.swing.JFrame {

    private java.util.List<DetalleVenta> carrito = new ArrayList<>();
    
    private Usuario usuarioActual;
    public RegistrarVenta(Usuario usuario) {
        initComponents();
        this.usuarioActual = usuario;
        
        lblNombreUsuario.setText("Vendedor: " + usuarioActual.getNombre()); 
                
        ComboBoxLibros.removeAllItems();
        for (LibroenInventario libro : Proyectob.libros) {
        ComboBoxLibros.addItem(libro.getTitulo());
                        
}
        ComboBoxLibros.addActionListener(e -> actualizarPrecioUnitario());
        txtMonto.addActionListener(e -> actualizarTotal());
    }    
 
    private void actualizarPrecioUnitario() {
    String tituloSeleccionado = (String) ComboBoxLibros.getSelectedItem();
    if (tituloSeleccionado == null) return;

    for (LibroenInventario libro : Proyectob.libros) {
        if (libro.getTitulo().equals(tituloSeleccionado)) {
            lblPriceperUnit.setText("Precio Unitario: Q" + libro.getPrecio());
            break;
        }
    }
}
    private void actualizarTotal() {
    String tituloSeleccionado = (String) ComboBoxLibros.getSelectedItem();
    if (tituloSeleccionado == null) return;

      String cantidadTexto = txtMonto.getText().trim();
    if (cantidadTexto.isEmpty()) return;

    try {
        int cantidad = Integer.parseInt(cantidadTexto);

        for (LibroenInventario libro : Proyectob.libros) {
            if (libro.getTitulo().equals(tituloSeleccionado)) {
                double total = libro.getPrecio() * cantidad;
                lblTotalIndividual.setText("Total a Pagar: Q" + total);
                break;
            }
        }
    } catch (NumberFormatException e) {
        // Si ingresaron texto en vez de numero
        lblTotalIndividual.setText("Cantidad inválida");
    }
}
    private void agregarAlCarrito() {
    String tituloSeleccionado = (String) ComboBoxLibros.getSelectedItem();
    if (tituloSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un libro.");
        return;
    }

    String cantidadTexto = txtMonto.getText().trim();
    if (cantidadTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la cantidad.");
        return;
    }

    int cantidad;
    try {
        cantidad = Integer.parseInt(cantidadTexto);
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Cantidad inválida.");
        return;
    }

    for (LibroenInventario libro : Proyectob.libros) {
        if (libro.getTitulo().equals(tituloSeleccionado)) {
            if (libro.getStock() < cantidad) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente.");
                return;
            }

            int item = carrito.size() + 1;
            carrito.add(new DetalleVenta(item, libro, cantidad));
            libro.setStock(libro.getStock() - cantidad);
            actualizarTablaCarrito();
            actualizarTotalAcumulado();
            txtMonto.setText("");
            return;
        }
    }
}
    private void actualizarTablaCarrito() {
        String[] encabezados = {"Item", "Libro", "Precio Unitario", "Cantidad", "Subtotal"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(encabezados, carrito.size());

        for (int i = 0; i < carrito.size(); i++) {
            DetalleVenta d = carrito.get(i);
            modelo.setValueAt(d.getItem(), i, 0);
            modelo.setValueAt(d.getLibro().getTitulo(), i, 1);
            modelo.setValueAt("Q" + d.getPrecioUnitario(), i, 2);
            modelo.setValueAt(d.getCantidad(), i, 3);
            modelo.setValueAt("Q" + d.getSubtotal(), i, 4);
        }

        tblCart.setModel(modelo);
    }
    private void actualizarTotalAcumulado() {
    double total = 0;
    for (DetalleVenta d : carrito) {
        total += d.getSubtotal();
    }
    lblTotalAllBooks.setText("Total a Pagar: Q" + total);
}
    private void confirmarVenta() {
    if (carrito.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay productos en el carrito.");
        return;
    }

       JOptionPane.showMessageDialog(this, "Venta Confirmada");

    // Limpiar carrito y tabla
    carrito.clear();
    actualizarTablaCarrito();
    lblTotalIndividual.setText("Total a Pagar");
    txtMonto.setText("");
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblInstructions = new javax.swing.JLabel();
        lblLibro = new javax.swing.JLabel();
        ComboBoxLibros = new javax.swing.JComboBox<>();
        lblAmount = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        lblPriceperUnit = new javax.swing.JLabel();
        lblTotalIndividual = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        lblNombreUsuario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        btnAddtoCart = new javax.swing.JButton();
        lblTotalAllBooks = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblInstructions.setText("Ingrese los datos de venta");

        lblLibro.setText("Libro");

        ComboBoxLibros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblAmount.setText("Cantidad");

        lblPriceperUnit.setPreferredSize(new java.awt.Dimension(200, 15));

        lblTotalIndividual.setPreferredSize(new java.awt.Dimension(200, 15));

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnConfirm.setText("Confirmar Venta");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        lblNombreUsuario.setMaximumSize(new java.awt.Dimension(50, 50));
        lblNombreUsuario.setMinimumSize(new java.awt.Dimension(10, 10));
        lblNombreUsuario.setPreferredSize(new java.awt.Dimension(200, 15));

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblCart);

        btnAddtoCart.setText("Agregar");
        btnAddtoCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddtoCartActionPerformed(evt);
            }
        });

        lblTotalAllBooks.setPreferredSize(new java.awt.Dimension(300, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInstructions)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 688, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTotalAllBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnConfirm)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnClose)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLibro)
                        .addGap(18, 18, 18)
                        .addComponent(ComboBoxLibros, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPriceperUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAmount)
                        .addGap(18, 18, 18)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                        .addComponent(btnAddtoCart))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInstructions)
                    .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLibro)
                    .addComponent(ComboBoxLibros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPriceperUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAmount)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddtoCart)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTotalAllBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnConfirm))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
            confirmarVenta();
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
                this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddtoCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddtoCartActionPerformed
            agregarAlCarrito();
    }//GEN-LAST:event_btnAddtoCartActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxLibros;
    private javax.swing.JButton btnAddtoCart;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblInstructions;
    private javax.swing.JLabel lblLibro;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblPriceperUnit;
    private javax.swing.JLabel lblTotalAllBooks;
    private javax.swing.JLabel lblTotalIndividual;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}