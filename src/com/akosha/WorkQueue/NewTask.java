package com.akosha.WorkQueue;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private final static String TASK_QUEUE_NAME  = "hello";
	
	public static void main(String args[]) throws IOException {

	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

	    String message = "hello";

	    channel.basicPublish( "", TASK_QUEUE_NAME, 
	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	            message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");

	    channel.close();
	    connection.close();}

}
