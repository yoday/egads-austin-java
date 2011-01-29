public abstract class Enemy extends Entity{
	protected int cx,cy,myR;
	public boolean isColliding(int x,int y,int r){
		int dx2 = (x-cx); dx2 *= dx2;
		int dy2 = (y-cy); dy2 *= dy2;
		int sr2 = (r + myR); sr2 *= sr2;
		int sd = dx2+dy2;
		return sr2 <= sd;
	}
	public abstract boolean isEdible();
	public abstract int getPointsValue();
}