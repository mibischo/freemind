package freemind.controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import freemind.controller.Controller;
import freemind.dialog.AbstractDialog;
import freemind.main.FreeMind;
import freemind.main.Tools;
import freemind.preferences.layout.OptionPanel;
import freemind.preferences.layout.OptionPanel.OptionPanelFeedback;

/**
 * @author foltin
 * 
 */
public class PropertyAction extends AbstractAction {

	private final Controller controller;

	/**
	 *
	 */
	public PropertyAction(Controller controller) {
		super(controller.getResourceString("property_dialog"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent arg0) {
		JDialog dialog = new JDialog(controller.getFrame().getJFrame(), true /* modal */);
		dialog.setResizable(true);
		dialog.setUndecorated(false);
		final OptionPanel options = new OptionPanel((FreeMind) controller.getFrame(),
				dialog, new OptionPanelFeedback() {

					public void writeProperties(Properties props) {
						Vector sortedKeys = new Vector();
						sortedKeys.addAll(props.keySet());
						Collections.sort(sortedKeys);
						boolean propertiesChanged = false;
						for (Iterator i = sortedKeys.iterator(); i
								.hasNext();) {
							String key = (String) i.next();
							// save only changed keys:
							String newProperty = props.getProperty(key);
							propertiesChanged = propertiesChanged
									|| !newProperty.equals(controller
											.getProperty(key));
							controller.setProperty(key, newProperty);
						}

						if (propertiesChanged) {
							JOptionPane
									.showMessageDialog(
											null,
											controller.getResourceString("option_changes_may_require_restart"));
							controller.getFrame().saveProperties(false);
						}
					}
				});
		options.buildPanel();
		options.setProperties();
		dialog.setTitle("Freemind Properties");
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				options.closeWindow();
			}
		});
		Action action = new AbstractAction() {

			public void actionPerformed(ActionEvent arg0) {
				options.closeWindow();
			}
		};
		AbstractDialog.addEscapeActionToDialog(dialog, action);
		dialog.setVisible(true);

	}

}