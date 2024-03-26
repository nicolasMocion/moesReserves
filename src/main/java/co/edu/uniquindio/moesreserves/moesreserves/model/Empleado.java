package co.edu.uniquindio.moesreserves.moesreserves.model;

import java.util.ArrayList;

public class Empleado extends Persona{

    ArrayList<Evento> listaEventosAsignados = new ArrayList<Evento>();
    public Empleado() {
    }

    public ArrayList<Evento> getListaEventosAsignados() {
        return listaEventosAsignados;
    }

    public void setListaEventosAsignados(ArrayList<Evento> listaEventosAsignados) {
        this.listaEventosAsignados = listaEventosAsignados;
    }

}
