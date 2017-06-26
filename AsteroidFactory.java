import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.Random;

public class AsteroidFactory {

	private final static AsteroidFactory instance = new AsteroidFactory();
	
	private static Rectangle startBounds;
	
	private AsteroidFactory() {}
	
	//create instance of asteroidFactory
	public static AsteroidFactory getInstance() {
		return instance;
	}
	
	public void setStartBounds(int x, int minY, int maxY) {
		startBounds = new Rectangle(x, minY, x, maxY);
	}
	
	public Asteroid makeAsteroid() {
		Asteroid asteroid = new AsteroidImpl(startBounds.x, random(startBounds.y,startBounds.height)
											, random(10,40), random(10,40)
											, random(1,4));
		return asteroid;
	}

	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble()*(max-min));
	}
	
//************Asteroid Impl***************** */
	private static class AsteroidImpl implements Asteroid {

		private final static Color COLOR = Color.DARK_GRAY;
		private final Ellipse2D.Double shape;
		private final int velocity;

		private AsteroidImpl(int x, int y, int width, int height, int velocity) {
			shape = new Ellipse2D.Double(x, y, width, height);
			this.velocity = velocity;
		}
		
		public void move() {
			shape.x-= velocity;
		}

		public boolean isVisible() {
			if (outOfRange()) {
				return false;
			}
			return true;
		}

		public void draw(Graphics2D g) {
			g.setColor(COLOR);
			g.draw(shape);
			g.fill(shape);
		}

		public Shape getShape() {
			return shape;
		}

		@Override
		public boolean intersects(Sprite ship) {
			Area asteroid = new Area(shape);
			return ship.intersects(this);
		}

		public boolean outOfRange(){
			return (shape.x <=0 ? true : false);
		}
	}
}