package freemind.controller.action;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import freemind.controller.Controller;

// open faq url from freeminds page:
public class OpenURLAction extends AbstractAction {
	Controller c;
	private final String url;

	public OpenURLAction(Controller controller, String description, String url) {
		super(description, new ImageIcon(
				controller.getResource("images/Link.png")));
		c = controller;
		this.url = url;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			c.getFrame().openDocument(new URL(url));
		} catch (MalformedURLException ex) {
			c.errorMessage(c.getResourceString("url_error") + "\n" + ex);
		} catch (Exception ex) {
			c.errorMessage(ex);
		}
	}
}