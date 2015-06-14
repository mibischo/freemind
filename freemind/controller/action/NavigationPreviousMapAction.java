package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

public class NavigationPreviousMapAction extends AbstractAction {
	Controller controller;
	
	public NavigationPreviousMapAction(Controller controller) {
		super(controller.getResourceString("previous_map"), new ImageIcon(
				controller.getResource("images/1leftarrow.png")));
		setEnabled(false);
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		controller.getMapModuleManager().previousMapModule();
	}
}