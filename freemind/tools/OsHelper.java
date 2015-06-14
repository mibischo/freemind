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

}
