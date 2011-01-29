import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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
		frame.setPreferredSize(size);
		frame.setSize(size);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= GAME_WIDTH/2;
		p.y -= GAME_HEIGHT/2;
		frame.setLocation(p);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.requestFocusInWindow();
		frame.requestFocus();
		setupTimer();
		core.onInit();
	}
	
	private void setupTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				core.onUpdate(this);
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000 / FRAMES_PER_SEC);
	}
	
	@Override
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
	
	@Override
	public void paint(Graphics g) {
		core.onRender((Graphics2D)g);
	}
	
	@Override
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
