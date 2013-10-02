package com.klangner.rcs

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
import javax.swing.{UIManager}
import scala.swing.SwingApplication
import scala.collection.immutable.HashMap
  
object Main extends SwingApplication {
	UIManager.setLookAndFeel(new NimbusLookAndFeel)
	val robot = new Robot
	val actions = HashMap[String, ()=>Unit](
	    "forward"->robot.moveForward, "backward"->robot.moveBackward, 
	    "right"->robot.turnRight, "left"->robot.turnLeft, "stop"->robot.stop)
	def mainFrame = new AppWindow(actions)
	
	override def startup(args: Array[String]) {
		mainFrame.visible = true
	}	
}

