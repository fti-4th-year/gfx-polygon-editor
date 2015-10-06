package FF_12312_Gerasev_PG;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line implements Drawable {
	private Point _start;
	private Point _end;
	
	public Line(Point start, Point end) {
		_start = new Point(start);
		_end = new Point(end);
	}
	
	public Line(Line line) {
		_start = new Point(line._start);
		_end = new Point(line._end);
	}
	
	public Point getStart() {
		return new Point(_start);
	}
	
	public Point getEnd() {
		return new Point(_end);
	}
	
	public void setStart(Point start) {
		_start.x = start.x;
		_start.y = start.y;
	}
	
	public void setEnd(Point end) {
		_end.x = end.x;
		_end.y = end.y;
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawLine(_start.x, _start.y, _end.x, _end.y);
	}
}
