import java.awt.*;
import java.awt.image.*;

public class Food extends Enemy{
	//some constants that state at what phase this food can be eaten
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	private static String[] tadimageNames = new String[]{};
	private static int[] tadimgval = new int[]{};
	private static String[] hinimageNames = new String[]{};
	private static int[] hinimgval = new int[]{};
	private static String[] nerimageNames = new String[]{};
	private static int[] nerimgval = new int[]{};
	private static String[] frgimageNames = new String[]{};
	private static int[] frgimgval = new int[]{};
	private BufferedImage bi;
	private int score;
	public Food(int edibility){
		
	}
	public void draw(Graphics2D g2){
		
	}
	public void update(){
		
	}
	public void kill(int condition){
		
	}
	public void init(GameCore gc){
		
	}
	public boolean isEdible(){
		return false;
	}
	public int getPointsValue(){
		return 0;
	}
}