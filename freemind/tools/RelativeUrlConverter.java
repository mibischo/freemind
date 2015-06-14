package freemind.tools;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import freemind.main.Resources;
import freemind.main.Tools;

public class RelativeUrlConverter {
	
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
				relative = toRelativeURL(Tools.fileToUrl(pMapFile), link);
			}
			return relative;
		} catch (MalformedURLException ex) {
			freemind.main.Resources.getInstance().logException(ex);
		}
		return input.getAbsolutePath();
	}


}
