import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.*;

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

public class ShipFeatures {
    private Rectangle2D healthBar; 
    private ShipImpl ship;
    private int health; 
    private int health_MAX;
    public int bulletSpeed;
    private static int bulletWidth = 10; 
    private static int bulletHeight = 2;

    /**
     * constructor for the shipFeatures
     * @param ship  the shipImpl to add features to
     * @param x     the x position for the health-bar
     * @param y     the y postition for the health-bar  
     * @param bulletSpeed   the speed at which a bullet moves
     */
    public ShipFeatures(ShipImpl ship,int x,int y, int bulletSpeed) {
        healthBar = new Rectangle2D.Double(x,y,100,20);
        this.ship = ship;
        this.bulletSpeed = bulletSpeed; 
    }

    /**
     * create a bullet instance
     * upgrade bullets
     * @return Bullets  a bullet instance created
     */
    public Bullets shootBullet(){
        if (GameComponent.asteroidsShot % 20 ==0 ){
            bulletHeight += GameComponent.asteroidsShot /20 ; 
            bulletWidth += GameComponent.asteroidsShot /20 ; 
       }
        return new Bullets((int) ship.getGunPoint().getX() , (int)( ship.getGunPoint().getY() -(bulletWidth)/2)
                            ,bulletWidth,bulletHeight, bulletSpeed);
    }

    /**
     * draw the features
     * @param g Graphics2D used to draw features
     */
    public void draw(Graphics2D g){

        //draw health bar
        g.setColor(Color.BLUE);
        g.fillRect((int)healthBar.getX(),(int)healthBar.getY(),(int) (ship.health*100/ship.health_MAX), 20);
        //draw Health info
        g.setColor(Color.cyan);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.drawString(""+ship.health,0,36);
        //draw scores
        g.setColor(Color.GREEN);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Lived :"+GameComponent.scores , 0, 18);
        //draw number of asteroids shot
        g.drawString("Killed :"+GameComponent.asteroidsShot , 0,57);

    }
//*****************Bullets class *********************8 */
    public class Bullets implements Bullet{
        private final Rectangle2D.Double shape; 
        private int speed ;

        /**
         * construct a bullet
         * @param speed the speed of the bullet
         * @param width the width of the bullet
         * @param height the height of the bullet
         */
        public Bullets(int posX, int posY,int width, int height, int speed){
            shape = new Rectangle2D.Double(posX, posY, width ,height);
            this.speed = speed;
        }

        /**
         * move the bullet 
         */
        public void move(){
            shape.x +=speed;
        }

        /**
         *  draw the bullet
         *  @param g  Graphics2D used to draw
         */
        public void draw(Graphics2D g){
            g.setColor(Color.red);
            g.fill(shape);
        }

        /**
         * check if the bullet is on the game-screen
         * @return boolean  whether the bullet is in the screen
         */
        public boolean isVisible(){
            return (shape.x >= ship.movementBounds.getMaxX() ? false : true);
        }
        
        /**
         * check if the bullet has collided with another sprite
         * @param asteroid  the sprite to check
         * @return boolean  whether there has been a collision
         */
        public boolean intersects(Sprite asteroid){
            Area bulletArea = new Area(shape);
            bulletArea.intersect(new Area(asteroid.getShape()));
            return !bulletArea.isEmpty();
        }
        
        /**
         * return the shape of the bullet
         * @return bullet   the shape of the bullet
         */
        public Shape getShape(){
            return shape;
        }
    }
}