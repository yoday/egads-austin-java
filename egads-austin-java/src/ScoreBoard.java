import java.awt.*;
import java.awt.image.*;

public class ScoreBoard extends Entity{
	//location information
	private static final int BARLOCX = 20;
	private static final int BARLOCY = 20;
	private static final int NUMLOCX = 10;
	private static final int NUMLOCY = 70;
	private static final int BARWIDTH = 600;
	private static final int BARHEIGHT = 20;
	private static final Color BARCOL = new Color(0,0,0,.5f);
	private int NUMWIDTH;
	//image names
	private static String[] numimgnames = new String[]{"art/numbers/0.png","art/numbers/1.png","art/numbers/2.png","art/numbers/3.png","art/numbers/4.png","art/numbers/5.png","art/numbers/6.png","art/numbers/7.png","art/numbers/8.png","art/numbers/9.png"};
	private static String[] onstatenames = new String[]{"art/forevolutionbar/1egg-color.png","art/forevolutionbar/2hatched-color.png","art/forevolutionbar/3hindlegs-color.png","art/forevolutionbar/4almostfrog-color.png","art/forevolutionbar/5frog-color.png"};
	private static String[] offstatenames = new String[]{"art/forevolutionbar/1egg-bw.png","art/forevolutionbar/2hatched-bw.png","art/forevolutionbar/3hindlegs-bw.png","art/forevolutionbar/4almostfrog-bw.png","art/forevolutionbar/5frog-bw.png"};
	//how many points required to proceed to the next state
	private static int[] toNextState = new int[]{10,100,100,100,100};
	//the actual images
	private BufferedImage[] numbs;
	private BufferedImage[] onstates;
	private BufferedImage[] offstates;
	//what state is the tadpole in
	private int curSt = 0;
	//how many points to the next state
	private int curPt = toNextState[0];
	//the score, split apart into digits
	private int[] digits = new int[8];
	//the score in one place
	private long score = 0;
	//used for a rolling counter, how many points must counter count
	private int over = 0;
	public void draw(Graphics2D g2){
		int barw = (BARWIDTH*curSt)/4;
		if(curSt<4){
			barw = barw + (int)((BARWIDTH*(toNextState[curSt]-curPt)/4)/((1.0f) * toNextState[curSt]));
		}
		Color tmp = g2.getColor();
		g2.setColor(BARCOL);
		g2.fillRect(BARLOCX,BARLOCY,barw,BARHEIGHT);
		g2.setColor(tmp);
		
		barw = BARWIDTH /4;
		int cxl = BARLOCX;
		for(int i = 0;i<=curSt;i++){
			g2.drawImage(onstates[i],cxl,BARLOCY,onstates[i].getWidth()/4,onstates[i].getHeight()/4,null);
			cxl += barw;
		}
		for(int i = curSt+1;i<offstates.length;i++){
			g2.drawImage(offstates[i],cxl,BARLOCY,offstates[i].getWidth()/4,offstates[i].getHeight()/4,null);
			cxl += barw;
		}
		int nxl = NUMLOCX;
		for(int i = 0;i<digits.length;i++){
			g2.drawImage(numbs[digits[i]],nxl,NUMLOCY,numbs[digits[i]].getWidth()/4,numbs[digits[i]].getHeight()/4,null);
			nxl += NUMWIDTH/4;
		}
	}
	public void update(){
		
	}
	public void init(GameCore gc){
		numbs = new BufferedImage[numimgnames.length];
		for(int i = 0;i<numbs.length;i++){
			numbs[i] = gc.getImage(numimgnames[i]);
		}
		NUMWIDTH = numbs[0].getWidth();
		onstates = new BufferedImage[onstatenames.length];
		for(int i = 0;i<onstatenames.length;i++){
			onstates[i] = gc.getImage(onstatenames[i]);
		}
		offstates = new BufferedImage[offstatenames.length];
		for(int i = 0;i<offstatenames.length;i++){
			offstates[i] = gc.getImage(offstatenames[i]);
		}
	}
	public void kill(int condition){
		
	}
	public void addPts(int num){
		score += num;
		over += num;
		curPt -= num;
		if(curPt<=0){
			p.evolve();
			curSt += 1;
			if(curSt>= toNextState.length){
				curSt = 0;
			}
			curPt = toNextState[curSt];
		}
		
	}
}