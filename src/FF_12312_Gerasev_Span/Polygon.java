package FF_12312_Gerasev_Span;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Polygon implements Drawable, ImageDrawable {
	private ArrayList<Point> vertices;
	private int width = 3;
	
	public Polygon() {
		vertices = new ArrayList<Point>();
	}
	public Polygon(Polygon polygon) {
		vertices = polygon.getVertices();
	}
	public void add(Point vertex) {
		vertices.add(new Point(vertex));
	}
	public int getSize() {
		return vertices.size();
	}
	public ArrayList<Point> getVertices() {
		ArrayList<Point> list = new ArrayList<Point>();
		Iterator<Point> i = vertices.iterator();
		while(i.hasNext()) {
			list.add(new Point(i.next()));
		}
		return list;
	}
	
	public void setWidth(int w) {
		width = w;
	}
	public int getWidth() {
		return width;
	}
	
	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < vertices.size(); ++i) {
			Line line = new Line(vertices.get(i), vertices.get((i + 1) % vertices.size()));
			line.setWidth(width);
			line.draw(g);
		}
	}
	
	@Override
	public void drawImage(BufferedImage bi) {
		for(int i = 0; i < vertices.size(); ++i) {
			Line line = new Line(vertices.get(i), vertices.get((i + 1) % vertices.size()));
			line.setWidth(width);
			line.drawImage(bi);
		}
	}
}
