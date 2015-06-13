package freemind.tools;

import java.awt.Color;
import java.awt.Point;
import java.util.Date;

/**
 * Handles Transformations for certain XML Elements
 * @author clu
 *
 */
public interface IXmlTransformations {

	public String colorToXml(Color col);
	
	public Color xmlToColor(String string);
	
	public String PointToXml(Point col);
	
	public Point xmlToPoint(String string);
	
	public String BooleanToXml(boolean col);
	
	public boolean xmlToBoolean(String string);
	
	public Date xmlToDate(String xmlString);

	String dateToString(Date date);
	
}
