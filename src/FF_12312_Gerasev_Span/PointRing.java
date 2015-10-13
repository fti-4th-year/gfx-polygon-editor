package FF_12312_Gerasev_Span;

import java.awt.Point;

public class PointRing {
	private int[] x;
	private int[] y;
	private int head;
	private int tail;
	private int size;
	private int cap;
	
	public PointRing(int cap) {
		this.cap = cap;
		x = new int[cap];
		y = new int[cap];
		head = 0;
		tail = 0;
		size = 0;
	}
	public void add(int px, int py) {
		if(size + 1 > cap) {
			System.err.println("StackRing full");
			return;
		}
		++size;
		x[head] = px;
		y[head] = py;
		++head;
		if(head >= cap) {
			head = 0;
		}
	}
	public void remove(Point p) {
		if(size - 1 < 0) {
			System.err.println("StackRing empty");
			return;
		}
		--size;
		p.x = x[tail];
		p.y = y[tail];
		++tail;
		if(tail >= cap) {
			tail = 0;
		}
	}
	public int getSize() {
		return size;
	}
}