package umg.proyectob;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PuntosExtra {

        public static void guardarTodo() {
        guardarArchivo(Proyectob.usuarios, "usuarios.dat");
        guardarArchivo(Proyectob.libros, "libros.dat");
        guardarArchivo(Proyectob.cupones, "cupones.dat");
         }

        public static void cargarTodo() {
        Proyectob.usuarios = cargarArchivo("usuarios.dat");
        Proyectob.libros = cargarArchivo("libros.dat");
        Proyectob.cupones = cargarArchivo("cupones.dat");
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

    }
