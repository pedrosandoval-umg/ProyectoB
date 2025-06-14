package umg.proyectob;

import umg.proyectob.io.LectorCupones;
import umg.proyectob.io.LectorUsuarios;
import umg.proyectob.io.LectorLibros;

import javax.swing.JOptionPane;
import java.util.List;


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
        btnCargarUsuarios = new javax.swing.JButton();
        btnGuardarUsuarios = new javax.swing.JButton();
        btnGuardarLibros = new javax.swing.JButton();
        btnCargarLibros = new javax.swing.JButton();
        btnCargarCupones = new javax.swing.JButton();
        btnGuardarCupones = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        menuConsultas = new javax.swing.JMenu();
        optCheckUsers = new javax.swing.JMenuItem();
        optCheckBooks = new javax.swing.JMenuItem();
        OptCheckCupons = new javax.swing.JMenuItem();
        menuReporte = new javax.swing.JMenu();
        optCheckReports = new javax.swing.JMenuItem();
        optCheckBooksSold = new javax.swing.JMenuItem();
        optCheckCuponsUsed = new javax.swing.JMenuItem();

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

        btnCargarUsuarios.setText("Cargar");
        btnCargarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarUsuariosActionPerformed(evt);
            }
        });

        btnGuardarUsuarios.setText("Guardar");
        btnGuardarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuariosActionPerformed(evt);
            }
        });

        btnGuardarLibros.setText("Guardar");
        btnGuardarLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarLibrosActionPerformed(evt);
            }
        });

        btnCargarLibros.setText("Cargar");
        btnCargarLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarLibrosActionPerformed(evt);
            }
        });

        btnCargarCupones.setText("Cargar");
        btnCargarCupones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarCuponesActionPerformed(evt);
            }
        });

        btnGuardarCupones.setText("Guardar");
        btnGuardarCupones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCuponesActionPerformed(evt);
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

        optCheckBooks.setText("Consulta Libros");
        optCheckBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckBooksActionPerformed(evt);
            }
        });
        menuConsultas.add(optCheckBooks);

        OptCheckCupons.setText("Consulta Cupones");
        OptCheckCupons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptCheckCuponsActionPerformed(evt);
            }
        });
        menuConsultas.add(OptCheckCupons);

        jMenuBar2.add(menuConsultas);

        menuReporte.setText("Reportes");

        optCheckReports.setText("Reporte de Ventas");
        optCheckReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckReportsActionPerformed(evt);
            }
        });
        menuReporte.add(optCheckReports);

        optCheckBooksSold.setText("Reporte de Libros");
        optCheckBooksSold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckBooksSoldActionPerformed(evt);
            }
        });
        menuReporte.add(optCheckBooksSold);

        optCheckCuponsUsed.setText("Reporte de Cupones");
        optCheckCuponsUsed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optCheckCuponsUsedActionPerformed(evt);
            }
        });
        menuReporte.add(optCheckCuponsUsed);

        jMenuBar2.add(menuReporte);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCupon, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddBook, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCargarCupones)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarCupones))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnCargarLibros)
                            .addGap(18, 18, 18)
                            .addComponent(btnGuardarLibros))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnCargarUsuarios)
                            .addGap(18, 18, 18)
                            .addComponent(btnGuardarUsuarios))))
                .addContainerGap(101, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnClose)
                    .addComponent(lblWelcomeAdmin))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblWelcomeAdmin)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnCargarUsuarios)
                    .addComponent(btnGuardarUsuarios))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddBook)
                    .addComponent(btnCargarLibros)
                    .addComponent(btnGuardarLibros))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCupon)
                    .addComponent(btnCargarCupones)
                    .addComponent(btnGuardarCupones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
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
        // TODO add your handling code here:
        ReportedeVentas rdv = new ReportedeVentas();
        rdv.setVisible(true);
    }//GEN-LAST:event_optCheckReportsActionPerformed

    private void btnCargarCuponesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarCuponesActionPerformed
        List<Cupon> nuevos = LectorCupones.leerConSelector();
        Proyectob.cupones.addAll(nuevos);
        JOptionPane.showMessageDialog(this, "Cupones cargados correctamente.");
        PuntosExtra.guardarArchivo(Proyectob.cupones, "cupones.dat"); // << Esto asegura persistencia inmediata

    }//GEN-LAST:event_btnCargarCuponesActionPerformed

    private void btnGuardarCuponesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCuponesActionPerformed
        System.out.println("Guardando " + Proyectob.cupones.size() + " cupones...");
        LectorCupones.guardarConSelector(Proyectob.cupones);
        JOptionPane.showMessageDialog(this, "Cupones guardados correctamente.");
    
    }//GEN-LAST:event_btnGuardarCuponesActionPerformed

    private void btnCargarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarUsuariosActionPerformed
        List<Usuario> nuevos = LectorUsuarios.leerConSelector();
        Proyectob.usuarios.addAll(nuevos); // Agrega los nuevos usuarios a la lista general
        PuntosExtra.guardarArchivo(Proyectob.usuarios, "usuarios.dat"); // Persistencia en binario
        JOptionPane.showMessageDialog(this, "Usuarios cargados correctamente.");
    }//GEN-LAST:event_btnCargarUsuariosActionPerformed

    private void btnGuardarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuariosActionPerformed
        System.out.println("Guardando " + Proyectob.usuarios.size() + " usuarios...");
        LectorUsuarios.guardarConSelector(Proyectob.usuarios); // Guarda como XML editable
        JOptionPane.showMessageDialog(this, "Usuarios exportados correctamente.");
    }//GEN-LAST:event_btnGuardarUsuariosActionPerformed

    private void btnCargarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarLibrosActionPerformed
        List<LibroenInventario> nuevos = LectorLibros.leerConSelector();
        Proyectob.libros.addAll(nuevos);
        PuntosExtra.guardarArchivo(Proyectob.libros, "libros.dat");
        JOptionPane.showMessageDialog(this, "Libros cargados exitosamente.");
    }//GEN-LAST:event_btnCargarLibrosActionPerformed

    private void btnGuardarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarLibrosActionPerformed
        // TODO add your handling code here:
        LectorLibros.guardarConSelector(Proyectob.libros);
        JOptionPane.showMessageDialog(this, "Usuarios exportados correctamente.");
    }//GEN-LAST:event_btnGuardarLibrosActionPerformed

    private void optCheckCuponsUsedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optCheckCuponsUsedActionPerformed
        // TODO add your handling code here:
        ReportedeCupones rdc = new ReportedeCupones();
        rdc.setVisible(true);
    }//GEN-LAST:event_optCheckCuponsUsedActionPerformed

    private void optCheckBooksSoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optCheckBooksSoldActionPerformed
        // TODO add your handling code here:
        ReportedeLibros rdl = new ReportedeLibros();
        rdl.setVisible(true);
    }//GEN-LAST:event_optCheckBooksSoldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem OptCheckCupons;
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnCargarCupones;
    private javax.swing.JButton btnCargarLibros;
    private javax.swing.JButton btnCargarUsuarios;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCupon;
    private javax.swing.JButton btnGuardarCupones;
    private javax.swing.JButton btnGuardarLibros;
    private javax.swing.JButton btnGuardarUsuarios;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel lblWelcomeAdmin;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuReporte;
    private javax.swing.JMenuItem optCheckBooks;
    private javax.swing.JMenuItem optCheckBooksSold;
    private javax.swing.JMenuItem optCheckCuponsUsed;
    private javax.swing.JMenuItem optCheckReports;
    private javax.swing.JMenuItem optCheckUsers;
    // End of variables declaration//GEN-END:variables
}
