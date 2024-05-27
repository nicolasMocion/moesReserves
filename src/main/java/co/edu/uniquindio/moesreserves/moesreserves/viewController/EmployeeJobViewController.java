package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import co.edu.uniquindio.moesreserves.moesreserves.controller.EmpleadoController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.EventoController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.ReservaController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeJobViewController {
    ReservaController reservaControllerService;

    EventoController eventoControllerService;

    EmpleadoController empleadoControllerService;

    ObservableList<ReservaDto> employeeListaReservasDto = FXCollections.observableArrayList();

    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();

    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();

    ObservableList<EmpleadoDto> listaEmpleadosDto = FXCollections.observableArrayList();
    ReservaDto reservaSeleccionado;

    private String employee;

    ArrayList<EventoDto> employeeEvent = new ArrayList<>();

    public void setEmployee(String employee) {
        this.employee = employee;
    }


    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtEvento;

    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtEstado;

    @FXML
    private TableView<ReservaDto> tableReservas;

    @FXML
    private TableColumn<ReservaDto, String> tcId;

    @FXML
    private TableColumn<ReservaDto, String> tcFecha;

    @FXML
    private TableColumn<ReservaDto, String> tcUser;

    @FXML
    private TableColumn<ReservaDto, String> tcEvento;

    @FXML
    private TableColumn<ReservaDto, String> tcEstado;

    @FXML
    void initialize() {
        reservaControllerService = new ReservaController();
        eventoControllerService = new EventoController();
        empleadoControllerService = new EmpleadoController();
        empleadoControllerService.enableConsumer();
        setEmployee(this.employee);
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerReservas();
        employeeEvent = getEmployeeEvent(listaEventosDto);
        employeeListaReservasDto = filterReserves(listaReservasDto, employeeEvent, employeeListaReservasDto);
        tableReservas.getItems().clear();
        tableReservas.setItems(employeeListaReservasDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaDeSolicitud()));
        tcUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usuario()));
        tcEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().evento()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));


    }

    private void obtenerReservas() {
        listaReservasDto.addAll(reservaControllerService.obtenerReservas());
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());
        listaEmpleadosDto.addAll(empleadoControllerService.obtenerEmpleados());
    }

    private void listenerSelection() {
        tableReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionado = newSelection;
            mostrarInformacionReserva(reservaSeleccionado);
        });
    }

    private void mostrarInformacionReserva(ReservaDto reservaSeleccionado) {
        if (reservaSeleccionado != null) {
            txtId.setText(reservaSeleccionado.id());
            txtFecha.setText(reservaSeleccionado.fechaDeSolicitud());
            txtEvento.setText(reservaSeleccionado.evento());
            txtUser.setText(reservaSeleccionado.usuario());
            txtEstado.setText(reservaSeleccionado.estado());
        }
    }

    @FXML
    void nuevoReservaAction(ActionEvent event) {
        txtId.setText("Ingrese la id");
        txtUser.setText("Ingrese el usuario");
        txtEvento.setText("Ingrese el evento");
        txtFecha.setText("Ingrese la fecha");
        txtEstado.setText("Ingrese el estado");
    }

    @FXML
    void agregarReservaAction(ActionEvent event) {
        crearReserva();
    }

    @FXML
    void eliminarReservaAction(ActionEvent event) {
        eliminarReserva();
    }


    @FXML
    void actualizarReservaAction(ActionEvent event) {
        actualizarReserva();
    }

    private void crearReserva() {
        //1. Capturar los datos
        ReservaDto reservaDto = construirReservaDto();
        //2. Validar la información
        if (datosValidos(reservaDto)) {
            if (reservaControllerService.agregarReserva(reservaDto)) {
                listaReservasDto.add(reservaDto);
                mostrarMensaje("Notificación reserva", "reserva creada", "La reserva se ha creado con éxito", AlertType.INFORMATION);
                limpiarCamposReserva();
            } else {
                mostrarMensaje("Notificación reserva", "reserva no creada", "la reserva no se ha creado con éxito", AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Notificación reserva", "reserva no creada", "Los datos ingresados son invalidos", AlertType.ERROR);
        }

    }

    private void eliminarReserva() {
        boolean reservaEliminado = false;
        if (reservaSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Estas seguro de elmininar al reserva?")) {
                reservaEliminado = reservaControllerService.eliminarReserva(reservaSeleccionado.id());
                if (reservaEliminado == true) {
                    listaReservasDto.remove(reservaSeleccionado);
                    reservaSeleccionado = null;
                    tableReservas.getSelectionModel().clearSelection();
                    limpiarCamposReserva();
                    mostrarMensaje("Notificación reserva", "reserva eliminada", "La reserva se ha eliminado con éxito", AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificación reserva", "reserva no eliminada", "La reserva no se puede eliminar", AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Notificación reserva", "reserva no seleccionada", "Seleccionado una reserva de la lista", AlertType.WARNING);
        }
    }

    private void actualizarReserva() {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String cedulaActual = reservaSeleccionado.id();
        ReservaDto reservaDto = construirReservaDto();
        //2. verificar el reserva seleccionado
        if (reservaSeleccionado != null) {
            //3. Validar la información
            if (datosValidos(reservaSeleccionado)) {
                clienteActualizado = reservaControllerService.actualizarReserva(cedulaActual, reservaDto);
                if (clienteActualizado) {
                    listaReservasDto.remove(reservaSeleccionado);
                    listaReservasDto.add(reservaDto);
                    tableReservas.refresh();
                    mostrarMensaje("Notificación reserva", "reserva actualizado", "El reserva se ha actualizado con éxito", AlertType.INFORMATION);
                    limpiarCamposReserva();
                } else {
                    mostrarMensaje("Notificación reserva", "reserva no actualizado", "El reserva no se ha actualizado con éxito", AlertType.INFORMATION);
                }
            } else {
                mostrarMensaje("Notificación reserva", "reserva no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
            }

        }
    }

    private ReservaDto construirReservaDto() {
        return new ReservaDto(

                txtId.getText(),
                txtUser.getText(),
                txtEvento.getText(),
                txtFecha.getText(),
                txtEstado.getText()

        );
    }

    private void limpiarCamposReserva() {
        txtId.setText("");
        txtFecha.setText("");
        txtUser.setText("");
        txtEstado.setText("");
        txtEvento.setText("");


    }

    private boolean datosValidos(ReservaDto reservaDto) {
        String mensaje = "";
        if (reservaDto.id() == null || reservaDto.id().equals(""))
            mensaje += "El id es invalido \n";
        if (reservaDto.fechaDeSolicitud() == null || reservaDto.fechaDeSolicitud().equals(""))
            mensaje += "La fecha es invalida \n";
        if (reservaDto.usuario() == null || reservaDto.usuario().equals(""))
            mensaje += "El usuario es invalido \n";
        if (reservaDto.evento() == null || reservaDto.evento().equals(""))
            mensaje += "El evento es invalido \n";
        if (reservaDto.estado() == null || reservaDto.estado().equals(""))
            mensaje += "El estado es invalido \n";
        if (mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación cliente", "Datos invalidos", mensaje, AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType) {
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


    public ObservableList<ReservaDto> filterReserves(ObservableList<ReservaDto> listaReservasDto, ArrayList<EventoDto> event, ObservableList<ReservaDto> listaUserReservasDto) {

        for (int i = 0; i < event.size(); i++) {

            String eventE = event.get(i).name();

            for (int j = 0; j < listaReservasDto.size(); j++) {

                if (listaReservasDto.get(j).evento().equals(eventE)) {

                    listaUserReservasDto.add(listaReservasDto.get(j));
                }
            }
        }

        return listaUserReservasDto;
    }

    private ArrayList<EventoDto> getEmployeeEvent(ObservableList<EventoDto> listaEventosDto){

        ArrayList<EventoDto> currentE = new ArrayList<>();

        for (int i = 0; i < listaEventosDto.size() ; i++) {

            if(listaEventosDto.get(i).encargado().equals(this.employee)){

                currentE.add(listaEventosDto.get(i));

            }

        }
        return currentE;

    }

    @FXML
    public void goBackAction(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MoesApplication.class.getResource("MoesSelect.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}