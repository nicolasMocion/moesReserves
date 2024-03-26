package co.edu.uniquindio.moesreserves.moesreserves.mapping.dto;

import co.edu.uniquindio.moesreserves.moesreserves.model.Empleado;

public record EventoDto(
        String id,
        String name,
        String description,
        String fecha,
        String maxCapcity,
        Empleado encargado) {

}
