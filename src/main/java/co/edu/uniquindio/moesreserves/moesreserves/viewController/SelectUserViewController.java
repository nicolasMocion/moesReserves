package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;

import co.edu.uniquindio.moesreserves.moesreserves.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectUserViewController {

    private Stage primaryStage;

    private BorderPane rootLayout;
    @FXML
    private Button existBttn;

    @FXML
    private Button registerBttn;

    @FXML
    void initialize() {
        registerBttn.setOnAction(this::handleRegisterButton);
        existBttn.setOnAction(this::handleExistButton);
    }

    private void handleRegisterButton(ActionEvent event) {
        loadWindow("CrearUsuario.fxml");
    }
    private void handleExistButton(ActionEvent event) {
        loadWindow("Login.fxml");
    }


    private void loadWindow(String fxmlFile) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MoesApplication.class.getResource(fxmlFile));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}