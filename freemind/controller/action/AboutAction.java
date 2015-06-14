package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import freemind.controller.Controller;

public class AboutAction extends AbstractAction {
	Controller controller;

	public AboutAction(Controller controller) {
		super(controller.getResourceString("about"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(controller.getView(),
				controller.getResourceString("about_text")
						+ controller.getFrame().getFreemindVersion(),
				controller.getResourceString("about"),
				JOptionPane.INFORMATION_MESSAGE);
	}
}