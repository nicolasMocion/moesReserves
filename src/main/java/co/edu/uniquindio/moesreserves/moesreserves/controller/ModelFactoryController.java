package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.config.RabbitFactory;
import co.edu.uniquindio.moesreserves.moesreserves.config.RabbitMQProducer;
import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IModelFactoryService;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.*;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.mappers.MoesMapper;
import co.edu.uniquindio.moesreserves.moesreserves.model.*;
import co.edu.uniquindio.moesreserves.moesreserves.exceptions.*;
import co.edu.uniquindio.moesreserves.moesreserves.utils.*;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static co.edu.uniquindio.moesreserves.moesreserves.utils.Constantes.QUEUE_NUEVA_PUBLICACION;

public class ModelFactoryController implements IModelFactoryService, Runnable {


    MoesReserves moesReserves;
    MoesMapper mapper = MoesMapper.INSTANCE;
    BoundedSemaphore semaphore = new BoundedSemaphore(1);
    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;

    String mensaje = "";
    int nivel = 0;
    String accion = "";

    Thread hilo1GuardarXml;
    Thread hilo2GuardarLog;



    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        System.out.println("invocación clase singleton");
        try {
            cargarResourceXML();
            if (moesReserves == null) {
                cargarDatosDesdeArchivos();
            }
            initRabbitConnection();
            registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");
        } catch (Exception e) {
            System.err.println("Error initializing ModelFactoryController: " + e.getMessage());
            e.printStackTrace();
        }


    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecida");
    }


    @Override
    public void run() {
        Thread hiloActual = Thread.currentThread();
        ocupar();
        if (hiloActual == hilo1GuardarXml) {
            Persistencia.guardarRecursoMoesReservesXML(moesReserves);
            liberar();
        }
        else if (hiloActual == hilo2GuardarLog) {
            Persistencia.guardaRegistroLog(mensaje, nivel, accion);
            liberar();
        }

    }

    private void cargarDatosBase() {
        moesReserves = MoesUtils.inicializarDatos();
    }

    public MoesReserves getMoesReserves() {
        return moesReserves;
    }

    public void setMoesReserves(MoesReserves moesReserves) {
        this.moesReserves = moesReserves;
    }

    @Override
    public List<EmpleadoDto> obtenerEmpleados() {
        return mapper.getEmpleadosDto(moesReserves.getListaEmpleados());
    }

    @Override
    public List<EventoDto> obtenerEventos() {
        return mapper.getEventosDto(moesReserves.getListaEventos());
    }

    @Override
    public List<ReservaDto> obtenerReservas() {
        return mapper.getReservasDto(moesReserves.getListaReservas());
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return mapper.getUsuariosDto(moesReserves.getListaUsuarios());
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try {
            if (!moesReserves.verificarEmpleadoExistente(empleadoDto.id())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getMoesReserves().agregarEmpleado(empleado);
                registrarAccionesSistema("Se agrego el empleado" + empleado.getName(), 1, "agregarEmpleado");
                guardarResourceXML();
                salvarDatosPrueba();
                RabbitMQProducer.sendUpdateMessage("Empleado agregado: " + empleadoDto.id());


            }
            return true;
        } catch (EmpleadoException e) {
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
            guardarResourceXML();
            salvarDatosPrueba();


            return true;
        } catch (EmpleadoException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarEvento(EventoDto eventoDto) {
        try {
            if (!moesReserves.verificarEventoExistente(eventoDto.id())) {
                Evento evento = mapper.eventoDtoToEvento(eventoDto);
                getMoesReserves().agregarEvento(evento);
                registrarAccionesSistema("Se agrego el empleado" + evento.getName(), 1, "agregarEvento");
                guardarResourceXML();
                salvarDatosPrueba();
            }
            return true;
        } catch (EventoException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarEvento(String id) {

        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarEvento(id);
            if (isDeleted) {
                guardarResourceXML(); // Save state
                salvarDatosPrueba();
            }

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
            guardarResourceXML();
            salvarDatosPrueba();

            return true;
        } catch (EventoException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarReserva(ReservaDto reservaDto) {
        try {
            if (!moesReserves.verificarReservaExistente(reservaDto.id())) {
                Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
                moesReserves.agregarReserva(reserva);
                registrarAccionesSistema("Se agrego el empleado" + reserva.getId(), 1, "agregarReserva");
                guardarResourceXML();
                salvarDatosPrueba();

            }
            return true;
        } catch (ReservaException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarReserva(String id) {
        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarReserva(id);

            if (isDeleted) {
                guardarResourceXML(); // Save state
                salvarDatosPrueba();  // Ensure all data is saved properly

            }
        } catch (ReservaException e) {
            throw new RuntimeException(e);
        }
        return isDeleted;
    }

    @Override
    public boolean actualizarReserva(String currentId, ReservaDto reservaDto) {
        try {
            Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
            getMoesReserves().actualizarReserva(currentId, reserva);
            guardarResourceXML();
            salvarDatosPrueba();

            return true;
        } catch (ReservaException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        try {
            if (!moesReserves.verificarUsuarioExistente(usuarioDto.getId())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                moesReserves.agregarUsuario(usuario);
                registrarAccionesSistema("Se agrego el empleado" + usuario.getName(), 1, "agregarUsuario");
                guardarResourceXML();
                salvarDatosPrueba();

            }
            return true;
        } catch (UsuarioException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String id) {
        boolean isDeleted = false;
        try {
            isDeleted = getMoesReserves().eliminarUsuario(id);

            if (isDeleted) {
                guardarResourceXML(); // Save state
                salvarDatosPrueba();  // Ensure all data is saved properly

            }
        } catch (UsuarioException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public boolean actualizarUsuario(String currentId, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
            moesReserves.actualizarUsuario(currentId, usuario);
            guardarResourceXML();
            salvarDatosPrueba();


            return true;
        } catch (UsuarioException e) {
            throw new RuntimeException(e);
        }
    }


    private void cargarDatosDesdeArchivos() {
        moesReserves = new MoesReserves();
        try {
            Persistencia.cargarDatosArchivos(moesReserves);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarEmpleados(getMoesReserves().getListaEmpleados());
            Persistencia.guardarEventos(getMoesReserves().getListaEventos());
            Persistencia.guardarReservas(getMoesReserves().getListaReservas());
            Persistencia.guardarUsuarios(getMoesReserves().getListaUsuarios());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargarResourceXML() {
        System.out.println("XML reloaded");
        moesReserves = Persistencia.cargarRecursoMoesReservesXML();
    }

    private void guardarResourceXML() {
        hilo1GuardarXml = new Thread(this);
        hilo1GuardarXml.start();
        RabbitMQProducer.sendUpdateMessage("XML updated");
    }

    private void cargarResourceBinario() {
        moesReserves = Persistencia.cargarRecursoMoesReservesBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoMoesReservesBinario(moesReserves);
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        this.mensaje = mensaje;
        this.nivel = nivel;
        this.accion = accion;
        hilo2GuardarLog = new Thread(this);
        hilo2GuardarLog.start();
    }

    private void ocupar() {
        try {
            semaphore.ocupar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void liberar() {
        try {
            semaphore.liberar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
