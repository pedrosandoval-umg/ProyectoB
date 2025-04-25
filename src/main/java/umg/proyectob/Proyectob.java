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
    
    public static void main(String[] args) {
        Login v = new Login();
        v.setVisible(true);
    }  
    }