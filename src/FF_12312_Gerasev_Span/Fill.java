package FF_12312_Gerasev_Span;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

public class Fill implements ImageDrawable {
	private Point seed;
	private int color;
	
	public static final int DEFAULT = 0x00ff00;
	public static final int WHITE = 0xffffff;
	
	public Fill(Point seed, int color) {
		this.seed = new Point(seed);
		this.color = color;
	}
	
	public Fill(Fill fill) {
		seed = fill.seed;
		color = fill.color;
	}
	
	public Point getSeed() {
		return new Point(seed);
	}
	
	public int getColor() {
		return color;
	}
		
	
	private class Process {
		private Queue<Point> queue;
		private int base;
		private BufferedImage image;
		private int width;
		private int height;
		
		public Process(BufferedImage bi) {
			image = bi;
			width = image.getWidth();
			height = image.getHeight();
			queue = new ArrayDeque<Point>();
			base = (image.getRGB(seed.x, seed.y) & 0xffffff);
			queue.add(seed);
		}
		
		void addSeeds(int x, int y) {
			addSeedTop(x, y);
			addSeedBottom(x, y);
		}
		void addSeedTop(int x, int y) {
			if(y + 1 < height && (image.getRGB(x, y + 1) & 0xffffff) == base)
				queue.add(new Point(x, y + 1));
		}
		void addSeedBottom(int x, int y) {
			if(y - 1 >= 0 && (image.getRGB(x, y - 1) & 0xffffff) == base)
				queue.add(new Point(x, y - 1));
		}
		
		void fill() {
			while(queue.size() > 0) {
				Point p = queue.remove();
				if((image.getRGB(p.x, p.y) & 0xffffff) == color) {
					continue;
				}
				image.setRGB(p.x, p.y, color);
				addSeeds(p.x, p.y);
				for(int ix = p.x + 1; ix < width && (image.getRGB(ix, p.y) & 0xffffff) == base; ++ix) {
					image.setRGB(ix, p.y, color);
					addSeeds(ix, p.y);
				}
				for(int ix = p.x - 1; ix >= 0 && (image.getRGB(ix, p.y) & 0xffffff) == base; --ix) {
					image.setRGB(ix, p.y, color);
					addSeeds(ix, p.y);
				}
			}
		}
	}
	
	@Override
	public void drawImage(BufferedImage bi) {
		(new Process(bi)).fill();
	}
}
