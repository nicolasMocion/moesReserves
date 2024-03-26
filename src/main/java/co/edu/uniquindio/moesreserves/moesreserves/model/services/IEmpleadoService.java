package co.edu.uniquindio.moesreserves.moesreserves.model.services;

import co.edu.uniquindio.moesreserves.moesreserves.exceptions.EmpleadoException;
import co.edu.uniquindio.moesreserves.moesreserves.model.Empleado;

import java.util.ArrayList;

public interface IEmpleadoService {

    public Empleado crearEmpleado(String id, String name, String email) throws EmpleadoException;

    boolean actualizarEmpleado(String id, Empleado empleado) throws EmpleadoException;

    public Boolean eliminarEmpleado(String id) throws EmpleadoException;

    public boolean verificarEmpleadoExistente(String id) throws EmpleadoException;

    public Empleado obtenerEmpleado(String id);
    public ArrayList<Empleado> obtenerEmpleados();

}
