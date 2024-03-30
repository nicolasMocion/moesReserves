package co.edu.uniquindio.moesreserves.moesreserves.utils;

import co.edu.uniquindio.moesreserves.moesreserves.model.*;

public class MoesUtils {

    public static MoesReserves inicializarDatos(){
        MoesReserves moesReserves = new MoesReserves();

        Empleado empleado = new Empleado();

        empleado.setId("123");
        empleado.setName("Omar");
        empleado.setEmail("saksaoks");
        moesReserves.getListaEmpleados().add(empleado);


        Reserva reserva = new Reserva();

        reserva.setId("232");
        reserva.setEstado("OK");
        reserva.setUsuario("omar");
        reserva.setEvento("main");
        reserva.setFechaDeSolicitud("hoy");
        moesReserves.getListaReservas().add(reserva);

        Evento evento = new Evento();

        evento.setFecha("hoy");
        evento.setDescription("Hola");
        evento.setMaxCapacity("3");
        evento.setEncargado("Omar");
        evento.setId("121212");
        evento.setName("Champions");
        moesReserves.getListaEventos().add(evento);

        Usuario usuario = new Usuario();

        usuario.setId("1212");
        usuario.setName("2323");
        usuario.setEmail("2323232");
        moesReserves.getListaUsuarios().add(usuario);


        return moesReserves;
    }

}
