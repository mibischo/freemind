package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import freemind.controller.Controller;
import freemind.controller.MenuItemSelectedListener;

public class ToggleLeftToolbarAction extends AbstractAction implements
		MenuItemSelectedListener {
	Controller controller;
	
	public ToggleLeftToolbarAction(Controller controller) {
		super(controller.getResourceString("toggle_left_toolbar"));
		setEnabled(true);
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		controller.setLeftToolbarVisible(!controller.isLeftToolbarVisible());
		controller.setLeftToolbarVisible(controller.isLeftToolbarVisible());
	}

	public boolean isSelected(JMenuItem pCheckItem, Action pAction) {
		return controller.isLeftToolbarVisible();
	}
}