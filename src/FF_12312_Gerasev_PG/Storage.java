package FF_12312_Gerasev_PG;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Storage implements Drawable {
	private ArrayList<Polygon> _polygons;
	public Storage() {
		_polygons = new ArrayList<Polygon>();
	}
	public void add(Polygon polygon) {
		_polygons.add(new Polygon(polygon));
	}
	public void removeLast() {
		if(getSize() > 0)
			_polygons.remove(getSize() - 1);
	}
	public int getSize() {
		return _polygons.size();
	}
	public ArrayList<Polygon> getPolygons() {
		ArrayList<Polygon> list = new ArrayList<Polygon>();
		Iterator<Polygon> i = _polygons.iterator();
		while(i.hasNext()) {
			list.add(new Polygon(i.next()));
		}
		return list;
	}
	@Override
	public void draw(Graphics g) {
		Iterator<Polygon> i = _polygons.iterator();
		while(i.hasNext()) {
			i.next().draw(g);
		}
	}
}
