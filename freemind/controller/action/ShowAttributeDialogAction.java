package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;
import freemind.modes.mindmapmode.attributeactors.AttributeManagerDialog;

public class ShowAttributeDialogAction extends AbstractAction {
	private Controller controller;
	private AttributeManagerDialog attributeDialog = null;

	public ShowAttributeDialogAction(Controller c) {
		super(c.getResourceString("attributes_dialog"), new ImageIcon(
				c.getResource("images/showAttributes.gif")));

		this.controller = c;
	}

	private AttributeManagerDialog getAttributeDialog() {
		if (attributeDialog == null) {
			attributeDialog = new AttributeManagerDialog(controller);
		}
		return attributeDialog;
	}

	public void actionPerformed(ActionEvent e) {
		if (getAttributeDialog().isVisible() == false
				&& controller.getMapModule() != null) {
			getAttributeDialog().pack();
			getAttributeDialog().show();
		}
	}
}