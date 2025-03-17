

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultEditorKit.CopyAction;;

// import Hardpoint.HardpointType;

public class InfoDisplay extends JPanel{

	private JTextField coordinateField = new JTextField("X: 000.0 Y: 000.0 A: 0.0");
	private JTextArea hardpointArea = new JTextArea("");

	protected InfoDisplay info_panel;
	protected boolean set_clear_hardpoints = false;

	public InfoDisplay() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		info_panel = this;
		// this.setLayout(new GridLayout(5, 1));

		JButton clearBtn = new JButton("Clear");
		clearBtn.setBackground(Shipyard.backgroundColor);
		clearBtn.setForeground(Shipyard.textColor);
		clearBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(clearBtn, 
											"You're about to delete all created hardpoint coordinate. Confirm?", 
											"Clear all",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE);
				// System.out.println("res:" + res);
				if (res == 0)
				{
					info_panel.hardpointArea.setText("");
					set_clear_hardpoints = true;
				}
			}
		});

		JPanel top_subframe = new JPanel();
		top_subframe.setLayout(new GridLayout(1, 2));
		top_subframe.setMaximumSize(new Dimension(300, 30));

		coordinateField.setEditable(false);
		coordinateField.setVisible(true);
		coordinateField.setBackground(Shipyard.backgroundColor);
		coordinateField.setForeground(Shipyard.textColor);
		coordinateField.setAlignmentX(Component.LEFT_ALIGNMENT);
		coordinateField.setAlignmentY(Component.TOP_ALIGNMENT);
		// coordinateField.setMargin(new Insets(5, 5, 5, 5));
		coordinateField.setMinimumSize(new Dimension(120, 15));
		coordinateField.setMaximumSize(new Dimension(200, 15));
		coordinateField.setPreferredSize(new Dimension(150, 15));
		top_subframe.add(coordinateField);
		top_subframe.add(clearBtn);
		this.add(top_subframe);
		// this.add(coordinateField);

		JScrollPane scrollPane = new JScrollPane(hardpointArea);
		hardpointArea.setEditable(false);
		hardpointArea.setBackground(Shipyard.backgroundColor);
		hardpointArea.setForeground(Shipyard.textColor);
		hardpointArea.setVisible(true);
		hardpointArea.setTabSize(2);
		hardpointArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		hardpointArea.setAlignmentY(Component.TOP_ALIGNMENT);
		hardpointArea.setPreferredSize(new Dimension(150, 500));
		this.add(scrollPane);
		// this.add(hardpointArea);

		//Another focus bs
		// JButton copyButton = new JButton(new CopyAction());
		// copyButton.setText("Copy");
		// this.add(copyButton);
	}

	public void updateCoordinateField(double x, double y, double angle) {
		coordinateField.setText(String.format("X: %2.1f Y: %2.1f A: %2.1f", x, y, angle));
	}

	public void displayHardpoints(Ship ship) {
		if (set_clear_hardpoints) {
			ship.getHardpoints().clear();
			set_clear_hardpoints = false;
			//TODO:clear undo history?
		}
		// int x_off = 0;
		// int y_off = 12;
		hardpointArea.setText("");
		// x_off = 0;
		for (Hardpoint hp : ship.getHardpoints()) {
			hardpointArea.append("\t" + hp.data_string + " " + hp.x + " " + hp.y + "\n");
			if (hp.zoom != 1 && hp.zoom != 0) {
				hardpointArea.append("\t\tzoom " + hp.zoom + "\n");
			}
			if (hp.angle != 0) {
				hardpointArea.append("\t\tangle " + hp.angle + "\n");
			}
			if (hp.parallel) {
				hardpointArea.append("\t\tparallel" + "\n");
			}
			if (hp.gimble != 0) {
				hardpointArea.append("\t\tgimble " + hp.gimble + "\n");
			}
			if (hp.have_arc) {
				hardpointArea.append("\t\tarc " + hp.arc_min + " " + hp.arc_max + "\n");
			}
			if (hp.have_turn_mult) {
				hardpointArea.append("\t\t\"turret turn multiplier\" " + hp.turn_mult + "\n");
			}
			if (hp.over) {
				hardpointArea.append("\t\tover" + "\n");
			}
			if (hp.under) {
				hardpointArea.append("\t\tunder" + "\n");
			}
			if (hp.facing != null){
				switch (hp.facing){
					case LEFT:
						hardpointArea.append("\t\tleft" + "\n");
						break;
					case RIGHT:
						hardpointArea.append("\t\tright" + "\n");
						break;
					case BACK:
						hardpointArea.append("\t\tback" + "\n");
						break;
					default:
				}
			}
			if (hp.launch_effect != null && !hp.launch_effect.equals("") && hp.launch_effect_count > 0) {
				hardpointArea.append("\t\t\"launch effect\" " + hp.launch_effect + " " + hp.launch_effect_count + "\n");
			}
			// tmp.setText(hp.data_string + " " + hp.x + " " + hp.y);
			// System.out.println("Label: " + tmp.getText() + " at [" + x_off + ", " + y_off + "]");
			// x_off += tmp.getFont().getSize() + 5;
			// System.out.println("Check: " + tmp.getText() + " at [" + tmp.getLocation().x + ", " + tmp.getLocation().y + "]");
			// this.add(tmp);
		}
	}
}
