package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import freemind.controller.Controller;
import freemind.controller.MenuItemSelectedListener;

public class ShowSelectionAsRectangleAction extends AbstractAction
		implements MenuItemSelectedListener {
	Controller controller;
	
	public ShowSelectionAsRectangleAction(Controller controller) {
		super(controller.getResourceString("selection_as_rectangle"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		// logger.info("ShowSelectionAsRectangleAction action Performed");
		controller.toggleSelectionAsRectangle();
	}

	public boolean isSelected(JMenuItem pCheckItem, Action pAction) {
		return controller.isSelectionAsRectangle();
	}
}