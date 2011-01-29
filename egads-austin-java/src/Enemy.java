public abstract class Enemy extends Entity{
	//some constants that enumerate the state of the
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	protected int cx,cy,myR;
	public int getX(){
		return cx;
	}
	public int getY(){
		return cy;
	}
	public boolean isColliding(int x,int y,int r){
		int dx2 = (x-cx); dx2 *= dx2;
		int dy2 = (y-cy); dy2 *= dy2;
		int sr2 = (r + myR); sr2 *= sr2;
		int sd = dx2+dy2;
		return sr2 <= sd;
	}
	public abstract boolean isEdible();
	public abstract int getPointsValue();
	public abstract boolean isMobile();
	
}