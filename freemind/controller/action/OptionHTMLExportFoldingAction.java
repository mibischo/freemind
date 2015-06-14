package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;

public class OptionHTMLExportFoldingAction extends AbstractAction {
	Controller controller;
	
	public OptionHTMLExportFoldingAction(Controller controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.setProperty("html_export_folding", e.getActionCommand());
	}
}