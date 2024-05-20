package co.edu.uniquindio.moesreserves.moesreserves;

import co.edu.uniquindio.moesreserves.moesreserves.controller.ModelFactoryController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import co.edu.uniquindio.moesreserves.moesreserves.model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class MainMoes {

    public static void main(String[] args) {
        ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

        ArrayList <ReservaDto> reserva = new ArrayList<>();


        EmpleadoDto empleadoDto = new EmpleadoDto(
                "1092850934",
                "Omar",
                "546@fkeofe"
        );

        ReservaDto reservaDto = new ReservaDto(
                "Omar",
                "Main",
                "Final champions",
                "Main",
                "Ok"

        );

        reserva.add(reservaDto);

        EventoDto eventoDto = new EventoDto(
                "23232",
                "Chamipois",
                "Fpptbañ",
                "Hpy",
                "4",
                "Yo"
        );

        UsuarioDto usuarioDto = new UsuarioDto(

                "Omarr",
                "O12323",
                "SKFOSKDOSKD",
                reserva

        );

        if(modelFactoryController.agregarEmpleado(empleadoDto)){
            System.out.println("No existe se agrgeo correctamente");
        }else{
            System.out.println("Ya existe");
        }

        if(modelFactoryController.agregarReserva(reservaDto)){
            System.out.println("No existe se agrgeo correctamente");
        }else{
            System.out.println("Ya existe");
        }

        if(modelFactoryController.agregarEvento(eventoDto)){
            System.out.println("No existe se agrgeo correctamente");
        }else{
            System.out.println("Ya existe");
        }

        if(modelFactoryController.agregarUsuario(usuarioDto)){
            System.out.println("No existe se agrgeo correctamente");
        }else{
            System.out.println("Ya existe");
        }

        List<EmpleadoDto> empleadoDtoList = modelFactoryController.obtenerEmpleados();
        empleadoDtoList.forEach(System.out::println);

        List<ReservaDto> reservaDtoList = modelFactoryController.obtenerReservas();
        reservaDtoList.forEach(System.out::println);

        List<EventoDto> eventoDtoList = modelFactoryController.obtenerEventos();
        eventoDtoList.forEach(System.out::println);

        List<UsuarioDto> usuarioDtoList = modelFactoryController.obtenerUsuarios();
        usuarioDtoList.forEach(System.out::println);
    }


}
