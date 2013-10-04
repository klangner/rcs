package com.klangner.rcs

import com.klangner.rcs.bt.BluetoothDevice

class Robot {

  val device = BluetoothDevice.connect();
  
  def moveForward(){
	  device.send("Forward")
  }
  
  def moveBackward(){
	  device.send("Backward")
  }
  
  def turnRight(){
	  device.send("Turn right")
  }
  
  def turnLeft(){
	  device.send("Turn left")
  }
  
  def stop(){
	  device.send("stop\r\n")
  }
}