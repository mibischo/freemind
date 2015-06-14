package freemind.tools;

import java.awt.print.Paper;
import java.util.StringTokenizer;

public class PageFormatter {
	
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
	 * @param pPageFormatProperty
	 */
	public static void setPageFormatFromString(Paper pPaper,
			String pPageFormatProperty) {
		try {
			// parse string:
			StringTokenizer tokenizer = new StringTokenizer(
					pPageFormatProperty, ";");
			
			pPaper.setSize(nt(tokenizer), nt(tokenizer));
			pPaper.setImageableArea(nt(tokenizer), nt(tokenizer),
					nt(tokenizer), nt(tokenizer));
		} catch (Exception e) {
			freemind.main.Resources.getInstance().logException(e);
		}
	}

}
