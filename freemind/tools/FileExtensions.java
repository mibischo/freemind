package freemind.tools;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileExtensions {
	
	public static final Set executableExtensions = new HashSet(
			Arrays.asList(new String[] { "exe", "com", "vbs", "bat", "lnk" }));
	

	public static boolean executableByExtension(String file) {
		return executableExtensions.contains(getExtension(file));
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


}
