package com.klangner.rcs

import com.klangner.rcs.bt.BluetoothDevice


class Robot{

  val device = BluetoothDevice.connect();
  def send(message:String) = device.send(message)
  
  def moveForward(){
	  send("Forward")
  }
  
  def moveBackward(){
	  send("Backward")
  }
  
  def turnRight(){
	  send("Turn right")
  }
  
  def turnLeft(){
	  send("Turn left")
  }
  
  def stop(){
	  send("stop")
  }
  
  def receive() : String = {
	  device.receive() 
  }
  
}