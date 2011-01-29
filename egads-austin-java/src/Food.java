import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Food extends Enemy{
	
	private static String[] tadimageNames = new String[]{};
	private static int[] tadimgval = new int[]{};
	private static String[] hinimageNames = new String[]{};
	private static int[] hinimgval = new int[]{};
	private static String[] nerimageNames = new String[]{};
	private static int[] nerimgval = new int[]{};
	private static String[] frgimageNames = new String[]{};
	private static int[] frgimgval = new int[]{};
	private Random rand = new Random();
	private BufferedImage bi;
	private int score;
	int index;
	String imgnm;
	int edible;
	public Food(int edibility){
		edible = edibility;
		switch(edibility){
		case TADPOLE:
			index = rand.nextInt(tadimageNames.length);
			score = tadimgval[index];
			imgnm = tadimageNames[index];
			break;
		case HINDLEGS:
			index = rand.nextInt(hinimageNames.length);
			score = hinimgval[index];
			imgnm = hinimageNames[index];
			break;
		case NEARFROG:
			index = rand.nextInt(nerimageNames.length);
			score = nerimgval[index];
			imgnm = nerimageNames[index];
			break;
		case FROG:
			index = rand.nextInt(frgimageNames.length);
			score = frgimgval[index];
			imgnm = frgimageNames[index];
			break;
		default: throw new IllegalArgumentException("Edibility must be from TADPOLE to FROG");
		}
	}
	public void draw(Graphics2D g2){
		
	}
	public void update(){}
	public void kill(int condition){
		
	}
	public void init(GameCore gc){
		
	}
	public boolean isEdible(){
		return false;
	}
	public int getPointsValue(){
		return score;
	}
	public boolean isMobile(){
		return false;
	}
}