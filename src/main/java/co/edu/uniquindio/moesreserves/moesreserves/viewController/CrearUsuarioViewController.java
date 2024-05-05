package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.controller.UsuarioController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
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

public class CrearUsuarioViewController {
    UsuarioController usuarioControllerService;
    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;


    @FXML
    private TextField txtNameu;

    @FXML
    private TextField txtIdu;

    @FXML
    private TextField txtCorreoEu;

    @FXML
    private TableView<UsuarioDto> tableUsuarios;

    @FXML
    private TableColumn<UsuarioDto, String> tcNameu;

    @FXML
    private TableColumn<UsuarioDto, String> tcIdu;

    @FXML
    private TableColumn<UsuarioDto, String> tcCorreoEu;

    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();

    }


    private void initDataBinding() {
        tcNameu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        tcIdu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCorreoEu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));

    }
    private void obtenerUsuarios() {
        listaUsuariosDto.addAll(usuarioControllerService.obtenerUsuarios());
    }

    private void listenerSelection() {
        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txtNameu.setText(usuarioSeleccionado.name());
            txtIdu.setText(usuarioSeleccionado.id());
            txtCorreoEu.setText(usuarioSeleccionado.email());
        }
    }

    @FXML
    void nuevoUsuarioAction(ActionEvent event) {
        txtNameu.setText("Ingrese el nombre");
        txtIdu.setText("Ingrese la id");
        txtCorreoEu.setText("Ingrese el correo");
    }

    @FXML
    void agregarUsuarioAction(ActionEvent event) {
        crearUsuario();
    }


    private void crearUsuario() {
        //1. Capturar los datos
        UsuarioDto usuarioDto = construirUsuarioDto();
        //2. Validar la información
        if(datosValidos(usuarioDto)){
            if(usuarioControllerService.agregarUsuario(usuarioDto)){
                listaUsuariosDto.add(usuarioDto);
                mostrarMensaje("Notificación usuario", "usuario creado", "El usuario se ha creado con éxito", AlertType.INFORMATION);
                limpiarCamposUsuario();
            }else{
                mostrarMensaje("Notificación usuario", "usuario no creado", "El usuario no se ha creado con éxito", AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación usuario", "usuario no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
        }

    }


    private UsuarioDto construirUsuarioDto() {
        return new UsuarioDto(
                txtNameu.getText(),
                txtIdu.getText(),
                txtCorreoEu.getText()
        );
    }
    private void limpiarCamposUsuario() {
        txtNameu.setText("");
        txtIdu.setText("");
        txtCorreoEu.setText("");
    }

    private boolean datosValidos(UsuarioDto usuarioDto) {
        String mensaje = "";
        if(usuarioDto.name() == null || usuarioDto.name().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(usuarioDto.id() == null || usuarioDto.id() .equals(""))
            mensaje += "El id es invalido \n" ;
        if(usuarioDto.email() == null || usuarioDto.email().equals(""))
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
