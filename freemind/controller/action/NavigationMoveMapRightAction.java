package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

public class NavigationMoveMapRightAction extends AbstractAction {
	Controller controller;
	public NavigationMoveMapRightAction(Controller controller) {
		super(controller.getResourceString("move_map_right"),
				new ImageIcon(controller.getResource("images/draw-arrow-forward.png")));
		setEnabled(false);
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent event) {
		if (controller.getmTabbedPane() != null) {
			int selectedIndex = controller.getmTabbedPane().getSelectedIndex();
			int previousIndex = (selectedIndex >= controller.getmTabbedPane().getTabCount() - 1) ? 0
					: (selectedIndex + 1);
			controller.moveTab(selectedIndex, previousIndex);
		}
	}
}