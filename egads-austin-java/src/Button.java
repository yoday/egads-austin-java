import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Button {
	public enum State {
		NORMAL,
		OVER,
		DOWN;
	}
	
	public Rectangle bounds;
	private BufferedImage mouseOverImage;
	private BufferedImage mouseDownImage;
	private BufferedImage mouseNormalImage;
	private boolean clicked;
	private State state;
	public String text;
	
	public Button() {
		this("<BUTTON>");
	}
	public Button(String str) {
		text = str;
		state = State.NORMAL;
	}
	
	public void init(GameCore core) {
		mouseOverImage = core.getImage("art/button_over.png");
		mouseDownImage = core.getImage("art/button_down.png");
		mouseNormalImage = core.getImage("art/button_normal.png");
		clicked = false;
		bounds = new Rectangle();
		bounds.width = mouseDownImage.getWidth();
		bounds.height = mouseDownImage.getHeight();
	}
	
	public void reset() {
		clicked = false;
		state = State.NORMAL;
	}
	
	public void centerOn(int x, int y) {
		bounds.x = x - bounds.width/2;
		bounds.y = y - bounds.height/2;
	}
	
	public boolean wasClicked() {
		return clicked;
	}
	
	public void render(Graphics2D g) {
		switch (state) {
		case DOWN:
			g.drawImage(mouseDownImage, bounds.x, bounds.y, bounds.width, bounds.height, null);
			break;
		case OVER:
			g.drawImage(mouseOverImage, bounds.x, bounds.y, bounds.width, bounds.height, null);
			break;
		case NORMAL:
			g.drawImage(mouseNormalImage, bounds.x, bounds.y, bounds.width, bounds.height, null);
			break;
		default:
			throw new RuntimeException();
		}
		
		if (clicked) {
			g.setColor(Color.BLACK);
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			g.setColor(Color.WHITE);
			g.drawString("Button has been pressed.", bounds.x + bounds.width/16, bounds.y + bounds.height/2);
		}
		
		Rectangle2D rect = g.getFontMetrics().getStringBounds(text, g);
		g.drawString(text, bounds.x + (int)rect.getCenterX()/2, bounds.y + (int)rect.getCenterY()/2);
	}
	
	public void onMouseMove(int x, int y) {
		if (bounds.contains(x, y))
			state = State.OVER;
		else
			state = State.NORMAL;
	}
	public void onMouseDown(int x, int y) {
		if (bounds.contains(x, y) || state == State.OVER)
			state = State.DOWN;
	}
	public void onMouseUp(int x, int y) {
		if (bounds.contains(x, y) && state == State.DOWN) {
			state = State.OVER;
			clicked = true;
		}
	}

}
