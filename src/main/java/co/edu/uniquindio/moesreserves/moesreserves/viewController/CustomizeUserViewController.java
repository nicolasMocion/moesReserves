package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import co.edu.uniquindio.moesreserves.moesreserves.controller.ReservaController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.UsuarioController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

import java.util.Properties;

public class CustomizeUserViewController {

    private String user;

    public void setUser(String user) {
        this.user = user;
    }
    UsuarioController usuarioControllerService;

    ReservaController reservaControllerService;

    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();

    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();

    @FXML
    private Button btnActualizar;

    @FXML
    private Button bttnEliminar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtPass;

    @FXML
    private Button btnNuevo;

    @FXML
    private TextField txtNameu;

    @FXML
    private TextField txtIdu;

    @FXML
    private TextField txtCorreoEu;

    UsuarioDto currentUsuario;
    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        reservaControllerService = new ReservaController();
        intiView();
    }

    Properties properties = loadProperties("src/main/resources/Login.properties");


    private void intiView() {
        obtenerUsuarios();
        mostrarInformacionUsuario();
    }

    private void obtenerUsuarios() {
        listaUsuariosDto.addAll(usuarioControllerService.obtenerUsuarios());
        listaReservasDto.addAll(reservaControllerService.obtenerReservas());
        currentUsuario = getUserId(listaUsuariosDto);
    }


    private void mostrarInformacionUsuario() {
        txtNameu.setText(currentUsuario.name());
        txtIdu.setText(currentUsuario.id());
        txtCorreoEu.setText(currentUsuario.email());

    }

    @FXML
    void eliminarUsuarioAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        eliminarUsuario();
        goBackAction(event);
    }


    @FXML
    void actualizarUsuarioAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        actualizarUsuario();
        goBackAction(event);
    }

    private void eliminarUsuario() {
        boolean usuarioEliminado = false;
        if(currentUsuario != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al usuario?")){
                usuarioEliminado = usuarioControllerService.eliminarUsuario(currentUsuario.id());
                if(usuarioEliminado == true){
                    listaUsuariosDto.remove(currentUsuario);
                    currentUsuario= null;
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
        boolean usuarioActualizado = false;
        String cedulaActual = currentUsuario.id();
        String password = txtPass.getText();
        UsuarioDto upusuarioDto  = construirUsuarioDto();

            if(datosValidos(currentUsuario)){
                usuarioActualizado = usuarioControllerService.actualizarUsuario(cedulaActual,upusuarioDto);
                if(usuarioActualizado){
                    properties.setProperty(upusuarioDto.id(),password);
                    saveProperties(properties, "src/main/resources/Login.properties");
                    updateAllUserReserves(listaReservasDto, upusuarioDto.getId());
                    listaUsuariosDto.remove(currentUsuario);
                    listaUsuariosDto.add(upusuarioDto);
                    currentUsuario = upusuarioDto;

                    mostrarMensaje("Notificación usuario", "usuario actualizado", "El usuario se ha actualizado con éxito", AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación usuario", "usuario no actualizado", "El usuario no se ha actualizado con éxito", AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación usuario", "usuario no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
            }

    }

    private UsuarioDto construirUsuarioDto() {

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

    private UsuarioDto getUserId(ObservableList<UsuarioDto> usuarioCurrent){

        UsuarioDto currentU = null;

        boolean isValid = false;

        for (int i = 0; i < usuarioCurrent.size() && !isValid; i++) {

            if(usuarioCurrent.get(i).getId().equals(user)){

                currentU = usuarioCurrent.get(i);
                isValid = true;

            }

        }

        return currentU;

    }

    private static void saveProperties(Properties properties, String PROPERTY_FILE_PATH) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PROPERTY_FILE_PATH)) {
            properties.store(fileOutputStream, "New users added");
            System.out.println("Entries added to the property file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the property file: " + e.getMessage());
        }
    }

    private static Properties loadProperties(String PROPERTY_FILE_PATH) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PROPERTY_FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Error loading properties from the file: " + e.getMessage());
        }
        return properties;
    }

    private void updateAllUserReserves(ObservableList<ReservaDto> reserves, String newUser){

        for (int i = 0; i < reserves.size(); i++) {

            if ((reserves.get(i).getUser()).equals(user)){

                ReservaDto currentReserve = reserves.get(i);

                boolean clienteActualizado = false;
                String reservaActual = currentReserve.id();
                ReservaDto reservaDto = construirReservaDto(currentReserve, newUser);
                //2. verificar el reserva seleccionado
                if(currentUsuario != null){
                    //3. Validar la información
                    if(datosValidos(currentUsuario)){
                        clienteActualizado = reservaControllerService.actualizarReserva(reservaActual,reservaDto);
                        if(clienteActualizado){
                            listaReservasDto.remove(currentReserve);
                            listaReservasDto.add(reservaDto);
                        }else{
                            mostrarMensaje("Notificación reserva", "reserva no actualizado", "El reserva no se ha actualizado con éxito", AlertType.INFORMATION);
                        }
                    }else{
                        mostrarMensaje("Notificación reserva", "reserva no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
                    }

                }

            }

        }

    }

    private ReservaDto construirReservaDto(ReservaDto current, String newUser) {
        return new ReservaDto(
                current.id(),
                newUser,
                current.evento(),
                current.fechaDeSolicitud(),
                current.estado()
        );
    }

    @FXML
    public void goBackAction(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MoesApplication.class.getResource("Login.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
