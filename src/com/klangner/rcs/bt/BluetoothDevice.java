package com.klangner.rcs.bt;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;

/**
 *
 * Device representing BT connection
 */
public class BluetoothDevice {

	private OutputStream output;
	private InputStream input;
	
	
    private BluetoothDevice(String url) {
		try {
//			input = Connector.openInputStream(url);
			output = Connector.openOutputStream(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BluetoothDevice connect(){
		try {
			String url = ServicesSearch.getService();
	    	if(url != null) return new BluetoothDevice(url);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
    	return null;
    }
	
	public void send(String message){
		try {
			output.write(message.getBytes());
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String receive(){
		String text = null;
		try {
			byte[] buffer = new byte[1024];
			input.read(buffer);
			text = new String(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
	}
	
	public void close(){
		try {
			if(output != null) output.close();
			if(input != null) input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		BluetoothDevice device = connect();
//		device.send("hello");
//		device.send("world");
//		System.out.println(device.receive());
	}
}

