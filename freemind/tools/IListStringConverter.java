package freemind.tools;

import java.util.List;

public interface IListStringConverter {

	public List<String> stringToList(String string);
		
	public String listToString(List<?> list);
}
