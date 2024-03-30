package co.edu.uniquindio.moesreserves.moesreserves.model.services;

import co.edu.uniquindio.moesreserves.moesreserves.exceptions.EmpleadoException;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.EventoException;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.ReservaException;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.UsuarioException;
import co.edu.uniquindio.moesreserves.moesreserves.model.*;

import java.util.ArrayList;

public interface IMoesService {

    public Empleado crearEmpleado(String id, String name, String email) throws EmpleadoException;

    boolean actualizarEmpleado(String id, Empleado empleado) throws EmpleadoException;

    public Boolean eliminarEmpleado(String id) throws EmpleadoException;

    public boolean verificarEmpleadoExistente(String id) throws EmpleadoException;

    public Empleado obtenerEmpleado(String id);
    public ArrayList<Empleado> obtenerEmpleados();

    Evento crearEvento(String id, String name, String description, String fecha, String maxCapacity, Empleado encargado) throws EventoException;

    boolean actualizarEvento(String id, Evento evento) throws EventoException;

    Boolean eliminarEvento(String id) throws EventoException;

    boolean verificarEventoExistente(String id) throws EventoException;

    Evento obtenerEvento(String id);

    ArrayList<Evento> obtenerEventos();

    Reserva crearReserva(String id, Usuario usuario, Evento evento, String fechaDeSolicitud, Estado estado) throws ReservaException;

    boolean actualizarReserva(String id, Reserva reserva) throws ReservaException;

    Boolean eliminarReserva(String id) throws ReservaException;

    boolean verificarReservaExistente(String id) throws ReservaException;

    Reserva obtenerReserva(String id);

    ArrayList<Reserva> obtenerReservas();
    public Usuario crearUsuario(String id, String name, String email) throws UsuarioException;

    boolean actualizarUsuario(String id, Usuario usuario) throws UsuarioException;

    public Boolean eliminarUsuario(String id) throws UsuarioException;

    public boolean verificarUsuarioExistente(String id) throws UsuarioException;

    public Usuario obtenerUsuario(String id);
    public ArrayList<Usuario> obtenerUsuarios();


}
