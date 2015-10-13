package FF_12312_Gerasev_Span;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class PolygonEditor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private EditorPanel panel;
	private Scene scene;
	private EditorHandle handle;
	
	public PolygonEditor() {
		scene = new Scene();
		
		panel = new EditorPanel(scene);
		
		handle = new EditorHandle(scene, this, panel, panel.getImage());
		
		new EditorMenu(handle);
		
		setTitle("Polygon Editor");
		add(new JScrollPane(panel));
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
                handle.exit();
            }
		});
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				PolygonEditor editor = new PolygonEditor();
				editor.setVisible(true);
			}
		});
	}
}
