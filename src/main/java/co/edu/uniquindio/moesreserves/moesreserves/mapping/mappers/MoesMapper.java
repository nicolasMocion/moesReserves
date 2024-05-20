package co.edu.uniquindio.moesreserves.moesreserves.mapping.mappers;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.*;
import co.edu.uniquindio.moesreserves.moesreserves.model.Empleado;
import co.edu.uniquindio.moesreserves.moesreserves.model.Reserva;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;
import co.edu.uniquindio.moesreserves.moesreserves.model.Usuario;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MoesMapper {

    MoesMapper INSTANCE = Mappers.getMapper(MoesMapper.class);

    @Named("empleadoToEmpleadoDto")
    EmpleadoDto empleadoToEmpleadoDto(Empleado empleado);
    Empleado empleadoDtoToEmpleado(EmpleadoDto empleadoDto);

    @IterableMapping(qualifiedByName = "empleadoToEmpleadoDto")
    List<EmpleadoDto> getEmpleadosDto( List<Empleado> listaEmpleados);

    @Named("eventoToEventoDto")
    EventoDto eventoToEventoDto(Evento evento);
    Evento eventoDtoToEvento(EventoDto eventoDto);

    @IterableMapping(qualifiedByName = "eventoToEventoDto")
    List<EventoDto> getEventosDto( List<Evento> listaEventos);

    @Named("reservaToReservaDto")
    ReservaDto reservaToReservaDto(Reserva reserva);
    Reserva reservaDtoToReserva(ReservaDto reservaDto);

    @IterableMapping(qualifiedByName = "reservaToReservaDto")
    List<ReservaDto> getReservasDto( List<Reserva> listaReservas);

    @Named("usuarioToUsuarioDto")
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @IterableMapping(qualifiedByName = "usuarioToUsuarioDto")
    List<UsuarioDto> getUsuariosDto( List<Usuario> listaUsuarios);



}
