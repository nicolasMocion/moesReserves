package co.edu.uniquindio.moesreserves.moesreserves.controller.Service;

import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;

import java.util.List;

public interface IEventoControllerService {

    List<EventoDto> obtenerEventos();
    boolean agregarEvento(EventoDto eventoDto);

    boolean eliminarEvento(String id);

    boolean actualizarEvento(String currentId, EventoDto eventoDto);
}
