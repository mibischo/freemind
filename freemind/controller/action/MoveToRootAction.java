package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;

public class MoveToRootAction extends AbstractAction {
	Controller controller;
	
	public MoveToRootAction(Controller controller) {
		super(controller.getResourceString("move_to_root"));
		setEnabled(false);
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		controller.moveToRoot();
	}
}