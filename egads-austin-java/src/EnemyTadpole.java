import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.util.*;

public class EnemyTadpole extends Enemy {
	
	//some constants
	public static final int EGG = 0;
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	
	public static  BufferedImage [] imgEgg;
	public static  BufferedImage [] imgHatched;
	public static  BufferedImage [] imgHindlegs;
	public static  BufferedImage [] imgAlmostFrog;
	public static  BufferedImage [] imgFrog;
	public static  BufferedImage [] Currentimg = imgEgg;
	
	private BufferedImage bi;
	private AudioClip ac;
	WeakReference<GameCore> gmc;
	
	int AgeState = EGG;
	List<Entity> foodList;
	List<Entity> closestList;
	Entity target;
	double theta = (Math.PI * 3) / 2;
	double destinationTheta = 0;
	int growth = 0;
	int speed = 10;
	int r = 50;
	int difficulty;
	int eggTimer = 0;

	//constructor
	public EnemyTadpole(int xPos,int yPos, List<Entity> food, int diff){
		cx = xPos;
		cy = yPos;
		foodList = food;
		difficulty = diff;
	}
	
	@Override
	public boolean isEdible(int tadpoleState) {
		return tadpoleState > AgeState;
	}

	@Override
	public int getPointsValue() {
		return (AgeState+2)*2;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.rotate(theta,cx + r,cy + r);
		//DRAW THE IMAGE
		//g2.drawImage(bi, (int)x, (int)y, null);
		g2.rotate(-theta,-(cx + r),-(cy + r));
	}

	@Override
	public void update() {
		switch(AgeState){
		case EGG: 
			if(growth >= 20){
				AgeState = TADPOLE; 
				growth = 0;
				speed = 4;
				//imgName = imgHatched;
				//bi = gmc.get().getImage(imgName);
				target = getClosest(difficulty);
			}
			eggTimer++;
			if(eggTimer >= 20) {
				eggTimer = 0;
				growth++;
			}
		break;
		case TADPOLE:
			if(growth >= 40){
				AgeState = HINDLEGS; 
				growth = 0;
				speed = 3;
				//imgName = imgHindlegs;
				//bi = gmc.get().getImage(imgName);	
			}
		break;
		case HINDLEGS:
			if(growth >= 80){
				AgeState = NEARFROG; 
				growth = 0;
				speed = 3;
				//imgName = imgAlmostFrog;
				//bi = gmc.get().getImage(imgName);	
			}
		break;
		case NEARFROG:
			if(growth >= 160){
				AgeState = FROG; 
				growth = 0;
				speed = 4;
				//imgName = imgFrog;
				//bi = gmc.get().getImage(imgName);	
			}
		break;
		case FROG:
			if(growth >= 320){
				AgeState = EGG; 
				growth = 0;
				speed = 0;
				//imgName = imgEgg;
				//bi = gmc.get().getImage(imgName);	
			}
		break;
		}
		if( target == null )
			target = getClosest(difficulty);
		
		destinationTheta = Math.abs(Math.atan((1.0*(target.cy - cy))/(1.0*(target.cx - cx))));
		if(destinationTheta > theta) {
			if(destinationTheta - theta <= Math.PI && destinationTheta - theta >= 0)
				theta += Math.PI/16;
			else
				theta -= Math.PI/16;
		}
		else {
			if(theta - destinationTheta <= Math.PI && theta - destinationTheta >= 0)
				theta -= Math.PI/16;
			else
				theta += Math.PI/16;
		}
		
		cx += speed*Math.cos(theta);
		cy += speed*Math.sin(theta);
	}

