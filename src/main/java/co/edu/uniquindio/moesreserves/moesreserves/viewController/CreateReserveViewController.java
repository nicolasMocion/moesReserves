package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.controller.EventoController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.ReservaController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.UsuarioController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class CreateReserveViewController {

    String selectedChoice;
    private String user;

    public void setUser(String user) {
        this.user = user;
    }


    ReservaController reservaControllerService;

    EventoController eventoControllerService;
    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();

    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();

    ReservaDto reservaSeleccionado;


    @FXML
    private ComboBox<String> eventosList;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtId;

    @FXML
    private Button createReserveBttn;

    @FXML
    void initialize() {
        reservaControllerService = new ReservaController();
        eventoControllerService = new EventoController();

        obtenerReservas();

        String[] eventosId = getEventosId(listaEventosDto);
        eventosList.getItems().addAll(eventosId);
    }

    private void obtenerReservas() {
        listaReservasDto.addAll(reservaControllerService.obtenerReservas());
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());

    }


    @FXML
    void agregarReservaAction(ActionEvent event) {
        crearReserva();
    }


    private void crearReserva() {
        //1. Capturar los datos
        ReservaDto reservaDto = construirReservaDto();
        //2. Validar la información
        if(datosValidos(reservaDto)){
            if(reservaControllerService.agregarReserva(reservaDto)){
                listaReservasDto.add(reservaDto);
                mostrarMensaje("Notificación reserva", "reserva creada", "La reserva se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposReserva();
            }else{
                mostrarMensaje("Notificación reserva", "reserva no creada", "la reserva no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación reserva", "reserva no creada", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private ReservaDto construirReservaDto() {
        String selectedChoice = eventosList.getValue();
        return new ReservaDto(
                txtId.getText(),
                txtFecha.getText(),
                this.user,
                selectedChoice,
                "Pendiente"
        );
    }
    private void limpiarCamposReserva() {
        txtId.setText("");
        txtFecha.setText("");

    }

    private boolean datosValidos(ReservaDto reservaDto) {
        String mensaje = "";
        if(reservaDto.id() == null || reservaDto.id().equals(""))
            mensaje += "El id es invalido \n" ;
        if(reservaDto.fechaDeSolicitud() == null || reservaDto.fechaDeSolicitud() .equals(""))
            mensaje += "La fecha es invalida \n" ;
        if(reservaDto.usuario() == null || reservaDto.usuario().equals(""))
            mensaje += "El usuario es invalido \n" ;
        if(reservaDto.evento() == null || reservaDto.evento() .equals(""))
            mensaje += "El evento es invalido \n" ;
        if(reservaDto.estado() == null || reservaDto.estado() .equals(""))
            mensaje += "El estado es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private String[] getEventosId(ObservableList<EventoDto> eventos){

        String[] eventosId = new String[eventos.size()];

        for (int i = 0; i < eventos.size() ; i++) {

            eventosId[i] = eventos.get(i).getId();

        }

        return eventosId;


    }





}
