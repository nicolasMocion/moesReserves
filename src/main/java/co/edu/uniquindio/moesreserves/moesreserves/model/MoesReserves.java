package co.edu.uniquindio.moesreserves.moesreserves.model;

import co.edu.uniquindio.moesreserves.moesreserves.model.services.IEmpleadoService;
import co.edu.uniquindio.moesreserves.moesreserves.model.services.IEventService;
import co.edu.uniquindio.moesreserves.moesreserves.model.services.IReservaService;
import co.edu.uniquindio.moesreserves.moesreserves.model.services.IUsuarioService;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.EmpleadoException;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.UsuarioException;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.EventoException;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.ReservaException;

import java.util.ArrayList;

public class MoesReserves implements IEmpleadoService, IUsuarioService, IEventService, IReservaService {
    ArrayList<Reserva> listaReservas = new ArrayList<Reserva>();
    ArrayList<Evento> listaEventos = new ArrayList<Evento>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
    ArrayList<Empleado> listaEmpleados = new ArrayList<>();

    public MoesReserves(){

    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ArrayList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    @Override
    public Empleado crearEmpleado(String id, String name, String email) throws EmpleadoException {
        Empleado nuevoEmpleado = null;
        boolean empleadoExiste = verificarEmpleadoExistente(id);
        if(empleadoExiste){
            throw new EmpleadoException("El empleado con id: "+id+" ya existe");
        }else{
            nuevoEmpleado = new Empleado();
            nuevoEmpleado.setName(name);
            nuevoEmpleado.setId(id);
            nuevoEmpleado.setEmail(email);
            getListaEmpleados().add(nuevoEmpleado);
        }
        return nuevoEmpleado;
    }

    public void agregarEmpleado(Empleado nuevoEmpleado) throws EmpleadoException {
        getListaEmpleados().add(nuevoEmpleado);
    }

    @Override
    public boolean actualizarEmpleado(String currentId, Empleado empleado) throws EmpleadoException {
        Empleado empleadoActual = obtenerEmpleado(currentId);
        if(empleadoActual == null)
            throw new EmpleadoException("El empleado a actualizar no existe");
        else{
            empleadoActual.setId(empleado.getName());
            empleadoActual.setName(empleado.getId());
            empleadoActual.setEmail(empleado.getEmail());
            return true;
        }
    }
    @Override
    public Boolean eliminarEmpleado(String id) throws EmpleadoException{
        Empleado empleado = null;
        boolean isDeleted = false;
        empleado = obtenerEmpleado(id);
        if(empleado == null)
            throw new EmpleadoException("El empleado a eliminar no existe");
        else{
            getListaEmpleados().remove(empleado);
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean verificarEmpleadoExistente(String id) throws EmpleadoException{
        if(empleadoExiste(id)){
            throw new EmpleadoException("El empleado con id: "+id+" ya existe");
        }else{
            return false;
        }
    }
    @Override
    public Empleado obtenerEmpleado(String id) {
        Empleado empleadoEncontrado = null;
        for (Empleado empleado : getListaEmpleados()) {
            if(empleado.getId().equalsIgnoreCase(id)){
                empleadoEncontrado = empleado;
                break;
            }
        }
        return empleadoEncontrado;
    }

    @Override
    public ArrayList<Empleado> obtenerEmpleados() {
        // TODO Auto-generated method stub
        return getListaEmpleados();
    }

    public boolean empleadoExiste(String id) {
        boolean empleadoEncontrado = false;
        for (Empleado empleado : getListaEmpleados()) {
            if(empleado.getId().equalsIgnoreCase(id)){
                empleadoEncontrado = true;
                break;
            }
        }
        return empleadoEncontrado;
    }

    @Override
    public Usuario crearUsuario(String id, String name, String email, ArrayList<Reserva> reservas) throws UsuarioException {
        Usuario nuevoUsuario = null;
        boolean usuarioExiste = verificarUsuarioExistente(id);
        if(usuarioExiste){
            throw new UsuarioException("El usuario con id: "+id+" ya existe");
        }else{
            nuevoUsuario = new Usuario();
            nuevoUsuario.setName(name);
            nuevoUsuario.setId(id);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setListaReservas(listaReservas);
            getListaUsuarios().add(nuevoUsuario);
        }
        return nuevoUsuario;
    }

    public void agregarUsuario(Usuario nuevoUsuario){
        getListaUsuarios().add(nuevoUsuario);
    }

    @Override
    public boolean actualizarUsuario(String currentId, Usuario usuario) throws UsuarioException {
        Usuario usuarioActual = obtenerUsuario(currentId);
        if(usuarioActual == null)
            throw new UsuarioException("El Usuario a actualizar no existe");
        else{
            usuarioActual.setName(usuario.getId());
            usuarioActual.setId(usuario.getName());
            usuarioActual.setEmail(usuario.getEmail());
            return true;
        }
    }

    @Override
    public Boolean eliminarUsuario(String id) throws UsuarioException{
        Usuario usuario = null;
        boolean isDeleted = false;
        usuario = obtenerUsuario(id);
        if(usuario == null)
            throw new UsuarioException("El usuario a eliminar no existe");
        else{
            getListaEmpleados().remove(usuario);
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public Usuario obtenerUsuario(String id){
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : getListaUsuarios()) {
            if(usuario.getId().equalsIgnoreCase(id)){
                usuarioEncontrado = usuario;
                break;
            }
        }
        return usuarioEncontrado;
    }

    @Override
    public boolean verificarUsuarioExistente(String id) throws UsuarioException{
        if(usuarioExiste(id)){
            throw new UsuarioException("El empleado con id: "+id+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Usuario> obtenerUsuarios() {
        // TODO Auto-generated method stub
        return getListaUsuarios();
    }

    public boolean usuarioExiste(String id) {
        boolean usuarioEncontrado = false;
        for (Usuario usuario : getListaUsuarios()) {
            if(usuario.getId().equalsIgnoreCase(id)){
                usuarioEncontrado = true;
                break;
            }
        }
        return usuarioEncontrado;
    }

    public void agregarEvento(Evento nuevoEvento) throws EventoException{
        getListaEventos().add(nuevoEvento);
    }

    @Override
    public Evento crearEvento(String id, String name, String description, String fecha, String maxCapacity, String encargado) throws EventoException {
        Evento nuevoEvento = null;
        boolean eventoExiste = verificarEventoExistente(id);
        if(eventoExiste){
            throw new EventoException("El Evento con cedula: "+id+" ya existe");
        }else{
            nuevoEvento = new Evento();
            nuevoEvento.setId(id);
            nuevoEvento.setName(name);
            nuevoEvento.setDescription(description);
            nuevoEvento.setFecha(fecha);
            nuevoEvento.setMaxCapacity(maxCapacity);
            nuevoEvento.setEncargado(encargado);
            getListaEventos().add(nuevoEvento);
        }
        return nuevoEvento;
    }

    @Override
    public boolean actualizarEvento(String id, Evento evento) throws EventoException {
        Evento eventoActual = obtenerEvento(id);
        if(eventoActual == null)
            throw new EventoException("El Evento a actualizar no existe");
        else{
            eventoActual.setName(evento.getName());
            eventoActual.setId(evento.getId());
            eventoActual.setDescription(evento.getDescription());
            eventoActual.setMaxCapacity(evento.getMaxCapacity());
            eventoActual.setFecha(evento.getFecha());
            eventoActual.setEncargado(evento.getEncargado());
            return true;
        }
    }

    @Override
    public Boolean eliminarEvento(String id) throws EventoException {
        Evento evento = null;
        boolean isDeleted = false;
        evento = obtenerEvento(id);
        if(evento == null)
            throw new EventoException("El Evento a eliminar no existe");
        else{
            getListaEventos().remove(evento);
           isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean verificarEventoExistente(String id) throws EventoException {
        if(eventoExiste(id)){
            throw new EventoException("El Evento con cedula: "+id+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public Evento obtenerEvento(String id) {
        Evento eventoEncontrado = null;
        for (Evento evento : getListaEventos()) {
            if(evento.getId().equalsIgnoreCase(id)){
                eventoEncontrado = evento;
                break;
            }
        }
        return eventoEncontrado;
    }

    @Override
    public ArrayList<Evento> obtenerEventos() {
        // TODO Auto-generated method stub
        return getListaEventos();
    }

    public boolean eventoExiste(String id) {
        boolean eventoEncontrado = false;
        for (Evento evento : getListaEventos()) {
            if(evento.getId().equalsIgnoreCase(id)){
                eventoEncontrado = true;
                break;
            }
        }
        return eventoEncontrado;
    }


    @Override
    public Reserva crearReserva(String id, Usuario usuario, Evento evento, String fechaDeSolicitud, Estado estado) throws ReservaException {
        return null;
    }

    @Override
    public Reserva crearReserva(String id, String usuario, String evento, String fechaDeSolicitud, String estado) throws ReservaException{
        Reserva nuevoReserva = null;
        boolean reservaExiste = verificarReservaExistente(id);
        if(reservaExiste){
            throw new ReservaException("El Reserva con id: "+id+" ya existe");
        }else{
            nuevoReserva = new Reserva();
            nuevoReserva.setId(id);
            nuevoReserva.setUsuario(usuario);
            nuevoReserva.setEvento(evento);
            nuevoReserva.setFechaDeSolicitud(fechaDeSolicitud);
            nuevoReserva.setEstado(estado);
            getListaReservas().add(nuevoReserva);
        }
        return nuevoReserva;
    }
    public void agregarReserva(Reserva nuevoReserva) throws ReservaException{
        getListaReservas().add(nuevoReserva);
    }

    @Override
    public boolean actualizarReserva(String id, Reserva reserva) throws ReservaException {
        Reserva reservaActual = obtenerReserva(id);
        if(reservaActual == null)
            throw new ReservaException("El reserva a actualizar no existe");
        else{
            reservaActual.setId(reserva.getId());
            reservaActual.setUsuario(reserva.getUsuario());
            reservaActual.setEstado(reserva.getEstado());
            reservaActual.setFechaDeSolicitud(reserva.getFechaDeSolicitud());
            reservaActual.setEvento(reserva.getEvento());
            return true;
        }
    }

    @Override
    public Boolean eliminarReserva(String id) throws ReservaException {
        Reserva reserva = null;
        boolean isDeleted = false;
        reserva = obtenerReserva(id);
        if(reserva == null)
            throw new ReservaException("El reserva a eliminar no existe");
        else{
            getListaReservas().remove(reserva);
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean verificarReservaExistente(String id) throws ReservaException {
        if(reservaExiste(id)){
            throw new ReservaException("El reserva con cedula: "+id+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public Reserva obtenerReserva(String id) {
        Reserva reservaEncontrado = null;
        for (Reserva reserva : getListaReservas()) {
            if(reserva.getId().equalsIgnoreCase(id)){
                reservaEncontrado = reserva;
                break;
            }
        }
        return reservaEncontrado;
    }

    @Override
    public ArrayList<Reserva> obtenerReservas() {
        // TODO Auto-generated method stub
        return getListaReservas();
    }

    public boolean reservaExiste(String id) {
        boolean reservaEncontrado = false;
        for (Reserva reserva : getListaReservas()) {
            if(reserva.getId().equalsIgnoreCase(id)){
                reservaEncontrado = true;
                break;
            }
        }
        return reservaEncontrado;
    }


}
