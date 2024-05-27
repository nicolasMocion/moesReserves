package co.edu.uniquindio.moesreserves.moesreserves.config;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import co.edu.uniquindio.moesreserves.moesreserves.controller.*;
import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IModelFactoryService;
import co.edu.uniquindio.moesreserves.moesreserves.utils.Persistencia;
import com.rabbitmq.client.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static co.edu.uniquindio.moesreserves.moesreserves.utils.Constantes.QUEUE_DEFINIR_RESERVA;
import static co.edu.uniquindio.moesreserves.moesreserves.utils.Constantes.QUEUE_NUEVA_PUBLICACION;

public class RabbitMQConsumer extends Thread {

    private ConnectionFactory connectionFactory;
    private String queueName;

    private UsuarioController userControllerService;
    private ReservaController reserveControllerService;
    private EventoController eventoControllerService;
    private EmpleadoController empleadoControllerService;

    private String empleadoResponse;

    public RabbitMQConsumer(ConnectionFactory connectionFactory, String queueName) {
        this.connectionFactory = connectionFactory;
        this.queueName = queueName;

    }

    @Override
    public void run() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Message Received: " + message);

                if (queueName.equals(QUEUE_NUEVA_PUBLICACION)) {
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(MoesApplication.class.getResource("aceptarReservas.fxml"));
                            AnchorPane root = loader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else if (queueName.equals("Enviar respuesta")) {
                    empleadoResponse = message;
                    Platform.runLater(() -> mostrarMensaje("Estado Reserva", "Actualizacion", "Su reserva ha sido "+message + " por el empleado, en breve se actualizará en la app" , Alert.AlertType.INFORMATION));
                }
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }


}