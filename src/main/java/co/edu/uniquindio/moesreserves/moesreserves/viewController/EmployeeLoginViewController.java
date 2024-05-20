package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import co.edu.uniquindio.moesreserves.moesreserves.controller.EmpleadoController;
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

public class EmployeeLoginViewController {

    Properties empleados = new Properties();

    EmpleadoController empleadoControllerService;

    private String user;

    @FXML
    private Button loginBttn;

    @FXML
    private TextField Idretriever;


    @FXML
    void initialize() {
        empleadoControllerService = new EmpleadoController();
        obtenerEmpleados();
    }


    private void obtenerEmpleados() {

        try {
            empleados.load(new FileInputStream("src/main/resources/Login_employee.properties"));
        } catch (IOException e) {
            System.err.println("Error reading user data from the property file.");
            return;
        }
    }

    public void loginAction() {
        String enteredId = Idretriever.getText().trim();

        user = enteredId;

        if (empleados.containsKey(enteredId)) {
            System.out.println("Login successful!");

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MoesApplication.class.getResource("employeeJob.fxml"));

                loader.setControllerFactory(c -> {
                    EmployeeJobViewController controller = new EmployeeJobViewController();
                    controller.setEmployee(user);
                    return controller;
                });

                AnchorPane root = loader.load();
                EmployeeJobViewController controller2 = loader.getController();
                controller2.setEmployee(user);
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
    void agregarEmpleadoAction(ActionEvent event) {
        loginAction();
    }

    public String getUser() {
        return user;
    }



}
