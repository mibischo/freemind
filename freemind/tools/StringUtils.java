package freemind.tools;

public class StringUtils {

	/**
	 * @param pString
	 * @param pSearchString
	 * @return the amount of occurrences of pSearchString in pString.
	 */
	public static int countOccurrences(String pString, String pSearchString) {
		int amount = 0;
		while (true) {
			final int index = pString.indexOf(pSearchString);
			if (index < 0) {
				break;
			}
			amount++;
			pString = pString.substring(index + pSearchString.length());
		}
		return amount;
	}
}
