import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.awt.geom.*;

public class Player extends Entity{
	
	//some constants that state at what phase this food can be eaten
	
//	public static final String imgEgg = "art/animations/1egg.png";
//	public static final String imgHatched = "art/animations/2hatched.png";
//	public static final String imgHindlegs = "art/animations/3hindlegs.png";
//	public static final String imgAlmostFrog = "art/animations/4almostfrog.png";
//	public static final String imgFrog = "art/animations/5Frog.png";
	
//	public static final String [] imgEgg = {"art/animations/1egg.png"};
//	public static final String [] imgHatched = {"art/animations/2hatched.png"};
//	public static final String [] imgHindlegs = {"art/animations/3hindlegs.png"};
//	public static final String [] imgAlmostFrog = {"art/animations/4almostfrog.png"};
//	public static final String [] imgFrog = {"art/animations/5Frog.png"};
//	public static final String [] Currentimg = imgEgg;
//	
	
	public static  BufferedImage [] imgEgg;
	public static  BufferedImage [] imgHatched;
	public static  BufferedImage [] imgHindlegs;
	public static  BufferedImage [] imgAlmostFrog;
	public static  BufferedImage [] imgFrog;
	public static  BufferedImage [] Currentimg = imgEgg;
	
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
	String sndName;
	boolean up,left,down,right,space;
	int direction = 0;
	double theta = 0;
	int growth = 0;
	int speed = 10;
	int seqslot = 0; // Where I am in the image loop
	int nimgs = 4; // Number of images for the current GIF; There are 4 images for the Egg so we start with that.
	int imageDelay = 0;
	int maxDelay = 10;
	
	public int getCX(){
		return (int) (x+r);
	}
	public int getCY(){
		return (int)(y+r);
	}
	
	
	public void draw(Graphics2D g2) {
		bi = Currentimg[seqslot];
		AffineTransform atmp = g2.getTransform();
		g2.rotate(theta,(x-gmc.get().getLevel().getScreenULX()) + r,(y-gmc.get().getLevel().getScreenULY()) + r);
		g2.drawImage(bi, (int)(x-gmc.get().getLevel().getScreenULX()), (int)(y-gmc.get().getLevel().getScreenULY()), null);
		g2.setTransform(atmp);
		if(imageDelay >= maxDelay){
			imageDelay = 0;
		if(seqslot < Currentimg.length -1) seqslot ++; else seqslot = 0;
		}
		else
			imageDelay++;
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
				Currentimg = imgHatched;
				seqslot = 0;
				needEvolve = false;
				bi = Currentimg[0];
				int w = bi.getWidth();
				int h = bi.getHeight();
				r = (w>h) ? h/2 : w/2;
			}
		break;
		case TADPOLE:
			if(needEvolve){
				AgeState = HINDLEGS; 
				growth = 0;
				speed = 3;
				Currentimg = imgHindlegs;
				seqslot = 0;
				needEvolve = false;
				bi = Currentimg[0];
				int w = bi.getWidth();
				int h = bi.getHeight();
				r = (w>h) ? h/2 : w/2;
			}
		break;
		case HINDLEGS:
			if(needEvolve){
				AgeState = NEARFROG; 
				growth = 0;
				speed = 3;
				Currentimg = imgAlmostFrog;
				seqslot = 0;
				needEvolve = false;
				bi = Currentimg[0];
				int w = bi.getWidth();
				int h = bi.getHeight();
				r = (w>h) ? h/2 : w/2;
			}
		break;
		case NEARFROG:
			if(needEvolve){
				AgeState = FROG; 
				growth = 0;
				speed = 4;
				Currentimg = imgFrog;
				seqslot = 0;
				needEvolve = false;
				bi = Currentimg[0];
				int w = bi.getWidth();
				int h = bi.getHeight();
				r = (w>h) ? h/2 : w/2;
			}
		break;
		case FROG:
			if(needEvolve){
				AgeState = EGG; 
				growth = 0;
				speed = 0;
				Currentimg = imgEgg;
				seqslot = 0;
				needEvolve = false;
				bi = Currentimg[0];
				int w = bi.getWidth();
				int h = bi.getHeight();
				r = (w>h) ? h/2 : w/2;
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
			theta -= Math.PI/64;
		}
		else if(right && !left){
			theta += Math.PI/64;
		}
		//Now for the movement.
		x += direction*speed*Math.cos(theta + 3* Math.PI/2);
		y += direction*speed*Math.sin(theta + 3* Math.PI/2);
		
	}

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
				gc.getImage("art/animations/frog/5frog_8.png"),gc.getImage("art/animations/frog/5frog_9.png")};
		Currentimg = imgEgg;
		bi = Currentimg[0];
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
//		if(isDown && AgeState == EGG)
			score.addPts(1);
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