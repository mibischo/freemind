package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import freemind.controller.Controller;
import freemind.controller.MenuBar;
import freemind.controller.MenuItemSelectedListener;

public class ToggleToolbarAction extends AbstractAction implements
		MenuItemSelectedListener {
	Controller controller;
	static java.util.logging.Logger logger;
	public ToggleToolbarAction(Controller controller) {
		super(controller.getResourceString("toggle_toolbar"));
		setEnabled(true);
		this.controller = controller;
		
		if (logger == null) {
			logger = controller.getFrame().getLogger(this.getClass().getName());
		}
	}

	public void actionPerformed(ActionEvent event) {
		controller.setToolbarVisible(!controller.isToolbarVisible());
		controller.setToolbarVisible(controller.isToolbarVisible());
	}

	public boolean isSelected(JMenuItem pCheckItem, Action pAction) {
		logger.info("ToggleToolbar was asked for selectedness.");
		return controller.isToolbarVisible();
	}
}