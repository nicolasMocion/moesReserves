package co.edu.uniquindio.moesreserves.moesreserves.model;

import java.util.ArrayList;

public class Evento {

    private String id;
    private String name;
    private String description;
    private String fecha;
    private String maxCapacity;

    private Empleado encargado;

    ArrayList<Reserva> listaReservasEvento = new ArrayList<Reserva>();

    public Evento(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Reserva> getListaReservasEvento() {
        return listaReservasEvento;
    }

    public void setListaReservasEvento(ArrayList<Reserva> listaReservasEvento) {
        this.listaReservasEvento = listaReservasEvento;
    }

    public Empleado getEncargado() {
        return encargado;
    }

    public void setEncargado(Empleado encargado) {
        this.encargado = encargado;
    }


}
