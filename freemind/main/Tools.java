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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.Paper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import freemind.common.XmlBindingTools;
import freemind.controller.actions.generated.instance.CompoundAction;
import freemind.controller.actions.generated.instance.XmlAction;
import freemind.modes.MindMapNode;
import freemind.modes.mindmapmode.MindMapController;
import freemind.tools.Base64Coding;
import freemind.view.mindmapview.NodeView;

/**
 * @author foltin
 * 
 */
public class Tools {
	/**
	 * 
	 */
	public static final String FREEMIND_LIB_FREEMIND_JAR = "lib/freemind.jar";

	private static java.util.logging.Logger logger = null;
	static {
		logger = freemind.main.Resources.getInstance().getLogger("Tools");
	}

	public static final String CONTENTS_JAVA_FREEMIND_JAR = "Contents/Java/freemind.jar";

	public static final String FREE_MIND_APP_CONTENTS_RESOURCES_JAVA = "Contents/Resources/Java/";

	// public static final Set executableExtensions = new HashSet ({ "exe",
	// "com", "vbs" });

	// The Java programming language provides a shortcut syntax for creating and
	// initializing an array. Here's an example of this syntax:
	// boolean[] answers = { true, false, true, true, false };

	public static final Set executableExtensions = new HashSet(
			Arrays.asList(new String[] { "exe", "com", "vbs", "bat", "lnk" }));

	private static Set availableFontFamilyNames = null; // Keep set of platform

	private static String sEnvFonts[] = null;

	// bug fix from Dimitri.
	public static Random ran = new Random();

	// fonts

	public static boolean executableByExtension(String file) {
		return executableExtensions.contains(getExtension(file));
	}
	
	

	public static Set getAvailableFontFamilyNames() {
		if (availableFontFamilyNames == null) {
			String[] envFonts = getAvailableFonts();
			availableFontFamilyNames = new HashSet();
			for (int i = 0; i < envFonts.length; i++) {
				availableFontFamilyNames.add(envFonts[i]);
			}
			// Add this one explicitly, Java defaults to it if the font is not
			availableFontFamilyNames.add("dialog");
		}
		return availableFontFamilyNames;
	}

	/**
     */
	private static String[] getAvailableFonts() {
		if (sEnvFonts == null) {
			GraphicsEnvironment gEnv = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			sEnvFonts = gEnv.getAvailableFontFamilyNames();
		}
		return sEnvFonts;
	}

	public static Vector getAvailableFontFamilyNamesAsVector() {
		String[] envFonts = getAvailableFonts();
		Vector availableFontFamilyNames = new Vector();
		for (int i = 0; i < envFonts.length; i++) {
			availableFontFamilyNames.add(envFonts[i]);
		}
		return availableFontFamilyNames;
	}

	public static boolean isAvailableFontFamily(String fontFamilyName) {
		return getAvailableFontFamilyNames().contains(fontFamilyName);
	}

	/**
	 * Returns the lowercase of the extension of a file. Example:
	 * getExtension("fork.pork.MM") ==
	 * freemind.main.FreeMindCommon.FREEMIND_FILE_EXTENSION_WITHOUT_DOT
	 */
	public static String getExtension(File f) {
		return getExtension(f.toString());
	}

	/**
	 * Returns the lowercase of the extension of a file name. Example:
	 * getExtension("fork.pork.MM") ==
	 * freemind.main.FreeMindCommon.FREEMIND_FILE_EXTENSION_WITHOUT_DOT
	 */
	public static String getExtension(String s) {
		int i = s.lastIndexOf('.');
		return (i > 0 && i < s.length() - 1) ? s.substring(i + 1).toLowerCase()
				.trim() : "";
	}

	public static boolean isAbsolutePath(String path) {
		// On Windows, we cannot just ask if the file name starts with file
		// separator.
		// If path contains ":" at the second position, then it is not relative,
		// I guess.
		// However, if it starts with separator, then it is absolute too.

		// Possible problems: Not tested on Macintosh, but should work.
		// Koh, 1.4.2004: Resolved problem: I tested on Mac OS X 10.3.3 and
		// worked.

		String osNameStart = System.getProperty("os.name").substring(0, 3);
		String fileSeparator = System.getProperty("file.separator");
		if (osNameStart.equals("Win")) {
			return ((path.length() > 1) && path.substring(1, 2).equals(":"))
					|| path.startsWith(fileSeparator);
		} else if (osNameStart.equals("Mac")) {
			// Koh:Panther (or Java 1.4.2) may change file path rule
			return path.startsWith(fileSeparator);
		} else {
			return path.startsWith(fileSeparator);
		}
	}

