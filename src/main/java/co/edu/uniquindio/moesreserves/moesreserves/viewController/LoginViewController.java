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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class LoginViewController {

    Properties users = new Properties();

    UsuarioController usuarioControllerService;

    private String user;

    @FXML
    private Button loginBttn;

    @FXML
    private TextField Idretriever;

    @FXML
    private TextField passwordRetriever;


    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        obtenerUsuarios();
    }


    private void obtenerUsuarios() {

        try {
            users.load(new FileInputStream("src/main/resources/Login.properties"));
        } catch (IOException e) {
            System.err.println("Error reading user data from the property file.");
            return;
        }
    }

    public void loginAction(ActionEvent event) {
        String enteredId = Idretriever.getText().trim();
        String enteredPassword = passwordRetriever.getText().trim();

        user = enteredId;

        if (users.containsKey(enteredId) && users.getProperty(enteredId).equals(enteredPassword)) {
            System.out.println("Login successful!");

            try {
                ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MoesApplication.class.getResource("BehaviorSelect.fxml"));
                AnchorPane root = loader.load();

                BehaviorSelectViewController controller = loader.getController();
                controller.setUser(enteredId);

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
            passwordRetriever.clear();
        }
    }

    @FXML
    void agregarUsuarioAction(ActionEvent event) {
        loginAction(event);
    }


    public String getUser() {
        return user;
    }

    @FXML
    public void goBackAction(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MoesApplication.class.getResource("SelectUser.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
