package co.edu.uniquindio.moesreserves.moesreserves.utils;

import co.edu.uniquindio.moesreserves.moesreserves.model.Empleado;
import co.edu.uniquindio.moesreserves.moesreserves.model.MoesReserves;

public class MoesUtils {

    public static MoesReserves inicializarDatos(){
        MoesReserves moesReserves = new MoesReserves();

        Empleado empleado = new Empleado();

        empleado.setId("123");
        empleado.setName("Omar");
        empleado.setEmail("saksaoks");
        moesReserves.getListaEmpleados().add(empleado);

        return moesReserves;
    }

}
