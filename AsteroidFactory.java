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
		Asteroid asteroid = new AsteroidImpl(startBounds.x, random(startBounds.y,startBounds.height));
		return asteroid;
	}

	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble()*(max-min));
	}
	
	private static class AsteroidImpl implements Asteroid {

		private final static Color COLOR = Color.DARK_GRAY;
		private final Ellipse2D.Double shape;
		private int x; 
		private int y;

		private AsteroidImpl(int x, int y) {
			shape = new Ellipse2D.Double(x, y, 30, 30);
			this.x =x;
			this.y =y;
		}
		
		public void move() {
			x-=1;
		}

		public boolean isVisible() {
			return true;
		}

		public void draw(Graphics2D g) {
			g.translate(x,y);
			g.setColor(COLOR);
			g.draw(shape);
			g.fill(shape);
		}

		public Shape getShape() {
			return shape;
		}

		public boolean intersects(Sprite other) {
			return false;
		}
	}
}