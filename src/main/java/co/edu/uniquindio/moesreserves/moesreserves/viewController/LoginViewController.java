package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import co.edu.uniquindio.moesreserves.moesreserves.controller.UsuarioController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginViewController {

    UsuarioController usuarioControllerService;

    @FXML
    private Button loginBttn;

    @FXML
    private TextField Idretriever;

    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();
    ObservableList<String> listaIdUsuariosDto = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        obtenerUsuarios();
    }


    private void obtenerUsuarios() {

        listaUsuariosDto.addAll(usuarioControllerService.obtenerUsuarios());

        for (UsuarioDto usuarioDto : listaUsuariosDto) {

            listaIdUsuariosDto.add(usuarioDto.id());

        }

    }


    public void loginAction() {
        String enteredId = Idretriever.getText().trim();

        if ( listaIdUsuariosDto.contains(enteredId)) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MoesApplication.class.getResource("BehaviorSelect.fxml"));
                AnchorPane root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }



        } else {
            // Display a warning message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid ID");
            alert.setHeaderText(null);
            alert.setContentText("The entered ID is invalid. Please try again.");
            alert.showAndWait();


            Idretriever.clear();
        }
    }

    @FXML
    void agregarUsuarioAction(ActionEvent event) {
        loginAction();
    }







}
