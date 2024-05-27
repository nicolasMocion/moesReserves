package co.edu.uniquindio.moesreserves.moesreserves.viewController;


import co.edu.uniquindio.moesreserves.moesreserves.config.RabbitMQProducer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AceptarReservasViewController {

    @FXML
    private Button acceptBtn;

    @FXML
    private Button denyBttn;

    @FXML
    void aceptarAction(ActionEvent event) {
        System.out.println("Aceptada");
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        RabbitMQProducer.sendResponceMessage("Aceptada");


    }
    @FXML
    void denegarAction(ActionEvent event) {
        System.out.println("Rechazada");
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        RabbitMQProducer.sendResponceMessage("Denegada");
    }

}