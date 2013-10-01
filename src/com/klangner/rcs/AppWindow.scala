
package com.klangner.rcs

import scala.swing._
  

class AppWindow extends MainFrame {
	preferredSize = new Dimension(1000,700)
	title = "Robotic Command Station"
	val canvasPanel = new Canvas
	val logPanel = new TextArea
	  
	contents = new BoxPanel(Orientation.Horizontal) {
		contents += new  SplitPane(Orientation.Vertical){
			dividerLocation = 150
			// Commands
			leftComponent = new GridPanel(5, 1){
				contents += Button("Forward") {} 
				contents += Button("Backward") {}
				contents += Button("Turn right") {}
				contents += Button("Turn left") {}
				contents += Button("Stop") {}
				
			}
			// Robot world model
			rightComponent = new SplitPane(Orientation.Horizontal){
				dividerLocation = 500
				leftComponent = canvasPanel
				rightComponent = logPanel
			}
		}
	}
	
	centerOnScreen()
}

class Canvas extends Component{
  
}

