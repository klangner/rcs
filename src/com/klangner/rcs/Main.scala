package com.klangner.rcs

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
import javax.swing.{UIManager}
import scala.swing.SwingApplication
import scala.collection.immutable.HashMap
import javax.swing.Timer
import scala.swing.Publisher
  
case class TimerEvent() extends scala.swing.event.Event
class ScalaTimer(val delay0:Int) extends javax.swing.Timer(delay0, null) with Publisher {

  // to mimic the swing Timer interface with an easier-to-user scala closure
  def this(delay0:Int, action:(()=>Unit)) = {
    this(delay0)
    reactions += {
      case TimerEvent() => action()
    }
  }

  addActionListener(new java.awt.event.ActionListener {
    def actionPerformed(e: java.awt.event.ActionEvent) = publish(TimerEvent())
  })
}


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
	
	def loop() = {
	  val text = robot.receive
	  if(text != null) println(">" + text);
	}
	
	new ScalaTimer(100, loop).start()
}

