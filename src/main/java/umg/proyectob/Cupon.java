package umg.proyectob;

public class Cupon {
    private String codigo;
    private String descripcion;
    private double valor; // puede representar porcentaje o monto fijo
    private boolean esPorcentaje; // true = porcentaje, false = monto fijo
    private boolean activo; 

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the esPorcentaje
     */
    public boolean isEsPorcentaje() {
        return esPorcentaje;
    }

    /**
     * @param esPorcentaje the esPorcentaje to set
     */
    public void setEsPorcentaje(boolean esPorcentaje) {
        this.esPorcentaje = esPorcentaje;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
