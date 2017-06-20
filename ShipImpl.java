import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
public class ShipImpl implements Ship {

	private final static Color FILL = Color.GREEN;
	private final static Color BORDER = Color.BLACK;
	private final static int HIGHEST_I = 0; // the array position of the top
	private final static int LOWEST_I = 1;  // the array position of the bottom
	private final static int FRONT_I = 2;
	private final static int HEIGHT = 20;
	private final static int WIDTH = HEIGHT;
	private Polygon shape;
	private Direction d;
	private Rectangle2D movementBounds;


	public ShipImpl(int x, int y){
		shape = new Polygon(
			new int[] {0,0,WIDTH}, //top left, bottom left, front middle
			new int[] {0,HEIGHT,HEIGHT/2}, 3);
		shape.translate(x, y);
		d = Direction.NONE;
	}

	public void setDirection(Direction d) {
		this.d = d;
	}

	public void setMovementBounds(Rectangle2D movementBounds) {
	}

	public void move() {
		shape.translate(d.dx,d.dy);
	}

	public void draw(Graphics2D g) {
		g.setColor(FILL);
		g.fill(shape); 
		g.setColor(BORDER);
		g.draw(shape);
		g.dispose();
	}

	public Shape getShape() {
		return shape;
	}

	public boolean intersects(Sprite other) {
		return false;
	}
}
