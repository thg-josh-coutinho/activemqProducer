package com.mycompany.app;

import java.io.*;
import com.example.myschema.*;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.jms.Session;
import javax.xml.bind.*;
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

    private static String getStringMessage(MessageConsumer consumer){
	
	try {
	    Message msg = consumer.receive();
	    if (! (msg instanceof TextMessage)) {
		throw new RuntimeException("Expected a TextMessage");
	    }
	    return ((TextMessage) msg).getText();

	} catch (Exception e) {
	    return null;
	}
    }

    public static void consumeXMLQueue(String queueName){
	String resultString = null;
	try {
	    Connection conn = new ActiveMQConnectionFactory().createConnection();
	    conn.start();
	    Session s = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

	    
	    resultString = getStringMessage(s.createConsumer(s.createQueue(queueName)));
	} catch (Exception e) {System.out.println(e); System.exit(1);}

	try {
	   String packageNames = "com.example.myschema";
	   JAXBContext jaxbContext = JAXBContext.newInstance(packageNames, Class.forName("Consumer").getClassLoader());  
   
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
	   Object o = jaxbUnmarshaller.unmarshal(new File(""));  
          
	   if(o instanceof NewOrderRequest)
	       {
		   NewOrderRequest no = (NewOrderRequest)o;
		   System.out.println(no.getLink().getHref());
	       }
   
	} catch (Exception e) {
	    e.printStackTrace();  
	}
    }
}
