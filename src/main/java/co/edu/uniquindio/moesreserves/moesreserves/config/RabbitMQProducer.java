package co.edu.uniquindio.moesreserves.moesreserves.config;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQProducer {

    private final static String QUEUE_NAME = "Enviar Reserva";

    private final static String QUEUE_NAME2 = "Enviar respuesta";

    public static void sendUpdateMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("nicolas");
        factory.setPassword("omar56663313");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Usuario" + message + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    public static void sendResponceMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("nicolas");
        factory.setPassword("omar56663313");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME2, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME2, null, message.getBytes());
            System.out.println("Mandando Respuesta del empleado" + message + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }


}