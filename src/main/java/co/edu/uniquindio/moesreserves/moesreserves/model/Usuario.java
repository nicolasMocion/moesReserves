package co.edu.uniquindio.moesreserves.moesreserves.model;

import java.util.ArrayList;

public class Usuario extends Persona {
    ArrayList<Reserva> listaReservasUsuario = new ArrayList<Reserva>();
    public Usuario() {
    }

    public ArrayList<Reserva> getListaReservass() {
        return listaReservasUsuario;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservasUsuario = listaReservas;
    }




}
