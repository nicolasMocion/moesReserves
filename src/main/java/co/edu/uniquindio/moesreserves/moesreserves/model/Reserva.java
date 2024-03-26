package co.edu.uniquindio.moesreserves.moesreserves.model;

public class Reserva {

    private String id;
    private Usuario usuario;
    private Evento evento;
    private String fechaDeSolicitud;
    private Estado estado;

    public Reserva(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getFechaDeSolicitud() {
        return fechaDeSolicitud;
    }

    public void setFechaDeSolicitud(String fechaDeSolicitud) {
        this.fechaDeSolicitud = fechaDeSolicitud;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
