package freemind.tools;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class ListStringConverter implements IListStringConverter {

	public List<String> stringToList(String string) {
		StringTokenizer tok = new StringTokenizer(string, ";");
		List<String> list = new LinkedList<String>();
		while (tok.hasMoreTokens()) {
			list.add(tok.nextToken());
		}
		return list;
	}

	public String listToString(List<?> list) {
		ListIterator<?> it = list.listIterator(0);
		String str = new String();
		while (it.hasNext()) {
			str += it.next().toString() + ";";
		}
		return str;
	}
}
