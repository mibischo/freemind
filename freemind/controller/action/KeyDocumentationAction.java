package freemind.controller.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

import javax.swing.AbstractAction;

import freemind.controller.Controller;
import freemind.controller.MenuBar;
import freemind.main.Tools;
import freemind.tools.UrlHelper;

public class KeyDocumentationAction extends AbstractAction {
	Controller controller;
	static java.util.logging.Logger logger;

	public KeyDocumentationAction(Controller controller) {
		super(controller.getResourceString("KeyDoc"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		String urlText = controller.getFrame().getResourceString(
				"pdfKeyDocLocation");
		// if the current language does not provide its own translation,
		// POSTFIX_TRANSLATE_ME is appended:
		urlText = Tools.removeTranslateComment(urlText);
		try {
			URL url = null;
			if (urlText != null && urlText.startsWith(".")) {
				url = controller.localDocumentationLinkConverter
						.convertLocalLink(urlText);
			} else {
				url = UrlHelper.fileToUrl(new File(urlText));
			}
			logger.info("Opening key docs under " + url);
			controller.getFrame().openDocument(url);
		} catch (Exception e2) {
			freemind.main.Resources.getInstance().logException(e2);
			return;
		}
	}
}