	/**
	 * This is a correction of a method getFile of a class URL. Namely, on
	 * Windows it returned file paths like /C: etc., which are not valid on
	 * Windows. This correction is heuristic to a great extend. One of the
	 * reasons is that file:// is basically no protocol at all, but rather
	 * something every browser and every system uses slightly differently.
	 */
	public static String urlGetFile(URL url) {
		if (isWindows() && isFile(url)) {
			String fileName = url.toString().replaceFirst("^file:", "")
					.replace('/', '\\');
			return (fileName.indexOf(':') >= 0) ? fileName.replaceFirst(
					"^\\\\*", "") : fileName;
		} // Network path
		else {
			return url.getFile();
		}
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").substring(0, 3).equals("Win");
	}

	public static boolean isFile(URL url) {
		return url.getProtocol().equals("file");
	}

	/**
	 * @return "/" for absolute file names under Unix, "c:\\" or similar under
	 *         windows, null otherwise
	 */
	public static String getPrefix(String pFileName) {
		if (isWindows()) {
			if (pFileName.matches("^[a-zA-Z]:\\\\.*")) {
				return pFileName.substring(0, 3);
			}
		} else {
			if (pFileName.startsWith(File.separator)) {
				return File.separator;
			}
		}
		return null;
	}

	/**
	 * This method converts an absolute url to an url relative to a given
	 * base-url. Something like this should be included in the librarys, but I
	 * couldn't find it. You can create a new absolute url with
	 * "new URL(URL context, URL relative)".
	 */
	public static String toRelativeURL(URL base, URL target) {
		// Precondition: If URL is a path to folder, then it must end with '/'
		// character.
		if (base == null || !base.getProtocol().equals(target.getProtocol())
				|| !base.getHost().equals(target.getHost())) {
			return target.toString();
		}
		String baseString = base.getFile();
		String targetString = target.getFile();
		String result = "";
		// remove filename from URL
		targetString = targetString.substring(0,
				targetString.lastIndexOf("/") + 1);
		// remove filename from URL
		baseString = baseString.substring(0, baseString.lastIndexOf("/") + 1);

		// Algorithm
		// look for same start:
		int index = targetString.length() - 1;
		while (!baseString.startsWith(targetString.substring(0, index + 1))) {
			// remove last part:
			index = targetString.lastIndexOf("/", index - 1);
			if (index < 0) {
				// no common part. This is strange, as both should start with /,
				// but...
				break;
			}
		}

		// now, baseString is targetString + "/" + rest. we determine
		// rest=baseStringRest now.
		String baseStringRest = baseString
				.substring(index, baseString.length());

		// Maybe this causes problems under windows
		StringTokenizer baseTokens = new StringTokenizer(baseStringRest, "/");

		// Maybe this causes problems under windows
		StringTokenizer targetTokens = new StringTokenizer(
				targetString.substring(index + 1), "/");

		String nextTargetToken = "";

		while (baseTokens.hasMoreTokens()) {
			result = result.concat("../");
			baseTokens.nextToken();
		}
		while (targetTokens.hasMoreTokens()) {
			nextTargetToken = targetTokens.nextToken();
			result = result.concat(nextTargetToken + "/");
		}

		String temp = target.getFile();
		result = result.concat(temp.substring(temp.lastIndexOf("/") + 1,
				temp.length()));
		return result;
	}

