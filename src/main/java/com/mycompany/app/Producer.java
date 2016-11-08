package com.mycompany.app;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import java.nio.charset.StandardCharsets;
import java.io.*;
import com.example.myschema.*;
import javax.xml.bind.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.List;
import java.util.LinkedList;
import com.josh.utils.Tuple;
import java.util.Scanner;

import java.util.stream.Stream;
 
public final class Producer {
 
    static Session sess;
    static MessageProducer prod;

    static ObjectFactory obj;
    static Marshaller jaxbMarshaller;
    static Connection conn;

    public static void main(String []args)
    {
	init();
	for(int i = 0; i < 10; i++){
	    sendStringStream(fileStream("/events", 739, 747));
	}
    }

    public static Stream<String> fileStream(String prefix, int start, int end)
    {
	return Stream
	    .iterate(start, n -> n + 1)
	    .limit(end - start + 1)
	    .map(x -> {
		    try { 
			Scanner sc = new Scanner(
						 new File(
							  Producer.class
							  .getResource(prefix + x)
							  .toURI()));
			StringBuffer result = new StringBuffer();
			while(sc.hasNext()){
			    result.append(sc.nextLine());
			    result.append("\n");
			}
			return result.toString();
		    } catch(Exception e) { e.printStackTrace(); }
		    return "";
		});
    }

    public static void sendSimpleNewOrderRequests(int numMessages) {

      for(int i = 0; i < numMessages; i++)
      {
	  try {
	  Message msg = sess.createTextMessage(marshallValueSimpleNewOrderRequest());
	  prod.send(msg);
	  } catch(Exception e) { e.printStackTrace(); }
      }

      try { conn.close(); } catch(Exception e) { e.printStackTrace(); }

    }

    public static void init()
    {
      initConnection();
      initMarshaller();
    }

    public static void sendStringStream(Stream<String> s)
    {
	s.forEach(str -> {
		try {
		    prod.send(sess.createTextMessage(str));
		} catch(Exception e) { e.printStackTrace(); }
	    });
    }

    public static void initConnection() {
	try {
	    final ConnectionFactory connFactory = new ActiveMQConnectionFactory();
	    conn = connFactory.createConnection();
	    sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    final Destination dest = sess.createQueue("SampleQueue");
	    prod = sess.createProducer(dest);
	} catch(Exception e) { e.printStackTrace(); System.exit(1); } 
    }

    public static void initMarshaller(){
	try{
	    JAXBContext jaxbContext = JAXBContext.newInstance(NewOrderRequest.class);
	    jaxbMarshaller = jaxbContext.createMarshaller();

	    // output pretty printed
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    obj = new ObjectFactory();
	} catch(Exception e) { e.printStackTrace(); System.exit(1); } 

    }

    public static String marshallValueSimpleNewOrderRequest(){
	try {

    
	    JAXBElement<NewOrderRequest> req = obj.createNewOrderRequest(obj.createNewOrderRequest());
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
	    jaxbMarshaller.marshal(req, baos);
	    return baos.toString();
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
	

    }


}
