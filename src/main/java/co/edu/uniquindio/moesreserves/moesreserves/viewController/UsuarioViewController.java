package co.edu.uniquindio.moesreserves.moesreserves.viewController;


import co.edu.uniquindio.moesreserves.moesreserves.controller.ReservaController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.UsuarioController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import co.edu.uniquindio.moesreserves.moesreserves.config.*;
import co.edu.uniquindio.moesreserves.moesreserves.model.Reserva;
import com.rabbitmq.client.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static co.edu.uniquindio.moesreserves.moesreserves.utils.Constantes.QUEUE_NUEVA_PUBLICACION;

public class UsuarioViewController {
    UsuarioController usuarioControllerService;
    RabbitFactory rabbitFactory;

    Boolean restart = true;

    ConnectionFactory connectionFactory;
    Thread hiloServicioConsumer1;

    ReservaController reservaControllerService;
    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;
    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> resevesList;

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
        reservaControllerService = new ReservaController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuarios.getItems().clear();
        tableUsuarios.setItems(listaUsuariosDto);
        listenerSelection();

    }

    private void initDataBinding() {
        tcNameu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        tcIdu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCorreoEu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));

    }
    private void obtenerUsuarios() {
        listaUsuariosDto.addAll(usuarioControllerService.obtenerUsuarios());
        listaReservasDto.addAll(reservaControllerService.obtenerReservas());
    }

    private void listenerSelection() {
        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);

            if (usuarioSeleccionado != null) {

                String currentUserId = usuarioSeleccionado.getId();
                ArrayList<String> reservesId = getReservesId(listaReservasDto, currentUserId);
                resevesList.getItems().clear(); // Clear previous items
                resevesList.getItems().addAll(reservesId); // Add new items
            }

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
        restart = false;
        crearUsuario();
    }

    @FXML
    void eliminarUsuarioAction(ActionEvent event) {
        eliminarUsuario();
    }


    @FXML
    void actualizarUsuarioAction(ActionEvent event) {
        actualizarUsuario();
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

    private void eliminarUsuario() {
        boolean usuarioEliminado = false;
        if(usuarioSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al usuario?")){
                usuarioEliminado = usuarioControllerService.eliminarUsuario(usuarioSeleccionado.id());
                if(usuarioEliminado == true){
                    listaUsuariosDto.remove(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    tableUsuarios.getSelectionModel().clearSelection();
                    limpiarCamposUsuario();
                    mostrarMensaje("Notificación usuario", "usuario eliminado", "El usuario se ha eliminado con éxito", AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación usuario", "usuario no eliminado", "El usuario no se puede eliminar", AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación usuario", "usuario no seleccionado", "Seleccionado un usuario de la lista", AlertType.WARNING);
        }
    }

    private void actualizarUsuario() {
        boolean clienteActualizado = false;
        // 1. Capturar los datos
        String cedulaActual = usuarioSeleccionado.getId();
        UsuarioDto usuarioDto = construirUsuarioDto();

        System.out.println("Cedula Actual: " + cedulaActual);
        System.out.println("Usuario DTO - Name: " + usuarioDto.name() + ", ID: " + usuarioDto.id());

        // 2. Verificar el usuario seleccionado
        if (usuarioSeleccionado != null) {
            // 3. Validar la información del nuevo usuarioDto
            if (datosValidos(usuarioDto)) {
                clienteActualizado = usuarioControllerService.actualizarUsuario(cedulaActual, usuarioDto);
                if (clienteActualizado) {
                    listaUsuariosDto.remove(usuarioSeleccionado);
                    listaUsuariosDto.add(usuarioDto);
                    tableUsuarios.refresh();
                    mostrarMensaje("Notificación usuario", "usuario actualizado", "El usuario se ha actualizado con éxito", AlertType.INFORMATION);
                    limpiarCamposUsuario();
                } else {
                    mostrarMensaje("Notificación usuario", "usuario no actualizado", "El usuario no se ha actualizado con éxito", AlertType.INFORMATION);
                }
            } else {
                mostrarMensaje("Notificación usuario", "usuario no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
            }
        }
    }

    private UsuarioDto construirUsuarioDto() {

        System.out.println("Construyendo UsuarioDto - Name: " + txtNameu.getText() + ", ID: " + txtIdu.getText() + ", Email: " + txtCorreoEu.getText());


        return new UsuarioDto(

                txtNameu.getText(),
                txtIdu.getText(),
                txtCorreoEu.getText(),
                null

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


    private ArrayList<String> getReservesId(ObservableList<ReservaDto> reservas, String currentId){

        ArrayList<String> reservasId = new ArrayList<>();

        for (int i = 0; i < reservas.size() ; i++) {

            if((reservas.get(i).getUser()).equals(currentId)){

                reservasId.add(reservas.get(i).getId());
            }

        }
        return reservasId;

    }



}
