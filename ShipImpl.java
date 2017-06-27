import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Game of Gradius, with features. 10 java files + 1 jpg image in total.
 * @Name			Gradius
 * @Author 			Trung Kien Nguyen (aka :Ken Nguyen) 
 * @ID 	   			100284963	
 * @CoAuthor 		Professor Jeremy Hilliker
 * @School			Langara College
 * @Course 			CPSC 1181
 * @Date   			June 27th, 2017
 * @version 		1.0 
 * @Acknowledgement Pro.Jeremy Hilliker, StackOverFLow, Javadoc 
 * @GitHub			https://github.com/ikendoit/Gradius
 */

public class ShipImpl implements Ship {

	private final static Color FILL = Color.GREEN;
	private final static Color BORDER = Color.BLACK;
	private final static int HIGHEST_I = 0; // the array position of the top
	private final static int LOWEST_I = 1;  // the array position of the bottom
	private final static int FRONT_I = 2;
	private final static int HEIGHT = 20;
	private final static int WIDTH = HEIGHT;
	public static int health;
	public static final int health_MAX = 18;
	private Polygon shape;
	private Direction d;
	public Rectangle2D movementBounds;

	/**
	 * constructor for the shipImpl
	 * @param x	the x position of the ship
	 * @param y	the y position of the ship
	 */
	public ShipImpl(int x, int y){
		shape = new Polygon(
			new int[] {0,0,WIDTH}, //top left, bottom left, front middle
			new int[] {0,HEIGHT,HEIGHT/2}, 3);
		shape.translate(x, y);
		d = Direction.NONE;
		health = health_MAX;
	}

	/**
	 * set direction of the ship
	 * @param d	the direction enum 
	 */
	public void setDirection(Direction d) {
		this.d = d;
	}

	/**
	 * set the movement boundary rectangle for the ship
	 * @param movementBounds	the rectangle to limit the ship movements
	 */
	public void setMovementBounds(Rectangle2D movementBounds) {
		this.movementBounds = movementBounds;
	}

	/**
	 * move the ship on the screen, restrict the ship
	 * from going off-screen
	 * 
	 */
	public void move() {
		shape.translate(d.dx,d.dy);
		if ( (shape.xpoints[0] <= movementBounds.getMinX() || shape.ypoints[0] <=movementBounds.getMinY()) ||
			 (shape.xpoints[2] >= movementBounds.getMaxX() || shape.ypoints[0] <=movementBounds.getMinY()) || 
			 (shape.xpoints[2] >= movementBounds.getMaxX() || shape.ypoints[1] >=movementBounds.getMaxY()) ||
			 (shape.xpoints[0] <= movementBounds.getMinX() || shape.ypoints[1] >=movementBounds.getMaxY())) {
				 shape.translate(-d.dx,-d.dy);
		} 
	}

	/**
	 * draw the ship
	 * @param g	Graphics2D used to draw the ship
	 */
	public void draw(Graphics2D g) {
		g.setColor(FILL);
		g.draw(shape);
	}

	/**
	 * return the shape of the ship
	 * @return shape the shape object of the ship
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * get the ship's gun-point position
	 * to shoot bullets
	 * @return Point2D	the point at which the ship's gun is located
	 */
	public Point2D getGunPoint(){
		return new Point2D.Double(shape.xpoints[2], shape.ypoints[2]);
	}

	/**
	 * check if the ship has intersected with another sprite
	 * @param asteroid	the sprite to check 
	 * @return boolean 	whether there has been a collision
	 */
	public boolean intersects(Sprite asteroid) {
		Area shipArea = new Area(shape); 
		shipArea.intersect(new Area(asteroid.getShape()));
		return !shipArea.isEmpty();
	}
}
