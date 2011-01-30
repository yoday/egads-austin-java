import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Cutscene {
	
	public enum Mode {
		SPAWN,
		MOVE_OUT,
		MOVE_ACCROSS,
		ARIVE,
	}
	
	BufferedImage background;
	int backgroundWidth;
	int backgroundHeight;
	BufferedImage[] frogAnims;
	int frogState;
	float viewX;
	float viewY;
	float viewVelX;
	float viewVelY;
	float frogX;
	float frogY;
	float frogAngle;
	float frogVelX;
	float frogVelY;
	float frogVelAngle;
	float frogScale;
	int frameCounter;
	float viewScale;
	
	public Cutscene() {
		
	}
	
	public void init(GameCore core) {
		background = core.getImage("art/screens/cut_pond-trans.png");
		frogAnims = new BufferedImage[] {
				core.getImage("art/animations/frog/5frog_1.png"),
				//core.getImage("art/animations/frog/5frog_2.png"),
				core.getImage("art/animations/frog/5frog_3.png"),
				core.getImage("art/animations/frog/5frog_4.png"),
				core.getImage("art/animations/frog/5frog_5.png"),
				core.getImage("art/animations/frog/5frog_6.png"),
				core.getImage("art/animations/frog/5frog_7.png"),
				core.getImage("art/animations/frog/5frog_8.png"),
				core.getImage("art/animations/frog/5frog_9.png"),
				core.getImage("art/animations/frog/5frog_10.png"),
				core.getImage("art/animations/frog/5frog_2.png"),
				core.getImage("art/animations/frog/5frog_11.png")
		};
		backgroundWidth = background.getWidth();
		backgroundHeight = background.getHeight();
		
		reset();
	}
	
	public void render(Graphics2D g) {
		AffineTransform at = g.getTransform();
		
		g.translate(-viewX, -viewY);
		g.scale(2,2);
		//g.scale(viewScale, viewScale);
		g.drawImage(background, 0, 0, backgroundWidth, backgroundHeight, null);
		//g.scale(1/viewScale, 1/viewScale);
		//g.scale(frogScale, frogScale);
		g.scale(.5 * .75, .5 * .75);
		g.rotate(frogAngle, frogX, frogY);
		g.drawImage(frogAnims[frogState], (int)frogX, (int)frogY, null);
		g.setTransform(at);
	}
	
	public void update() {
		frameCounter++;
		System.out.print(frameCounter);
		System.out.printf(" >> %f : %f%n",frogX,frogY);
		
		if (frameCounter < 0)
			return;
		
		if (frameCounter > 830) {
			done = true;
			return;
		}
		
		if (frameCounter > 500 && frameCounter < 650) {
			viewScale -= 0.005;
			frogScale -= 0.008;
			//frogVelX += (frameCounter-500) * 0.09f;
			//frogVelY += (frameCounter-500) * 0.09f;
		}
		
		if (frameCounter % 6 == 0)
			frogState = ++frogState % frogAnims.length;
		
		frogAngle += frogVelAngle;
		
		if (frameCounter < 550) {
			frogVelX += 0.010f * (float)Math.cos(frogAngle - Math.PI/2);
			frogVelY += 0.007f * (float)Math.sin(frogAngle - Math.PI/2);
		
			viewVelX += 0.007f * (float)Math.cos(frogAngle - Math.PI/2);
			viewVelY += 0.005f * (float)Math.sin(frogAngle - Math.PI/2);
		}
		if (frameCounter > 550) {
			frogVelX -= 0.010f * (float)Math.cos(frogAngle - Math.PI/2);
			frogVelY -= 0.007f * (float)Math.sin(frogAngle - Math.PI/2);
		
			viewVelX -= 0.007f * (float)Math.cos(frogAngle - Math.PI/2);
			viewVelY -= 0.005f * (float)Math.sin(frogAngle - Math.PI/2);
		}
		
		frogX += frogVelX;
		frogY += frogVelY;
		
		
		viewX += viewVelX;
		viewY += viewVelY;
		
	}
	
	public void reset() {
		done = false;
		
		frameCounter = -10;
		viewX = 100;
		viewY = 100;
		viewVelX = 0.6f;
		viewVelY = 0.4f;
		viewScale = 2.0f;
		
		frogX = 650;
		frogY = 400;
		frogAngle = -(float)(Math.PI/4 + Math.PI);
		frogVelX = 0.7f;
		frogVelY = 0.5f;
		frogVelAngle = (float)(Math.PI/4096/1.5);
		frogScale = 0.75f;
	}
	
	private boolean done;
	public boolean isDone() {
		return done;
	}
	
}
