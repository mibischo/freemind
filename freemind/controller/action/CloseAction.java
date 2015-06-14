package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;
import freemind.dialog.AbstractDialog;

/** This closes only the current map */
public class CloseAction extends AbstractAction {
	private final Controller controller;

	public CloseAction(Controller controller) {
		AbstractDialog.setLabelAndMnemonic(this,
				controller.getResourceString("close"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.close(false);
	}
}