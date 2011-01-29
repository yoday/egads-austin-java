import java.awt.Graphics2D;

public class Player extends Entity{
	int x = 0; int y = 0;
	boolean up,left,down,right;
	int speed = 0;
	double theta = 0;
	double deltatheta = 0;
	int score = 0;
	

	public void draw(Graphics2D g2) {
		g2.rotate(deltatheta);
		deltatheta = 0;
	}
	
	public void kill(int condition) {
		// TODO Auto-generated method stub
		
	}

	
	//Key Cases that should do nothing: UP && DOWN || LEFT && RIGHT
	public void update() {
		if(up){ // We have up thrust
			speed = 10;
		}
		if(down)
		{
			speed = -10;
		}
		if(left){
			theta += Math.PI/16;
			deltatheta = Math.PI/16;
		}
		if(right){
			theta -= Math.PI/16;
			deltatheta = - Math.PI/16;
		}
		//Now for the movement.
		x += speed* Math.cos(theta);
		y += speed* Math.sin(theta);
		
		
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
	public void ChangeState(){
		//go to next state.
	}
	
	//upthrust, downthrust, Nostate CCW, Cw
	
}