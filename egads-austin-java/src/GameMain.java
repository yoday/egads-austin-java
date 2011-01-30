import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

/**
 * This class is the "entry point" to the game. That is, when the
 * game is ran as an applet or a desktop application it manages and
 * makes input, drawing, and update calls to the GameCore.
 * 
 * @author Yoan Chinique
 */
public class GameMain extends JApplet {

	private static final long serialVersionUID = -925518549680460124L;
	//tocom
	//global variables
	public static final int FRAMES_PER_SEC = 60;
	public static final String GAME_NAME = "http://code.google.com/p/egads-austin-java/";
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final Dimension GAME_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	
	private GameCore core;
	private JFrame frame;
	private Timer timer;
	private BufferedImage screenBuffer;
	private Graphics2D screenGraphics;
	
	public GameMain() {
	}
	
	/**
	 * Method for double buffering.
	 */
	public void bufferAndRender(Graphics2D g) {
		g.drawImage(screenBuffer, 0, 0, null);
		screenGraphics = screenBuffer.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		//screenGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		core.onRender(screenGraphics);
		screenGraphics.dispose();
	}
	
	/**
	 * This method handles desktop mode for the game.
	 */
	private void setupGUI() {
		core = new GameCore();
		screenBuffer = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screenBuffer.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		screenGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		screenGraphics.dispose();
		//create a frame, capture and forward painting to GameCore
		frame = new JFrame(GAME_NAME) {
			private static final long serialVersionUID = -621672776770107554L;
			public void paint(Graphics g) {
				bufferAndRender((Graphics2D)g);
			};
		};
		//capture and forward inputs to GameCore
		frame.addKeyListener(core);
		frame.addMouseListener(core);
		frame.addMouseMotionListener(core);
		this.addKeyListener(core);
		this.addMouseListener(core);
		this.addMouseMotionListener(core);
		//close the window when you click on the X
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//calculate and set the position of the window to center it on the screen
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= GAME_WIDTH/2;
		p.y -= GAME_HEIGHT/2;
		frame.setLocation(p);
		this.setPreferredSize(GAME_SIZE);
		frame.setPreferredSize(GAME_SIZE);
		this.setSize(GAME_SIZE);
		frame.setSize(GAME_SIZE);
		//more steps to make the frame ready for the game
		frame.add(this);
		this.requestFocusInWindow();
		this.requestFocus();
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		//set up the update timer and initialize the GameCore
		core.onInit();
		
		setupTimer();
		
	}
	
	/**
	 * Here we set up a timer that will give us updates at a (near)
	 * constant rate based on the FRAMES_PER_SEC variable;
	 */
	private void setupTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask(){
			public void run() {
				core.onUpdate(this);
				repaint();
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000 / FRAMES_PER_SEC);
	}
	
	/**
	 * This is the initialization method for applet mode.
	 */
	public void init() {
		core = new GameCore();
		screenBuffer = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screenBuffer.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		screenGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		screenGraphics.dispose();
		//capture and forward inputs to GameCore
		this.addKeyListener(core);
		this.addMouseListener(core);
		this.addMouseMotionListener(core);
		//set up sizes
		this.setPreferredSize(GAME_SIZE);
		this.setSize(GAME_SIZE);
		//force focus so that all inputs go to us
		this.requestFocus();
		this.requestFocusInWindow();
		//set up the update timer and initialize the GameCore
		setupTimer();
		core.onInit();
	}
	
	/**
	 * This allows us to intercept and forward painting
	 * from the applet to the GameCore.
	 */
	public void paint(Graphics g) {
		bufferAndRender((Graphics2D)g);
	}
	
	/**
	 * This allows us to intercept and forward the destruction
	 * of the applet to the GameCore.
	 */
	public void destroy() {
		core.onExit();
	}
	
	/**
	 * We use this so that if the game is executed as an application
	 * we can make a frame to get inputs and graphics.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				GameMain game = new GameMain();
				game.setupGUI();
			}
		});
	}
	
}
