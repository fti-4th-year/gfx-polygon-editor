package editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class EditorMenu {
	private JMenuBar menu_bar;
	private JToolBar toolbar;
	private EditorHandle _handle;
	
	private JToggleButton draw_poly;
	private JToggleButton fill;
	
	public EditorMenu(EditorHandle handle) {
		_handle = handle;
		
		JMenu menu = null;
		JMenuItem menu_item = null;
		
		menu_bar = new JMenuBar();
		
		menu = new JMenu("File");
		
		menu_item = new JMenuItem("New");
		menu_item.setToolTipText("Create new document");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_handle.create();
			}
		});
		menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Open");
		menu_item.setToolTipText("Open existing document");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_handle.open();
			}
		});
		menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Save");
		menu_item.setToolTipText("Save current document");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_handle.save();
			}
		});
		menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Save As");
		menu_item.setToolTipText("Save current document as ...");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_handle.saveAs();
			}
		});
		menu_item.setAccelerator(
				KeyStroke.getKeyStroke(
						KeyEvent.VK_S, 
						KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK
						)
				);
		menu.add(menu_item);
		
		
		menu_item = new JMenuItem("Exit");
		menu_item.setToolTipText("Exit application");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_handle.exit();
			}
		});
		menu.add(menu_item);
		
		menu_bar.add(menu);
		
		menu = new JMenu("Edit");
		menu_item = new JMenuItem("Undo");
		menu_item.setToolTipText("Undo last polygon");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_handle.undo();
			}
		});
		menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menu_item);
		menu_bar.add(menu);
		
		
		menu = new JMenu("Help");
		menu_item = new JMenuItem("About");
		menu_item.setToolTipText("About this application");
		menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		menu.add(menu_item);
		menu_bar.add(menu);
		
		_handle.getFrame().setJMenuBar(menu_bar);
		
		toolbar = new JToolBar();
		toolbar.add(makeToolBarButton("resources/New document.gif", "New", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				_handle.create();
			}
		}));
		toolbar.add(makeToolBarButton("resources/Folder.gif", "Open", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				_handle.open();
			}
		}));
		toolbar.add(makeToolBarButton("resources/Save.gif", "Save", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				_handle.save();
			}
		}));
		toolbar.add(makeToolBarButton("resources/Undo.gif", "Undo", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				_handle.undo();
			}
		}));
		toolbar.add(makeToolBarButton("resources/About.gif", "About", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				showAbout();
			}
		}));
		draw_poly = makeToggleButton("resources/Modify.gif", "Draw polygon");
		fill = makeToggleButton("resources/Retort.gif", "Fill");
		draw_poly.setSelected(true);
		draw_poly.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(draw_poly.isSelected()) {
					fill.setSelected(false);
					_handle.getScene().setMode(Scene.DRAW);
				} else {
					draw_poly.setSelected(true);
				}
				_handle.getPanel().repaint();
			}
		});
		fill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fill.isSelected()) {
					draw_poly.setSelected(false);
					_handle.getScene().setMode(Scene.FILL);
				} else {
					fill.setSelected(true);
				}
				_handle.getPanel().repaint();
			}
		});
		toolbar.add(draw_poly);
		toolbar.add(fill);
		toolbar.setFloatable(false);
		
		_handle.getFrame().add(toolbar, BorderLayout.PAGE_START);
	}
	
	private JButton makeToolBarButton(String image_path, String text, ActionListener listener) {
		//Look for the image.
		String imgLocation = image_path;
		URL imageURL = getClass().getResource(imgLocation);
		
		//Create and initialize the button.
		JButton button = new JButton();
		button.setToolTipText(text);
		
		if (imageURL != null) {
			button.setIcon(new ImageIcon(imageURL, text));
		} else {
			button.setText(text);
			System.err.println("resource not found: " + imgLocation);
		}
		
		button.addActionListener(listener);
		
		return button;
	}
	
	private JToggleButton makeToggleButton(String image_path, String text) {
		//Look for the image.
		String imgLocation = image_path;
		URL imageURL = getClass().getResource(imgLocation);
		
		//Create and initialize the button.
		JToggleButton button = new JToggleButton();
		button.setToolTipText(text);
		
		if (imageURL != null) {
			button.setIcon(new ImageIcon(imageURL, text));
		} else {
			button.setText(text);
			System.err.println("resource not found: " + imgLocation);
		}
		
		return button;
	}
	
	private void showAbout() {
		JOptionPane.showMessageDialog(
				_handle.getFrame(), 
				"Controls:\n" + 
				"Left mouse button - add new vertex to current polygon or start new polygon\n" +
				"Right mouse button - complete current polygon", 
				"About", 
				JOptionPane.INFORMATION_MESSAGE
				);
	}
}
