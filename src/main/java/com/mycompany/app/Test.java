package com.mycompany.app;

import java.io.*;
import com.example.myschema.*;
import javax.xml.bind.*;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Test {

    public static void main(String []args){

    String resultString = "<ord:newOrderRequest eventId=\"10\" eventTime=\"008-09-\"9T0\":49:45\" startTime=\"014-09-19T00:18:33\" finishTime=\"006-08-19T18:\"7:14+01:00\" failTime=\"009-05-16T13:4\":\"8\" eventType=\"RESERVATION_RESPONSE\" xmlns:ord=\"http://xml.thehutgroup.com/orderEvents\">\n  <!--Optional:-->\n  <ord:link rel=\"http://www.test.com/foedere/ferant\" href=\"http://www.corp.org/et/turbine\" mediaType=\"string\"/>\n  <ord:orderNumber>string</ord:orderNumber>\n  <ord:orderLink rel=\"http://www.any.org/speluncis/profundum\" href=\"http://www.my.com/flammas/ac\" mediaType=\"string\"/>\n</ord:newOrderRequest>";

    try{

	   String packageNames = "com.example.myschema";
	   JAXBContext jaxbContext = JAXBContext.newInstance(packageNames);  
   
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
	   Object o = jaxbUnmarshaller.unmarshal(new StringReader(resultString));
          
	   if(o instanceof NewOrderRequest)
	       {
		   NewOrderRequest no = (NewOrderRequest)o;
		   System.out.println(no.getLink().getHref());
	       }
	   else {
	       System.out.printf("Could not understand message: %s\n", o);
	   }
    }catch(Exception e) { System.out.println("Could not print results"); }
	
    }

}
