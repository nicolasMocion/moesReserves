package co.edu.uniquindio.moesreserves.moesreserves.model.services;

import co.edu.uniquindio.moesreserves.moesreserves.model.Empleado;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.EventoException;

import java.util.ArrayList;

public interface IEventService {

    Evento crearEvento(String id, String name, String description, String fecha, String maxCapacity, Empleado encargado) throws EventoException;

    boolean actualizarEvento(String id, Evento evento) throws EventoException;

    Boolean eliminarEvento(String id) throws EventoException;

    boolean verificarEventoExistente(String id) throws EventoException;

    Evento obtenerEvento(String id);

    ArrayList<Evento> obtenerEventos();
}
