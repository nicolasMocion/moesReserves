package co.edu.uniquindio.moesreserves.moesreserves.config;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitFactory {
    private ConnectionFactory connectionFactory;
    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(5672);
        this.connectionFactory.setUsername("nicolas");
        this.connectionFactory.setPassword("omar56663313");
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
