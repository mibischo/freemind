package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

public class NavigationMoveMapLeftAction extends AbstractAction {
	Controller controller;
	
	public NavigationMoveMapLeftAction(Controller controller) {
		super(controller.getResourceString("move_map_left"), new ImageIcon(
				controller.getResource("images/draw-arrow-back.png")));
		setEnabled(false);
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		if (controller.getmTabbedPane() != null) {
			int selectedIndex = controller.getmTabbedPane().getSelectedIndex();
			int previousIndex = (selectedIndex > 0) ? (selectedIndex - 1)
					: (controller.getmTabbedPane().getTabCount() - 1);
			controller.moveTab(selectedIndex, previousIndex);
		}
	}
}