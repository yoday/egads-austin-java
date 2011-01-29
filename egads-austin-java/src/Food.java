import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.lang.ref.*;
import java.applet.*;

public class Food extends Enemy{
	
	private static String[] tadimageNames = new String[]{"art/food/green_50h50w.png"};
	private static String[] tadsoundNames = new String[]{};
	private static int[] tadimgval = new int[]{};
	private static String[] hinimageNames = new String[]{"art/food/yellow_50h50w.png"};
	private static String[] hinsoundNames = new String[]{};
	private static int[] hinimgval = new int[]{};
	private static String[] nerimageNames = new String[]{"art/food/green_75h75w.png"};
	private static String[] nersoundNames = new String[]{};
	private static int[] nerimgval = new int[]{};
	private static String[] frgimageNames = new String[]{"art/food/yellow_75h75w.png"};
	private static String[] frgsoundNames = new String[]{};
	private static int[] frgimgval = new int[]{};
	private Random rand = new Random();
	private BufferedImage bi;
	private AudioClip ac;
	private int score;
	int index;
	String imgnm;
	String sndnm;
	int edible;
	WeakReference<GameCore> gmc;
	
	public Food(int edibility,int x,int y){
		cx = x;
		cy = y;
		edible = edibility;
		switch(edibility){
		case TADPOLE:
			index = rand.nextInt(tadimageNames.length);
			score = tadimgval[index];
			sndnm = tadsoundNames[index];
			imgnm = tadimageNames[index];
			break;
		case HINDLEGS:
			index = rand.nextInt(hinimageNames.length);
			score = hinimgval[index];
			sndnm = hinsoundNames[index];
			imgnm = hinimageNames[index];
			break;
		case NEARFROG:
			index = rand.nextInt(nerimageNames.length);
			score = nerimgval[index];
			sndnm = nersoundNames[index];
			imgnm = nerimageNames[index];
			break;
		case FROG:
			index = rand.nextInt(frgimageNames.length);
			score = frgimgval[index];
			sndnm = frgsoundNames[index];
			imgnm = frgimageNames[index];
			break;
		default: throw new IllegalArgumentException("Edibility must be from TADPOLE to FROG");
		}
	}
	public void draw(Graphics2D g2){
		int x;
		int y;
		x = 0;
		y = 0;
		//x = gmc.get().getLevel().getULX();
		//y = gmc.get().getLevel().getULY();
		g2.drawImage(bi,cx-x,cy-y,null);
	}
	//this food sits still and dissappears when eaten
	public void update(){}
	public void kill(int condition){
		//ac.play();
	}
	public void init(GameCore gc){
		bi = gc.getImage(imgnm);
		ac = gc.getAudio(sndnm);
		int w = bi.getWidth();
		int h = bi.getHeight();
		myR = (w>h) ? h/2 : w/2;
		gmc = new WeakReference<GameCore>(gc);
	}
	public boolean isEdible(int tadState){
		return tadState>=edible;
	}
	public int getPointsValue(){
		return score;
	}
	public boolean isMobile(){
		return false;
	}
}