package FF_12312_Gerasev_Span;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EditorHandle {
	private Scene _scene;
	private JFrame _frame;
	private JPanel _panel;
	private BufferedImage _image;
	private JFileChooser chooser;
	private String file;
	private int undone;
	
	Scene getScene() {
		return _scene;
	}
	
	JFrame getFrame() {
		return _frame;
	}
	
	JPanel getPanel() {
		return _panel;
	}
	
	public EditorHandle(Scene scene, JFrame frame, JPanel panel, BufferedImage image) {
		_scene = scene;
		_frame = frame;
		_panel = panel;
		_image = image;
		chooser = new JFileChooser();
		file = null;
		undone = 0;
	}
	
	private void clearImage() {
		_image.getGraphics().setColor(new Color(0xffffff));
		_image.getGraphics().fillRect(0, 0, _image.getWidth(), _image.getHeight());
	}
	
	public void create() {
		int n = 0;
		if(_scene.getDirty()) {
			n = askSave();
		}
		if(n != 2) {
			_scene.setStorage(new Storage());
			_scene.setPolygon(new Polygon());
			_scene.setLine(null);
			_scene.setDirty(false);
			
			clearImage();
			
			_panel.repaint();
			undone = 0;
			file = null;
		}
	}
	
	public void open() {
		int n = JOptionPane.OK_OPTION;
		if(_scene.getDirty()) {
			n = askSave();
		}
		if(n != JOptionPane.CANCEL_OPTION) {
			int option = chooser.showOpenDialog(_frame);
			if (option == JFileChooser.APPROVE_OPTION) {
	            openFile(chooser.getSelectedFile().getPath());
	        }
		}
	}
	
	public void openFile(String path) {
		Storage storage = null;
		
		try {
			storage = FileLoader.load(path);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					_frame, 
					"Error while opening file " + path + " : " + e.getMessage(), 
					"Open error", JOptionPane.ERROR_MESSAGE
					);
		}
		
		file = path;
		_scene.setDirty(false);
		
		clearImage();
		
		if(storage != null) {
			_scene.setStorage(storage);
			_scene.setPolygon(new Polygon());
			_scene.setLine(null);
			undone = storage.getSize();
			repaint();
		}
	}
	
	public int save() {
		int n = JOptionPane.OK_OPTION;
		if(file == null) {
			n = saveAs();
		} else {
			saveFile(file);
		}
		return n;
	}
	
	public int saveAs() {
		int option = chooser.showSaveDialog(_frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();
			if(!path.endsWith(".pg"))
				path = path + ".pg";
            saveFile(path);
            return JOptionPane.OK_OPTION;
        }
		return JOptionPane.CANCEL_OPTION;
	}
	
	public void saveFile(String path) {
		try {
			FileLoader.save(path, _scene.getStorage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					_frame, 
					"Error while saving file " + path + " : " + e.getMessage(), 
					"Save error", JOptionPane.ERROR_MESSAGE
					);
		}
		file = path;
		_scene.setDirty(false);
	}
	
	public int askSave() {
		Object[] options = {"Yes", "No", "Cancel"};
		int n = JOptionPane.showOptionDialog(
			_frame, 
			"Would you like to save changes?",
			"Save?",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[2]
		);
		if(n == JOptionPane.OK_OPTION) {
			n = save();
		}
		return n;
	}
	
	public void exit() {
		int n = JOptionPane.OK_OPTION;
		if(_scene.getDirty()) {
			n = askSave();
		}
		if(n != JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}
	
	public void undo() {
		boolean changed = false;
		if(_scene.getPolygon().getSize() > 0) {
			_scene.setPolygon(new Polygon());
			_scene.setLine(null);
			changed = true;
			
		} else if(undone < _scene.getStorage().getSize()) {
			_scene.getStorage().removeLast();
			clearImage();
			changed = true;
		}
		
		if(changed) {
			undone = _scene.getStorage().getSize();
			_scene.setDirty(true);
			repaint();
		}
	}
	
	public void repaint() {
		_panel.repaint();
	}
}
