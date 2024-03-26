package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IEmpleadoControllerService;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;

import java.util.List;

public class EmpleadoController implements IEmpleadoControllerService{
    ModelFactoryController modelFactoryController;
    public EmpleadoController(){
        System.out.println("Llamando al singleton desde EmpleadoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<EmpleadoDto> obtenerEmpleados() {
        return modelFactoryController.obtenerEmpleados();
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        return modelFactoryController.agregarEmpleado(empleadoDto);
    }

    @Override
    public boolean eliminarEmpleado(String id) {
        return modelFactoryController.eliminarEmpleado(id);
    }

    @Override
    public boolean actualizarEmpleado(String currentId, EmpleadoDto empleadoDto) {
        return modelFactoryController.actualizarEmpleado(currentId, empleadoDto);
    }
}
