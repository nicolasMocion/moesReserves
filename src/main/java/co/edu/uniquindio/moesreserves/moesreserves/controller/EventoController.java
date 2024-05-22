package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IEventoControllerService;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;

import java.util.List;

public class EventoController implements IEventoControllerService {

    ModelFactoryController modelFactoryController;

    public EventoController(){
        System.out.println("Llamando al singleton desde EventoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<EventoDto> obtenerEventos() {
        return modelFactoryController.obtenerEventos();
    }

    @Override
    public boolean agregarEvento(EventoDto eventoDto) {
        return modelFactoryController.agregarEvento(eventoDto);
    }

    @Override
    public boolean eliminarEvento(String id) {
        return modelFactoryController.eliminarEvento(id);
    }

    @Override
    public boolean actualizarEvento(String currentId, EventoDto eventoDto) {
        return modelFactoryController.actualizarEvento(currentId, eventoDto);
    }
}


