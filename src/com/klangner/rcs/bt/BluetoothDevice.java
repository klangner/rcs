package com.klangner.rcs.bt;


import java.io.IOException;

/**
 *
 * Minimal Services Search example.
 */
public class BluetoothDevice {

	private final String url;
	
    private BluetoothDevice(String url) {
		this.url = url;
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
		
	}
}

