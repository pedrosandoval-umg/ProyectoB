package umg.proyectob;

import javax.swing.JOptionPane;
import umg.proyectob.PasswordRequirements;

public class NuevoUsuario extends javax.swing.JFrame {

    public NuevoUsuario() {
        initComponents();
        comboRol.removeAllItems();
        comboRol.addItem("Administrador");
        comboRol.addItem("Vendedor");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFullName = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        btnAddUser = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        comboRol = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFullName.setText("Full Name");

        lblUserName.setText("User Name");

        lblPassword.setText("Password");

        lblRol.setText("Rol");

        txtName.setMinimumSize(new java.awt.Dimension(15, 75));

        txtUser.setToolTipText("");
        txtUser.setMinimumSize(new java.awt.Dimension(15, 75));

        txtPassword.setMinimumSize(new java.awt.Dimension(15, 75));

        btnAddUser.setText("Add User");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFullName)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblUserName)
                        .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblRol, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddUser)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(190, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFullName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUserName)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addComponent(btnAddUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

            
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed
    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
    
    
        Usuario u = new Usuario();
        
        String nombre = txtName.getText();
        String usuario = txtUser.getText();
        String password = txtPassword.getText();
        String rol = comboRol.getSelectedItem().toString();
        
        if (rol.equals("Administrador")){
            u.setRol(1);
            }
        else {
                u.setRol(2);      
                }
  
    // Validar que los campos no estén vacíos
    if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty() || rol.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
        return;
    }

    // Validar formato del password
    if (!PasswordRequirements.validarPassword(password)) {
        JOptionPane.showMessageDialog(this, "La contraseña debe cumplir con una mayúscula, una minúscula,  un número y al menos 6 caracteres.");
        return;
    }

    // Validar que el usuario no exista ya
        for (Usuario existing : Proyectob.usuarios) {
        if (existing.getUsuario().equals(usuario)) {
        JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe.");
        return;
    }
}
        u.setNombre(nombre);
        u.setUsuario(usuario);
        u.setPassword(password);
        Proyectob.usuarios.add(u);
              
        JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.");
    }//GEN-LAST:event_btnAddUserActionPerformed
            
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnClose;
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
