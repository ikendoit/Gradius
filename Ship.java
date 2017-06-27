import java.awt.geom.Rectangle2D;

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

public interface Ship extends Sprite {

	public enum Direction {
		NONE(0,0), UP(0,-3), DOWN(0,3), LEFT(-3,0), RIGHT(3,0), UPRIGHT(3,-3), DOWNRIGHT(3,3),UPLEFT(-3,-3),DOWNLEFT(-3,3);
		public final int dy;
		public final int dx;
	
		Direction(int dx, int dy) {
			this.dy = dy;
			this.dx = dx;
		}
		
	};

	public void setDirection(Direction d);
	public void setMovementBounds(Rectangle2D bounds);
}
