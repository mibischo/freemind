package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;

public class ZoomOutAction extends AbstractAction {
	Controller controller;
	public ZoomOutAction(Controller controller) {
		super(controller.getResourceString("zoom_out"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		float currentZoom = controller.getView().getZoom();
		float lastZoom = controller.getZoomvalues()[0];
		for (int i = 0; i < controller.getZoomvalues().length; i++) {
			float val = controller.getZoomvalues()[i];
			if (val >= currentZoom) {
				controller.setZoom(lastZoom);
				return;
			}
			lastZoom = val;
		}
		controller.setZoom(lastZoom);
	}
}