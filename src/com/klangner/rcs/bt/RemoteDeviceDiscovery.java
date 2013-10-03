package com.klangner.rcs.bt;

import java.io.IOException;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

/**
 * Minimal Device Discovery example.
 */
class RemoteDeviceDiscovery {

    private static RemoteDevice deviceDiscovered = null;

    public static void main(String[] args) throws IOException, InterruptedException {
    	RemoteDevice device = findDevice();
    	System.out.println(device);
    }
    	
    public static RemoteDevice findDevice() throws IOException, InterruptedException {

        final Object inquiryCompletedEvent = new Object();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                deviceDiscovered = btDevice;
                try {
                    System.out.println("Device " + btDevice.getBluetoothAddress() + 
                    		" name: " + btDevice.getFriendlyName(false) + " found");
                } catch (IOException cantGetDeviceName) {
                }
            }

            public void inquiryCompleted(int discType) {
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
            }
        }
        
        return deviceDiscovered;
    }

}