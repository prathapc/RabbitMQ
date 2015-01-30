package com.akosha.WorkQueue;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

	private final static String TASK_QUEUE_NAME  = "Worker_Queue";
	
	public static void main(String args[]) throws IOException {

	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

	    String message1 = "hello.";
	    String message2 = "hello..";
	    String message3 = "hello...";
	    String message4 = "hello....";
	    String message5 = "hello.....";

	    //default exchange --> ""
	    	channel.basicPublish( "", TASK_QUEUE_NAME, 
	 	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	 	            (message1).getBytes());
	    	channel.basicPublish( "", TASK_QUEUE_NAME, 
	 	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	 	            (message2).getBytes());
	    	channel.basicPublish( "", TASK_QUEUE_NAME, 
	 	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	 	            (message3).getBytes());
	    	channel.basicPublish( "", TASK_QUEUE_NAME, 
	 	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	 	            (message4).getBytes());
	    	channel.basicPublish( "", TASK_QUEUE_NAME, 
	 	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	 	            (message5).getBytes());
	 	    System.out.println(" Messages Sent ");

	    channel.close();
	    connection.close();}

}
