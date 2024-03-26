package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IUsuarioControllerService;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;

import java.util.List;

public class UsuarioController implements IUsuarioControllerService {

    ModelFactoryController modelFactoryController;

    public UsuarioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }
    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactoryController.obtenerUsuarios();
    }

    @Override public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return modelFactoryController.agregarUsuario(usuarioDto);
    }
    @Override
    public boolean eliminarUsuario(String id) {
        return modelFactoryController.eliminarUsuario(id);
    }
    @Override
    public boolean actualizarUsuario(String currentId, UsuarioDto usuarioDto) {
        return modelFactoryController.actualizarUsuario(currentId, usuarioDto);
    }
}
