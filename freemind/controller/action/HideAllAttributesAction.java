package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;
import freemind.main.Resources;
import freemind.modes.MindMap;
import freemind.modes.attributes.AttributeRegistry;
import freemind.modes.attributes.AttributeTableLayoutModel;

public class HideAllAttributesAction extends AbstractAction {
	Controller controller;
	public HideAllAttributesAction(Controller controller) {
		super(Resources.getInstance().getResourceString(
				"attributes_hide_all"));
		
		this.controller = controller;
	};

	public void actionPerformed(ActionEvent e) {
		final MindMap map = controller.getMap();
		setAttributeViewType(map);
	}

	public void setAttributeViewType(final MindMap map) {
		final AttributeRegistry attributes = map.getRegistry()
				.getAttributes();
		if (attributes.getAttributeViewType() != AttributeTableLayoutModel.HIDE_ALL) {
			attributes
					.setAttributeViewType(AttributeTableLayoutModel.HIDE_ALL);
		}
	}
}