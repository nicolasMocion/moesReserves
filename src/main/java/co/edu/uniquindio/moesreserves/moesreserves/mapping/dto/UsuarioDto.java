package co.edu.uniquindio.moesreserves.moesreserves.mapping.dto;

import co.edu.uniquindio.moesreserves.moesreserves.model.Reserva;

import java.util.ArrayList;

public record UsuarioDto(
        String name,
        String id,
        String email,
        ArrayList<ReservaDto> listaReservasUsuario) {


}
