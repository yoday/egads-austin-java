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
	Enemy goToEnemy;

	//constructor
	public EnemyTadpole(int xPos,int yPos, List<Enemy> food){
		cx = xPos;
		cy = yPos;
		foodList = food;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

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
		return true;
	}
	
	public Enemy getClosest(int amount) {
		closestList = new ArrayList<Enemy>();
		if(amount > foodList.size())
			amount = foodList.size();	
		
		foodList.iterator();
		int closestDistance = -1;
		Enemy closestEnemy;
		int count = 0;
		for(Enemy e: foodList) {
			if(count < amount) {
				closestList.add(e);
			}
			else {
				if(count == amount)
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

}
