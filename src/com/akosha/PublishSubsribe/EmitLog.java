package com.akosha.PublishSubsribe;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv)
                  throws java.io.IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout" ,true);

        String message = "msg:";
        for(int i=1;i<=4;i++) {
        	channel.basicPublish(EXCHANGE_NAME, "", null, (message+i).getBytes());
        	System.out.println(" [x] Sent '" + (message+i) + "'");
        }
        
        channel.close();
        connection.close();
    }
    //...
}