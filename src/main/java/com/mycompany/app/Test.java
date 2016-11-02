package com.mycompany.app;

import java.io.*;
import com.example.myschema.*;
import javax.xml.bind.*;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Test {

    public static void main(String []args){
	unmarshallValue();
    }


    public static void marshallValue(){
	try {

    
	    JAXBContext jaxbContext = JAXBContext.newInstance(NewOrderRequest.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    // output pretty printed
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    ObjectFactory obj = new ObjectFactory();
	    JAXBElement<NewOrderRequest> req = obj.createNewOrderRequest(obj.createNewOrderRequest());
	    jaxbMarshaller.marshal(req, System.out);

	} catch (JAXBException e) {
	    e.printStackTrace();
	}

    }


    public static void unmarshallValue(){

    String resultString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<newOrderRequest xmlns=\"http://xml.thehutgroup.com/orderEvents\" eventId=\"0\"/>";

    try{

	   String packageNames = "com.example.myschema";
	   JAXBContext jaxbContext = JAXBContext.newInstance(packageNames);  
   
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 
	   Object o = jaxbUnmarshaller.unmarshal(new StringReader(resultString));
	   Object x = ((JAXBElement)o).getValue();;


	   /*if(o instanceof NewOrderRequest)
	     {
	   NewOrderRequest no = (NewOrderRequest)o;
	   System.out.println(no.getLink().getHref());
		       }
	   else {
	       System.out.printf("Could not understand message: %s\n", o);
	       }*/
    }catch(Exception e) { e.printStackTrace(); }
	
    }

}
