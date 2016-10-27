package com.mycompany.app;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.List;
import java.util.LinkedList;
import com.josh.utils.Tuple;
 
public final class App {
 
  public static void main(final String[] args) throws Exception {

      
      
      final List<Tuple<String, String>> edges = initEdges();

    final ConnectionFactory connFactory = new ActiveMQConnectionFactory();
    final Connection conn = connFactory.createConnection();
    final Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
    final Destination dest = sess.createQueue("SampleQueue");
    final MessageProducer prod = sess.createProducer(dest);

    Message msg;

    for(int i = 0; i < 1000; i++) {
	
	msg = sess.createTextMessage(genRandomMessage(edges));
	prod.send(msg);

	Thread.sleep(5000);
    }
 
    conn.close();
    
  }

    private static List<Tuple<String, String>> initEdges() {
	List<Tuple<String, String>> result = new LinkedList<Tuple<String, String>>();

	result.add(new Tuple<String, String>("eq", "21"));
	result.add(new Tuple<String, String>("21", "1"));

	result.add(new Tuple<String, String>("1", "2"));
 
	result.add(new Tuple<String, String>("2", "3"));
 
	result.add(new Tuple<String, String>("3", "3"));
 
	result.add(new Tuple<String, String>("3", "14"));

	result.add(new Tuple<String, String>("14", "4"));

	result.add(new Tuple<String, String>("4", "22"));

	result.add(new Tuple<String, String>("22", "5"));

	result.add(new Tuple<String, String>("5", "6"));
 
	result.add(new Tuple<String, String>("6", "7"));
 
	result.add(new Tuple<String, String>("6", "8"));
 
	result.add(new Tuple<String, String>("2", "10"));

	result.add(new Tuple<String, String>("10", "11"));
	result.add(new Tuple<String, String>("11", "12"));
	result.add(new Tuple<String, String>("11", "13"));
	result.add(new Tuple<String, String>("2", "9"));
 
	result.add(new Tuple<String, String>("1", "9"));
 
	result.add(new Tuple<String, String>("4", "9"));
 
	return result;

    }

    private static String genRandomMessage(List<Tuple<String, String>> edges)
    {

	int randInd = (int)(Math.random()*edges.size());
	Tuple<String, String> t = edges.get(randInd);
	String edgeSource = t._1;
	String edgeTarget = t._2;

	double newWeight = Math.random()*30;
	return String.format("%s|%s|%f", edgeSource, edgeTarget, newWeight);
    }
 
}
