
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

// import Hardpoint.HardpointType;

import java.util.Vector;
import java.util.logging.Logger;
import java.util.logging.Level;

/*
 * Contains all information for a ship like images, data, etc.
 * 
 */
public class Ship {
	private int w, h;
	//Position of the image center relative to the Broad panel.
	private int pos_x, pos_y;
	public double draw_off_x, draw_off_y;
	private int img_pos_x, img_pos_y;
	private String ship_name = "default";
	private String filename = "shuttle.png";
	protected double img_scale = 1;
	protected double prev_img_scale = 1;
	private double orig_img_scale = 1;
	private BufferedImage bimg = null;
	private Image img;
	private Image orig_scaled_img;
	private Exception exception;
	private Vector<Hardpoint> hardpoints = new Vector<Hardpoint>();

	public static int frame_margin = 50;

	public Ship(String filename, JFrame frame) throws IOException,NullPointerException {
		this.filename = filename;
		loadImage(frame);
		if (bimg == null) {
			throw new IIOException(exception.getMessage());
		}
	}

	private void loadImage(JFrame frame) {
		try {
			bimg = ImageIO.read(new File(filename));
		} catch (IOException e) {
			Logger.getLogger("Ship").log(Level.SEVERE, e.getMessage());
			exception = e;
			return ;
		} catch (NullPointerException e) {
			Logger.getLogger("Ship").log(Level.SEVERE, e.getMessage());
			exception = e;
			return ;
		}
		// ImageIcon ii = new ImageIcon(this.filename);
		// img = ii.getImage();
		if (filename.endsWith("@2x")) {
			img = (Image)bimg;
		}
		else {
			// Shipyard sy_frame = (Shipyard) frame;
			int frame_h = Shipyard.frame_height - frame_margin;
			if (frame_h < bimg.getHeight() * 2) {
				img_scale = (double)(frame_h -50.) / ((double)bimg.getHeight() * 2);
				prev_img_scale = img_scale;
				orig_img_scale = img_scale;
				System.out.println("Img too large, scaling by " + img_scale);
			}
			img = bimg.getScaledInstance((int)(bimg.getWidth() * 2 * img_scale), (int)(bimg.getHeight() * 2 * img_scale), Image.SCALE_DEFAULT);
			orig_scaled_img = img;
		}

		w = img.getWidth(null);
		h = img.getHeight(null);
	}

	/*
	 * Scale the image according to img_scale if it wasn't scaled already.
	 * @returns true if image was scaled, false if not;
	 */
	public boolean scaleImg() {
		if (prev_img_scale != img_scale && orig_scaled_img != null) {
			img = orig_scaled_img.getScaledInstance((int)(bimg.getWidth() * 2 * img_scale), (int)(bimg.getHeight() * 2 * img_scale), Image.SCALE_DEFAULT);
			img_pos_x *= img_scale;
			img_pos_y *= img_scale;
			prev_img_scale = img_scale;
			return true;
		}
		return false;
	}

	public double getStartingImgScale() {
		return orig_img_scale;
	}
	public double getImgScale() {
		return img_scale;
	}
	public void setImgScale(double new_scale) {
		img_scale = new_scale;
	}
	public void setImgX(int x) {
		img_pos_x = x;
		pos_x = img_pos_x + w/2;
	}
	public void setImgY(int y) {
		img_pos_y = y;
		pos_y = img_pos_y + h/2;
	}
	public int getImgX() {
		return img_pos_x;
	}
	public int getImgY() {
		return img_pos_y;
	}
	// public void setX(int x) {
	// 	pos_x = x;
	// }
	// public void setY(int y) {
	// 	pos_y = y;
	// }
	public int getX() {
		return pos_x;
	}
	public int getY() {
		return pos_y;
	}
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}
	public Point getCenterPoint() {
		return new Point(w/2, h/2);
	}
	public Image getImage() {
		return img;
	}
	public String getFileName() {
		return filename;
	}

	public void createHardpoint(Hardpoint.HardpointType type, double x, double y, boolean mirrored) {
		hardpoints.add(Hardpoint.createHardpoint(type, x, y, mirrored));
	}
	public Vector<Hardpoint> getHardpoints() {
		return (hardpoints);
	}
}
