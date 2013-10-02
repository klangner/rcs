
package com.klangner.rcs

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
import javax.swing.{UIManager}
import scala.swing.SwingApplication
  

object Main extends SwingApplication {
	val robot = new Robot
	def mainFrame = new AppWindow()
	UIManager.setLookAndFeel(new NimbusLookAndFeel)
	
	override def startup(args: Array[String]) {
		mainFrame.visible = true
	}	
}