package umg.proyectob;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import umg.proyectob.PasswordRequirements;


public class ConsultaUsuarios extends javax.swing.JFrame {

    public ConsultaUsuarios() {
        initComponents();
        cargarTabla();
        }
          
    private void cargarTabla() {
        String[] encabezados = {"Nombre","Usuario","Rol","Password"};
        DefaultTableModel tblbooks = new DefaultTableModel(encabezados, Proyectob.usuarios.size());
        tblUsers.setModel(tblbooks);
        
        TableModel tabla = tblUsers.getModel();    
        for (int i = 0; i < Proyectob.usuarios.size(); i++) {
            Usuario u = Proyectob.usuarios.get(i);
            tabla.setValueAt(u.getNombre(), i, 0);
            tabla.setValueAt(u.getUsuario(), i, 1);
            tabla.setValueAt(u.getRol(), i, 2);
            tabla.setValueAt(u.getPassword(), i, 3);            
        }      
    }    
    
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        bntClose = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblUsers);

        bntClose.setText("Cerrar");
        bntClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCloseActionPerformed(evt);
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntClose)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bntClose))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCloseActionPerformed
            this.dispose();
    }//GEN-LAST:event_bntCloseActionPerformed
   
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int editar = tblUsers.getSelectedRow();
        if (editar > -1) {
            
        Usuario u = Proyectob.usuarios.get(editar);
        String Nombre = u.getNombre();
        String Usuario = u.getUsuario();
        String Password = u.getPassword();
        int Rol = u.getRol();
        
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo Nombre:", Nombre);
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;
        
        String nuevoUsuario = JOptionPane.showInputDialog(this, "Nuevo Usuario:", Usuario);
        if (nuevoUsuario == null || nuevoUsuario.trim().isEmpty()) return;
        
             for (int i = 0; i < Proyectob.usuarios.size(); i++) {
                if (i != editar && Proyectob.usuarios.get(i).getUsuario().equalsIgnoreCase(nuevoUsuario.trim())) {
                    JOptionPane.showMessageDialog(this, "El Usuario ingresado ya existe.");
                return;
            }
        }
       
        String nuevoPassword;
            boolean passwordValida = false;
            do {
                nuevoPassword = JOptionPane.showInputDialog(this, "Nuevo Password:", Password);
                if (nuevoPassword == null) return; // Cancelación directa
                if (!PasswordRequirements.validarPassword(nuevoPassword.trim())) {
                    JOptionPane.showMessageDialog(this, 
                    "La contraseña debe tener al menos 6 caracteres, una letra mayúscula, una minúscula y un número.",
                    "Contraseña inválida", JOptionPane.ERROR_MESSAGE);
                } else {
                    passwordValida = true;
                }
            } while (!passwordValida);
        
        String nuevoRolStr = JOptionPane.showInputDialog(this, "Nuevo Rol:", String.valueOf(Rol));
        if (nuevoRolStr == null) return;
        
        int nuevoRol;
        try {
            nuevoRol = Integer.parseInt(nuevoRolStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
            return;
        }
        
        
        u.setNombre(nuevoNombre);
        u.setUsuario(nuevoUsuario);
        u.setPassword(nuevoPassword);
        u.setRol(nuevoRol);
        
        cargarTabla();
        JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");

    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un Usuario para editar");
    }            
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int filaSeleccionada = tblUsers.getSelectedRow();
        if (filaSeleccionada != -1) {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar este Usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            Proyectob.usuarios.remove(filaSeleccionada);
            cargarTabla();
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor selecciona un usuario para eliminar.");
       
        }
        
        
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsers;
    // End of variables declaration//GEN-END:variables
}
