package com.akosha.requeing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;

public class Recieve {

	public static void main(String args[]) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare("testpulkit1", true, false, false, null);
        System.out.println("[*] waiting for messages. To exit press CTRL+C");
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume("testpulkit1",  consumer);
        int count =0;
        while(true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
               // GetResponse delivery = channel.basicGet("testpulkit1", false);   //uncomment and comment channel.basicConsume() above to get one by one
                if(delivery!=null){
                	String message= new String(delivery.getBody());
                	System.out.println("Message:"+message);
                		if(count==2)
                			channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false, true);  //2nd arg true-> reque
                		else
                			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
 	 					count++;
                }
        }
}

}
