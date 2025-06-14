package umg.proyectob.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import umg.proyectob.Cupon;
import java.time.LocalDate;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LectorCupones {

    public static List<Cupon> leerDesdeArchivo(String nombreArchivo) {
        List<Cupon> cupones = new ArrayList<>();

        try (Stream<String> lineas = Files.lines(Paths.get(nombreArchivo))) {

            // Se omite la primera línea porque se asume que es un encabezado
            lineas.skip(1).forEach(linea -> {
                String[] partes = linea.split("\\|"); // Separamos la línea por el carácter pipe '|'

                if (partes.length == 4) { // Validamos que haya 4 campos: código, valor, tipo, fecha
                    try {
                        String codigo = partes[0].trim(); // Código del cupón
                        double valor = Double.parseDouble(partes[1].trim()); // Valor numérico (puede ser monto o porcentaje)
                        String tipo = partes[2].trim(); // Tipo de descuento: 'porcentaje' o 'monto'
                        String fecha = partes[3].trim(); // Fecha en formato yyyy-MM-dd

                        Cupon cupon = new Cupon(); // Creamos un nuevo objeto Cupon
                        cupon.setCodigo(codigo);
                        cupon.setValor(valor);
                        cupon.setEsPorcentaje(tipo.equalsIgnoreCase("porcentaje")); // Convertimos texto a booleano
                        cupon.setActivo(true); // Se establece como activo por defecto
                        cupon.setFechaVencimiento(LocalDate.parse(fecha)); // Convertimos la fecha a LocalDate

                        cupones.add(cupon); // Agregamos el cupón a la lista

                    } catch (Exception e) {
                        System.err.println("Error en línea: " + linea + " → " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            // Si ocurre un error al abrir o leer el archivo, mostramos un mensaje
            JOptionPane.showMessageDialog(null, "Error al leer cupones: " + e.getMessage());
        }

        return cupones; // Retornamos la lista de cupones leídos
    }

    public static List<Cupon> leerConSelector() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de cupones");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));

        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();
            return leerDesdeArchivo(ruta);
        }

        return new ArrayList<>(); // Retorna lista vacía si se cancela
    }

    public static void guardarEnArchivo(String nombreArchivo, List<Cupon> cupones) {
        List<String> lineas = new ArrayList<>();

        // Agregamos encabezado
        lineas.add("codigo|valor|tipo_descuento|fecha_vencimiento");

        for (Cupon cupon : cupones) {
            String tipo = cupon.isEsPorcentaje() ? "porcentaje" : "monto";
            String linea = String.join("|",
                    cupon.getCodigo(),
                    String.valueOf(cupon.getValor()),
                    tipo,
                    cupon.getFechaVencimiento().toString()
            );
            lineas.add(linea);
        }

        try {
            Files.write(Paths.get(nombreArchivo), lineas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cupones: " + e.getMessage());
        }
    }

    public static boolean guardarConSelector(List<Cupon> cupones) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar archivo de cupones");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

        int resultado = selector.showSaveDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            // Asegura que tenga la extensión .csv
            String ruta = archivo.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".csv")) {
                archivo = new File(ruta + ".csv");
            }

            guardarEnArchivo(archivo.getAbsolutePath(), cupones);
            return true;
        }

        return false;
    }

}
