package umg.proyectob;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; // <-- Para formatear la fecha y crear el IDFactura

public class DetalleVenta implements Serializable {
    
    private String IDFactura;
    private String ID_NIT;  
    private String ID_NombreCliente;
    private String ID_Direccion;  
    private int item;  
    private LibroenInventario libro;
    private int cantidad;
    private String ID_Vendedor;
    private LocalDate fechaVenta;

    public DetalleVenta(String ID_NIT, String ID_NombreCliente, String ID_Direccion, int item, LibroenInventario libro, int cantidad, LocalDate fechaVenta, String ID_Vendedor) {
        this.ID_NIT = ID_NIT;
        this.ID_NombreCliente = ID_NombreCliente;
        this.ID_Direccion = ID_Direccion;
        this.item = item;
        this.libro = libro;
        this.cantidad = cantidad;
        this.fechaVenta = fechaVenta;
        this.ID_Vendedor = ID_Vendedor;
        this.IDFactura = generarIDFactura();
    }

    
    private String generarIDFactura() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        return fechaVenta.format(formatter);
    }
    
    // Getters
    
    public String getIDFactura() {
        return IDFactura;
    }
    public String getID_NIT() {
        return ID_NIT;
    }
    public String getID_NombreCliente() {
        return ID_NombreCliente;
    }
    public String getID_Direccion() {
        return ID_Direccion;
    }
    public String getID_Vendedor() {
    return ID_Vendedor;
    }
    public int getItem() {
        return item;
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
    public LocalDate getFechaVenta() {
        return fechaVenta;
    }
    public LibroenInventario getLibro() {
        return libro;
    }
}
