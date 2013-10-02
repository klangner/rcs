
package com.klangner.rcs

import scala.swing._
import scala.swing.event.ButtonClicked
import scala.collection.immutable.HashMap
  

class AppWindow(actions:HashMap[String, ()=>Unit]) extends MainFrame {
	preferredSize = new Dimension(1000,700)
	title = "Robotic Command Station"
	object Action extends Enumeration {
		type WeekDay = Value
		val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
	}
	val forwardButton = new Button("Forward")
	val backwardButton = new Button("Backward")
	val turnRightButton = new Button("Turn right")
	val turnLeftButton = new Button("Turn left")
	val stopButton = new Button("Stop")
	val canvasPanel = new Canvas
	val logPanel = new TextArea
	  
	contents = new BoxPanel(Orientation.Horizontal) {
		contents += new  SplitPane(Orientation.Vertical){
			dividerLocation = 150
			// Commands
			leftComponent = new GridPanel(5, 1){
				contents += forwardButton 
				contents += backwardButton
				contents += turnRightButton
				contents += turnLeftButton
				contents += stopButton
				
			}
			// Robot world model
			rightComponent = new SplitPane(Orientation.Horizontal){
				dividerLocation = 500
				leftComponent = canvasPanel
				rightComponent = logPanel
			}
		}
	}
	
	listenTo(forwardButton, backwardButton, turnRightButton, turnLeftButton, stopButton)
	reactions += {
		case ButtonClicked(`forwardButton`) =>
			actions.get("forward") match {
				case Some(action) => action()
				case None =>
			}
		case ButtonClicked(`backwardButton`) =>
			actions.get("backward") match {
				case Some(action) => action()
				case None =>
			}
		case ButtonClicked(`turnRightButton`) =>
			actions.get("right") match {
				case Some(action) => action()
				case None =>
			}
		case ButtonClicked(`turnLeftButton`) =>
			actions.get("left") match {
				case Some(action) => action()
				case None =>
			}
		case ButtonClicked(`stopButton`) =>
			actions.get("stop") match {
				case Some(action) => action()
				case None =>
			}
	}
	
	centerOnScreen()
}

class Canvas extends Component{
  
}

