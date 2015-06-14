package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

public class ShowFilterToolbarAction extends AbstractAction {
	Controller controller;
	
	public ShowFilterToolbarAction(Controller controller) {
		super(controller.getResourceString("filter_toolbar"), new ImageIcon(
				controller.getResource("images/filter.gif")));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		if (!controller.getFilterController().isVisible()) {
			controller.getFilterController().showFilterToolbar(true);
		} else {
			controller.getFilterController().showFilterToolbar(false);
		}
	}
}