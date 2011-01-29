import java.awt.Graphics2D;


public abstract class Entity {
	protected static Player p;
	public static void setPlayer(Player newP){
		p = newP;
	}
	public abstract void draw(Graphics2D g2);
	public abstract void update();
	public abstract void kill(int condition);
}
