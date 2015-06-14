package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import freemind.controller.Controller;
import freemind.controller.MenuItemSelectedListener;

public class ToggleMenubarAction extends AbstractAction implements
		MenuItemSelectedListener {
	
	Controller controller;
	public ToggleMenubarAction(Controller controller) {
		super(controller.getResourceString("toggle_menubar"));
		setEnabled(true);
		
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		controller.setMenubarVisible(!controller.isMenubarVisible());
		controller.setMenubarVisible(controller.isMenubarVisible());
	}

	public boolean isSelected(JMenuItem pCheckItem, Action pAction) {
		return controller.isMenubarVisible();
	}
}