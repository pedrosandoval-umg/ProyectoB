package umg.proyectob;

import java.util.ArrayList;
import java.util.List;

public class Proyectob {

    
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<LibroenInventario> libros = new ArrayList<>();
    public static List<Cupon> cupones = new ArrayList<>();
    public static List<Direcciones> direcciones = new ArrayList<>();
    
     
    public static void main(String[] args) {

        PuntosExtra.cargarTodo();

        Login v = new Login();
        v.setVisible(true);
            }  
    }