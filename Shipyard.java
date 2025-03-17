

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public class Shipyard extends JFrame{
	public static int frame_width = 1000;
	public static int frame_height = 700;

	public static Color backgroundColor = Color. DARK_GRAY;
	public static Color textColor = Color. LIGHT_GRAY;
	protected Board boardPanel;

	public Shipyard() {
		System.out.println("starting Shipyard...");
		JPanel main = new JPanel();
		GroupLayout layout = new GroupLayout(main);
		main.setLayout(layout);
		this.add(main);
		this.pack();
		String path_to_img = selectImage();
		if (path_to_img == null) {
			System.out.println("No image selected, quit.");
			System.exit(0);
		}
		boardPanel = new Board(path_to_img, this);
		boardPanel.setSize(500, frame_height);
		boardPanel.setPreferredSize(new Dimension(150, frame_height));
		boardPanel.setMinimumSize(new Dimension(boardPanel.getShip().getWidth(), boardPanel.getShip().getHeight()));
		// JScrollPane scrollPane = new JScrollPane(boardPanel);
		// main.add(boardPanel, BorderLayout.CENTER);

		InfoDisplay infoPanel = new InfoDisplay();
		infoPanel.setSize(200, frame_height);
		// infoPanel.setLocation(frame_width - frame_width/3, 0);
		infoPanel.setBackground(Shipyard.backgroundColor);
		infoPanel.setMinimumSize(new Dimension(300, 600));
		infoPanel.setPreferredSize(new Dimension(300, frame_height));
		infoPanel.setMaximumSize(new Dimension(300, frame_height * 10));

		ControlPanel controlPannel = new ControlPanel();
		controlPannel.setSize(200, frame_height);
		controlPannel.setMinimumSize(new Dimension(150, frame_height));
		controlPannel.setMaximumSize(new Dimension(250, frame_height * 10));
		controlPannel.setPreferredSize(new Dimension(175, frame_height - 100));
		controlPannel.setBackground(Shipyard.backgroundColor);

		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addComponent(controlPannel)
				.addComponent(boardPanel)
				.addComponent(infoPanel)
		);
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(controlPannel)
					.addComponent(boardPanel)
					.addComponent(infoPanel)
					)
		);
		// main.add(infoPanel, BorderLayout.EAST);
		boardPanel.setInfoPanel(infoPanel);
		boardPanel.setControlPanel(controlPannel);
		Hardpoint.setControlPanel(controlPannel);

		setSize(frame_width, frame_height);
		setTitle("ES Shipyard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// setBackground(Color.BLACK);

		main.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "undo");
		main.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), "redo");
		// KeyAction ka = new KeyAction();
		main.getActionMap().put("undo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.undo();
			}
		});
		main.getActionMap().put("redo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.redo();
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(()-> {
			Shipyard app = new Shipyard();
			app.setVisible(true);
			// System.out.println("final size = " + app.getSize().getHeight());
		});
	}

	public static String selectImage() {
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
		fc.setBackground(Shipyard.backgroundColor);
		fc.setForeground(Shipyard.textColor);
		fc.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".png") || f.getName().endsWith(".PNG") || f.isDirectory()) {
					return true;
				}
				return false;
			}
			@Override
			public String getDescription() {
				return "*.png";
			}
		});
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// System.out.println("Opened file:" + file.getName());
			return file.getAbsolutePath();
			// file.getName();
		}
		return null;
	}
}
