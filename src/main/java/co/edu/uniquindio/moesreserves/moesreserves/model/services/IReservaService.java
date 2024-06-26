package co.edu.uniquindio.moesreserves.moesreserves.model.services;

import co.edu.uniquindio.moesreserves.moesreserves.model.Estado;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;
import co.edu.uniquindio.moesreserves.moesreserves.model.Reserva;
import co.edu.uniquindio.moesreserves.moesreserves.model.Usuario;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.ReservaException;

import java.util.ArrayList;

public interface IReservaService {

    Reserva crearReserva(String id, String usuario, String evento, String fechaDeSolicitud, String estado) throws ReservaException;

    boolean actualizarReserva(String id, Reserva reserva) throws ReservaException;

    Boolean eliminarReserva(String id) throws ReservaException;

    boolean verificarReservaExistente(String id) throws ReservaException;

    Reserva obtenerReserva(String id);

    ArrayList<Reserva> obtenerReservas();
}
