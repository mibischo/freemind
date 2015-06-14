package freemind.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemInfo {

	public static final String JAVA_VERSION = System
				.getProperty("java.version");
	
	public static boolean isBelowJava6() {
		return JAVA_VERSION.compareTo("1.6.0") < 0;
	}

	public static boolean isAboveJava4() {
		return JAVA_VERSION.compareTo("1.4.0") > 0;
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
	
}
