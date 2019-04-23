package it.calverDesktop.bo;

import java.util.ArrayList;
import java.util.HashMap;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class PortReader implements SerialPortEventListener {

	SerialPort serialPort;
	String canale;
	private  ArrayList<String> lista;

	
	public PortReader(SerialPort serialPort) {
		this.serialPort = serialPort;
		lista = new ArrayList<String>();
	
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR() && event.getEventValue() > 0) {
				
				try {
					
					synchronized (this){
						 byte[] buffer = serialPort.readBytes(1024);
					     String s=new String(buffer);
					     
					     String[] parts= s.split("\\r\\n");
						 for (int i = 0; i < parts.length; i++) 
						 {
							 lista.add(parts[i]);		
						 }
					}		
//				
			} catch (Exception ex) {
				System.out.println("Error in receiving string from COM-port: " + ex);
			}
		
		}

	}
	public  ArrayList<String> getMessages() {
		synchronized (this){
			ArrayList<String> result =lista;
			lista= new ArrayList<>();
			return result;
		}
	}
}

