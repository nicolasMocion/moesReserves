package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IModelFactoryService;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.*;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.mappers.MoesMapper;
import co.edu.uniquindio.moesreserves.moesreserves.model.*;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.*;
import co.edu.uniquindio.moesreserves.moesreserves.utils.MoesUtils;

import java.util.List;
public class ModelFactoryController implements IModelFactoryService {

    MoesReserves moesReserves;

    MoesMapper mapper = MoesMapper.INSTANCE;

    private static class SingletonHolder{
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController(){
        System.out.println("invocación clase singleton");
        cargarDatosBase();
    }
    private void cargarDatosBase(){
        moesReserves = MoesUtils.inicializarDatos();
    }
    public MoesReserves getMoesReserves(){
        return moesReserves;
    }

    public void setMoesReserves(MoesReserves moesReserves){
        this.moesReserves = moesReserves;
    }
    @Override
    public List<EmpleadoDto> obtenerEmpleados(){
        return mapper.getEmpleadosDto(moesReserves.getListaEmpleados());
    }

    @Override
    public List<EventoDto> obtenerEventos(){
        return mapper.getEventosDto(moesReserves.getListaEventos());
    }

    @Override
    public List<ReservaDto> obtenerReservas(){
        return mapper.getReservasDto(moesReserves.getListaReservas());
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios(){
        return mapper.getUsuariosDto(moesReserves.getListaUsuarios());
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try{
            if(!moesReserves.verificarEmpleadoExistente(empleadoDto.id())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getMoesReserves().agregarEmpleado(empleado);
            }
            return true;
        }catch (EmpleadoException e){
            e.getMessage();
            return false;
        }
    }
    @Override
    public boolean eliminarEmpleado(String id) {
        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarEmpleado(id);
        } catch (EmpleadoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDeleted;
    }
    @Override
    public boolean actualizarEmpleado(String currentId, EmpleadoDto empleadoDto) {
        try {
            Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
            getMoesReserves().actualizarEmpleado(currentId, empleado);
            return true;
        } catch (EmpleadoException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean agregarEvento(EventoDto eventoDto) {
        try{
            if(!moesReserves.verificarEventoExistente(eventoDto.id())) {
                Evento evento = mapper.eventoDtoToEvento(eventoDto);
                getMoesReserves().agregarEvento(evento);
            }
            return true;
        }catch (EventoException e){
            e.getMessage();
            return false;
        }
    }
    @Override
    public boolean eliminarEvento(String id) {
        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarEvento(id);
        } catch (EventoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDeleted;
    }
    @Override
    public boolean actualizarEvento(String currentId, EventoDto eventoDto) {
        try {
            Evento evento = mapper.eventoDtoToEvento(eventoDto);
            getMoesReserves().actualizarEvento(currentId, evento);
            return true;
        } catch (EventoException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean agregarReserva(ReservaDto reservaDto) {
        try{
            if(!moesReserves.verificarReservaExistente(reservaDto.id())) {
                Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
                getMoesReserves().agregarReserva(reserva);
            }
            return true;
        }catch (ReservaException e){
            e.getMessage();
            return false;
        }
    }
    @Override
    public boolean eliminarReserva(String id) {
        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarReserva(id);
        } catch (ReservaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDeleted;
    }
    @Override
    public boolean actualizarReserva(String currentId, ReservaDto reservaDto) {
        try {
            Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
            getMoesReserves().actualizarReserva(currentId, reserva);
            return true;
        } catch (ReservaException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        try{
            if(!moesReserves.verificarUsuarioExistente(usuarioDto.id())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                getMoesReserves().agregarUsuario(usuario);
            }
            return true;
        }catch (UsuarioException e){
            e.getMessage();
            return false;
        }
    }
    @Override
    public boolean eliminarUsuario(String id) {
        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarUsuario(id);
        } catch (UsuarioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDeleted;
    }
    @Override
    public boolean actualizarUsuario(String currentId, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
            getMoesReserves().actualizarUsuario(currentId, usuario);
            return true;
        } catch (UsuarioException e) {
            e.printStackTrace();
            return false;
        }
    }






}
