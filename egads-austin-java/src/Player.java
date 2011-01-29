import java.awt.Graphics2D;

public class Player extends Entity{
	
	//some constants that state at what phase this food can be eaten
	public static final int EGG = 0;
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	
	int AgeState = EGG;
	int x = 0; int y = 0; int r = 7;
	boolean up,left,down,right,space;
	int direction = 0;
	double theta = 0;
	double deltatheta = 0;
	int growth = 0;
	int speed = 0;
	
	
	
	public void draw(Graphics2D g2) {
		g2.rotate(deltatheta);
		g2.drawOval(x, y, r, r/2);
		g2.rotate(-deltatheta);
	}
	
	public void kill(int condition) {
		// TODO Auto-generated method stub
		
	}

	
	//Key Cases that should do nothing: UP && DOWN || LEFT && RIGHT
	public void update() {
		switch(AgeState){
		case EGG: 
			if(growth >= 20){
				AgeState = TADPOLE; 
				growth = 0;
				speed = 2;
			}
		break;
		case TADPOLE:
			if(growth >= 40){
				AgeState = HINDLEGS; 
				growth = 0;
				speed = 3;
			}
		break;
		case HINDLEGS:
			if(growth >= 80){
				AgeState = NEARFROG; 
				growth = 0;
				speed = 3;
			}
		break;
		case NEARFROG:
			if(growth >= 160){
				AgeState = FROG; 
				growth = 0;
				speed = 4;
			}
		break;
		case FROG:
			if(growth >= 320){
				AgeState = EGG; 
				growth = 0;
				speed = 0;
			}
		break;
		}
		if(up && !down){ // We have up thrust
			direction = 1;
		}
		else if(down && !up)
		{
			direction = -1;
		}
		else { // No movement
			direction = 0;
		}
		if(left && !right){
			theta += Math.PI/16;
			deltatheta = Math.PI/16;
		}
		else if(right && !left){
			theta -= Math.PI/16;
			deltatheta = - Math.PI/16;
		}
		//Now for the movement.
		x += direction*speed*Math.cos(theta);
		y += direction*speed*Math.sin(theta);
		
		
		
	}

	public void init(GameCore gc) {
		// TODO Auto-generated method stub
		
	}
	public void upthrust(boolean isDown){
		this.up = isDown; // pressing the up key
	}
	public void downthrust(boolean isDown){
		this.down = isDown;
	}
	public void leftturn(boolean isDown){
		this.left = isDown;
	}
	public void rightturn(boolean isDown){
		this.right = isDown;
	}
	public void BreakEgg(boolean isDown){
		if(isDown && AgeState == EGG)
		growth++;
	}
	boolean isColliding(Enemy e){
		if(e.isColliding(x, y, r)){
			if(e.isEdible() && AgeState  != EGG){ // We need to kill the enemy and increase the score and growth of the the player
			growth += e.getPointsValue();
			e.kill(AgeState);
			
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