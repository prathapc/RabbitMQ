package com.akosha.messaging;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private final String QUEUE_NAME = "hello";
	
	public static void main(String args[]) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare("QUEUE_NAME", false, false, false, null);
		String message = "Hello World!";
		channel.basicPublish("", "QUEUE_NAME", null, message.getBytes());
		System.out.println("[x] sent'"+message+"'");
		
		channel.close();
		connection.close();
	}
}
