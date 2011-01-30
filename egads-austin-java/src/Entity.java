import java.awt.Graphics2D;


public abstract class Entity {
	protected boolean tokill;
	protected static Player p;
	protected int cx,cy,myR;
	public static void setPlayer(Player newP){
		p = newP;
	}
	public abstract void draw(Graphics2D g2);
	public abstract void update();
	public abstract void kill(int condition);
	public abstract void init(GameCore gc);
	public abstract boolean isEdible(int ageState);
	public abstract int getPointsValue();
	public boolean isColliding(int x,int y,int r){
		int dx2 = (x-cx); dx2 *= dx2;
		int dy2 = (y-cy); dy2 *= dy2;
		int sr2 = (r + myR); sr2 *= sr2;
		int sd = dx2+dy2;
		return sr2 <= sd;
	}
}
