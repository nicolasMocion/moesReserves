package co.edu.uniquindio.moesreserves.moesreserves.controller.Service;

import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;

import java.util.List;

public interface IEmpleadoControllerService {

    List<EmpleadoDto> obtenerEmpleados();

    boolean agregarEmpleado(EmpleadoDto empleadoDto);

    boolean eliminarEmpleado(String id);

    boolean actualizarEmpleado(String currentId, EmpleadoDto empleadoDto);

}
