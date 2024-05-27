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
        accountBttn.setOnAction(this::handleAccountButton);
    }

    private void handleViewReserveButton(ActionEvent event) {
        loadWindow("myReserves.fxml");
    }
    private void handleReservationButton(ActionEvent event) {
        loadWindow("createReserve.fxml");
    }
    private void handleAccountButton(ActionEvent event) {
        loadWindow("customizeUser.fxml");
    }


    private void loadWindow(String fxmlFile) {
        try {

            if(fxmlFile.equals("myReserves.fxml")){

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

            } else if (fxmlFile.equals("createReserve.fxml")) {
                System.out.println(user);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MoesApplication.class.getResource(fxmlFile));

                loader.setControllerFactory(c -> {
                    CreateReserveViewController controller = new CreateReserveViewController();
                    controller.setUser(user);
                    return controller;
                });

                AnchorPane root = loader.load();
                CreateReserveViewController controller2 = loader.getController();
                controller2.setUser(user);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } else if (fxmlFile.equals("customizeUser.fxml")) {
                System.out.println(user);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MoesApplication.class.getResource(fxmlFile));

                loader.setControllerFactory(c -> {
                    CustomizeUserViewController controller = new CustomizeUserViewController();
                    controller.setUser(user);
                    return controller;
                });

                AnchorPane root = loader.load();
                CustomizeUserViewController controller3 = loader.getController();
                controller3.setUser(user);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
