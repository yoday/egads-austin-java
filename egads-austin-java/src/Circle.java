import java.awt.Color;
import java.awt.Graphics2D;


public final class Circle {
	public float x;
	public float y;
	public float r;
	
	public Circle(float r) {
		this.r = r;
	}
	public Circle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public final boolean isColliding(Circle c) {
		assert c != null;
		return distance2(c) <= (this.r + c.r)*(this.r+c.r);
	}
	public final float distance2(Circle c){
		assert c != null;
		final float dx = this.x - c.x;
		final float dy = this.y - c.y;
		return (float)(dx*dx + dy*dy);
	}
	public final float distance(Circle c) {
		assert c != null;
		final float dx = this.x - c.x;
		final float dy = this.y - c.y;
		return (float)Math.sqrt(dx*dx + dy*dy);
	}
	
	public final boolean isInside(Circle c) {
		assert c != null;
		return distance2(c) <= (c.r - this.r)*(c.r-this.r);
	}
	
	public final void render(Graphics2D g) {
		//g.setColor(new Color(0f, 0f, 0f, 0.25f));
		g.setColor(Color.BLACK);
		g.drawArc((int)(this.x - this.r), (int)(this.y - this.r), (int)(this.r * 2), (int)(this.r * 2), 0, 360);
	}
	
	@Override
	public String toString() {
		return String.format("Circle[x=%f, y=%f, r=%f]", x, y, r);
	}
}
