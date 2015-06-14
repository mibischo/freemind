package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

public class NavigationNextMapAction extends AbstractAction {

	Controller controller;
	
	public NavigationNextMapAction(Controller controller) {
		super(controller.getResourceString("next_map"), new ImageIcon(
				controller.getResource("images/1rightarrow.png")));
		setEnabled(false);
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		controller.getMapModuleManager().nextMapModule();
	}
}