	@Override
	public void kill(int condition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GameCore gc) {
		imgEgg = new BufferedImage [] {gc.getImage("art/animations/egg/1egg_1.png"),
				gc.getImage("art/animations/egg/1egg_2.png"),gc.getImage("art/animations/egg/1egg_3.png"),
				gc.getImage("art/animations/egg/1egg_4.png")};
		 imgHatched = new BufferedImage []  {gc.getImage("art/animations/hatched/2hatched_1.png"),
				gc.getImage("art/animations/hatched/2hatched_2.png"),gc.getImage("art/animations/hatched/2hatched_3.png"),
				gc.getImage("art/animations/hatched/2hatched_4.png"),gc.getImage("art/animations/hatched/2hatched_5.png"),
				gc.getImage("art/animations/hatched/2hatched_6.png"),gc.getImage("art/animations/hatched/2hatched_7.png"),
				gc.getImage("art/animations/hatched/2hatched_8.png")};
		imgHindlegs = new BufferedImage []  {gc.getImage("art/animations/hindlegs/3hindlegs_1.png"),
				gc.getImage("art/animations/hindlegs/3hindlegs_2.png"),gc.getImage("art/animations/hindlegs/3hindlegs_3.png"),
				gc.getImage("art/animations/hindlegs/3hindlegs_4.png"),gc.getImage("art/animations/hindlegs/3hindlegs_5.png"),
				gc.getImage("art/animations/hindlegs/3hindlegs_6.png"),gc.getImage("art/animations/hindlegs/3hindlegs_7.png"),
				gc.getImage("art/animations/hindlegs/3hindlegs_8.png")};
		 imgAlmostFrog = new BufferedImage []  {gc.getImage("art/animations/almostfrog/4almostfrog_1.png"),
				gc.getImage("art/animations/almostfrog/4almostfrog_2.png"),gc.getImage("art/animations/almostfrog/4almostfrog_3.png"),
				gc.getImage("art/animations/almostfrog/4almostfrog_4.png")};
		 imgFrog  = new BufferedImage [] {gc.getImage("art/animations/frog/5frog_1.png"),
				gc.getImage("art/animations/frog/5frog_2.png"),gc.getImage("art/animations/frog/5frog_3.png"),
				gc.getImage("art/animations/frog/5frog_4.png"),gc.getImage("art/animations/frog/5frog_5.png"),
				gc.getImage("art/animations/frog/5frog_6.png"),gc.getImage("art/animations/frog/5frog_7.png"),
				gc.getImage("art/animations/frog/5frog_8.png"),gc.getImage("art/animations/frog/5frog_9.png"),
				gc.getImage("art/animations/frog/5frog_10.png"),gc.getImage("art/animations/frog/5frog_11.png")};
		Currentimg = imgEgg;
		bi = Currentimg[0];
		int w = bi.getWidth();
		int h = bi.getHeight();
		r = (w>h) ? h/2 : w/2;
		gmc = new WeakReference<GameCore>(gc);

	}
	
	@Override
	public boolean isMobile() {
		if(AgeState == EGG)
			return false;
		return true;
	}
	
	public Entity getClosest(int amount) {
		closestList = new ArrayList<Entity>();
		if(amount > foodList.size())
			amount = foodList.size();	
		
		foodList.iterator();
		int count = 0;
		for(Entity e: foodList) {
			if(count < amount) {
				closestList.add(e);
			}
			else {
				if(count == amount) // This is going to be sorting a lot, even if you don't add stuff.
					sortClosestList();
				int temp = getDistance(e);
				if(temp < getDistance(closestList.get(amount))) {
					int j = amount;
			
					for(; j > 0 && temp < getDistance(closestList.get(j-1)); j--)
						closestList.set(j , closestList.get(j-1));
					closestList.set(j , e);
				}
			}
			count++;			
		}
		Random rand = new Random();
		return closestList.get(rand.nextInt(amount));
	}
	
	public void sortClosestList(){
		for( int p = 1; p < closestList.size(); p++ ){
			Entity tempEntity = closestList.get(p);
			int temp = getDistance(tempEntity);
			int j = p;
			
			for(; j > 0 && temp < getDistance(closestList.get(j-1)); j--)
				closestList.set(j , closestList.get(j-1));
			closestList.set(j , tempEntity);
		}
	}
	
	//Returns the x^2+ y^2 distance of the enemy
	public int getDistance(Entity e) {
		int tempx = (this.cx - e.cx); tempx *= tempx;
		int tempy = (this.cy - e.cy); tempy *= tempy;
		return tempx + tempy;
	}
	
	boolean isColliding(Entity e){
		if(e.isColliding(cx, cy, r)){
			if(e.isEdible(AgeState)){ // We need to kill the enemy and increase the score and growth of the the player
				growth += e.getPointsValue();
				e.kill(AgeState);
				target = getClosest(difficulty);
			}
			else // e is not Edible (The player should die)
			{
				this.kill(AgeState); // show the animation for kill
				// TODO Do we need to make an e.grow(), so enemies can get bigger?
			}
		
		return true;
	}
		else
			return false; // there is no collision, so the calling method does not need to have a pointer.
	}

}
