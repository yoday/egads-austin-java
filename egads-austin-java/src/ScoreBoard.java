import java.awt.*;
import java.awt.image.*;

public class ScoreBoard extends Entity{
	private static final int BARLOCX = 0;
	private static final int BARLOCY = 0;
	private static final int NUMLOCX = 10;
	private static final int NUMLOCY = 10;
	private static String[] numimgnames = new String[]{"0","1","2","3","4","5","6","7","8","9"};
	private static String[] statenames = new String[]{"","","","",""};
	private static int[] toNextState = new int[]{100,100,100,100,100};
	private BufferedImage[] numbs;
	private BufferedImage[] states;
	
	private int[] digits = new int[8];
	public void draw(Graphics2D g2){
		
	}
	public void update(){
		
	}
	public void init(GameCore gc){
		numbs = new BufferedImage[numimgnames.length];
		for(int i = 0;i<numbs.length;i++){
			numbs[i] = gc.getImage(numimgnames[i]);
		}
	}
	public void kill(int condition){
		
	}
}