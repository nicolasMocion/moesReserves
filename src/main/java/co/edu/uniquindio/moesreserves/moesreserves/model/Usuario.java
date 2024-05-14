package co.edu.uniquindio.moesreserves.moesreserves.model;

import java.util.ArrayList;

public class Usuario extends Persona {
    private ArrayList<Reserva> listaReservasUsuario = new ArrayList<Reserva>();
    public Usuario() {
    }

    public ArrayList<Reserva> getListaReservasUsuario() {
        return listaReservasUsuario;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservasUsuario = listaReservas;
    }

    public void addReserve(Reserva reserve){

        listaReservasUsuario.add(reserve);

    }




}
