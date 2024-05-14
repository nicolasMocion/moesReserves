package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeAdminSelectorViewController {

    @FXML
    private Button adminBttn;

    @FXML
    private Button employeeBttn;


    @FXML
    void initialize() {
        adminBttn.setOnAction(this::handleAdminButton);
        employeeBttn.setOnAction(this::handleEmployeeButton);
    }

    private void handleAdminButton(ActionEvent event) {
        loadWindow("AdminLogin.fxml");
    }
    private void handleEmployeeButton(ActionEvent event) {
        loadWindow("EmployeeLogin.fxml");
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
