package com.akosha.WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Worker2 {
	private static final String QUEUE_NAME = "Worker_Queue";

	public static void main(String[] argv)
			throws java.io.IOException,
			java.lang.InterruptedException {	// TODO Auto-generated method stub

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, false, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			System.out.println(" [x] Received '" + message + "'");   
			doWork(message); 
			System.out.println(" [x] Done" );

			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	private static void doWork(String task) throws InterruptedException {
	    for (char ch: task.toCharArray()) {
	        if (ch == '.') {
	        	System.out.println("Worker2 going to sleep for a sec");
	        	Thread.sleep(1000);
	        }
	    }
	}
}