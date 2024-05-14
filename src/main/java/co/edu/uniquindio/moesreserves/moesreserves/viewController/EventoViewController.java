package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.controller.EventoController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;


public class EventoViewController {

    EventoController eventoControllerService;
    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    EventoDto eventoSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtMax;

    @FXML
    private TextField txtEncargado;

    @FXML
    private TextField txtFecha;

    @FXML
    private TableView<EventoDto> tableEventos;

    @FXML
    private TableColumn<EventoDto, String> tcName;

    @FXML
    private TableColumn<EventoDto, String> tcId;

    @FXML
    private TableColumn<EventoDto, String> tcDescription;

    @FXML
    private TableColumn<EventoDto, String> tcMax;
    @FXML
    private TableColumn<EventoDto, String> tcEncargado;
    @FXML
    private TableColumn<EventoDto, String> tcFecha;

    @FXML
    void initialize() {
        eventoControllerService = new EventoController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerEventos();
        tableEventos.getItems().clear();
        tableEventos.setItems(listaEventosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcMax.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().maxCapacity()));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().description()));
        tcEncargado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().encargado()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fecha()));

    }
    private void obtenerEventos() {
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());
    }

    private void listenerSelection() {
        tableEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
            mostrarInformacionEvento(eventoSeleccionado);
        });
    }

    private void mostrarInformacionEvento(EventoDto eventoSeleccionado) {
        if(eventoSeleccionado != null){
            txtName.setText(eventoSeleccionado.name());
            txtId.setText(eventoSeleccionado.id());
            txtMax.setText(eventoSeleccionado.maxCapacity());
            txtDescription.setText(eventoSeleccionado.description());
            txtEncargado.setText(eventoSeleccionado.encargado());
            txtFecha.setText(eventoSeleccionado.fecha());

        }
    }
    @FXML
    void nuevoEventoAction(ActionEvent event) {
        txtName.setText("Ingrese el nombre");
        txtId.setText("Ingrese la id");
        txtMax.setText("Ingrese la capacidad");
        txtDescription.setText("Ingrese la Descripcion");
        txtEncargado.setText("Ingrese el encargado");
        txtFecha.setText("Ingrese la fecha");

    }

    @FXML
    void agregarEventoAction(ActionEvent event) {
        crearEvento();
    }
    @FXML
    void eliminarEventoAction(ActionEvent event) {
        eliminarEvento();
    }
    @FXML
    void actualizarEventoAction(ActionEvent event) {
        actualizarEvento();
    }

    private void crearEvento() {
        //1. Capturar los datos
        EventoDto eventoDto = construirEventoDto();
        //2. Validar la información
        if(datosValidos(eventoDto)){
            if(eventoControllerService.agregarEvento(eventoDto)){
                listaEventosDto.add(eventoDto);
                mostrarMensaje("Notificación evento", "Evento creado", "El evento se ha creado con éxito", AlertType.INFORMATION);
                limpiarCamposEvento();
            }else{
                mostrarMensaje("Notificación evento", "Evento no creado", "El evento no se ha creado con éxito", AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación evento", "Evento no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
        }

    }

    private void eliminarEvento() {
        boolean eventoEliminado = false;
        if(eventoSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al evento?")){
                eventoEliminado = eventoControllerService.eliminarEvento(eventoSeleccionado.id());
                if(eventoEliminado == true){
                    listaEventosDto.remove(eventoSeleccionado);
                    eventoSeleccionado = null;
                    tableEventos.getSelectionModel().clearSelection();
                    limpiarCamposEvento();
                    mostrarMensaje("Notificación evento", "evento eliminado", "El evento se ha eliminado con éxito", AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación evento", "evento no eliminado", "El evento no se puede eliminar", AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación evento", "evento no seleccionado", "Seleccionado un evento de la lista", AlertType.WARNING);
        }
    }

    private void actualizarEvento() {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String cedulaActual = eventoSeleccionado.id();
        EventoDto eventoDto = construirEventoDto();
        //2. verificar el evento seleccionado
        if(eventoSeleccionado != null){
            //3. Validar la información
            if(datosValidos(eventoSeleccionado)){
                clienteActualizado = eventoControllerService.actualizarEvento(cedulaActual,eventoDto);
                if(clienteActualizado){
                    listaEventosDto.remove(eventoSeleccionado);
                    listaEventosDto.add(eventoDto);
                    tableEventos.refresh();
                    mostrarMensaje("Notificación evento", "evento actualizado", "El evento se ha actualizado con éxito", AlertType.INFORMATION);
                    limpiarCamposEvento();
                }else{
                    mostrarMensaje("Notificación evento", "evento no actualizado", "El evento no se ha actualizado con éxito", AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación evento", "evento no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
            }

        }
    }

    private EventoDto construirEventoDto() {
        return new EventoDto(
                txtName.getText(),
                txtId.getText(),
                txtMax.getText(),
                txtEncargado.getText(),
                txtDescription.getText(),
                txtFecha.getText()
        );
    }
    private void limpiarCamposEvento() {
        txtName.setText("");
        txtId.setText("");
        txtName.setText("");
        txtId.setText("");
        txtName.setText("");
        txtFecha.setText("");

    }

    private boolean datosValidos(EventoDto eventoDto) {
        String mensaje = "";
        if(eventoDto.name() == null || eventoDto.name().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(eventoDto.id() == null || eventoDto.id() .equals(""))
            mensaje += "El id es invalido \n" ;
        if(eventoDto.fecha() == null || eventoDto.fecha().equals(""))
            mensaje += "La fecha es invalida \n" ;
        if(eventoDto.maxCapacity() == null || eventoDto.maxCapacity().equals(""))
            mensaje += "La capacidad es invalida \n" ;
        if(eventoDto.description() == null || eventoDto.description().equals(""))
            mensaje += "La descripcion es invalida \n" ;
        if(eventoDto.encargado() == null || eventoDto.encargado().equals(""))
            mensaje += "El encargado es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, AlertType.WARNING);
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











}
