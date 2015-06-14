package freemind.dialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import freemind.tools.IOsHelper;
import freemind.tools.OsHelper;

public abstract class AbstractDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3131789143189146086L;
	
	private static IOsHelper osHelper = new OsHelper();
	
	public AbstractDialog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Dialog owner, String title) {
		super(owner, title);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Dialog owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Frame owner, boolean modal) {
		super(owner, modal);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Frame owner, String title) {
		super(owner, title);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Frame owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Window owner, String title) {
		super(owner, title);
		// TODO Auto-generated constructor stub
	}

	public AbstractDialog(Window owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	public static void addEscapeActionToDialog(final JDialog dialog) {
		class EscapeAction extends AbstractAction {
			private static final long serialVersionUID = 238333614987438806L;

			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			};
		}
		addEscapeActionToDialog(dialog, new EscapeAction());
	}

	public static void addEscapeActionToDialog(JDialog dialog, Action action) {
		addKeyActionToDialog(dialog, action, "ESCAPE", "end_dialog");
	}

	public static void addKeyActionToDialog(JDialog dialog, Action action,
			String keyStroke, String actionId) {
		action.putValue(Action.NAME, actionId);
		// Register keystroke
		dialog.getRootPane()
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(keyStroke),
						action.getValue(Action.NAME));

		// Register action
		dialog.getRootPane().getActionMap()
				.put(action.getValue(Action.NAME), action);
	}
	
	public interface NameMnemonicHolder {
		
		/**
		 */
		String getText();

		/**
		 */
		void setText(String replaceAll);

		/**
		 */
		void setMnemonic(char charAfterMnemoSign);

		/**
		 */
		void setDisplayedMnemonicIndex(int mnemoSignIndex);


	}
	
	private static class ButtonHolder implements NameMnemonicHolder {
		private AbstractButton btn;

		public ButtonHolder(AbstractButton btn) {
			super();
			this.btn = btn;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see freemind.main.Tools.IAbstractButton#getText()
		 */
		public String getText() {
			return btn.getText();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * freemind.main.Tools.IAbstractButton#setDisplayedMnemonicIndex(int)
		 */
		public void setDisplayedMnemonicIndex(int mnemoSignIndex) {
			btn.setDisplayedMnemonicIndex(mnemoSignIndex);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see freemind.main.Tools.IAbstractButton#setMnemonic(char)
		 */
		public void setMnemonic(char charAfterMnemoSign) {
			btn.setMnemonic(charAfterMnemoSign);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see freemind.main.Tools.IAbstractButton#setText(java.lang.String)
		 */
		public void setText(String text) {
			btn.setText(text);
		}

	}

	private static class ActionHolder implements NameMnemonicHolder {
		private Action action;

		public ActionHolder(Action action) {
			super();
			this.action = action;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see freemind.main.Tools.IAbstractButton#getText()
		 */
		public String getText() {
			return action.getValue(Action.NAME).toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * freemind.main.Tools.IAbstractButton#setDisplayedMnemonicIndex(int)
		 */
		public void setDisplayedMnemonicIndex(int mnemoSignIndex) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see freemind.main.Tools.IAbstractButton#setMnemonic(char)
		 */
		public void setMnemonic(char charAfterMnemoSign) {
			int vk = (int) charAfterMnemoSign;
			if (vk >= 'a' && vk <= 'z')
				vk -= ('a' - 'A');
			action.putValue(Action.MNEMONIC_KEY, new Integer(vk));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see freemind.main.Tools.IAbstractButton#setText(java.lang.String)
		 */
		public void setText(String text) {
			action.putValue(Action.NAME, text);
		}

	}
	
	/**
	 * Ampersand indicates that the character after it is a mnemo, unless the
	 * character is a space. In "Find & Replace", ampersand does not label
	 * mnemo, while in "&About", mnemo is "Alt + A".
	 */
	public static void setLabelAndMnemonic(AbstractButton btn, String inLabel) {
		setLabelAndMnemonic(new ButtonHolder(btn), inLabel);
	}

	/**
	 * Ampersand indicates that the character after it is a mnemo, unless the
	 * character is a space. In "Find & Replace", ampersand does not label
	 * mnemo, while in "&About", mnemo is "Alt + A".
	 */
	public static void setLabelAndMnemonic(Action action, String inLabel) {
		setLabelAndMnemonic(new ActionHolder(action), inLabel);
	}

	private static void setLabelAndMnemonic(NameMnemonicHolder item,
			String inLabel) {
		String rawLabel = inLabel;
		if (rawLabel == null)
			rawLabel = item.getText();
		if (rawLabel == null)
			return;
		item.setText(removeMnemonic(rawLabel));
		int mnemoSignIndex = rawLabel.indexOf("&");
		if (mnemoSignIndex >= 0 && mnemoSignIndex + 1 < rawLabel.length()) {
			char charAfterMnemoSign = rawLabel.charAt(mnemoSignIndex + 1);
			if (charAfterMnemoSign != ' ') {
				// no mnemonics under Mac OS:
				if (!osHelper.isMacOsX()) {
					item.setMnemonic(charAfterMnemoSign);
					// sets the underline to exactly this character.
					item.setDisplayedMnemonicIndex(mnemoSignIndex);
				}
			}
		}
	}
	
	public static String removeMnemonic(String rawLabel) {
		return rawLabel.replaceFirst("&([^ ])", "$1");
	}

}
