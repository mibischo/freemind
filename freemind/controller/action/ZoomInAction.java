package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;

public class ZoomInAction extends AbstractAction {
	Controller controller;
	public ZoomInAction(Controller controller) {
		super(controller.getResourceString("zoom_in"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		// logger.info("ZoomInAction actionPerformed");
		float currentZoom = controller.getView().getZoom();
		for (int i = 0; i < controller.getZoomvalues().length; i++) {
			float val = controller.getZoomvalues()[i];
			if (val > currentZoom) {
				controller.setZoom(val);
				return;
			}
		}
		controller.setZoom(controller.getZoomvalues()[controller.getZoomvalues().length - 1]);
	}
}