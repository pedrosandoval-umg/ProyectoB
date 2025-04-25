package umg.proyectob;

public class DetalleVenta {
    private int item;
    private LibroenInventario libro;
    private int cantidad;

    public DetalleVenta(int item, LibroenInventario libro, int cantidad) {
        this.item = item;
        this.libro = libro;
        this.cantidad = cantidad;
    }

    public int getItem() {
        return item;
    }

    public LibroenInventario getLibro() {
        return libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return libro.getPrecio();
    }

    public double getSubtotal() {
        return libro.getPrecio() * cantidad;
    }
}
