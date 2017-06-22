import java.awt.geom.Rectangle2D;

public interface Ship extends Sprite {
	public int speed =2;

	public enum Direction {
		NONE(0,0), UP(0,-speed), DOWN(0,speed), LEFT(-speed,0), RIGHT(speed,0), UPRIGHT(speed,-speed), DOWNRIGHT(speed,speed),UPLEFT(-speed,-speed),DOWNLEFT(-speed,speed);
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
