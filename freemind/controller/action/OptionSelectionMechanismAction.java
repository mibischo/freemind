package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;
import freemind.main.FreeMind;
import freemind.preferences.FreemindPropertyListener;

// switch auto properties for selection mechanism fc, 7.12.2003.
public class OptionSelectionMechanismAction extends AbstractAction
		implements FreemindPropertyListener {
	Controller controller;

	public OptionSelectionMechanismAction(Controller controller) {
		this.controller = controller;
		Controller.addPropertyChangeListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		changeSelection(command);
	}

	/**
     */
	private void changeSelection(String command) {
		controller.setProperty("selection_method", command);
		// and update the selection method in the NodeMouseMotionListener
		controller.getNodeMouseMotionListener().updateSelectionMethod();
		String statusBarString = controller.getResourceString(command);
		if (statusBarString != null) // should not happen
			controller.getFrame().out(statusBarString);
	}

	public void propertyChanged(String propertyName, String newValue,
			String oldValue) {
		if (propertyName.equals(FreeMind.RESOURCES_SELECTION_METHOD)) {
			changeSelection(newValue);
		}
	}
}