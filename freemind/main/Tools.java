/*
 * FreeMind - a program for creating and viewing mindmaps
 * Copyright (C) 2000-2006  Joerg Mueller, Daniel Polansky, Christian Foltin and others.
 * See COPYING for details
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package freemind.main;

//maybe move this class to another package like tools or something...

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import freemind.common.XmlBindingTools;
import freemind.controller.actions.generated.instance.XmlAction;
import freemind.tools.Holders;

/**
 * @author foltin
 * 
 */
public class Tools {
	/**
	 * 
	 */
	
	private static java.util.logging.Logger logger = null;
	static {
		logger = freemind.main.Resources.getInstance().getLogger("Tools");
	}

	/**
	 * Tests a string to be equals with "true".
	 * 
	 * @return true, iff the String is "true".
	 */
	public static boolean isPreferenceTrue(String option) {
		return Tools.safeEquals(option, "true");
	}

	/**
	 * @param string1
	 *            input (or null)
	 * @param string2
	 *            input (or null)
	 * @return true, if equal (that means: same text or both null)
	 */
	public static boolean safeEquals(String string1, String string2) {
		return (string1 != null && string2 != null && string1.equals(string2))
				|| (string1 == null && string2 == null);
	}

	public static boolean safeEquals(Object obj1, Object obj2) {
		return (obj1 != null && obj2 != null && obj1.equals(obj2))
				|| (obj1 == null && obj2 == null);
	}

	public static boolean safeEqualsIgnoreCase(String string1, String string2) {
		return (string1 != null && string2 != null && string1.toLowerCase()
				.equals(string2.toLowerCase()))
				|| (string1 == null && string2 == null);
	}
	
	public static boolean safeEquals(Holders.BooleanHolder holder, Holders.BooleanHolder holder2) {
		return (holder == null && holder2 == null)
				|| (holder != null && holder2 != null && holder.getValue() == holder2
						.getValue());
	}

	/**
	 * Removes the "TranslateMe" sign from the end of not translated texts.
	 */
	public static String removeTranslateComment(String inputString) {
		if (inputString != null
				&& inputString.endsWith(FreeMindCommon.POSTFIX_TRANSLATE_ME)) {
			// remove POSTFIX_TRANSLATE_ME:
			inputString = inputString.substring(0, inputString.length()
					- FreeMindCommon.POSTFIX_TRANSLATE_ME.length());
		}
		return inputString;
	}

	public static void restoreAntialiasing(Graphics2D g, Object renderingHint) {
		if (RenderingHints.KEY_ANTIALIASING.isCompatibleValue(renderingHint)) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, renderingHint);
		}
	}

	public static void waitForEventQueue() {
		try {
			// wait until AWT thread starts
			// final Exception e = new IllegalArgumentException("HERE");
			if (!EventQueue.isDispatchThread()) {
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						// logger.info("Waited for event queue.");
						// e.printStackTrace();
					};
				});
			} else {
				logger.warning("Can't wait for event queue, if I'm inside this queue!");
			}
		} catch (Exception e) {
			freemind.main.Resources.getInstance().logException(e);
		}
	}

	public static XmlAction deepCopy(XmlAction action) {
		return (XmlAction) XmlBindingTools.getInstance().unMarshall(XmlBindingTools.getInstance().marshall(action));
	}
	
	public static Vector getVectorWithSingleElement(Object obj) {
		Vector nodes = new Vector();
		nodes.add(obj);
		return nodes;
	}

	public static void swapVectorPositions(Vector pVector, int src, int dst) {
		if (src >= pVector.size() || dst >= pVector.size() || src < 0
				|| dst < 0) {
			throw new IllegalArgumentException("One index is out of bounds "
					+ src + ", " + dst + ", size= " + pVector.size());
		}
		pVector.set(dst, pVector.set(src, pVector.get(dst)));
	}

	public static void correctJSplitPaneKeyMap() {
		InputMap map = (InputMap) UIManager.get("SplitPane.ancestorInputMap");
		KeyStroke keyStrokeF6 = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0);
		KeyStroke keyStrokeF8 = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		map.remove(keyStrokeF6);
		map.remove(keyStrokeF8);
	}

	public static Properties copyChangedProperties(Properties props2,
			Properties defProps2) {
		Properties toBeStored = new Properties();
		for (Iterator it = props2.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (!Tools.safeEquals(props2.get(key), defProps2.get(key))) {
				toBeStored.put(key, props2.get(key));
			}
		}
		return toBeStored;
	}
}
