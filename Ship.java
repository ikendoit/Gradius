import java.awt.geom.Rectangle2D;

public interface Ship extends Sprite {

	public enum Direction {
		NONE(0,0), UP(0,-2), DOWN(0,2), LEFT(-2,0), RIGHT(2,0), UPRIGHT(2,-2), DOWNRIGHT(2,2),UPLEFT(-2,-2),DOWNLEFT(-2,2);
		public final int dy;
		public final int dx;
		Direction(int dx, int dy) {
			this.dy = dy;
			this.dx = dx;
		}
		
	};

	public void setDirection(Direction d);
	// public void setMovementBounds(Rectangle2D bounds);
}
