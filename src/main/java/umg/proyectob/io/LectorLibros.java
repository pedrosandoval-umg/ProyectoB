package umg.proyectob.io;

import umg.proyectob.LibroenInventario;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;

public class LectorLibros {
         
    public static List<LibroenInventario> leerDesdeArchivo(String rutaArchivo) {
    List<LibroenInventario> libros = new ArrayList<>();

    try {
        // Preparamos el lector de archivos
        FileReader reader = new FileReader(rutaArchivo);

        // Gson necesita saber qué tipo de objeto leer
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<LibroenInventario>>(){}.getType();

        // Leemos y convertimos el JSON en lista de objetos Java
        libros = gson.fromJson(reader, tipoLista);

        reader.close();

        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al leer JSON: " + e.getMessage());
            }

    return libros;
        }

    public static List<LibroenInventario> leerConSelector() {
    // Abre una ventana para que el usuario seleccione el archivo JSON
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Seleccionar archivo JSON de libros");

    int resultado = selector.showOpenDialog(null);
    
    // Si el usuario eligió un archivo, lo leemos
    if (resultado == JFileChooser.APPROVE_OPTION) {
        String ruta = selector.getSelectedFile().getAbsolutePath();
        return leerDesdeArchivo(ruta); // Llama al método del paso 1
    }

    // Si el usuario canceló, devolvemos una lista vacía
    return new ArrayList<>();
}

    public static void guardarComoJSON(List<LibroenInventario> libros, String rutaArchivo) {
    try {
        // Creamos una instancia de Gson que indenta el JSON (para que sea legible)
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Abrimos el archivo para escritura
        FileWriter writer = new FileWriter(rutaArchivo);

        // Convertimos la lista de libros a JSON y la escribimos al archivo
        gson.toJson(libros, writer);

        // Cerramos el archivo
        writer.close();

    } catch (Exception e) {
        // Si ocurre un error, mostramos un mensaje al usuario
        JOptionPane.showMessageDialog(null, "Error al guardar JSON: " + e.getMessage());
    }
}

    public static void guardarConSelector(List<LibroenInventario> libros) {
    // Creamos el selector para guardar archivos
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Guardar libros como JSON");

    // Mostramos el diálogo de guardado
    int resultado = selector.showSaveDialog(null);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        // Obtenemos la ruta seleccionada por el usuario
        String ruta = selector.getSelectedFile().getAbsolutePath();

        // Aseguramos que el archivo termine con .json
        if (!ruta.endsWith(".json")) {
            ruta += ".json";
        }

        // Llamamos al método que genera y guarda el JSON
        guardarComoJSON(libros, ruta);
    }
}

   } 