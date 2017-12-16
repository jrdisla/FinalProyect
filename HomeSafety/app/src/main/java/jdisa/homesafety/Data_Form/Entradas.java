package jdisa.homesafety.Data_Form;

/**
 * Created by jrdis on 16/12/2017.
 */

public class Entradas {
    private String Fecha;
    private String Hora;
    private String User;
    private String Device;

    public Entradas ()
    {

    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
