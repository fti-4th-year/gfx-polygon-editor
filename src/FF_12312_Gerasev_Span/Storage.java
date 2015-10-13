package FF_12312_Gerasev_Span;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Storage implements Drawable, ImageDrawable {
	private ArrayList<Object> elements;
	int last = 0;
	
	public Storage() {
		elements = new ArrayList<Object>();
	}
	public void add(Polygon polygon) {
		elements.add(new Polygon(polygon));
	}
	public void add(Fill fill) {
		elements.add(new Fill(fill));
	}
	public void removeLast() {
		if(getSize() > 0) {
			elements.remove(getSize() - 1);
			resetLast();
		}
	}
	public int getSize() {
		return elements.size();
	}
	public ArrayList<Object> getElements() {
		ArrayList<Object> list = new ArrayList<Object>();
		Iterator<Object> i = elements.iterator();
		while(i.hasNext()) {
			Object obj = i.next();
			if(obj instanceof Polygon) {
				list.add(new Polygon((Polygon) obj));
			} else if(obj instanceof Fill) {
				list.add(new Fill((Fill) obj));
			}
		}
		return list;
	}
	
	@Override
	public void draw(Graphics g) {
		Iterator<Object> i = elements.iterator();
		while(i.hasNext()) {
			if(i.next() instanceof Drawable) {
				((Drawable) i.next()).draw(g);
			}
		}
	}
	
	@Override
	public void drawImage(BufferedImage bi) {
		Iterator<Object> i = elements.iterator();
		while(i.hasNext()) {
			if(i.next() instanceof ImageDrawable) {
				((ImageDrawable) i.next()).drawImage(bi);
			}
		}
	}
	
	public void drawImageLast(BufferedImage bi) {
		for(int i = last; i < elements.size(); ++i) {
			if(elements.get(i) instanceof ImageDrawable) {
				((ImageDrawable) elements.get(i)).drawImage(bi);
			}
		}
		last = elements.size();
	}
	
	public void resetLast() {
		last = 0;
	}
}
