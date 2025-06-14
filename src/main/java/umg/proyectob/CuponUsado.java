package umg.proyectob;

import java.io.Serializable;
import java.time.LocalDate;

public class CuponUsado implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private String codigo;
    private String tipo;       // "Porcentaje" o "Monto fijo"
    private double valor;
    private String vendedor;

    public CuponUsado(LocalDate fecha, String codigo, String tipo, double valor, String vendedor) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.tipo = tipo;
        this.valor = valor;
        this.vendedor = vendedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getVendedor() {
        return vendedor;
    }
}
