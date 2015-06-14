package freemind.controller.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import freemind.controller.Controller;
import freemind.main.Tools;
import freemind.modes.browsemode.BrowseMode;
import freemind.tools.UrlHelper;

public class DocumentationAction extends AbstractAction {
	Controller controller;

	public DocumentationAction(Controller controller) {
		super(controller.getResourceString("documentation"));
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			String map = controller.getFrame().getResourceString(
					"browsemode_initial_map");
			// if the current language does not provide its own translation,
			// POSTFIX_TRANSLATE_ME is appended:
			map = Tools.removeTranslateComment(map);
			URL url = null;
			if (map != null && map.startsWith(".")) {
				url = controller.localDocumentationLinkConverter.convertLocalLink(map);
			} else {
				url = UrlHelper.fileToUrl(new File(map));
			}
			final URL endUrl = url;
			// invokeLater is necessary, as the mode changing removes
			// all
			// menus (inclusive this action!).
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						controller.createNewMode(BrowseMode.MODENAME);
						controller.getModeController().load(endUrl);
					} catch (Exception e1) {
						freemind.main.Resources.getInstance().logException(
								e1);
					}
				}
			});
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			freemind.main.Resources.getInstance().logException(e1);
		}
	}
}