package freemind.tools;

import java.awt.Component;
import java.awt.Point;

import javax.swing.SwingUtilities;

public class PointConverter {
	

	public static Point convertPointToAncestor(Component c, Point p,
			Component destination) {
		int x, y;
		while (c != destination) {
			x = c.getX();
			y = c.getY();

			p.x += x;
			p.y += y;

			c = c.getParent();
		}
		return p;

	}

	public static void convertPointFromAncestor(Component source, Point p,
			Component c) {
		int x, y;
		while (c != source) {
			x = c.getX();
			y = c.getY();

			p.x -= x;
			p.y -= y;

			c = c.getParent();
		}
		;

	}

	public static void convertPointToAncestor(Component source, Point point,
			Class ancestorClass) {
		Component destination = SwingUtilities.getAncestorOfClass(
				ancestorClass, source);
		convertPointToAncestor(source, point, destination);
	}


}
