package umg.proyectob;
import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class LibroenInventario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String autor;
    private String genero;
    private double precio;
    @SerializedName("cantidad")
    private int stock;  

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}    