package co.edu.uniquindio.moesreserves.moesreserves.controller.Service;

import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;

import java.util.List;

public interface IModelFactoryService {


    List<EmpleadoDto> obtenerEmpleados();
    List<ReservaDto> obtenerReservas();
    List<EventoDto> obtenerEventos();
    List<UsuarioDto> obtenerUsuarios();
    boolean agregarEmpleado(EmpleadoDto empleadoDto);
    boolean eliminarEmpleado(String id);
    boolean actualizarEmpleado(String currentId, EmpleadoDto empleadoDto);
    boolean agregarEvento(EventoDto eventoDto);
    boolean eliminarEvento(String id);
    boolean actualizarEvento(String currentId, EventoDto eventoDto);
    boolean agregarReserva(ReservaDto reservaDto);
    boolean eliminarReserva(String id);
    boolean actualizarReserva(String currentId, ReservaDto reservaDto);
    boolean agregarUsuario(UsuarioDto usuarioDto);
    boolean eliminarUsuario(String id);
    boolean actualizarUsuario(String currentId, UsuarioDto usuarioDto);



}
