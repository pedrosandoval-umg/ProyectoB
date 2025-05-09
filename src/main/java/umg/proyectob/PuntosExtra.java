package umg.proyectob;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PuntosExtra {
    
        public static List<Usuario> usuarios = new ArrayList<>();

        public static void guardarTodo() {
        guardarArchivo(Proyectob.usuarios, "usuarios.dat");
        guardarArchivo(Proyectob.libros, "libros.dat");
        guardarArchivo(Proyectob.cupones, "cupones.dat");
        guardarArchivo(Proyectob.ventas, "ventas.dat");
         }

        public static void cargarTodo() {
        cargarUsuariosDesdeArchivo();  // Aquí se asegura la creación de admin
        // Proyectob.usuarios = cargarArchivo("usuarios.dat");
        Proyectob.libros = cargarArchivo("libros.dat");
        Proyectob.cupones = cargarArchivo("cupones.dat");
        Proyectob.ventas = cargarArchivo("ventas.dat");
        }


    public static <T> void guardarArchivo(List<T> lista, String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(lista);
            System.out.println("Guardado exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo " + rutaArchivo + ": " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> List<T> cargarArchivo(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar archivo " + rutaArchivo + ": " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public static void cargarUsuariosDesdeArchivo() {
    File archivo = new File("usuarios.dat");

    if (archivo.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            usuarios = (ArrayList<Usuario>) ois.readObject();  // usa la de la clase
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer archivo de usuarios: " + e.getMessage());
            usuarios = new ArrayList<>();
        }
    } else {
        usuarios = new ArrayList<>();
        }
    if (usuarios.isEmpty()) {
        Usuario admin = new Usuario("Administrador", "admin", "admin", 1);
         usuarios.add(admin);
        }
        Proyectob.usuarios = usuarios;
        }

    }
