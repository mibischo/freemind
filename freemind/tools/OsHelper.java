package freemind.tools;

public class OsHelper implements IOsHelper {
	
	
	public boolean isMacOsX() {
		boolean underMac = false;
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS")) {
			underMac = true;
		}
		return underMac;
	}

	public boolean isLinux() {
		boolean underLinux = false;
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Linux")) {
			underLinux = true;
		}
		return underLinux;
	}
	
	public boolean isWindows() {
		return System.getProperty("os.name").substring(0, 3).equals("Win");
	}

}
