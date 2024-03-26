package co.edu.uniquindio.moesreserves.moesreserves.mapping.dto;

import co.edu.uniquindio.moesreserves.moesreserves.model.Estado;
import co.edu.uniquindio.moesreserves.moesreserves.model.Usuario;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;

public record ReservaDto(
        String id,
        Usuario usuario,
        Evento evento,
        String fechaDeSolicitud,
        Estado estado) {
}
