package com.akosha.NamedQueue;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;

public class Receive {
	private final static String QUEUE_NAME = "prathap";

	public static void main(String args[]) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println("[*] waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, consumer);
		while(true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if(delivery != null) {
				String message= new String(delivery.getBody());
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				System.out.println("[x] Received '"+message);
			}
		}

	}
}
