package co.edu.uniquindio.moesreserves.moesreserves.model.services;

import co.edu.uniquindio.moesreserves.moesreserves.exceptions.UsuarioException;
import co.edu.uniquindio.moesreserves.moesreserves.model.Reserva;
import co.edu.uniquindio.moesreserves.moesreserves.model.Usuario;

import java.util.ArrayList;

public interface IUsuarioService {


    Usuario crearUsuario(String id, String name, String email, ArrayList<Reserva> reservas) throws UsuarioException;

    boolean actualizarUsuario(String id, Usuario usuario) throws UsuarioException;

    public Boolean eliminarUsuario(String id) throws UsuarioException;

    public boolean verificarUsuarioExistente(String id) throws UsuarioException;

    public Usuario obtenerUsuario(String id);
    public ArrayList<Usuario> obtenerUsuarios();

}
