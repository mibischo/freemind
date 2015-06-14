package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import freemind.controller.Controller;

public class LicenseAction extends AbstractAction {
	Controller controller;

	public LicenseAction(Controller controller) {
		super(controller.getResourceString("license"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(controller.getView(),
				controller.getResourceString("license_text"),
				controller.getResourceString("license"),
				JOptionPane.INFORMATION_MESSAGE);
	}
}