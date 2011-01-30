import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.lang.ref.*;
import java.applet.*;

public class Food extends Enemy{
	
	private static String[] tadimageNames = new String[]{"art/food/green_50h50w.png"};
	private static String[] tadsoundNames = new String[]{"soundage/eating.wav"};
	private static int[] tadimgval = new int[]{10};
	private static String[] hinimageNames = new String[]{"art/food/yellow_50h50w.png"};
	private static String[] hinsoundNames = new String[]{"soundage/eating.wav"};
	private static int[] hinimgval = new int[]{20};
	private static String[] nerimageNames = new String[]{"art/food/green_75h75w.png"};
	private static String[] nersoundNames = new String[]{"soundage/eating.wav"};
	private static int[] nerimgval = new int[]{30};
	private static String[] frgimageNames = new String[]{"art/food/yellow_75h75w.png"};
	private static String[] frgsoundNames = new String[]{"soundage/eating.wav"};
	private static int[] frgimgval = new int[]{40};
	private Random rand = new Random();
	private BufferedImage bi;
	private AudioClip ac;
	private int score;
	private static int[] rect = new int[2];
	int index;
	String imgnm;
	String sndnm;
	int edible;
	WeakReference<GameCore> gmc;
	int wo2,ho2;
	public Food(int edibility,int x,int y){
		cx = x;
		cy = y;
		edible = edibility;
		tokill = false;
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
		x = gmc.get().getLevel().getScreenULX();
		y = gmc.get().getLevel().getScreenULY();
		g2.drawImage(bi,cx-x-wo2,cy-y-ho2,null);
	}
	//this food sits still and dissappears when eaten
	boolean first = true;
	public void update(){
		if(gmc.get().getLevel().isInPond(cx,cy)){}
		else{
			tokill = true;
		}
	}
	public void kill(int condition){
		ac.play();
		tokill = true;
	}
	public void init(GameCore gc){
		bi = gc.getImage(imgnm);
		ac = gc.getAudio(sndnm);
		int w = bi.getWidth();
		int h = bi.getHeight();
		myR = (w>h) ? h/2 : w/2;
		gmc = new WeakReference<GameCore>(gc);
		wo2 = w/2;
		ho2 = h/2;
		cx += wo2;
		cy += ho2;
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