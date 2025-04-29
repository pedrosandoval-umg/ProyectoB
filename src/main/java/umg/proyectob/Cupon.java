package umg.proyectob;
import java.io.Serializable;
public class Cupon implements Serializable {

    private String codigo;
    private String descripcion;
    private double valor; 
    private boolean esPorcentaje;
    private boolean activo; 
   
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public boolean isEsPorcentaje() {
        return esPorcentaje;
    }
    public void setEsPorcentaje(boolean esPorcentaje) {
        this.esPorcentaje = esPorcentaje;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
