package umg.proyectob;

import javax.swing.JOptionPane;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;


public class RegistrarVenta extends javax.swing.JFrame {

    private List<DetalleVenta> carrito = new ArrayList<>();
    private Usuario usuarioActual;
    private String numeroFacturaGenerado;

    public RegistrarVenta(Usuario usuario) {
        initComponents();
        
        this.usuarioActual = usuario;
        lblNombreUsuario.setText("Vendedor: " + usuarioActual.getNombre());
        this.numeroFacturaGenerado = generarIDFactura();
            lblID_Factura.setText("ID Factura: " + numeroFacturaGenerado);
    
        ComboBoxLibros.removeAllItems();
        for (LibroenInventario libro : Proyectob.libros) {
        ComboBoxLibros.addItem(libro.getTitulo());
}
        ComboBoxLibros.addActionListener(e -> actualizarPrecioUnitario());
        txtMonto.addActionListener(e -> actualizarTotal());
        
        ComboCupones.removeAllItems();
        System.out.println("Cupones cargados: " + Proyectob.cupones.size());      
        for (Cupon c : Proyectob.cupones) {
            if (c.isActivo() && (c.getFechaVencimiento() == null || !c.getFechaVencimiento().isBefore(LocalDate.now()))) {
            ComboCupones.addItem(c.getCodigo());
                }
            }
    }    
    private LocalDate validarFechaIngresada(String textoFecha) {
        List<DateTimeFormatter> formatos = Arrays.asList(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("yy/MM/dd")  // opcional
    );

    for (DateTimeFormatter formatter : formatos) {
        try {
            return LocalDate.parse(textoFecha, formatter);
        } catch (DateTimeParseException e) {
            // Probar con el siguiente formato
        }
    }

    JOptionPane.showMessageDialog(this,
        "Formato de fecha inválido. Usa por ejemplo: 2025-05-03, 03/05/2025, o 24/12/31");
    return null;
    }
    private String generarIDFactura() {
    java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ddMMyyHHmmss");
    return ahora.format(formatter);
}
    private void TotalSinIva() {
    double total = 0;
    for (DetalleVenta d : carrito) {
        total += d.getSubtotal();
    }

    double sinIVA = total / 1.12;
    sinIVA = Math.round(sinIVA * 100.0) / 100.0; // Redondeo a 2 decimales

    lblSinIVA.setText("Total sin IVA: Q" + String.format("%.2f", sinIVA));

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
        lblTotalIndividual.setText("Cantidad inválida");
    }
}
    
    private void agregarAlCarrito() {
            // Validar fecha
    String fechaTexto = txtDate.getText().trim();
    LocalDate fechaVenta = validarFechaIngresada(fechaTexto);
    if (fechaVenta == null) return;
    
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

    String nitCliente = txtID_NIT.getText().trim();
    String nombreCliente = txtNombre_Cliente.getText().trim();
    String direccionCliente = txtAddress.getText().trim();

    if (nitCliente.isEmpty() || nombreCliente.isEmpty() || direccionCliente.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Complete los datos del cliente (NIT, Nombre, Direccion).");
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
            carrito.add(new DetalleVenta(nitCliente,nombreCliente,direccionCliente,item,libro,cantidad,fechaVenta,usuarioActual.getUsuario()));
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
    lblTotalAllBooks.setText("Total a Pagar: Q" + String.format("%.2f", total));
    TotalSinIva();       
}
    private void confirmarVenta() {
        
    String fechaTexto = txtDate.getText().trim();
        LocalDate fechaVenta = validarFechaIngresada(fechaTexto);
        if (fechaVenta == null) return;
    
        if (carrito.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay productos en el carrito.");
        return;
    }
        
        
        String numeroFactura = numeroFacturaGenerado;
        // Obtener datos del cliente
        String nombreCliente = txtNombre_Cliente.getText().trim();
        String nit = txtID_NIT.getText().trim();
        String direccion = txtAddress.getText().trim();

        // Copiar los detalles del carrito
        List<DetalleVenta> detallesActuales = new ArrayList<>(carrito);

        // Calcular totales
        double totalSinIva = 0;
        for (DetalleVenta d : carrito) {
            totalSinIva += d.getSubtotal();
        }
        totalSinIva = Math.round(totalSinIva / 1.12 * 100.0) / 100.0;
        double totalFinal = (cuponAplicado == null) ? totalSinIva * 1.12 : totalConDescuento;

        // Crear la venta
        CompiladoVenta venta = new CompiladoVenta(
            numeroFactura,
            nombreCliente,
            nit,
            direccion,
            totalSinIva,
            totalFinal,
            usuarioActual,
            fechaVenta,
            detallesActuales
        );
        venta.setCupon(cuponAplicado); // ✅ << aquí se guarda el cupón usado
        // Guardar en lista global
        Proyectob.ventas.add(venta);
        PuntosExtra.guardarArchivo(Proyectob.ventas, "ventas.dat");


       JOptionPane.showMessageDialog(this, "Venta Exitosa");
       PuntosExtra.guardarArchivo(carrito, "Reporte_Ventas.dat");

    // Limpiar carrito y tabla
    carrito.clear();
    actualizarTablaCarrito();
    lblTotalIndividual.setText("Total a Pagar");
    txtMonto.setText("");
    }
    
    private Cupon cuponAplicado = null;
    private double totalConDescuento = 0.00;

    
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
        lblID_NIT = new javax.swing.JLabel();
        txtID_NIT = new javax.swing.JTextField();
        lblNombre_Cliente = new javax.swing.JLabel();
        txtNombre_Cliente = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        lblID_Factura = new javax.swing.JLabel();
        lblSinIVA = new javax.swing.JLabel();
        btnAPlicarCupon = new javax.swing.JButton();
        lblTotConDescuento = new javax.swing.JLabel();
        ComboCupones = new javax.swing.JComboBox<>();

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

        lblTotalAllBooks.setOpaque(true);
        lblTotalAllBooks.setPreferredSize(new java.awt.Dimension(300, 15));

        lblID_NIT.setText("NIT");

        lblNombre_Cliente.setText("Nombre Cliente");

        lblAddress.setText("Direccion");

        lblDate.setText("Fecha");

        lblID_Factura.setText(" ");

        btnAPlicarCupon.setText("Aplicar Cupon");
        btnAPlicarCupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAPlicarCuponActionPerformed(evt);
            }
        });

        lblTotConDescuento.setOpaque(true);
        lblTotConDescuento.setPreferredSize(new java.awt.Dimension(115, 15));

        ComboCupones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLibro)
                        .addGap(18, 18, 18)
                        .addComponent(ComboBoxLibros, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblID_NIT)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtID_NIT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblID_Factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(lblNombre_Cliente)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombre_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPriceperUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblInstructions)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(lblAddress)
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblAmount)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblTotalIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblDate)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btnAddtoCart))
                                        .addGap(20, 20, 20))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboCupones, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAPlicarCupon)
                                .addGap(825, 825, 825)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnConfirm)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnClose))
                                    .addComponent(lblTotConDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotalAllBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblSinIVA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblInstructions)
                        .addComponent(lblID_Factura)))
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblID_NIT)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNombre_Cliente)
                        .addComponent(txtNombre_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAddress)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDate)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtID_NIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ComboBoxLibros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLibro)
                        .addComponent(lblPriceperUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAmount)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddtoCart))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(lblSinIVA)
                .addGap(18, 18, 18)
                .addComponent(lblTotalAllBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblTotConDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirm)
                    .addComponent(btnClose)
                    .addComponent(btnAPlicarCupon)
                    .addComponent(ComboCupones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnAPlicarCuponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAPlicarCuponActionPerformed
        // TODO add your handling code here:
       if (cuponAplicado != null) {
        JOptionPane.showMessageDialog(this, "Ya se ha aplicado un cupón: " + cuponAplicado.getCodigo());
        return;
    }

    String codigoSeleccionado = (String) ComboCupones.getSelectedItem();
    if (codigoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un cupón.");
        return;
    }

    Cupon cupon = Proyectob.cupones.stream()
        .filter(c -> c.getCodigo().equalsIgnoreCase(codigoSeleccionado))
        .findFirst()
        .orElse(null);

    if (cupon == null) {
        JOptionPane.showMessageDialog(this, "Error interno: Cupón no encontrado.");
        return;
    }

    double total = 0;
    for (DetalleVenta d : carrito) {
        total += d.getSubtotal();
    }

    double descuento = cupon.isEsPorcentaje()
        ? total * (cupon.getValor() / 100)
        : cupon.getValor();

    totalConDescuento = Math.max(0, total - descuento);
    cuponAplicado = cupon;

    lblTotConDescuento.setText("Total con descuento: Q" + String.format("%.2f", totalConDescuento));
    JOptionPane.showMessageDialog(this, "Cupón aplicado exitosamente.");      
           
    }//GEN-LAST:event_btnAPlicarCuponActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxLibros;
    private javax.swing.JComboBox<String> ComboCupones;
    private javax.swing.JButton btnAPlicarCupon;
    private javax.swing.JButton btnAddtoCart;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblID_Factura;
    private javax.swing.JLabel lblID_NIT;
    private javax.swing.JLabel lblInstructions;
    private javax.swing.JLabel lblLibro;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblNombre_Cliente;
    private javax.swing.JLabel lblPriceperUnit;
    private javax.swing.JLabel lblSinIVA;
    private javax.swing.JLabel lblTotConDescuento;
    private javax.swing.JLabel lblTotalAllBooks;
    private javax.swing.JLabel lblTotalIndividual;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtID_NIT;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombre_Cliente;
    // End of variables declaration//GEN-END:variables
}