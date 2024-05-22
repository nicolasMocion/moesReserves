package co.edu.uniquindio.moesreserves.moesreserves.config;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IModelFactoryService;
import co.edu.uniquindio.moesreserves.moesreserves.controller.ModelFactoryController;
import co.edu.uniquindio.moesreserves.moesreserves.utils.Persistencia;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
public class RabbitMQConsumer extends Thread {

    private ConnectionFactory connectionFactory;
    private String queueName;

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
                System.out.println("Reserva Recibida: " + message);
                ModelFactoryController.getInstance().cargarResourceXML();  // Reload XML on message received
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}