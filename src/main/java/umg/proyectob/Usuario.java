package umg.proyectob;
import java.io.Serializable;
public class Usuario implements Serializable {

    private String nombre;
    private String usuario;
    private int rol;
    private String password;
               
        public Usuario() {
        // Constructor vacío requerido por Serializable
    }

    public Usuario(String nombre, String usuario, String contraseña, int rol) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = contraseña;
        this.rol = rol;
    }
    
// Getter y Setter de Usuario
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

// Getter y Setter de Nombre
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

// Getter y Setter de Rol   
    public int getRol() {
        return rol;
    }
    public void setRol(int rol) {
        this.rol = rol;
    }

// Getter y Setter de Password 
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
   
}