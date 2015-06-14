package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;
import freemind.main.FreeMindCommon;
import freemind.preferences.FreemindPropertyListener;

public class OptionAntialiasAction extends AbstractAction implements
		FreemindPropertyListener {
	Controller controller;
	
	public OptionAntialiasAction(Controller controller) {
		Controller.addPropertyChangeListener(this);
		
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		changeAntialias(command);
	}

	/**
     */
	public void changeAntialias(String command) {
		if (command == null) {
			return;
		}
		if (command.equals("antialias_none")) {
			controller.setAntialiasEdges(false);
			controller.setAntialiasAll(false);
		}
		if (command.equals("antialias_edges")) {
			controller.setAntialiasEdges(true);
			controller.setAntialiasAll(false);
		}
		if (command.equals("antialias_all")) {
			controller.setAntialiasEdges(true);
			controller.setAntialiasAll(true);
		}
		if (controller.getView() != null)
			controller.getView().repaint();
	}

	public void propertyChanged(String propertyName, String newValue,
			String oldValue) {
		if (propertyName.equals(FreeMindCommon.RESOURCE_ANTIALIAS)) {
			changeAntialias(newValue);
		}
	}
}