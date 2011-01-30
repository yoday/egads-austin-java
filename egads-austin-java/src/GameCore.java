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
	private static final int MENU = 0;
	private static final int MAINGAME = 1;
	private static final int GAMEOVER = 2;
	private static final int RUNFROGGYRUN = 3;
	private static final int CREDITS = 4;
	private int gameMode = MENU;
	BufferedImage menuscreen,gameoverscreen;
	private static final long serialVersionUID = 543954373910725885L;
	
	private HashMap<String, BufferedImage> imageCache;
	private HashMap<String, AudioClip> audioCache;
	Player p;
	Button credits,newgame,exit;
	Cutscene frogrun;
	public GameCore() {
		imageCache = new HashMap<String, BufferedImage>();
		audioCache = new HashMap<String, AudioClip>();
		level = new Level();
		frogrun = new Cutscene();
	}
	public void gameOver(){
		gameMode = GAMEOVER;
	}
	public void alertToCutscene(){
		gameMode = RUNFROGGYRUN;
		level.nextStage(this);
	}
	public void setPlayer(Player curPlayer){
		p = curPlayer;
	}
	Level level;
	public Level getLevel(){
		return level;
	}
	GameCore gcref = this;
	boolean first = true;
	AudioClip music;
	public void onInit() {
		frogrun.init(this);
		music = this.getAudio("/soundage/froghopharmony.wav");
		music.loop();
		menuscreen = this.getImage("art/screens/title.png");
		gameoverscreen = this.getImage("art/screens/game-over.png");
		newgame = new Button(this.getImage("art/menu/newgame.png"),this.getImage("art/menu/newgame-selected.png"),this.getImage("art/menu/newgame-selected.png"),new PressListener(){
			public void buttonPressed(){
				if(!first){
					level.resetGame(gcref);
				}
				else{
					first = false;
				}
				gameMode = MAINGAME;
			}
		});
		newgame.init(null);
		newgame.centerOn(675,30);
		credits = new Button(this.getImage("art/menu/credits.png"),this.getImage("art/menu/credits-selected.png"),this.getImage("art/menu/credits-selected.png"),new PressListener(){
			public void buttonPressed(){
				gameMode = CREDITS;
			}
		});
		credits.init(null);
		credits.centerOn(704,90);
		exit = new Button(this.getImage("art/menu/exit.png"),this.getImage("art/menu/exit-selected.png"),this.getImage("art/menu/exit-selected.png"),new PressListener(){
			public void buttonPressed(){
				System.exit(0);
			}
		});
		exit.init(null);
		exit.centerOn(738,152);
		level.init(this);
	}
	public void onRender(Graphics2D g) {
		if(gameMode==MAINGAME){
			level.render(g);
		}
		else{
			if(gameMode==GAMEOVER){
				g.drawImage(gameoverscreen,0,0,null);
			}
			else{
				if(gameMode==MENU){
					g.drawImage(menuscreen,0,0,null);
					newgame.render(g);
					credits.render(g);
					exit.render(g);
				}
				else{
					if(gameMode==RUNFROGGYRUN){
						frogrun.render(g);
					}
				}
			}
		}
	}
	public void onUpdate(TimerTask time) {
		if(gameMode == MENU){
			
		}
		else{
			if(gameMode == MAINGAME){
				//p.update();
				level.update(1000f / (float)GameMain.FRAMES_PER_SEC);
			}
			else{
				if(gameMode == GAMEOVER){
					
				}
				else{
					if(gameMode==RUNFROGGYRUN){
						if(!frogrun.isDone()){
							frogrun.update();
						}
						else{
							gameMode = MAINGAME;
							frogrun.reset();
						}
					}
				}
			}
		}
		
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
		if(gameMode==MAINGAME){
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
			case KeyEvent.VK_SPACE:
				p.BreakEgg(true);
				break;
			default : break;
			}
		}
		else{
			if(gameMode==GAMEOVER){
				if(key.getKeyCode()==KeyEvent.VK_ENTER){
					gameMode = MENU;
				}
			}
		}
		
	}
	public void keyReleased(KeyEvent key) {
		if(gameMode==MAINGAME){
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
		
	}
	
	public void keyTyped(KeyEvent key) {
		
	}
	
	public void mouseDragged(MouseEvent evt) {
		
	}
	
	public void mouseMoved(MouseEvent evt) {
		//level.cursor.x = evt.getPoint().x;
		//level.cursor.y = evt.getPoint().y;
		if(gameMode==MENU){
			newgame.onMouseMove(evt.getX(),evt.getY());
			credits.onMouseMove(evt.getX(),evt.getY());
			exit.onMouseMove(evt.getX(),evt.getY());
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {
		if(gameMode==GAMEOVER){
			gameMode = MENU;
		}
		
	}
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mousePressed(MouseEvent evt) {
		// TODO Auto-generated method stub
		if(gameMode==MENU){
			newgame.onMouseDown(evt.getX(),evt.getY());
			credits.onMouseDown(evt.getX(),evt.getY());
			exit.onMouseDown(evt.getX(),evt.getY());
		}
	}
	
	public void mouseReleased(MouseEvent evt) {
		// TODO Auto-generated method stub
		if(gameMode==MENU){
			newgame.onMouseUp(evt.getX(),evt.getY());
			credits.onMouseUp(evt.getX(),evt.getY());
			exit.onMouseUp(evt.getX(),evt.getY());
		}
	}
	
}
