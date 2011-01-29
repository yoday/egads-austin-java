import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameMain extends JApplet {

	private static final long serialVersionUID = -925518549680460124L;
	
	public static final int FRAMES_PER_SEC = 60;
	public static final String GAME_NAME = "GGJ2011";
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private GameCore core;
	private JFrame frame;
	private Timer timer;
	private Dimension size;
	
	public GameMain() {
		size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	}
	
	private void setupGUI() {
		core = new GameCore();
		frame = new JFrame(GAME_NAME) {
			private static final long serialVersionUID = -621672776770107554L;
			public void paint(Graphics g) {
				core.onRender((Graphics2D)g);
			};
		};
		frame.addKeyListener(core);
		frame.addMouseListener(core);
		frame.addMouseMotionListener(core);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= GAME_WIDTH/2;
		p.y -= GAME_HEIGHT/2;
		this.setPreferredSize(size);
		frame.add(this);
		this.requestFocusInWindow();
		this.requestFocus();
		frame.setLocation(p);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		setupTimer();
		core.onInit();
	}
	private void repaintGUI(){
		repaint();
	}
	private void setupTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask(){
			public void run() {
				core.onUpdate(this);
				repaintGUI();
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000 / FRAMES_PER_SEC);
	}
	
	public void init() {
		core = new GameCore();
		this.addKeyListener(core);
		this.addMouseListener(core);
		this.addMouseMotionListener(core);
		this.setPreferredSize(size);
		this.setSize(size);
		this.requestFocus();
		this.requestFocusInWindow();
		setupTimer();
		core.onInit();
	}
	
	public void paint(Graphics g) {
		core.onRender((Graphics2D)g);
	}
	
	public void destroy() {
		core.onExit();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				GameMain game = new GameMain();
				game.setupGUI();
			}
		});
	}
	
}
