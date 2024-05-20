package co.edu.uniquindio.moesreserves.moesreserves.mapping.dto;

import co.edu.uniquindio.moesreserves.moesreserves.model.Estado;
import co.edu.uniquindio.moesreserves.moesreserves.model.Usuario;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;

public record ReservaDto(
        String id,
        String usuario,
        String evento,
        String fechaDeSolicitud,
        String estado
) {

    public String getId() {
        return id;
    }

    public String getUser(){
        return usuario;
    }


}
