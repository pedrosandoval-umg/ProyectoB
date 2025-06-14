package umg.proyectob;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class CompiladoVenta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String numeroFactura;
    private String nombreCliente;
    private String nit;
    private String direccion;
    private Cupon cupon;
    private double totalSinIva;
    private double total;
    private Usuario vendedor; 
    private LocalDate fecha;
    private List<DetalleVenta> detalles;
    
    public CompiladoVenta(String numeroFactura, String nombreCliente, String nit, String direccion, double totalSinIva, double total, Usuario vendedor, LocalDate fecha, List<DetalleVenta> detalles) {
        
        this.numeroFactura = numeroFactura;
        this.nombreCliente = nombreCliente;
        this.nit = nit;
        this.direccion = direccion;
        this.totalSinIva = totalSinIva;
        this.total = total;
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.detalles = detalles;
    }

   public String getNumeroFactura() {
    return numeroFactura;
}

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getTotalSinIva() {
        return totalSinIva;
    }

    public double getTotal() {
        return total;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }
    public Cupon getCupon() {
        return cupon;
    }

    public void setCupon(Cupon cupon) {
        this.cupon = cupon;
    }
}
