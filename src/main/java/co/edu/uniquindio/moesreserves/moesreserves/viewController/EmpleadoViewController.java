package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.controller.EmpleadoController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpleadoViewController {
    EmpleadoController empleadoControllerService;
    ObservableList<EmpleadoDto> listaEmpleadosDto = FXCollections.observableArrayList();
    EmpleadoDto empleadoSeleccionado;

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
    private TextField txtCorreoE;

    @FXML
    private TableView<EmpleadoDto> tableEmpleados;

    @FXML
    private TableColumn<EmpleadoDto, String> tcName;

    @FXML
    private TableColumn<EmpleadoDto, String> tcId;

    @FXML
    private TableColumn<EmpleadoDto, String> tcCorreoE;

    @FXML
    void initialize() {
        empleadoControllerService = new EmpleadoController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerEmpleados();
        tableEmpleados.getItems().clear();
        tableEmpleados.setItems(listaEmpleadosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCorreoE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));

    }
    private void obtenerEmpleados() {
        listaEmpleadosDto.addAll(empleadoControllerService.obtenerEmpleados());
    }

    private void listenerSelection() {
        tableEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            empleadoSeleccionado = newSelection;
            mostrarInformacionEmpleado(empleadoSeleccionado);
        });
    }

    private void mostrarInformacionEmpleado(EmpleadoDto empleadoSeleccionado) {
        if(empleadoSeleccionado != null){
            txtName.setText(empleadoSeleccionado.name());
            txtId.setText(empleadoSeleccionado.id());
            txtCorreoE.setText(empleadoSeleccionado.email());
        }
    }

    @FXML
    void nuevoEmpleadoAction(ActionEvent event) {
        txtName.setText("Ingrese el nombre");
        txtId.setText("Ingrese la id");
        txtCorreoE.setText("Ingrese el correo");
    }

    @FXML
    void agregarEmpleadoAction(ActionEvent event) {
        crearEmpleado();
    }

    @FXML
    void eliminarEmpleadoAction(ActionEvent event) {
        eliminarEmpleado();
    }


    @FXML
    void actualizarEmpleadoAction(ActionEvent event) {
        actualizarEmpleado();
    }

    private void crearEmpleado() {
        //1. Capturar los datos
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        //2. Validar la información
        if(datosValidos(empleadoDto)){
            if(empleadoControllerService.agregarEmpleado(empleadoDto)){
                listaEmpleadosDto.add(empleadoDto);
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", AlertType.INFORMATION);
                limpiarCamposEmpleado();
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
        }

    }

    private void eliminarEmpleado() {
        boolean empleadoEliminado = false;
        if(empleadoSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al empleado?")){
                empleadoEliminado = empleadoControllerService.eliminarEmpleado(empleadoSeleccionado.id());
                if(empleadoEliminado == true){
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    empleadoSeleccionado = null;
                    tableEmpleados.getSelectionModel().clearSelection();
                    limpiarCamposEmpleado();
                    mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no eliminado", "El empleado no se puede eliminar", AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Seleccionado un empleado de la lista", AlertType.WARNING);
        }
    }

    private void actualizarEmpleado() {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String cedulaActual = empleadoSeleccionado.id();
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        //2. verificar el empleado seleccionado
        if(empleadoSeleccionado != null){
            //3. Validar la información
            if(datosValidos(empleadoSeleccionado)){
                clienteActualizado = empleadoControllerService.actualizarEmpleado(cedulaActual,empleadoDto);
                if(clienteActualizado){
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    listaEmpleadosDto.add(empleadoDto);
                    tableEmpleados.refresh();
                    mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", AlertType.INFORMATION);
                    limpiarCamposEmpleado();
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado con éxito", AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
            }

        }
    }

    private EmpleadoDto construirEmpleadoDto() {
        return new EmpleadoDto(
                txtName.getText(),
                txtId.getText(),
                txtCorreoE.getText()
        );
    }
    private void limpiarCamposEmpleado() {
        txtName.setText("");
        txtId.setText("");
        txtCorreoE.setText("");
    }

    private boolean datosValidos(EmpleadoDto empleadoDto) {
        String mensaje = "";
        if(empleadoDto.name() == null || empleadoDto.name().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(empleadoDto.id() == null || empleadoDto.id() .equals(""))
            mensaje += "El id es invalido \n" ;
        if(empleadoDto.email() == null || empleadoDto.email().equals(""))
            mensaje += "El email es invalido \n" ;
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
