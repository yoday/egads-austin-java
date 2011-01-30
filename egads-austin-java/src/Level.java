import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
	private static final int numDivs = 3;
	private int sourceWidth = GameMain.GAME_WIDTH/3;
	private int sourceHeight = GameMain.GAME_HEIGHT/3;
	private int destWidth = GameMain.GAME_WIDTH;
	private int destHeight = GameMain.GAME_HEIGHT;
	private int leftBnd = GameMain.GAME_WIDTH/2;
	private int rightBnd = GameMain.GAME_WIDTH*numDivs - GameMain.GAME_WIDTH/2;
	private int upBnd = GameMain.GAME_HEIGHT/2;
	private int lowBnd = GameMain.GAME_HEIGHT*numDivs - GameMain.GAME_HEIGHT/2;
	GameCore core;
	int viewX;
	int viewY;
	Player player;
	ScoreBoard sb;
	int puddleLevel;
	Circle[] puddleBounds;
	BufferedImage[] puddleImages;
	String[] puddleImageNames = {
			"art/improvedwater/water1.png",
			"art/improvedwater/water2.png",
			"art/improvedwater/water3.png",
			"art/improvedwater/water4.png",
			"art/improvedwater/water5.png",
			"art/improvedwater/water6.png",
			"art/improvedwater/water7.png",
			"art/improvedwater/water8.png",
			"art/improvedwater/water9.png",
	};
	private ArrayList<Enemy> edibles = new ArrayList<Enemy>();
	
	public Level() {
		puddleBounds = new Circle[puddleImageNames.length];
		puddleImages = new BufferedImage[puddleImageNames.length];
	}
	public int getScreenULX(){
		return viewX;
	}
	public int getScreenULY(){
		return viewY;
	}
	public void moveScreen(int newULX,int newULY){
		viewX = newULX;
		viewY = newULY;
	}
	
	public void init(GameCore core) {
		this.core = core;
		float centerX = GameMain.GAME_WIDTH/2;
		float centerY = GameMain.GAME_HEIGHT/2;
		float SCALE = 25;
		float lastRad = GameMain.GAME_HEIGHT/2 + 40;
		for (int i = 0; i < puddleImageNames.length; i++) {
			puddleImages[i] = core.getImage(puddleImageNames[i]);
			puddleBounds[i] = new Circle(centerX, centerY, lastRad -= SCALE);
		}
		sb = new ScoreBoard();
		player = new Player(sb);
		sb.init(core);
		player.init(core);
		core.setPlayer(player);
		Entity.setPlayer(player);
	}
	Circle cursor = new Circle(GameMain.GAME_WIDTH/2, GameMain.GAME_WIDTH/2, 32);
	
	public void render(Graphics2D g) {
		AffineTransform savedTransform = g.getTransform();
		g.translate(0, 0);
		g.setColor(Color.BLACK);
		
		g.drawImage(puddleImages[puddleLevel],0,0,destWidth,destHeight, viewX/3, viewY/3,sourceWidth+viewX/3,sourceHeight+viewY/3, null);
		puddleBounds[puddleLevel].render(g);
		
		cursor.render(g);
		
		g.setTransform(savedTransform);
		
		player.draw(g);
		
		sb.draw(g);
	}
	
	public void update(float dt) {
		player.update();
		
		sb.update();
		int px = player.getCX();
		int py = player.getCY();
		//keep the view away from the corners
		viewX = (px<=leftBnd) ? leftBnd-GameMain.GAME_WIDTH/2 : ( (px>=rightBnd) ? rightBnd-GameMain.GAME_WIDTH/2 : (px-GameMain.GAME_WIDTH/2) ) ;
		viewY = (py<=upBnd)   ? upBnd-GameMain.GAME_HEIGHT/2   : ( (py>=lowBnd  ) ? lowBnd-GameMain.GAME_HEIGHT/2   : (py-GameMain.GAME_HEIGHT/2) ) ;
	}
	
}
