package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.controller.Controller;
import freemind.main.Resources;
import freemind.modes.MindMap;
import freemind.modes.attributes.AttributeRegistry;
import freemind.modes.attributes.AttributeTableLayoutModel;

public class ShowSelectedAttributesAction extends AbstractAction {
	Controller controller;
	
	public ShowSelectedAttributesAction(Controller controller) {
		super(Resources.getInstance().getResourceString(
				"attributes_show_selected"));
		
		this.controller = controller;
	};

	public void actionPerformed(ActionEvent e) {
		MindMap map = controller.getMap();
		setAttributeViewType(map);
	}

	public void setAttributeViewType(MindMap map) {
		final AttributeRegistry attributes = map.getRegistry()
				.getAttributes();
		if (attributes.getAttributeViewType() != AttributeTableLayoutModel.SHOW_SELECTED) {
			attributes
					.setAttributeViewType(AttributeTableLayoutModel.SHOW_SELECTED);
		}
	}
}