import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
	private static final int numDivs = 3;
	private static final int initSpawn = 100;
	private static final float dndf = 0.001f;
	private int sourceWidth = GameMain.GAME_WIDTH/numDivs;
	private int sourceHeight = GameMain.GAME_HEIGHT/numDivs;
	private int destWidth = GameMain.GAME_WIDTH;
	private int destHeight = GameMain.GAME_HEIGHT;
	private int leftBnd = GameMain.GAME_WIDTH/2;
	private int rightBnd = GameMain.GAME_WIDTH*numDivs - GameMain.GAME_WIDTH/2;
	private int upBnd = GameMain.GAME_HEIGHT/2;
	private int lowBnd = GameMain.GAME_HEIGHT*numDivs - GameMain.GAME_HEIGHT/2;
	private int worldWidth = numDivs*GameMain.GAME_WIDTH;
	private int worldHeight = numDivs*GameMain.GAME_HEIGHT;
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
	private Random rand = new Random();
	private float dryness = 0;
	
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
		int curLv = 0;
		for(int i = 0;i<initSpawn;i++){
			int fx = rand.nextInt(worldWidth);
			int fy = rand.nextInt(worldHeight);
			int ed = 1;
			switch(curLv){
			case 0: ed = Food.TADPOLE; break;
			case 1: ed = Food.HINDLEGS; break;
			case 2: ed = Food.NEARFROG; break;
			case 3: ed = Food.FROG; break;
			}
			edibles.add(new Food(ed,fx,fy));
			curLv = (curLv + 1)%4;
		}
		
		sb.init(core);
		player.init(core);
		for(Enemy e:edibles){
			e.init(core);
		}
		core.setPlayer(player);
		Entity.setPlayer(player);
	}
	Circle cursor = new Circle(GameMain.GAME_WIDTH/2, GameMain.GAME_WIDTH/2, 32);
	
	public void render(Graphics2D g) {
		AffineTransform savedTransform = g.getTransform();
		g.translate(0, 0);
		g.setColor(Color.BLACK);
		int li = (int)Math.floor(dryness);
		int ui = (int)Math.floor(dryness+1);
		float al = ui-dryness;
		float au = dryness-li;
		Composite tmpc = g.getComposite();
		//g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,al));
		g.drawImage(puddleImages[li],0,0,destWidth,destHeight, viewX/3, viewY/3,sourceWidth+viewX/3,sourceHeight+viewY/3, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,au));
		g.drawImage(puddleImages[ui],0,0,destWidth,destHeight, viewX/3, viewY/3,sourceWidth+viewX/3,sourceHeight+viewY/3, null);
		g.setComposite(tmpc);
		//puddleBounds[puddleLevel].render(g);
		
		//cursor.render(g);
		
		g.setTransform(savedTransform);
		
		
		for(Enemy e:edibles){
			e.draw(g);
		}
		player.draw(g);
		sb.draw(g);
	}
	
	public void update(float dt) {
		player.update();
		sb.update();
		for(Enemy e:edibles){
			e.update();
		}
		int px = player.getCX();
		int py = player.getCY();
		//keep the view away from the corners
		viewX = (px<=leftBnd) ? leftBnd-GameMain.GAME_WIDTH/2 : ( (px>=rightBnd) ? rightBnd-GameMain.GAME_WIDTH/2 : (px-GameMain.GAME_WIDTH/2) ) ;
		viewY = (py<=upBnd)   ? upBnd-GameMain.GAME_HEIGHT/2   : ( (py>=lowBnd  ) ? lowBnd-GameMain.GAME_HEIGHT/2   : (py-GameMain.GAME_HEIGHT/2) ) ;
		dryness += dndf;
		if(dryness > puddleImages.length){
			//game over
		}
	}
	
}
