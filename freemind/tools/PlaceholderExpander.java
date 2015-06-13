package freemind.tools;

public class PlaceholderExpander implements IPlaceholderExpander {

	/**
	 * Example: expandPlaceholders("Hello $1.","Dolly"); => "Hello Dolly."
	 */
	public String expand(String message, String s1) {
		String result = message;
		if (s1 != null) {
			s1 = s1.replaceAll("\\\\", "\\\\\\\\"); // Replace \ with \\
			result = result.replaceAll("\\$1", s1);
		}
		return result;
	}

	public String expand(String message, String s1, String s2) {
		String result = message;
		if (s1 != null) {
			result = result.replaceAll("\\$1", s1);
		}
		if (s2 != null) {
			result = result.replaceAll("\\$2", s2);
		}
		return result;
	}

	public String expand(String message, String s1,
			String s2, String s3) {
		String result = message;
		if (s1 != null) {
			result = result.replaceAll("\\$1", s1);
		}
		if (s2 != null) {
			result = result.replaceAll("\\$2", s2);
		}
		if (s3 != null) {
			result = result.replaceAll("\\$3", s3);
		}
		return result;
	}
}
