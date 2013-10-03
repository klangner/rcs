package com.klangner.rcs.bt;


import java.io.IOException;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;

/**
 *
 * Minimal Services Search example.
 */
public class ServicesSearch {

	private static final UUID serviceUUID = new UUID(0x1101);
    private static String serviceUrl = null;

    public static void main(String[] args) throws IOException, InterruptedException {
    	String url = getService();
    	if(url != null){
	    	Connection out = Connector.open(url);
	    	System.out.println(out);
	    	out.close();
    	}
    }
    
    public static String getService() throws IOException, InterruptedException {

        // First run RemoteDeviceDiscovery and use discoved device
        RemoteDevice device = RemoteDeviceDiscovery.findDevice();
        
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
                LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, device, listener);
                serviceSearchCompletedEvent.wait();
            }
        }
        
        return serviceUrl;
    }

}

