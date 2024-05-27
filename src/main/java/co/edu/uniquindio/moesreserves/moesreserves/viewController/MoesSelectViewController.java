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
import javafx.stage.Window;

import java.io.IOException;

public class MoesSelectViewController {

    private Stage primaryStage;
    private BorderPane rootLayout;
    @FXML
    private Button adminBttn;

    @FXML
    private Button userBttn;

    @FXML
    void initialize() {
        adminBttn.setOnAction(this::handleAdminButton);
        userBttn.setOnAction(this::handleUserButton);
    }

    private void handleAdminButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        loadWindow("EmployeeAdminSelector.fxml");

    }
    private void handleUserButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        loadWindow("SelectUser.fxml");
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