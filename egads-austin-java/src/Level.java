import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
	private static final float probabilityOfSpawn = 0.1f;
	int curSpTp = 0;
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
	//length of semimajor axis in x direction
	int[] semia = new int[]{numDivs*(750/2),numDivs*(680/2),numDivs*(610/2),numDivs*(550/2),numDivs*(490/2),numDivs*(450/2),numDivs*(400/2),numDivs*(360/2),numDivs*(320/2)};
	//the center of the pond
	int[] centx = new int[]{410,400,405,400,410,410,400,405,400};
	//length of semimajor axis in y direction
	int[] semib = new int[]{numDivs*(580/2),numDivs*(534/2),numDivs*(474/2),numDivs*(436/2),numDivs*(390/2),numDivs*(340/2),numDivs*(314/2),numDivs*(278/2),numDivs*(250/2)};
	//the y coord of the center of the pond
	int[] centy = new int[]{284,280,290,290,290,290,300,290,300};
	private ArrayList<Enemy> edibles = new ArrayList<Enemy>();
	private Random rand = new Random();
	private float dryness = 0;
	
	private ArrayList<Entity> frogBabies = new ArrayList<Entity>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public boolean isInPond(int x,int y){
		float dx2 = x - centerX;  dx2 *= dx2;
		float dy2 = y - centerY;  dy2 *= dy2;
		float val = (dx2/(semimajorA*semimajorA))+(dy2/(semimajorB*semimajorB));
		
	
		return val<=1;
	}
	public void getRectification(int x,int y,int[] dxdy){
		int dx = centerX-x;
		int dy = centerY-y;
		int ox =x + dx/2;
		int oy = y + dy/2;
		int nx = x;
		int ny = y;
		int dif = 10;
		float outd = 0;
		float ind = 1;
		float midd = 0.5f;
		while(dif>3){
			midd = (outd+ind)/2;
			if(isInPond((int)(midd*dx)+x,(int)(midd*y)+y)){
				ind = midd;
			}
			else{
				outd = midd;
			}
			nx = (int)(dx*(outd+ind)/2+x);
			ny = (int)(dy*(outd+ind)/2+y);
			dif = Math.abs(nx-ox) + Math.abs(ny-oy);
			ox = nx;
			oy = ny;
		}
		dxdy[0] = (int)(midd*dx);
		dxdy[1] = (int)(midd*dy);
	}
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
	public void resetGame(GameCore core){
		
		init(core);
	}
	public void nextStage(GameCore core){
		dryness = 0;
		entities = new ArrayList<Entity>();
		edibles = new ArrayList<Enemy>();
		frogBabies = new ArrayList<Entity>();
		this.core = core;
		float centerX = GameMain.GAME_WIDTH/2;
		float centerY = GameMain.GAME_HEIGHT/2;
		float SCALE = 25;
		float lastRad = GameMain.GAME_HEIGHT/2 + 40;
		for (int i = 0; i < puddleImageNames.length; i++) {
			puddleImages[i] = core.getImage(puddleImageNames[i]);
			puddleBounds[i] = new Circle(centerX, centerY, lastRad -= SCALE);
		}
		//sb = new ScoreBoard();
		player = new Player(sb);
		frogBabies.add(player);
		
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
		frogBabies.add(new EnemyTadpole((int)player.cx + 200 + rand.nextInt(150),
									  (int)player.cy + 200 + rand.nextInt(150),
									  entities,
									  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		frogBabies.add(new EnemyTadpole((int)player.cx - 200 - rand.nextInt(150),
				  					  (int)player.cy + 200 + rand.nextInt(150),
				  					  entities,
				  					  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		frogBabies.add(new EnemyTadpole((int)player.cx + 200 + rand.nextInt(150),
				  					  (int)player.cy - 200 - rand.nextInt(150),
				  					  entities,
				  					  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		frogBabies.add(new EnemyTadpole((int)player.cx - 200 - rand.nextInt(150),
				  					  (int)player.cy - 200 - rand.nextInt(150),
				  					  entities,
				  					  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		entities.addAll(frogBabies);
		entities.addAll(edibles);
		//sb.init(core);
		player.init(core);
		for(Enemy e:edibles){
			e.init(core);
		}
		core.setPlayer(player);
		Entity.setPlayer(player);
	}
	public void init(GameCore core) {
		dryness = 0;
		entities.removeAll(entities);
		edibles.removeAll(edibles);
		frogBabies.retainAll(frogBabies);
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
		frogBabies.add(player);
		
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
		frogBabies.add(new EnemyTadpole((int)player.cx + 200 + rand.nextInt(150),
									  (int)player.cy + 200 + rand.nextInt(150),
									  entities,
									  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		frogBabies.add(new EnemyTadpole((int)player.cx - 200 - rand.nextInt(150),
				  					  (int)player.cy + 200 + rand.nextInt(150),
				  					  entities,
				  					  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		frogBabies.add(new EnemyTadpole((int)player.cx + 200 + rand.nextInt(150),
				  					  (int)player.cy - 200 - rand.nextInt(150),
				  					  entities,
				  					  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		frogBabies.add(new EnemyTadpole((int)player.cx - 200 - rand.nextInt(150),
				  					  (int)player.cy - 200 - rand.nextInt(150),
				  					  entities,
				  					  5));
		frogBabies.get(frogBabies.size() - 1).init(core);
		entities.addAll(frogBabies);
		entities.addAll(edibles);
		sb.init(core);
		player.init(core);
		for(Enemy e:edibles){
			e.init(core);
		}
		core.setPlayer(player);
		Entity.setPlayer(player);
	}
	Circle cursor = new Circle(GameMain.GAME_WIDTH/2, GameMain.GAME_WIDTH/2, 32);
	
	public synchronized void render(Graphics2D g) {
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
		if(ui<puddleImages.length){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,au));
			g.drawImage(puddleImages[ui],0,0,destWidth,destHeight, viewX/3, viewY/3,sourceWidth+viewX/3,sourceHeight+viewY/3, null);
			g.setComposite(tmpc);
		}
		//puddleBounds[puddleLevel].render(g);
		
		//cursor.render(g);
		
		g.setTransform(savedTransform);
		
		for(Entity e:entities){
			e.draw(g);
		}
		//player.draw(g);
		sb.draw(g);
	}
	
	public void HandleEats(){
		for(Entity p:frogBabies){
			for(Entity e:entities){
				if( !p.equals(e) ){ //Can't eat yourself silly.
					if(p instanceof Player){
						//System.out.println(p.getCX() + " " + p.getCY() + " " + p.getmyR() + " " + ((Player)p).theta);
						if(e.isEdible(((Player)p).AgeState) && p.isColliding(e.getCX(),e.getCY(), e.getmyR())){
							((Player)p).eat(e);
						}
					}
					else{ // it is an instance of EnemyTadPole
						if(e.isEdible(((EnemyTadpole)p).AgeState) && p.isColliding(e.cx,e.cy, e.myR))
							((EnemyTadpole)p).eat(e);
					}
				}
			}
		
		}
		
	}
	public void HandleRemoves(){
		ArrayList<Entity> temp = new ArrayList<Entity>();
		for(Entity e:entities){
			if(e.tokill){
				temp.add(e);
			}
		}
		for(Entity e:temp){
			entities.remove(e);
		}
		
	}
	
	public synchronized void update(float dt) {
		if(rand.nextFloat()<probabilityOfSpawn){
			Food f = new Food(curSpTp==0?Food.TADPOLE:(curSpTp==1 ? Food.HINDLEGS : Food.NEARFROG) , rand.nextInt(worldWidth),rand.nextInt(worldHeight));
			f.init(core);
			curSpTp = (curSpTp + 1)%3;
			entities.add(f);
			edibles.add(f);
		}
		//player.update();
		sb.update();
		for(Entity e:entities){
			e.update();
		}
		int px = player.getCX();
		int py = player.getCY();
		//keep the view away from the corners
		viewX = (px<=leftBnd) ? leftBnd-GameMain.GAME_WIDTH/2 : ( (px>=rightBnd) ? rightBnd-GameMain.GAME_WIDTH/2 : (px-GameMain.GAME_WIDTH/2) ) ;
		viewY = (py<=upBnd)   ? upBnd-GameMain.GAME_HEIGHT/2   : ( (py>=lowBnd  ) ? lowBnd-GameMain.GAME_HEIGHT/2   : (py-GameMain.GAME_HEIGHT/2) ) ;
		
		HandleEats();
		HandleRemoves();
		dryness += dndf;
		if(dryness >= (puddleImages.length-1)){
			core.gameOver();
		}
		else{
			int li = (int)Math.floor(dryness);
			int ui = (int)Math.floor(dryness+1);
			float al = ui-dryness;
			float au = dryness-li;
			centerX = (int)(al*centx[li] + au*centx[ui]) * numDivs;
			centerY = (int)(al*centy[li] + au*centy[ui]) * numDivs;
			semimajorA = (int)(al*semia[li] + au*semib[ui]);
			semimajorB = (int)(al*semib[li] + au*semib[ui]);
		}
	}
	int centerX,centerY,semimajorA,semimajorB;
	
}
