import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.*;

public class ShipFeatures {
    private Rectangle2D healthBar; 
    private ShipImpl ship;
    private int health; 
    private int health_MAX;
    private int posX;
    private int posY; 
    private int bulletSpeed;

    public ShipFeatures(ShipImpl ship,int x,int y) {
        healthBar = new Rectangle2D.Double(x,y,100,20);
        this.ship = ship;
    }

    public void shootBullet(){
        Bullets bullet = new Bullets(ship.getGunPoint().getX()., ship.getGunPoint().getY(), 5);
    }
    

    public void draw(Graphics2D g){
        //draw health bar
        g.setColor(Color.BLUE);
        g.fillRect((int)healthBar.getX(),(int)healthBar.getY(),(int) (ship.health*100/ship.health_MAX), 20);
        //draw Health info
        g.setColor(Color.cyan);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.drawString(""+ship.health,0,40);
        //draw scores
        g.setColor(Color.GREEN);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString(""+GameComponent.scores , 0,  18 );

    }

    public class Bullets implements Asteroid{
        private final Rectangle2D.Double bullet; 
        private int posX;
        private int posY;
        private int speed ;

        public Bullets(int posX, int posY, int speed){
            bullet = new Rectangle2D.Double(posX, posY, 10 , 2);
            this.posX = posX;
            this.posY = posY;
            this.speed = speed;
        }

        public void move(){
            bullet.x +=speed;
        }

        public void draw(Graphics2D g){
            g.setColor(Color.red);
            g.draw(bullet);
        }

        public boolean OutOfRangle(){
            return (bullet.x >= ship.movementBounds.getMaxX ? true : false);
        }
    
        public boolean intersects(Sprite other){

        }
       
        
        public Shape getShape(){
            return bullet;
        }
    }
}