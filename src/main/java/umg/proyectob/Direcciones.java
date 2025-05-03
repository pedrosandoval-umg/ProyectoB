package umg.proyectob;

import java.io.Serializable;

public class Direcciones implements Serializable {
    
private String Calle;
private String Avenida;
private String Zona;
private String DireccionCompleta;
   
public Direcciones() {
    }

    public Direcciones(String Calle, String Avenida, String Zona, String DireccionCompleta) {
        this.Calle = Calle;
        this.Avenida = Avenida;
        this.Zona = Zona;
        this.DireccionCompleta = DireccionCompleta;
    }
    
    public String getCalle() {
        return Calle;
    }
    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getAvenida() {
        return Avenida;
    }
    public void setAvenida(String Avenida) {
        this.Avenida = Avenida;
    }

    public String getZona() {
        return Zona;
    }
    public void setZona(String Zona) {
        this.Zona = Zona;
    }

    public String getDireccionCompleta() {
        return DireccionCompleta;
    }
    public void setDireccionCompleta(String DireccionCompleta) {
        this.DireccionCompleta = DireccionCompleta;
    }
   
}
