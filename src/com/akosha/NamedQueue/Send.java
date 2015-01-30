package com.akosha.NamedQueue;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private final static String QUEUE_NAME = "prathap";
	
	public static void main(String args[]) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		String message = "Hello World!";
		for(int i=0;i<5;i++)
			channel.basicPublish("", QUEUE_NAME,true, null, (message+i).getBytes());
		System.out.println("[x] sent'"+message+"'");
		
		channel.close();
		
		connection.close();
	}
}
