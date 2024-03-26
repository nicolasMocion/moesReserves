package co.edu.uniquindio.moesreserves.moesreserves.controller.Service;

import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;

import java.util.List;

public interface IReservaControllerService {


    List<ReservaDto> obtenerReservas();

    boolean agregarReserva(ReservaDto reservaDto);

    boolean eliminarReserva(String id);

    boolean actualizarReserva(String currentId, ReservaDto reservaDto);



}
