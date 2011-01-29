import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.awt.geom.*;

public class Player extends Entity{
	
	//some constants that state at what phase this food can be eaten
	public static final String imgEgg = "art/animations/1egg.png";
	public static final String imgHatched = "art/animations/2hatched.png";
	public static final String imgHindlegs = "art/animations/3hindlegs.png";
	public static final String imgAlmostFrog = "art/animations/4almostfrog.png";
	public static final String imgFrog = "art/animations/5Frog.png";
	public static final int EGG = 0;
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	
	int AgeState = EGG;
	float x = 150; float y = 150; int r = 50;
	private BufferedImage bi;
	private AudioClip ac;
	WeakReference<GameCore> gmc;
	String imgName = imgEgg;
	String sndName;
	boolean up,left,down,right,space;
	int direction = 0;
	double theta = 0;
	double deltatheta = 0;
	double slide = 0;
	int growth = 0;
	int speed = 10;
	int seqslot = 0; // Where I am in the image loop
	int nimgs = 4; // Number of images for the current GIF
	
	
	
	
	public void draw(Graphics2D g2) {
		AffineTransform atmp = g2.getTransform();
		g2.rotate(deltatheta,x + r,y + r);
		g2.drawImage(bi, (int)x, (int)y, null);
		g2.setTransform(atmp);
	}
	
	public void kill(int condition) {
		// TODO Auto-generated method stub
		
	}

	
	//Key Cases that should do nothing: UP && DOWN || LEFT && RIGHT
	public void update() {
		switch(AgeState){
		case EGG: 
			if(needEvolve){
				AgeState = TADPOLE; 
				growth = 0;
				speed = 4;
				imgName = imgHatched;
				bi = gmc.get().getImage(imgName);
				needEvolve = false;
			}
		break;
		case TADPOLE:
			if(needEvolve){
				AgeState = HINDLEGS; 
				growth = 0;
				speed = 3;
				imgName = imgHindlegs;
				bi = gmc.get().getImage(imgName);	
				needEvolve = false;
			}
		break;
		case HINDLEGS:
			if(needEvolve){
				AgeState = NEARFROG; 
				growth = 0;
				speed = 3;
				imgName = imgAlmostFrog;
				bi = gmc.get().getImage(imgName);
				needEvolve = false;
			}
		break;
		case NEARFROG:
			if(needEvolve){
				AgeState = FROG; 
				growth = 0;
				speed = 4;
				imgName = imgFrog;
				bi = gmc.get().getImage(imgName);
				needEvolve = false;
			}
		break;
		case FROG:
			if(needEvolve){
				AgeState = EGG; 
				growth = 0;
				speed = 0;
				imgName = imgEgg;
				bi = gmc.get().getImage(imgName);
				needEvolve = false;
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
			theta -= Math.PI/16;
			deltatheta +=  - Math.PI/16;
		}
		else if(right && !left){
			theta += Math.PI/16;
			deltatheta += Math.PI/16;
		}
		//Now for the movement.
		x += direction*speed*Math.cos(theta);
		y += direction*speed*Math.sin(theta);
		
		
		
	}

	public void init(GameCore gc) {
		bi = gc.getImage(imgName);
		//ac = gc.getAudio(sndName);
		int w = bi.getWidth();
		int h = bi.getHeight();
		r = (w>h) ? h/2 : w/2;
		gmc = new WeakReference<GameCore>(gc);
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
	//Need to be in egg to be able to break egg.
	public void BreakEgg(boolean isDown){
		if(isDown && AgeState == EGG)
			score.addPts(1);
		//growth++;
	}
	boolean isColliding(Enemy e){
		if(e.isColliding((int)x, (int)y, r)){
			if(e.isEdible(AgeState)){ // We need to kill the enemy and increase the score and growth of the the player
			//growth += e.getPointsValue();
			score.addPts(e.getPointsValue());
			e.kill(AgeState);
			
			}
			else // e is not Edible (The player should die)
			{
				//this.kill(AgeState); // show the animation for kill
				// TODO Do we need to make an e.grow(), so enemies can get bigger?
			}
		
		return true;
	}
		else
			return false; // there is no collision, so the calling method does not need to have a pointer.
	}
	
	public void evolve(){
		needEvolve = true;
	}
	private boolean needEvolve = false;
	private ScoreBoard score;
	public Player(ScoreBoard sc){
		score = sc;
	}
	
}