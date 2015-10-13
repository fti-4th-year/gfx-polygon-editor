package FF_12312_Gerasev_Span;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class EditorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 3000;
	public static final int HEIGHT = 2000;
	
	private BufferedImage image;
	
	private boolean newline = false;
	private Scene _scene;
	
	public EditorPanel(Scene scene) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().setColor(new Color(0xffffff));
		image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
		
		_scene = scene;
		
		addMouseListener(new MouseListener() {
			@Override 
			public void mouseReleased(MouseEvent event) {
				if(_scene.getMode() == Scene.DRAW) {
					if(event.getButton() == MouseEvent.BUTTON1) {
						if(_scene.getLine() != null) {
							if(newline) {
								newline = false;
							} else {
								_scene.getLine().setStart(event.getPoint());
								_scene.getPolygon().add(event.getPoint());
							}
						}
					} else if(event.getButton() == MouseEvent.BUTTON3) {
						_scene.setLine(null);
						if(_scene.getPolygon().getSize() > 2) {
							_scene.getStorage().add(_scene.getPolygon());
							_scene.setDirty(true);
						}
						_scene.setPolygon(new Polygon());
					}
				}
				repaint();
			}
			@Override 
			public void mousePressed(MouseEvent event) {
				if(_scene.getMode() == Scene.DRAW) {
					if(event.getButton() == MouseEvent.BUTTON1) {
						if(_scene.getLine() == null) {
							_scene.setLine(new Line(event.getPoint(), event.getPoint()));
							_scene.getPolygon().add(event.getPoint());
							newline = true;
						}
					}
				} else if((_scene.getMode() & Scene.FILL) == Scene.FILL) {
					int color = 0;
					if(event.getButton() == MouseEvent.BUTTON1) {
						color = 0x00ff00;
					} else if(event.getButton() == MouseEvent.BUTTON3) {
						color = 0xffffff;
					}
					if(color != 0) {
						_scene.getStorage().add(new Fill(event.getPoint(), color));
					}
				}
			}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});
		addMouseMotionListener(new MouseMotionListener() {
			private void refresh(Point point) {
				if(_scene.getLine() != null) {
					_scene.getLine().setEnd(point);
					repaint();
				}
			}
			@Override 
			public void mouseMoved(MouseEvent event) {
				refresh(event.getPoint());
			}
			@Override 
			public void mouseDragged(MouseEvent event) {
				refresh(event.getPoint());
			}
		});
		_scene.setLine(null);
		_scene.setPolygon(new Polygon());
		_scene.setStorage(new Storage());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		_scene.getStorage().drawImageLast(image);
		
		g.drawImage(image, 0, 0, null);
		
		if(_scene.getPolygon().getSize() > 1) {
			ArrayList<Point> vertices = _scene.getPolygon().getVertices();
			for(int i = 0; i < vertices.size() - 1; ++i) {
				Line line = new Line(vertices.get(i), vertices.get(i + 1));
				line.draw(g);
			}
		}
		
		if(_scene.getLine() != null) {
			_scene.getLine().draw(g);
		}
	}
	
	public Storage getScene() {
		return _scene.getStorage();
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
