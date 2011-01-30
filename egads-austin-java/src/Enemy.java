public abstract class Enemy extends Entity{
	//some constants that enumerate the state of the
	public static final int TADPOLE = 1;
	public static final int HINDLEGS = 2;
	public static final int NEARFROG = 3;
	public static final int FROG = 4;
	public int getX(){
		return cx;
	}
	public int getY(){
		return cy;
	}
	
	public abstract boolean isMobile();
	
}