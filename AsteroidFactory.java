import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.Random;

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

public class AsteroidFactory {

	private final static AsteroidFactory instance = new AsteroidFactory();
	
	private static Rectangle startBounds;
	
	private AsteroidFactory() {}
	
	/**
	 * create an instance of Asteroid factory
	 * @return instance of Asteroid Factory
	 */
	public static AsteroidFactory getInstance() {
		return instance;
	}
	
	/**
	 * set the boundary rectangle for the asteroids to spawn
	 * @param x		the starting / ending x position
	 * @param minY	the min value of y postition
	 * @param maxY  the max value of y position
	 */
	public void setStartBounds(int x, int minY, int maxY) {
		startBounds = new Rectangle(x, minY, x, maxY);
	}
	
	/**
	 * make an asteroid object
	 * @return asteroid	the asteroid object created
	 */
	public Asteroid makeAsteroid() {
		Asteroid asteroid = new AsteroidImpl(startBounds.x, random(startBounds.y,startBounds.height)
											, random(10,40), random(10,40)
											, random(1,4));
		return asteroid;
	}

	/**
	 * generate a random number in a specific range
	 * @param min	the min value of the range
	 * @param max	the max value of the range
	 */
	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble()*(max-min));
	}
	
//************Asteroid Impl***************** */
	private static class AsteroidImpl implements Asteroid {

		private final static Color COLOR = Color.DARK_GRAY;
		private final Ellipse2D.Double shape;
		private final int velocity;

		/**
		 * constructor for the asteroidimpl
		 * @param x 	x position for an asteroid
		 * @param y		y position for an asteroid
		 * @param height height of an asteroid
		 * @param width	 width of an asteroid
		 * @param velocity the velocity at which an asteroid moves
		 */
		private AsteroidImpl(int x, int y, int width, int height, int velocity) {
			shape = new Ellipse2D.Double(x, y, width, height);
			this.velocity = velocity;
		}
		
		/**
		 * move an asteroid
		 */
		public void move() {
			shape.x-= velocity;
		}

		/**
		 * check if an asteroid is visible on the game screen
		 * return boolean 	whether the asteroid is visible
		 */
		public boolean isVisible() {
			if (outOfRange()) {
				return false;
			}
			return true;
		}

		/**
		 * draw the asteroid
		 * @param g	graphics2D used to draw the asteroid
		 */
		public void draw(Graphics2D g) {
			g.setColor(COLOR);
			g.draw(shape);
			g.fill(shape);
		}

		/**
		 * return the shape object of an asteroid
		 * @return shape	the shape of an asteroid
		 */
		public Shape getShape() {
			return shape;
		}

		/**
		 * check if an asteroid has intersected with a ship
		 * @param ship	the ship sprite to check
		 * @return boolean 	if there has been a collision
		 */
		public boolean intersects(Sprite ship) {
			Area asteroid = new Area(shape);
			return ship.intersects(this);
		}

		/**
		 * check of the asteroid is off the screen
		 * @return boolean 	whether the asteroid has flown off screen
		 */
		public boolean outOfRange(){
			return (shape.x <=0 ? true : false);
		}
	}
}