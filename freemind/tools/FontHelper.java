package freemind.tools;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class FontHelper implements IFontHelper {
	
	private Set availableFontFamilyNames = null; // Keep set of platform
	private String sEnvFonts[] = null;
	
	public Set getAvailableFontFamilyNames() {
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
	private String[] getAvailableFonts() {
		if (sEnvFonts == null) {
			GraphicsEnvironment gEnv = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			sEnvFonts = gEnv.getAvailableFontFamilyNames();
		}
		return sEnvFonts;
	}

	public Vector getAvailableFontFamilyNamesAsVector() {
		String[] envFonts = getAvailableFonts();
		Vector availableFontFamilyNames = new Vector();
		for (int i = 0; i < envFonts.length; i++) {
			availableFontFamilyNames.add(envFonts[i]);
		}
		return availableFontFamilyNames;
	}

	public boolean isAvailableFontFamily(String fontFamilyName) {
		return getAvailableFontFamilyNames().contains(fontFamilyName);
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
	public Font updateFontSize(Font font, float zoom, int normalFontSize) {
		if (font != null) {
			float oldFontSize = font.getSize2D();
			float newFontSize = normalFontSize * zoom;
			if (oldFontSize != newFontSize) {
				font = font.deriveFont(newFontSize);
			}
		}
		return font;
	}




}
