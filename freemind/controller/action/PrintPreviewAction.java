package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import freemind.controller.Controller;
import freemind.controller.printpreview.PreviewDialog;

public class PrintPreviewAction extends AbstractAction {
	Controller controller;

	public PrintPreviewAction(Controller controller) {
		super(controller.getResourceString("print_preview"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		if (!controller.acquirePrinterJobAndPageFormat()) {
			return;
		}
		PreviewDialog previewDialog = new PreviewDialog(
				controller.getResourceString("print_preview_title"),
				controller.getView());
		previewDialog.pack();
		previewDialog.setLocationRelativeTo(JOptionPane
				.getFrameForComponent(controller.getView()));
		previewDialog.setVisible(true);
	}
}