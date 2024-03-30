package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IReservaControllerService;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;

import java.util.List;

public class ReservaController implements IReservaControllerService {

    ModelFactoryController modelFactoryController;

    public ReservaController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }
    public List<ReservaDto> obtenerReservas() {
        return modelFactoryController.obtenerReservas();
    }

    @Override
    public boolean agregarReserva(ReservaDto reservaDto) {
        return modelFactoryController.agregarReserva(reservaDto);
    }@Override
    public boolean eliminarReserva(String id) {
        return modelFactoryController.eliminarReserva(id);
    }
    @Override
    public boolean actualizarReserva(String currentId, ReservaDto reservaDto) {
        return modelFactoryController.actualizarReserva(currentId, reservaDto);
    }
}
