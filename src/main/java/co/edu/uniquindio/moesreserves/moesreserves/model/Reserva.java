package co.edu.uniquindio.moesreserves.moesreserves.model;

import java.util.ArrayList;

public class Reserva {

    private String id;
    private String usuario;
    private String evento;
    private String fechaDeSolicitud;
    private String estado;

    public Reserva() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaDeSolicitud() {
        return fechaDeSolicitud;
    }

    public void setFechaDeSolicitud(String fechaDeSolicitud) {
        this.fechaDeSolicitud = fechaDeSolicitud;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;

    }

}