	/**
	 * If the preferences say, that links should be relative, a relative url is
	 * returned.
	 * 
	 * @param input
	 *            the file that is treated
	 * @param pMapFile
	 *            the file, that input is made relative to
	 * @return in case of trouble the absolute path.
	 */
	public static String fileToRelativeUrlString(File input, File pMapFile) {
		URL link;
		String relative;
		try {
			link = Tools.fileToUrl(input);
			relative = link.toString();
			if ("relative".equals(Resources.getInstance().getProperty("links"))) {
				// Create relative URL
				relative = Tools.toRelativeURL(Tools.fileToUrl(pMapFile), link);
			}
			return relative;
		} catch (MalformedURLException ex) {
			freemind.main.Resources.getInstance().logException(ex);
		}
		return input.getAbsolutePath();
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

	public static boolean safeEquals(Color color1, Color color2) {
		return (color1 != null && color2 != null && color1.equals(color2))
				|| (color1 == null && color2 == null);
	}

	public static void setHidden(File file, boolean hidden,
			boolean synchronously) {
		// According to Web articles, UNIX systems do not have attribute hidden
		// in general, rather, they consider files starting with . as hidden.
		String osNameStart = System.getProperty("os.name").substring(0, 3);
		if (osNameStart.equals("Win")) {
			try {
				Runtime.getRuntime().exec(
						"attrib " + (hidden ? "+" : "-") + "H \""
								+ file.getAbsolutePath() + "\"");
				// Synchronize the effect, because it is asynchronous in
				// general.
				if (!synchronously) {
					return;
				}
				int timeOut = 10;
				while (file.isHidden() != hidden && timeOut > 0) {
					Thread.sleep(10/* miliseconds */);
					timeOut--;
				}
			} catch (Exception e) {
				freemind.main.Resources.getInstance().logException(e);
			}
		}
	}

	/**
	 * Extracts a long from xml. Only useful for dates.
	 */
	public static Date xmlToDate(String xmlString) {
		try {
			return new Date(Long.valueOf(xmlString).longValue());
		} catch (Exception e) {
			return new Date(System.currentTimeMillis());
		}
	}

	public static String dateToString(Date date) {
		return Long.toString(date.getTime());
	}

	public static boolean safeEquals(BooleanHolder holder, BooleanHolder holder2) {
		return (holder == null && holder2 == null)
				|| (holder != null && holder2 != null && holder.getValue() == holder2
						.getValue());
	}

	public static void setDialogLocationRelativeTo(JDialog dialog, Component c) {
		if (c == null) {
			// perhaps, the component is not yet existing.
			return;
		}
		if (c instanceof NodeView) {
			final NodeView nodeView = (NodeView) c;
			nodeView.getMap().scrollNodeToVisible(nodeView);
			c = nodeView.getMainView();
		}
		final Point compLocation = c.getLocationOnScreen();
		final int cw = c.getWidth();
		final int ch = c.getHeight();

		final Container parent = dialog.getParent();
		final Point parentLocation = parent.getLocationOnScreen();
		final int pw = parent.getWidth();
		final int ph = parent.getHeight();

		final int dw = dialog.getWidth();
		final int dh = dialog.getHeight();

		final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = defaultToolkit.getScreenSize();
		final Insets screenInsets = defaultToolkit.getScreenInsets(dialog
				.getGraphicsConfiguration());

		final int minX = Math.max(parentLocation.x, screenInsets.left);
		final int minY = Math.max(parentLocation.y, screenInsets.top);

		final int maxX = Math.min(parentLocation.x + pw, screenSize.width
				- screenInsets.right);
		final int maxY = Math.min(parentLocation.y + ph, screenSize.height
				- screenInsets.bottom);

		int dx, dy;

		if (compLocation.x + cw < minX) {
			dx = minX;
		} else if (compLocation.x > maxX) {
			dx = maxX - dw;
		} else // component X on screen
		{
			final int leftSpace = compLocation.x - minX;
			final int rightSpace = maxX - (compLocation.x + cw);
			if (leftSpace > rightSpace) {
				if (leftSpace > dw) {
					dx = compLocation.x - dw;
				} else {
					dx = minX;
				}
			} else {
				if (rightSpace > dw) {
					dx = compLocation.x + cw;
				} else {
					dx = maxX - dw;
				}
			}
		}

		if (compLocation.y + ch < minY) {
			dy = minY;
		} else if (compLocation.y > maxY) {
			dy = maxY - dh;
		} else // component Y on screen
		{
			final int topSpace = compLocation.y - minY;
			final int bottomSpace = maxY - (compLocation.y + ch);
			if (topSpace > bottomSpace) {
				if (topSpace > dh) {
					dy = compLocation.y - dh;
				} else {
					dy = minY;
				}
			} else {
				if (bottomSpace > dh) {
					dy = compLocation.y + ch;
				} else {
					dy = maxY - dh;
				}
			}
		}

		dialog.setLocation(dx, dy);
	}

	/**
	 * Creates a reader that pipes the input file through a XSLT-Script that
	 * updates the version to the current.
	 * 
	 * @throws IOException
	 */
	public static Reader getUpdateReader(Reader pReader, String xsltScript,
			FreeMindMain frame) throws IOException {
		StringWriter writer = null;
		InputStream inputStream = null;
		final java.util.logging.Logger logger = frame.getLogger(Tools.class
				.getName());
		logger.info("Updating the reader " + pReader
				+ " to the current version.");
		boolean successful = false;
		String errorMessage = null;
		try {
			// try to convert map with xslt:
			URL updaterUrl = null;
			updaterUrl = frame.getResource(xsltScript);
			if (updaterUrl == null) {
				throw new IllegalArgumentException(xsltScript + " not found.");
			}
			inputStream = updaterUrl.openStream();
			final Source xsltSource = new StreamSource(inputStream);
			// get output:
			writer = new StringWriter();
			final Result result = new StreamResult(writer);

			String fileContents = getFile(pReader);
			if (fileContents.length() > 10) {
				logger.info("File start before UTF8 replacement: '"
						+ fileContents.substring(0, 9) + "'");
			}
			fileContents = replaceUtf8AndIllegalXmlChars(fileContents);
			if (fileContents.length() > 10) {
				logger.info("File start after UTF8 replacement: '"
						+ fileContents.substring(0, 9) + "'");
			}
			final StreamSource sr = new StreamSource(new StringReader(
					fileContents));
			// Dimitry: to avoid a memory leak and properly release resources
			// after the XSLT transformation
			// everything should run in own thread. Only after the thread dies
			// the resources are released.
			class TransformerRunnable implements Runnable {
				private boolean successful = false;
				private String errorMessage;

				public void run() {
					// create an instance of TransformerFactory
					TransformerFactory transFact = TransformerFactory
							.newInstance();
					logger.info("TransformerFactory class: "
							+ transFact.getClass());
					Transformer trans;
					try {
						trans = transFact.newTransformer(xsltSource);
						trans.transform(sr, result);
						successful = true;
					} catch (Exception ex) {
						freemind.main.Resources.getInstance().logException(ex);
						errorMessage = ex.toString();
					}
				}

				public boolean isSuccessful() {
					return successful;
				}

				public String getErrorMessage() {
					return errorMessage;
				}
			}
			final TransformerRunnable transformer = new TransformerRunnable();
			Thread transformerThread = new Thread(transformer, "XSLT");
			transformerThread.start();
			transformerThread.join();
			logger.info("Updating the reader " + pReader
					+ " to the current version. Done."); // +
															// writer.getBuffer().toString());
			successful = transformer.isSuccessful();
			errorMessage = transformer.getErrorMessage();
		} catch (Exception ex) {
			Resources.getInstance().logException(ex, xsltScript);
			errorMessage = ex.getLocalizedMessage();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
		if (successful) {
			String content = writer.getBuffer().toString();
			// logger.info("Content before transformation: " + content);
			String replacedContent = Tools
					.replaceUtf8AndIllegalXmlChars(content);
			// logger.info("Content after transformation: " + replacedContent);
			return new StringReader(replacedContent);
		} else {
			return new StringReader("<map><node TEXT='"
					+ HtmlTools.toXMLEscapedText(errorMessage) + "'/></map>");
		}
	}

	public static String replaceUtf8AndIllegalXmlChars(String fileContents) {
		return HtmlTools.removeInvalidXmlCharacters(fileContents);
	}

	/**
	 * Creates a default reader that just reads the given file.
	 * 
	 * @throws FileNotFoundException
	 */
	public static Reader getActualReader(Reader pReader)
			throws FileNotFoundException {
		return new BufferedReader(pReader);
	}

	/**
	 * In case of trouble, the method returns null.
	 * 
	 * @param pInputFile
	 *            the file to read.
	 * @return the complete content of the file. or null if an exception has
	 *         occured.
	 */
	public static String getFile(File pInputFile) {
		try {
			return getFile(getReaderFromFile(pInputFile));
		} catch (FileNotFoundException e) {
			freemind.main.Resources.getInstance().logException(e);
			return null;
		}
	}

	public static Reader getReaderFromFile(File pInputFile)
			throws FileNotFoundException {
		return new FileReader(pInputFile);
	}

	public static String getFile(Reader pReader) {
		StringBuffer lines = new StringBuffer();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(pReader);
			final String endLine = System.getProperty("line.separator");
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.append(line).append(endLine);
			}
			bufferedReader.close();
		} catch (Exception e) {
			freemind.main.Resources.getInstance().logException(e);
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					freemind.main.Resources.getInstance().logException(ex);
				}
			}
			return null;
		}
		return lines.toString();
	}

	public static void logTransferable(Transferable t) {
		System.err.println();
		System.err.println("BEGIN OF Transferable:\t" + t);
		DataFlavor[] dataFlavors = t.getTransferDataFlavors();
		for (int i = 0; i < dataFlavors.length; i++) {
			System.out.println("  Flavor:\t" + dataFlavors[i]);
			System.out.println("    Supported:\t"
					+ t.isDataFlavorSupported(dataFlavors[i]));
			try {
				System.out.println("    Content:\t"
						+ t.getTransferData(dataFlavors[i]));
			} catch (Exception e) {
			}
		}
		System.err.println("END OF Transferable");
		System.err.println();
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

	/**
	 * Returns the same URL as input with the addition, that the reference part
	 * "#..." is filtered out.
	 * 
	 * @throws MalformedURLException
	 */
	public static URL getURLWithoutReference(URL input)
			throws MalformedURLException {
		return new URL(input.toString().replaceFirst("#.*", ""));
	}

	public static void copyStream(InputStream in, OutputStream out,
			boolean pCloseOutput) throws IOException {
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		if (pCloseOutput) {
			out.close();
		}
	}

	public static Point convertPointToAncestor(Component c, Point p,
			Component destination) {
		int x, y;
		while (c != destination) {
			x = c.getX();
			y = c.getY();

			p.x += x;
			p.y += y;

			c = c.getParent();
		}
		return p;

	}

	public static void convertPointFromAncestor(Component source, Point p,
			Component c) {
		int x, y;
		while (c != source) {
			x = c.getX();
			y = c.getY();

			p.x -= x;
			p.y -= y;

			c = c.getParent();
		}
		;

	}

	public static void convertPointToAncestor(Component source, Point point,
			Class ancestorClass) {
		Component destination = SwingUtilities.getAncestorOfClass(
				ancestorClass, source);
		convertPointToAncestor(source, point, destination);
	}

	interface NameMnemonicHolder {

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

	public static class MindMapNodePair {
		MindMapNode first;

		MindMapNode second;

		public MindMapNodePair(MindMapNode first, MindMapNode second) {
			this.first = first;
			this.second = second;
		}

		public MindMapNode getCorresponding() {
			return first;
		}

		public MindMapNode getCloneNode() {
			return second;
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
				if (!isMacOsX()) {
					item.setMnemonic(charAfterMnemoSign);
					// sets the underline to exactly this character.
					item.setDisplayedMnemonicIndex(mnemoSignIndex);
				}
			}
		}
	}

	public static boolean isMacOsX() {
		boolean underMac = false;
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS")) {
			underMac = true;
		}
		return underMac;
	}

	public static boolean isLinux() {
		boolean underLinux = false;
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Linux")) {
			underLinux = true;
		}
		return underLinux;
	}

	public static String removeMnemonic(String rawLabel) {
		return rawLabel.replaceFirst("&([^ ])", "$1");
	}

	public static KeyStroke getKeyStroke(final String keyStrokeDescription) {
		if (keyStrokeDescription == null) {
			return null;
		}
		final KeyStroke keyStroke = KeyStroke
				.getKeyStroke(keyStrokeDescription);
		if (keyStroke != null)
			return keyStroke;
		return KeyStroke.getKeyStroke("typed " + keyStrokeDescription);
	}

	public static final String JAVA_VERSION = System
			.getProperty("java.version");

	public static URL fileToUrl(File pFile) throws MalformedURLException {
		if (pFile == null)
			return null;
		return pFile.toURI().toURL();
	}

	public static boolean isBelowJava6() {
		return JAVA_VERSION.compareTo("1.6.0") < 0;
	}

	public static boolean isAboveJava4() {
		return JAVA_VERSION.compareTo("1.4.0") > 0;
	}

	public static File urlToFile(URL pUrl) throws URISyntaxException {
		// fix for java1.4 and java5 only.
		if (isBelowJava6()) {
			return new File(urlGetFile(pUrl));
		}
		return new File(new URI(pUrl.toString()));
	}

	public static void restoreAntialiasing(Graphics2D g, Object renderingHint) {
		if (RenderingHints.KEY_ANTIALIASING.isCompatibleValue(renderingHint)) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, renderingHint);
		}
	}

	public static String getFileNameProposal(MindMapNode node) {
		String rootText = node.getPlainTextContent();
		rootText = rootText.replaceAll("[&:/\\\\\0%$#~\\?\\*]+", "");
		return rootText;
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

	/**
	 * Logs the stacktrace via a dummy exception.
	 */
	public static void printStackTrace() {
		freemind.main.Resources.getInstance().logException(
				new IllegalArgumentException("HERE"));
	}

	/**
	 * Logs the stacktrace into a string.
	 */
	public static String getStackTrace() {
		IllegalArgumentException ex = new IllegalArgumentException("HERE");
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(b));
		return b.toString();
	}

	/**
	 * Adapts the font size inside of a component to the zoom
	 * 
	 * @param c
	 *            component
	 * @param zoom
	 *            zoom factor
	 * @param normalFontSize
	 *            "unzoomed" normal font size.
	 * @return a copy of the input font (if the size was effectively changed)
	 *         with the correct scale.
	 */
	public static Font updateFontSize(Font font, float zoom, int normalFontSize) {
		if (font != null) {
			float oldFontSize = font.getSize2D();
			float newFontSize = normalFontSize * zoom;
			if (oldFontSize != newFontSize) {
				font = font.deriveFont(newFontSize);
			}
		}
		return font;
	}

	public static String compareText(String pText1, String pText2) {
		if (pText1 == null || pText2 == null) {
			return "One of the Strings is null " + pText1 + ", " + pText2;
		}
		StringBuffer b = new StringBuffer();
		if (pText1.length() > pText2.length()) {
			b.append("First string is longer :"
					+ pText1.substring(pText2.length()) + "\n");
		}
		if (pText1.length() < pText2.length()) {
			b.append("Second string is longer :"
					+ pText2.substring(pText1.length()) + "\n");
		}
		for (int i = 0; i < Math.min(pText1.length(), pText2.length()); i++) {
			if (pText1.charAt(i) != pText2.charAt(i)) {
				b.append("Difference at " + i + ": " + pText1.charAt(i) + "!="
						+ pText2.charAt(i) + "\n");
			}

		}
		return b.toString();
	}

	public static String getHostName() {
		String hostname = "UNKNOWN";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
		}
		return hostname;
	}

	public static String getUserName() {
		// Get host name
		String hostname = getHostName();
		return System.getProperty("user.name") + "@" + hostname;
	}

	public static String marshall(XmlAction action) {
		return XmlBindingTools.getInstance().marshall(action);
	}

	public static XmlAction unMarshall(String inputString) {
		return XmlBindingTools.getInstance().unMarshall(inputString);
	}

	public static String getFileNameFromRestorable(String restoreable) {
		StringTokenizer token = new StringTokenizer(restoreable, ":");
		String fileName;
		if (token.hasMoreTokens()) {
			token.nextToken();
			// fix for windows (??, fc, 25.11.2005).
			fileName = token.nextToken("").substring(1);
		} else {
			fileName = null;
		}
		return fileName;
	}

	public static String getModeFromRestorable(String restoreable) {
		StringTokenizer token = new StringTokenizer(restoreable, ":");
		String mode;
		if (token.hasMoreTokens()) {
			mode = token.nextToken();
		} else {
			mode = null;
		}
		return mode;
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

	public static Object getField(Object[] pObjects, String pField)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
		for (int i = 0; i < pObjects.length; i++) {
			Object object = pObjects[i];
			for (int j = 0; j < object.getClass().getFields().length; j++) {
				Field f = object.getClass().getFields()[j];
				if (Tools.safeEquals(pField, f.getName())) {
					return object.getClass().getField(pField).get(object);
				}
			}
		}
		return null;
	}

	public static boolean isUnix() {
		return (File.separatorChar == '/') || isMacOsX();
	}

	// {{{ setPermissions() method
	/**
	 * Sets numeric permissions of a file. On non-Unix platforms, does nothing.
	 * From jEdit
	 */
	public static void setPermissions(String path, int permissions) {

		if (permissions != 0) {
			if (isUnix()) {
				String[] cmdarray = { "chmod",
						Integer.toString(permissions, 8), path };

				try {
					Process process = Runtime.getRuntime().exec(cmdarray);
					process.getInputStream().close();
					process.getOutputStream().close();
					process.getErrorStream().close();
					// Jun 9 2004 12:40 PM
					// waitFor() hangs on some Java
					// implementations.
					/*
					 * int exitCode = process.waitFor(); if(exitCode != 0)
					 * Log.log
					 * (Log.NOTICE,FileVFS.class,"chmod exited with code " +
					 * exitCode);
					 */
				}

				// Feb 4 2000 5:30 PM
				// Catch Throwable here rather than Exception.
				// Kaffe's implementation of Runtime.exec throws
				// java.lang.InternalError.
				catch (Throwable t) {
				}
			}
		}
	} // }}}

	public static String arrayToUrls(String[] pArgs) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < pArgs.length; i++) {
			String fileName = pArgs[i];
			try {
				b.append(fileToUrl(new File(fileName)));
				b.append('\n');
			} catch (MalformedURLException e) {
				freemind.main.Resources.getInstance().logException(e);
			}
		}
		return b.toString();
	}

	public static Vector/* <URL> */urlStringToUrls(String pUrls) {
		String[] urls = pUrls.split("\n");
		Vector ret = new Vector();
		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];
			try {
				ret.add(new URL(url));
			} catch (MalformedURLException e) {
				freemind.main.Resources.getInstance().logException(e);
			}
		}
		return ret;
	}

	/**
	 * @return
	 */
	public static boolean isHeadless() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadless();
	}

	/**
	 * @param pNode
	 * @param pMindMapController
	 * @return
	 */
	public static String getNodeTextHierarchy(MindMapNode pNode,
			MindMapController pMindMapController) {
		return pNode.getShortText(pMindMapController)
				+ ((pNode.isRoot()) ? "" : (" <- " + getNodeTextHierarchy(
						pNode.getParentNode(), pMindMapController)));
	}

	/**
     */
	public static Clipboard getClipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	public static void addFocusPrintTimer() {
		Timer timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				logger.info("Component: "
						+ KeyboardFocusManager.getCurrentKeyboardFocusManager()
								.getFocusOwner()
						+ ", Window: "
						+ KeyboardFocusManager.getCurrentKeyboardFocusManager()
								.getFocusedWindow());
			}
		});
		timer.start();

	}

	/**
	 * copied from HomePane.java 15 mai 2006
	 * 
	 * Sweet Home 3D, Copyright (c) 2006 Emmanuel PUYBARET / eTeks
	 * <info@eteks.com>
	 * 
	 * - This listener manages accelerator keys that may require the use of
	 * shift key depending on keyboard layout (like + - or ?)
	 */
	public static void invokeActionsToKeyboardLayoutDependantCharacters(
			KeyEvent pEvent, Action[] specialKeyActions, Object pObject) {
		// on purpose without shift.
		int modifiersMask = KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK
				| KeyEvent.META_MASK;
		for (int i = 0; i < specialKeyActions.length; i++) {
			Action specialKeyAction = specialKeyActions[i];
			KeyStroke actionKeyStroke = (KeyStroke) specialKeyAction
					.getValue(Action.ACCELERATOR_KEY);
			if (pEvent.getKeyChar() == actionKeyStroke.getKeyChar()
					&& (pEvent.getModifiers() & modifiersMask) == (actionKeyStroke
							.getModifiers() & modifiersMask)
					&& specialKeyAction.isEnabled()) {
				specialKeyAction.actionPerformed(new ActionEvent(pObject,
						ActionEvent.ACTION_PERFORMED, (String) specialKeyAction
								.getValue(Action.ACTION_COMMAND_KEY)));
				pEvent.consume();
			}
		}
	}

	/**
	 * @param pString
	 * @param pSearchString
	 * @return the amount of occurrences of pSearchString in pString.
	 */
	public static int countOccurrences(String pString, String pSearchString) {
		int amount = 0;
		while (true) {
			final int index = pString.indexOf(pSearchString);
			if (index < 0) {
				break;
			}
			amount++;
			pString = pString.substring(index + pSearchString.length());
		}
		return amount;
	}

	public static void correctJSplitPaneKeyMap() {
		InputMap map = (InputMap) UIManager.get("SplitPane.ancestorInputMap");
		KeyStroke keyStrokeF6 = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0);
		KeyStroke keyStrokeF8 = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		map.remove(keyStrokeF6);
		map.remove(keyStrokeF8);
	}

	/**
	 * @param pPageFormat
	 * @param pPageFormatProperty
	 */
	public static void setPageFormatFromString(Paper pPaper,
			String pPageFormatProperty) {
		try {
			// parse string:
			StringTokenizer tokenizer = new StringTokenizer(
					pPageFormatProperty, ";");
			if (tokenizer.countTokens() != 6) {
				logger.warning("Page format property has not the correct format:"
						+ pPageFormatProperty);
				return;
			}
			pPaper.setSize(nt(tokenizer), nt(tokenizer));
			pPaper.setImageableArea(nt(tokenizer), nt(tokenizer),
					nt(tokenizer), nt(tokenizer));
		} catch (Exception e) {
			freemind.main.Resources.getInstance().logException(e);
		}
	}

	/**
	 * @param pTokenizer
	 * @return
	 */
	private static double nt(StringTokenizer pTokenizer) {
		String nextToken = pTokenizer.nextToken();
		try {
			return Double.parseDouble(nextToken);
		} catch (Exception e) {
			freemind.main.Resources.getInstance().logException(e);
		}
		return 0;
	}

	/**
	 * @param pPageFormat
	 * @return
	 */
	public static String getPageFormatAsString(Paper pPaper) {
		return pPaper.getWidth() + ";" + pPaper.getHeight() + ";"
				+ pPaper.getImageableX() + ";" + pPaper.getImageableY() + ";"
				+ pPaper.getImageableWidth() + ";"
				+ pPaper.getImageableHeight();
	}

	/**
	 * @return
	 */
	public static String getHostIpAsString() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			freemind.main.Resources.getInstance().logException(e);
		}
		return null;
	}

	public static String printXmlAction(XmlAction pAction) {
		final String classString = pAction.getClass().getName()
				.replaceAll(".*\\.", "");
		if (pAction instanceof CompoundAction) {
			CompoundAction compound = (CompoundAction) pAction;
			StringBuffer buf = new StringBuffer("[");
			for (Iterator it = compound.getListChoiceList().iterator(); it
					.hasNext();) {
				if (buf.length() > 1) {
					buf.append(',');
				}
				XmlAction subAction = (XmlAction) it.next();
				buf.append(printXmlAction(subAction));
			}
			buf.append(']');
			return classString + " " + buf.toString();
		}
		return classString;
	}

	public static XmlAction deepCopy(XmlAction action) {
		return (XmlAction) unMarshall(marshall(action));
	}

	public static String generateID(String proposedID, HashMap hashMap,
			String prefix) {
		String myProposedID = new String((proposedID != null) ? proposedID : "");
		String returnValue;
		do {
			if (!myProposedID.isEmpty()) {
				// there is a proposal:
				returnValue = myProposedID;
				// this string is tried only once:
				myProposedID = "";
			} else {
				/*
				 * The prefix is to enable the id to be an ID in the sense of
				 * XML/DTD.
				 */
				returnValue = prefix
						+ Integer.toString(Tools.ran.nextInt(2000000000));
			}
		} while (hashMap.containsKey(returnValue));
		return returnValue;
	}

	/**
	 * Call this method, if you don't know, if you are in the event thread or
	 * not. It checks this and calls the invokeandwait or the runnable directly.
	 * 
	 * @param pRunnable
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	public static void invokeAndWait(Runnable pRunnable)
			throws InvocationTargetException, InterruptedException {
		if (EventQueue.isDispatchThread()) {
			pRunnable.run();
		} else {
			EventQueue.invokeAndWait(pRunnable);
		}
	}

	public static String getFreeMindBasePath()
			throws UnsupportedEncodingException {
		String path = FreeMindStarter.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		logger.info("Path: " + decodedPath);
		if (decodedPath.endsWith(CONTENTS_JAVA_FREEMIND_JAR)) {
			decodedPath = decodedPath.substring(0, decodedPath.length()
					- CONTENTS_JAVA_FREEMIND_JAR.length());
			decodedPath = decodedPath + FREE_MIND_APP_CONTENTS_RESOURCES_JAVA;
			logger.info("macPath: " + decodedPath);
		} else if (decodedPath.endsWith(FREEMIND_LIB_FREEMIND_JAR)) {
			decodedPath = decodedPath.substring(0, decodedPath.length()
					- FREEMIND_LIB_FREEMIND_JAR.length());
			logger.info("reducded Path: " + decodedPath);
		}
		return decodedPath;
	}

	public static Properties copyChangedProperties(Properties props2,
			Properties defProps2) {
		Properties toBeStored = new Properties();
		for (Iterator it = props2.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (!safeEquals(props2.get(key), defProps2.get(key))) {
				toBeStored.put(key, props2.get(key));
			}
		}
		return toBeStored;
	}

}
