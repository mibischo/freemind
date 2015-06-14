package freemind.tools;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.StringTokenizer;

import freemind.main.Resources;

public class UrlHelper {
	
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
			link = UrlHelper.fileToUrl(input);
			relative = link.toString();
			if ("relative".equals(Resources.getInstance().getProperty("links"))) {
				// Create relative URL
				relative = toRelativeURL(UrlHelper.fileToUrl(pMapFile), link);
			}
			return relative;
		} catch (MalformedURLException ex) {
			freemind.main.Resources.getInstance().logException(ex);
		}
		return input.getAbsolutePath();
	}
	
	public static URL fileToUrl(File pFile) throws MalformedURLException {
		if (pFile == null)
			return null;
		return pFile.toURI().toURL();
	}
	
	public static File urlToFile(URL pUrl) throws URISyntaxException {
		// fix for java1.4 and java5 only.
		if (SystemInfo.isBelowJava6()) {
			return new File(urlGetFile(pUrl));
		}
		return new File(new URI(pUrl.toString()));
	}
	
	/**
	 * This is a correction of a method getFile of a class URL. Namely, on
	 * Windows it returned file paths like /C: etc., which are not valid on
	 * Windows. This correction is heuristic to a great extend. One of the
	 * reasons is that file:// is basically no protocol at all, but rather
	 * something every browser and every system uses slightly differently.
	 */
	public static String urlGetFile(URL url) {
		if (OsHelper.isWindows() && isFile(url)) {
			String fileName = url.toString().replaceFirst("^file:", "")
					.replace('/', '\\');
			return (fileName.indexOf(':') >= 0) ? fileName.replaceFirst(
					"^\\\\*", "") : fileName;
		} // Network path
		else {
			return url.getFile();
		}
	}
	

	private static boolean isFile(URL url) {
		return url.getProtocol().equals("file");
	}





}
