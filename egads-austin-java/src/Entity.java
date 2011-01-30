import java.awt.Graphics2D;


public abstract class Entity {
	protected boolean tokill;
	protected static Player p;
	protected int cx,cy,myR;
	public static void setPlayer(Player newP){
		p = newP;
	}
	public int getCX(){return cx;}
	public int getCY(){return cy;}
	public int getmyR(){return myR;}
	public abstract void draw(Graphics2D g2);
	public abstract void update();
	public abstract void kill(int condition);
	public abstract void init(GameCore gc);
	public abstract boolean isEdible(int ageState);
	public abstract int getPointsValue();
	public boolean isColliding(int x,int y,int r){
		//System.out.println("x:" + x + " cx: " + getCX() + " y: " + y + " cy:" + getCY());
		int dx2 = (x-getCX()); dx2 *= dx2;
		int dy2 = (y-getCY()); dy2 *= dy2;
		int sr2 = (r + getmyR()); sr2 *= sr2;
		int sd = dx2+dy2;
		//System.out.println(dx2 + " " + dy2 + " " + sd + " " + sr2);
		return sr2 >= sd;
		/*int dx2 = (x-cx)*(x-cx);
		int dy2 = (y-cy)*(y-cy);
		int sd = (int)Math.sqrt(dx2+dy2);
		int sr2 = (r+myR);
		if(sd <= sr2)
			return true;
		else
			return false;*/
	}
}
