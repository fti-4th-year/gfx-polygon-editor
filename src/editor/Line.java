package editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Line implements Drawable, ImageDrawable {
	private Point _start;
	private Point _end;
	private int width = 1;
	
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
	
	public void setWidth(int w) {
		width = w;
	}
	
	public int getWidth() {
		return width;
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0x000000));
		g2d.setStroke(new BasicStroke(width));
		g2d.drawLine(_start.x, _start.y, _end.x, _end.y);
	}

	@Override
	public void drawImage(BufferedImage bi) {
		if(width == 1) {
			Point s, e;
			
			if(_start.y == _end.y || Math.abs((float) (_start.x - _end.x)/(_start.y - _end.y)) > 1.0) {
				if(_start.x == _end.x) {
					return;
				}
				
				if(_start.x <= _end.x) {
					s = _start;
					e = _end;
				} else {
					e = _start;
					s = _end;
				}
				
				for(int ix = s.x; ix <= e.x; ++ix) {
					int iy = Math.round((ix - s.x)*((float)(s.y - e.y)/(s.x - e.x)) + s.y);
					bi.setRGB(ix, iy, 0x000000);
				}
			} else {
				if(_start.y <= _end.y) {
					s = _start;
					e = _end;
				} else {
					e = _start;
					s = _end;
				}
				
				for(int iy = s.y; iy <= e.y; ++iy) {
					int ix = Math.round((iy - s.y)*((float)(s.x - e.x)/(s.y - e.y)) + s.x);
					bi.setRGB(ix, iy, 0x000000);
				}
			}
		} else {
			Graphics g = bi.getGraphics();
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(0x000000));
			g2d.setStroke(new BasicStroke(width));
			g2d.drawLine(_start.x, _start.y, _end.x, _end.y);
		}
	}
}
