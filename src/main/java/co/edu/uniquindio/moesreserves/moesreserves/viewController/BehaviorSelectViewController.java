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

public class BehaviorSelectViewController {

    @FXML
    private Button reserveBttn;

    @FXML
    private Button viewReserves;

    @FXML
    private Button accountBttn;

    private String user;

    public void setUser(String user) {
        this.user = user;
    }


    @FXML
    void initialize() {
        viewReserves.setOnAction(this::handleViewReserveButton);
        reserveBttn.setOnAction(this::handleReservationButton);
        //accountBttn.setOnAction(this::handleUserButton);
    }

    private void handleViewReserveButton(ActionEvent event) {
        loadWindow("myReserves.fxml");
    }
    private void handleReservationButton(ActionEvent event) {
        loadWindow("createReserve.fxml");
    }


    private void loadWindow(String fxmlFile) {
        try {

            System.out.println(user);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MoesApplication.class.getResource(fxmlFile));

            loader.setControllerFactory(c -> {
                MyReservesViewController controller = new MyReservesViewController();
                controller.setUser(user);
                return controller;
            });

            AnchorPane root = loader.load();
            MyReservesViewController controller2 = loader.getController();
            controller2.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
