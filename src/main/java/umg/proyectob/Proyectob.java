package umg.proyectob;

import java.util.ArrayList;

public class Proyectob {

    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<LibroenInventario> libros = new ArrayList<>();
    public static ArrayList<Cupon> cupones = new ArrayList<>();


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
        
        Usuario ventas2 = new Usuario();
        ventas2.setNombre("Ventas 2");
        ventas2.setUsuario("ventas2");
        ventas2.setPassword("Asd456");
        ventas2.setRol(2); // 
        usuarios.add(ventas2);
        
        Usuario ventas3 = new Usuario();
        ventas3.setNombre("Ventas 3");
        ventas3.setUsuario("ventas3");
        ventas3.setPassword("Asd789");
        ventas3.setRol(2); // 
        usuarios.add(ventas3);
        }
        
    public static void main(String[] args) {
        Login v = new Login();
        v.setVisible(true);
    }  
    }