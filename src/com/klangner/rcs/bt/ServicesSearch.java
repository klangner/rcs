package com.klangner.rcs.bt;


import java.io.IOException;
import java.io.OutputStream;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;

/**
 *
 * Minimal Services Search example.
 */
public class ServicesSearch {

    private static String serviceUrl = null;

    public static void main(String[] args) throws IOException, InterruptedException {
    	String url = getService();
    	System.out.println(url);
    	if(url != null){
	    	OutputStream out = Connector.openOutputStream(url);
	    	String text = "hello";
	    	out.write(text.getBytes());
	    	out.close();
    	}
    }
    
    public static String getService() throws IOException, InterruptedException {

        // First run RemoteDeviceDiscovery and use discoved device
        RemoteDevice device = RemoteDeviceDiscovery.findDevice();
        UUID serviceUUID = new UUID(0x1101); // android service
        final Object serviceSearchCompletedEvent = new Object();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            }

            public void inquiryCompleted(int discType) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            	for(ServiceRecord record : servRecord){
                    String url = record.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
                    if (url == null) {
                        continue;
                    }
                    serviceUrl = url;
                    DataElement serviceName = record.getAttributeValue(0x0100);
                    if (serviceName != null) {
                        System.out.println("service " + serviceName.getValue() + " found " + url);
                    } else {
                        System.out.println("service found " + url);
                    }
                    break;
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
                System.out.println("service search completed!");
                synchronized(serviceSearchCompletedEvent){
                    serviceSearchCompletedEvent.notifyAll();
                }
            }

        };

        UUID[] searchUuidSet = new UUID[] { serviceUUID };
        int[] attrIDs =  new int[] {
                0x0100 // Service name
        };

        if(device != null) {

            synchronized(serviceSearchCompletedEvent) {
//                System.out.println("search services on " + device.getBluetoothAddress() + " " + device.getFriendlyName(false));
            	System.out.println("Test: " + device);
                LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, device, listener);
                serviceSearchCompletedEvent.wait();
            }
        }
        
        return serviceUrl;
    }

}

