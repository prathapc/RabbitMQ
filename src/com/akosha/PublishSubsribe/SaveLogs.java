package com.akosha.PublishSubsribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class SaveLogs {

	private static final String EXCHANGE_NAME = "logs";
	private final static String QUEUE_NAME = "SaveLog_Queue";
	
	public static void main(String[] argv)throws java.io.IOException, java.lang.InterruptedException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);

		File file = new File("D:/rabbit_log.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw); 
		try {
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				bw.write(message+"\r\n");
				bw.flush();
				System.out.println(message+" Logged to file");
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		}
		finally {
			bw.close();
		}
		
	}
}
