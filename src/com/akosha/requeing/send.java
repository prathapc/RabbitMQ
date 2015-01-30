package com.akosha.requeing;

import java.io.IOException;

import com.rabbitmq.client.AMQP.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class send {

	public static void main(String args[]) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        com.rabbitmq.client.Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare("testpulkit1", true, false, false, null);
        String message = "Hello World!";
        for(int i=0;i<5;i++)
                channel.basicPublish("", "testpulkit1",true, null, (message+i).getBytes());
        System.out.println("[x] sent'"+message+"'");
        
        channel.close();
        
        connection.close();
}

}
