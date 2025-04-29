package umg.proyectob;

import java.util.ArrayList;
import java.util.List;

public class Proyectob {

    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<LibroenInventario> libros = new ArrayList<>();
    public static List<Cupon> cupones = new ArrayList<>();


    static {
        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setUsuario("admin");
        admin.setPassword("admin");
        admin.setRol(1); // 1 = administrador
        usuarios.add(admin);
        }
    
        static {
        Usuario ventas1 = new Usuario();
        ventas1.setNombre("Ventas 1");
        ventas1.setUsuario("ventas1");
        ventas1.setPassword("Asd123");
        ventas1.setRol(2); // 
        usuarios.add(ventas1);
        }
        
    public static void main(String[] args) {
        PuntosExtra.cargarTodo();
            Login v = new Login();
            v.setVisible(true);
            }  
    }