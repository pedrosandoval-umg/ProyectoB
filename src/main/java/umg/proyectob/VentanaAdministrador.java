
package umg.proyectob;

public class VentanaAdministrador extends javax.swing.JFrame {

    private Usuario usuarioActual;
        public VentanaAdministrador(Usuario usuario) {
            initComponents();
            this.usuarioActual = usuario;
            lblWelcomeAdmin.setText("Bienvenido " + usuarioActual.getNombre()); 
            
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                PuntosExtra.guardarTodo();
                System.out.println("Datos guardados correctamente al salir.");
    }
});

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        lblWelcomeAdmin = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnAddUser = new javax.swing.JButton();
        btnAddBook = new javax.swing.JButton();
        btnCupon = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        menuConsultas = new javax.swing.JMenu();
        optCheckUsers = new javax.swing.JMenuItem();
        OptCheckCupons = new javax.swing.JMenuItem();
        optCheckBooks = new javax.swing.JMenuItem();
        menuReporte = new javax.swing.JMenu();
        optCheckReports = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblWelcomeAdmin.setText("Bienvenido Administrador");

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnAddUser.setText("Crear Usuario");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnAddBook.setText("Crear Libro");
        btnAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookActionPerformed(evt);
            }
        });

        btnCupon.setText("Crear Cupon");
        btnCupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuponActionPerformed(evt);
            }
        });

        menuConsultas.setText("Consultas");

        optCheckUsers.setText("Consultar Usuarios");
        optCheckUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckUsersActionPerformed(evt);
            }
        });
        menuConsultas.add(optCheckUsers);

        OptCheckCupons.setText("Consulta Cupones");
        OptCheckCupons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptCheckCuponsActionPerformed(evt);
            }
        });
        menuConsultas.add(OptCheckCupons);

        optCheckBooks.setText("Consulta Libros");
        optCheckBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckBooksActionPerformed(evt);
            }
        });
        menuConsultas.add(optCheckBooks);

        jMenuBar2.add(menuConsultas);

        menuReporte.setText("Reportes");

        optCheckReports.setText("Reporte de Ventas");
        optCheckReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckReportsActionPerformed(evt);
            }
        });
        menuReporte.add(optCheckReports);

        jMenuBar2.add(menuReporte);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 242, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblWelcomeAdmin, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddUser)
                            .addComponent(btnAddBook)
                            .addComponent(btnCupon))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWelcomeAdmin)
                .addGap(18, 18, 18)
                .addComponent(btnAddUser)
                .addGap(18, 18, 18)
                .addComponent(btnAddBook)
                .addGap(18, 18, 18)
                .addComponent(btnCupon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        NuevoUsuario v = new NuevoUsuario();
        v.setVisible(true);
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void optCheckUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optCheckUsersActionPerformed
        ConsultaUsuarios c = new ConsultaUsuarios();
        c.setVisible(true);
    }//GEN-LAST:event_optCheckUsersActionPerformed

    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookActionPerformed
        NuevoLibro l = new NuevoLibro();
        l.setVisible(true); 
    }//GEN-LAST:event_btnAddBookActionPerformed

    private void optCheckBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optCheckBooksActionPerformed
        ConsultaLibros l = new ConsultaLibros();
        l.setVisible(true);
    }//GEN-LAST:event_optCheckBooksActionPerformed

    private void btnCuponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuponActionPerformed
        NuevoCupon cpn = new NuevoCupon();
        cpn.setVisible(true);
    }//GEN-LAST:event_btnCuponActionPerformed

    private void OptCheckCuponsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptCheckCuponsActionPerformed
        ConsultaCupon cpn = new ConsultaCupon();
        cpn.setVisible(true);
    }//GEN-LAST:event_OptCheckCuponsActionPerformed

    private void optCheckReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optCheckReportsActionPerformed
        ReportedeVentas rdv = new ReportedeVentas();
        rdv.setVisible(true);
    }//GEN-LAST:event_optCheckReportsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem OptCheckCupons;
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCupon;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel lblWelcomeAdmin;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuReporte;
    private javax.swing.JMenuItem optCheckBooks;
    private javax.swing.JMenuItem optCheckReports;
    private javax.swing.JMenuItem optCheckUsers;
    // End of variables declaration//GEN-END:variables
}
