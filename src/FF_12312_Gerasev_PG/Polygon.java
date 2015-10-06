package FF_12312_Gerasev_PG;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class Polygon implements Drawable {
	ArrayList<Point> _vertices;
	public Polygon() {
		_vertices = new ArrayList<Point>();
	}
	public Polygon(Polygon polygon) {
		_vertices = polygon.getVertices();
	}
	public void add(Point vertex) {
		_vertices.add(new Point(vertex));
	}
	public int getSize() {
		return _vertices.size();
	}
	public ArrayList<Point> getVertices() {
		ArrayList<Point> list = new ArrayList<Point>();
		Iterator<Point> i = _vertices.iterator();
		while(i.hasNext()) {
			list.add(new Point(i.next()));
		}
		return list;
	}
	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < _vertices.size(); ++i) {
			Line line = new Line(_vertices.get(i), _vertices.get((i + 1) % _vertices.size()));
			line.draw(g);
		}
	}
}
