package freemind.tools;

import java.io.File;

public class OsHelper{
	
	
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
	
	public static boolean isWindows() {
		return System.getProperty("os.name").substring(0, 3).equals("Win");
	}
	
	public static boolean isUnix() {
		return (File.separatorChar == '/') || isMacOsX();
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


}
