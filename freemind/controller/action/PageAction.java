package freemind.controller.action;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import freemind.controller.Controller;
import freemind.main.Resources;
import freemind.main.Tools;

public class PageAction extends AbstractAction {
	Controller controller;

	public PageAction(Controller controller) {
		super(controller.getResourceString("page"));
		this.controller = controller;
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (!controller.acquirePrinterJobAndPageFormat()) {
			return;
		}

		// Ask about custom printing settings
		final JDialog dialog = new JDialog((JFrame) controller.getFrame(),
				controller.getResourceString("printing_settings"), /* modal= */true);
		final JCheckBox fitToPage = new JCheckBox(
				controller.getResourceString("fit_to_page"), Resources.getInstance()
						.getBoolProperty("fit_to_page"));
		final JLabel userZoomL = new JLabel(controller.getResourceString("user_zoom"));
		final JTextField userZoom = new JTextField(
				controller.getProperty("user_zoom"), 3);
		userZoom.setEditable(!fitToPage.isSelected());
		final JButton okButton = new JButton();
		Tools.setLabelAndMnemonic(okButton, controller.getResourceString("ok"));
		final Tools.IntHolder eventSource = new Tools.IntHolder();
		JPanel panel = new JPanel();

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		eventSource.setValue(0);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventSource.setValue(1);
				dialog.dispose();
			}
		});
		fitToPage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				userZoom.setEditable(e.getStateChange() == ItemEvent.DESELECTED);
			}
		});

		// c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		gridbag.setConstraints(fitToPage, c);
		panel.add(fitToPage);
		c.gridy = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(userZoomL, c);
		panel.add(userZoomL);
		c.gridx = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(userZoom, c);
		panel.add(userZoom);
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 3;
		c.insets = new Insets(10, 0, 0, 0);
		gridbag.setConstraints(okButton, c);
		panel.add(okButton);
		panel.setLayout(gridbag);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setContentPane(panel);
		dialog.setLocationRelativeTo((JFrame) controller.getFrame());
		dialog.getRootPane().setDefaultButton(okButton);
		dialog.pack(); // calculate the size
		dialog.setVisible(true);

		if (eventSource.getValue() == 1) {
			controller.setProperty("user_zoom", userZoom.getText());
			controller.setProperty("fit_to_page", fitToPage.isSelected() ? "true"
					: "false");
		} else
			return;

		// Ask user for page format (e.g., portrait/landscape)
		controller.setPageFormat(controller.getPrinterJob().pageDialog(controller.getPageFormat()));
		controller.storePageFormat();
	}
}