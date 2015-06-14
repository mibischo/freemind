package freemind.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

public class PrintAction extends AbstractAction {
		Controller controller;
		boolean isDlg;

		public PrintAction(Controller controller, boolean isDlg) {
			super(isDlg ? controller.getResourceString("print_dialog")
					: controller.getResourceString("print"), new ImageIcon(
					controller.getResource("images/fileprint.png")));
			this.controller = controller;
			setEnabled(false);
			this.isDlg = isDlg;
		}

		public void actionPerformed(ActionEvent e) {
			if (!controller.acquirePrinterJobAndPageFormat()) {
				return;
			}

			controller.getPrinterJob().setPrintable(controller.getView(), controller.getPageFormat());

			if (!isDlg || controller.getPrinterJob().printDialog()) {
				try {
					controller.getFrame().setWaitingCursor(true);
					controller.getPrinterJob().print();
					controller.storePageFormat();
				} catch (Exception ex) {
					freemind.main.Resources.getInstance().logException(ex);
				} finally {
					controller.getFrame().setWaitingCursor(false);
				}
			}
		}
	}