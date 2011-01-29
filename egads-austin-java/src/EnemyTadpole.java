import java.awt.Graphics2D;
import java.util.*;

public class EnemyTadpole extends Enemy {
	
	//some constants
	public static final int EGG = 0;
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	
	int AgeState = EGG;
	List<Enemy> foodList;
	List<Enemy> closestList;
	Enemy target;
	double theta = (Math.PI * 3) / 2;
	double destinationTheta = 0;
	int growth = 0;
	int speed = 10;
	int r = 50;
	int difficulty;

	//constructor
	public EnemyTadpole(int xPos,int yPos, List<Enemy> food, int diff){
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
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean isMobile() {
		if(AgeState == EGG)
			return false;
		return true;
	}
	
	public Enemy getClosest(int amount) {
		closestList = new ArrayList<Enemy>();
		if(amount > foodList.size())
			amount = foodList.size();	
		
		foodList.iterator();
		int count = 0;
		for(Enemy e: foodList) {
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
			Enemy tempEnemy = closestList.get(p);
			int temp = getDistance(tempEnemy);
			int j = p;
			
			for(; j > 0 && temp < getDistance(closestList.get(j-1)); j--)
				closestList.set(j , closestList.get(j-1));
			closestList.set(j , tempEnemy);
		}
	}
	
	//Returns the x^2+ y^2 distance of the enemy
	public int getDistance(Enemy e) {
		int tempx = (this.cx - e.cx); tempx *= tempx;
		int tempy = (this.cy - e.cy); tempy *= tempy;
		return tempx + tempy;
	}
	
	boolean isColliding(Enemy e){
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
