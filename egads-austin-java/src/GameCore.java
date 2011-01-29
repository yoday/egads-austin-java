import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;

import javax.imageio.ImageIO;


public class GameCore implements MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 543954373910725885L;
	
	public GameCore() {
		super();
	}
	
	public void onInit() {
		
	}
	public void onRender(Graphics2D g) {
		
	}
	public void onUpdate(TimerTask time) {
		
	}
	public void onExit() {
		
	}
	
	public void shutdown() {
		throw new RuntimeException("Method not implemented!");
	}
	public BufferedImage getImage(String path) {
		InputStream stream = this.getClass().getResourceAsStream(path);
		if (stream == null)
			return null;
		try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Object getAudio(String path) {
		return null;
	}
	public void saveScore(String player, int score) {
		throw new RuntimeException("Method not implemented!");
	}
	public int loadScore(String player) {
		throw new RuntimeException("Method not implemented!");
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
