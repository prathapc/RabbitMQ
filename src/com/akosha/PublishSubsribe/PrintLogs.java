package com.akosha.PublishSubsribe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class PrintLogs {

    private static final String EXCHANGE_NAME = "logs";
    private final static String QUEUE_NAME = "PrintLog_Queue";
    
    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);  //durable: so true
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //String queueName = channel.queueDeclare().getQueue(); //queue created nd given by server
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        int count = 0;
        File file = new File("D:/rabbit_error_log.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		try {
			while (true) {
	            QueueingConsumer.Delivery delivery = consumer.nextDelivery(); //channel.basicGet
	            String message = new String(delivery.getBody());
	            if(count ==2) {
	            	//Error log record
	            	bw.write(message+"\r\n");
	            	bw.flush();
	            	System.out.println("********ERROR*********");
	            	channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false, true);
	            } else {
	            	//Save to DB here
	            	 System.out.println(" [x] Received '" + message + "'");
	            	 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	            }
	            count++;
	        }
		}
        finally {
        	bw.close();
        }
    }
}