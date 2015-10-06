package FF_12312_Gerasev_PG;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class EditorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private boolean newline = false;
	private Scene _scene;
	
	public EditorPanel(Scene scene) {
		_scene = scene;
		
		addMouseListener(new MouseListener() {
			@Override 
			public void mouseReleased(MouseEvent event) {
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
				repaint();
			}
			@Override 
			public void mousePressed(MouseEvent event) {
				if(_scene.getLine() == null) {
					_scene.setLine(new Line(event.getPoint(), event.getPoint()));
					_scene.getPolygon().add(event.getPoint());
					newline = true;
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
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.black);
		_scene.getStorage().draw(g);
		
		if(_scene.getPolygon().getSize() > 1) {
			ArrayList<Point> vertices = _scene.getPolygon().getVertices();
			for(int i = 0; i < vertices.size() - 1; ++i) {
				Line line = new Line(vertices.get(i), vertices.get(i + 1));
				line.draw(g);
			}
		}
		
		if(_scene.getLine() != null) {
			g2d.setColor(Color.blue);
			_scene.getLine().draw(g);
		}
	}
	
	public Storage getScene() {
		return _scene.getStorage();
	}
}
