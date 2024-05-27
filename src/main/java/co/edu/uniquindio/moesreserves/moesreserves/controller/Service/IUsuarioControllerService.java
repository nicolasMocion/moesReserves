package co.edu.uniquindio.moesreserves.moesreserves.controller.Service;

import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;

import java.util.List;

public interface IUsuarioControllerService {

    List<UsuarioDto> obtenerUsuarios();

    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String id);

    boolean actualizarUsuario(String currentId, UsuarioDto usuarioDto);

    void enableConsumer();
}
