import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

/**
 * This class is the receiver of all inputs of GameMain and
 * allows us to not worry about where the inputs are coming from.
 * 
 * @author Yoan Chinique
 */
public class GameCore implements MouseListener, MouseMotionListener, KeyListener {
	Player p = new Player();
	private static final long serialVersionUID = 543954373910725885L;
	
	private HashMap<String, BufferedImage> imageCache;
	private HashMap<String, AudioClip> audioCache;
	
	public GameCore() {
		imageCache = new HashMap<String, BufferedImage>();
		audioCache = new HashMap<String, AudioClip>();
	}
	
	public void onInit() {
	}
	public void onRender(Graphics2D g) {
		g.clearRect(0, 0, 800, 600);
		p.draw(g);
	}
	public void onUpdate(TimerTask time) {
		p.update();
	}
	public void onExit() {
		
	}
	
	
	public void shutdown() {
			this.onExit();

	}
	/**
	 * This method finds, loads, and caches an image resource given its file path.
	 * 
	 * @param path The file path to the resource.
	 * @return An image is returned or null if it could not be loaded.
	 */
	public BufferedImage getImage(String path) {
		//if the image has been catched, return it
		if (imageCache.containsKey(path))
			return imageCache.get(path);
		
		//open a resource stream, if we can't, then return null
		InputStream stream = this.getClass().getResourceAsStream(path);
		if (stream == null)
			return null;
		
		//try to decode the image from the input stream
		BufferedImage img;
		try {
			//if we succeed, cache the image and return it
			img = ImageIO.read(stream);
			imageCache.put(path, img);
			return img;
		} catch (IOException e) {
			//if we fail, print the error and return null
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * This method finds, loads, and caches an audio resource given its file path.
	 * 
	 * @param path The file path to the resource.
	 * @return An AudioClip is returned or null if it could not be loaded.
	 */
	public AudioClip getAudio(String path) {
		//if it has been catched, return it
		if (audioCache.containsKey(path))
			return audioCache.get(path);
		
		//get the url of the resource, if we fail, return null
		URL url = this.getClass().getResource(path);
		if (url == null)
			return null;
		
		//try to load the clip, if we fail, return null
		AudioClip clip = JApplet.newAudioClip(url);
		if (clip == null)
			return null;
		
		//if we succeed, catch it and return it
		audioCache.put(path, clip);
		return clip;
	}
	
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()){
		case KeyEvent.VK_W: 
			p.upthrust(true);
			break;
		case KeyEvent.VK_S:
			p.downthrust(true);
			break;
		case KeyEvent.VK_A:
			p.leftturn(true);
			break;
		case KeyEvent.VK_D:
			p.rightturn(true);
			break;
		default : break;
		}
		
		
	}
	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()){
		case KeyEvent.VK_W: 
			p.upthrust(false);
			break;
		case KeyEvent.VK_S:
			p.downthrust(false);
			break;
		case KeyEvent.VK_A:
			p.leftturn(false);
			break;
		case KeyEvent.VK_D:
			p.rightturn(false);
			break;
		default : break;
		}
		
	}
	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
