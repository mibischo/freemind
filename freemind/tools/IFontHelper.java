package freemind.tools;

import java.util.Set;
import java.util.Vector;

public interface IFontHelper {

	public Set getAvailableFontFamilyNames();
	
	public Vector getAvailableFontFamilyNamesAsVector();
	
	public boolean isAvailableFontFamily(String fontFamilyName); 
}
