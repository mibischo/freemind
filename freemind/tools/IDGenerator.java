package freemind.tools;

import java.util.HashMap;
import java.util.Random;

import freemind.main.Tools;

public class IDGenerator {

	public static Random ran = new Random();
	
	public static String generate(String proposedID, HashMap hashMap,
			String prefix) {
		String myProposedID = new String((proposedID != null) ? proposedID : "");
		String returnValue;
		do {
			if (!myProposedID.isEmpty()) {
				// there is a proposal:
				returnValue = myProposedID;
				// this string is tried only once:
				myProposedID = "";
			} else {
				/*
				 * The prefix is to enable the id to be an ID in the sense of
				 * XML/DTD.
				 */
				returnValue = prefix
						+ Integer.toString(ran.nextInt(2000000000));
			}
		} while (hashMap.containsKey(returnValue));
		return returnValue;
	}
}
