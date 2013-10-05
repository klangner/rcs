package com.klangner.rcs.bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
/**
 * A simple SPP client that connects with an SPP server
 */
public class BluetoothDevice implements DiscoveryListener{
//	private static final String DEVICE_ADDRESS = "0018E40C6809"; // Arduino
	private static final String DEVICE_ADDRESS = "40B0FA0B07EF"; // Nexus
	//object used for waiting
	private static Object lock=new Object();
	private static RemoteDevice deviceDiscovered = null;
	private static String connectionURL=null;
	private PrintWriter writer;
	private BufferedReader reader;
	
	
	public static void main(String[] args) throws IOException {
		BluetoothDevice connection = connect();
		System.out.println("Send test messages");
		connection.send("Test String from SPP Client\r\n");
//		System.out.println(connection.receive());
	}
		
	public static BluetoothDevice connect() throws IOException {
		BluetoothDevice client=new BluetoothDevice();
		//display local device address and name
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		DiscoveryAgent agent = localDevice.getDiscoveryAgent();
		System.out.println("Starting device inquiry...");
		agent.startInquiry(DiscoveryAgent.GIAC, client);
		try {
			synchronized(lock){
				lock.wait();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Device Inquiry Completed. ");
		if(deviceDiscovered == null){
			System.out.println("Device not found.");
			return null;
		}
		System.out.println("Device found: "+deviceDiscovered.getFriendlyName(true));
		//check for serial service
		UUID[] uuidSet = new UUID[1];
		uuidSet[0]=new UUID(0x1101);
		System.out.println("\nSearching for service...");
		agent.searchServices(null,uuidSet,deviceDiscovered,client);
		try {
			synchronized(lock){
				lock.wait();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(connectionURL==null){
			System.out.println("Device does not support Serial Service.");
			return null;
		}
		
		return client;
	}
	
	public void send(String message){
		writer.write(message);
		writer.flush();
	}
	
	public String receive(){
		try {
			if(reader.ready()){
				return reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//methods of DiscoveryListener
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		if(btDevice.getBluetoothAddress().equals(DEVICE_ADDRESS)){
			deviceDiscovered = btDevice;
		}
		else{
			System.out.println(btDevice.getBluetoothAddress());
		}
	}
	//implement this method since services are not being discovered
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		if(servRecord!=null && servRecord.length>0){
			connectionURL=servRecord[0].getConnectionURL(0,false);
			StreamConnection streamConnection;
			try {
				streamConnection = (StreamConnection)Connector.open(connectionURL);
				writer= new PrintWriter(streamConnection.openOutputStream());
				reader=new BufferedReader(new InputStreamReader(streamConnection.openInputStream()));
				System.out.println("Service: " + connectionURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		synchronized(lock){
			lock.notify();
		}
	}
	//implement this method since services are not being discovered
	public void serviceSearchCompleted(int transID, int respCode) {
		synchronized(lock){
			lock.notify();
		}
	}
	public void inquiryCompleted(int discType) {
		synchronized(lock){
			lock.notify();
		}
	}//end method
}


