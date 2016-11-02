package com.mycompany.app;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Consumer {

    public static void main(String []args){
	exhaustQueue("SampleQueue");
    }

    public static void exhaustQueue(String queueName)
    {
	MessageConsumer c = null;
	try {
	Connection conn = new ActiveMQConnectionFactory().createConnection();
	conn.start();
	Session s = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

	c = s.createConsumer(s.createQueue(queueName));

	Object msg = c.receive(1000);
	int counter = 0;
	for(;msg != null; counter++) { c.receive(1000); }

	System.out.printf("Exhausted %d messages from %s\n", counter, queueName);
	} catch (Exception e) {System.out.println(e); System.exit(1);}

    }

}
