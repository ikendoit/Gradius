import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.awt.Image;

/**
 * Game of Gradius, with features. 10 java files + 1 jpg image in total.
 * @Name			Gradius
 * @Author 			Trung Kien Nguyen 
 * @ID 	   			100284963	
 * @CoAuthor 		Professor Jeremy Hilliker
 * @School			Langara College
 * @Course 			CPSC 1181
 * @Date   			June 27th, 2017
 * @version 		1.0 
 * @Acknowledgement Pro.Jeremy Hilliker, StackOverFLow, Javadoc 
 */

public class GameComponent extends JComponent {
	ArrayList<Timer> timers;
	Timer timer;
	ShipImpl ship;
	ShipFeatures shipFeatures ; 
	ArrayList<Asteroid> asteroids;
	ArrayList<Bullet> bullets;
	static double scores =0;
	static int asteroidsShot = 0 ;

	/**
	 * construct keyadapter + asteroids + ship+bullet + timers
	 */
	public GameComponent() {
		//key Adapter
		setFocusable(true);
		addKeyListener(new ShipKeyListener());

		//starts Asteroids
		asteroids = new ArrayList<Asteroid>();
		
		//starts ship
		ship = new ShipImpl(10, GameFrame.HEIGHT/2);
		shipFeatures = new ShipFeatures(ship,0, 20,2);

		//start Bullets
		bullets = new ArrayList<Bullet>();

		//timer task
		Timer timerAsteroids = new Timer(1000/4,(e) -> { 
			for (int i = 0 ; i < 2 ; i ++){
				asteroids.add(makeAsteroid()); 
			}
			scores+=0.25;
			
		});
		timer = new Timer(1000/60,(e) -> {update(); }); 
		timers = new ArrayList<Timer>();
		timers.add(timerAsteroids);
		timers.add(timer);
	}

//*******************Paint Component ******************* */
	/**
	 * paint component 
	 * @param g	Graphics used to draw
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintComponent(g2);
	}

	/**
	 * draw components : ship + asteroids + bullets + features + end screen
	 * @param g	Graphics2D used to draw
	 */
	private void paintComponent(Graphics2D g) {
		g.setColor(Color.BLACK);
		Image image = Toolkit.getDefaultToolkit().getImage("space.jpg");
		g.drawImage(image,0,0,getWidth(),getHeight(),null,this);
  		g.setColor(Color.blue);
		g.fillRect(getWidth(), getHeight(), 50, 200);
		//draw asteroids
		for(Asteroid asteroid : asteroids){
			asteroid.draw(g);
		}
		//draw ship
		ship.draw(g);
		//draw features
		shipFeatures.draw(g);
		//draw bullets
		for (Bullet bullet : bullets) {
			bullet.draw(g);
		}
		//draw ending screen
		if (ship.health <= 0 ){
			paintComponentEnd(g);
		}
		
	}

	/**
	 * paint ending screen
	 * @param g graphics2D used to draw
	 */
	private void paintComponentEnd(Graphics2D g){
		g.setColor(Color.GREEN);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("YOU CRASHED YOUR SHIP, SPOCK !", getWidth()/3,  getHeight()/2 );
		g.drawString("YOU LASTED: "+scores +" SECONDS" , getWidth()/3,getHeight()/2 +18 );
		g.drawString("SHOT: "+asteroidsShot + " ASTEROIDS", getWidth()/3 , getHeight()/2 +36); 	
		gameOver();
	}

//*******************update and start ************************
	/**
	 * update the components 
	 */
	private void update(){
		requestFocusInWindow();
		//move components
		ship.move(); 
		moveAsteroid();
		moveBullet();
		//check components
		checkAsteroid();
		checkBullet();
		checkCollisions();
		checkCollisionBullet();
		repaint();
	}

	/**
	 * start the timers+ set MovementBounds of ship
	 */
	public void start() {
		AsteroidFactory.getInstance().setStartBounds(getWidth(),0,getHeight()-30);
		for (Timer timerInternal : timers){
			timerInternal.start();
		}
		ship.setMovementBounds(new Rectangle2D.Double(0,0,getWidth(),getHeight()));
	}

	/**
	 * stop timers when game ends
	 */
	public void gameOver(){
		for (Timer timerInternal : timers){
			timerInternal.stop();
		}

	}
//*******************Asteroid Methods**************************** */
	/**
	 * make an asteroid
	 */
	public Asteroid makeAsteroid() {
		return AsteroidFactory.getInstance().makeAsteroid();
	}

	/**
	 * move all asteroids
	 */
	public void moveAsteroid(){
		for (Asteroid asteroid : asteroids){
			asteroid.move();
		}
	}
	/**
	 * check asteroids if visible on screen
	 */
	public void checkAsteroid(){
		asteroids.removeIf(a -> !a.isVisible());
	} 
	

	/**
	 * check asteroids collision with ship
	 */
	public void	checkCollisions(){ 
		Iterator<Asteroid> iterator = asteroids.iterator();
		while (iterator.hasNext()){
			Asteroid asteroid = iterator.next();
			if (asteroid.intersects(ship)){
				iterator.remove();
				ship.health-- ;
			}
		}
	}

	/**
	 * check asteroids collision with bullets
	 */
	public void checkCollisionBullet() throws ConcurrentModificationException{
		try {
			for (Asteroid asteroid : asteroids){ 
				for (Bullet bullet : bullets){
					if (bullet.intersects(asteroid)){
						asteroids.remove(asteroid); 
						bullets.remove(bullet) ;
						asteroidsShot++;	
					}
				}
			}
		}  catch (Exception e) {}
	}


//*******************Bullet Creation********************* */
	/**
	 * make a bullet
	 */
	public Bullet makeBullet(){
		return shipFeatures.shootBullet();
	}

	/**
	 * move all bullets
	 */
	public void moveBullet(){
		for (Bullet bullet : bullets){
			bullet.move();
		}
	}

	/**
	 * check if bullet is visible on screen
	 */
	public void checkBullet() throws ConcurrentModificationException {
		try {
			for (Bullet bullet : bullets){
				if (!bullet.isVisible()){
					bullets.remove(bullet);
				}
			}
		} catch (Exception e) {}
	}


//*******************ShipkeyListener********************* */
	class ShipKeyListener extends KeyAdapter{	
		boolean up,down,left,right,shoot;

		/**
		 * check keys types
		 * @param e KeyEvent to read
		 */
		@Override
		public void keyTyped(KeyEvent e) {
		}

		/**
		 * check keys pressed
		 * @param e KeyEvent to read
		 */
		@Override
		public void keyPressed(KeyEvent e){ 			
			readKey(e.getKeyCode(),true);
		}

		/**
		 * check keys released
		 * @param e	keyEvent to read
		 */
		@Override
		public void keyReleased(KeyEvent e){
				readKey(e.getKeyCode(),false);
			}
		
		/**
		 * read Keys info and modifies movements
		 * @param keyCode	integer keyCode from key Events
		 * @param action	check if key is pressed
		 */
		public void readKey(int keyCode,boolean action){
			switch(keyCode) {
				case KeyEvent.VK_UP: 
				case KeyEvent.VK_W:  
				case KeyEvent.VK_KP_UP :
					up = action;
					break;
				case KeyEvent.VK_RIGHT:  
				case KeyEvent.VK_D:  
				case KeyEvent.VK_KP_RIGHT:
					right = action;
					break;
				case KeyEvent.VK_LEFT:  
				case KeyEvent.VK_A:  
				case KeyEvent.VK_KP_LEFT:
					left = action;
					break;
				case KeyEvent.VK_DOWN:  
				case KeyEvent.VK_S:  
				case KeyEvent.VK_KP_DOWN:
					down = action;
					break;
				case KeyEvent.VK_SPACE:
					shoot = action;
					break;
			}
			if (up){
				ship.setDirection(Ship.Direction.UP);
			}
			if (down){
				ship.setDirection(Ship.Direction.DOWN);
			}
			if (left){
				ship.setDirection(Ship.Direction.LEFT);
			}
			if (right){
				ship.setDirection(Ship.Direction.RIGHT);
			}
			if (up && right){
				ship.setDirection(Ship.Direction.UPRIGHT);
			}
			if (up && left){
				ship.setDirection(Ship.Direction.UPLEFT);
			}
			if (down && right){
				ship.setDirection(Ship.Direction.DOWNRIGHT);
			}
			if (down && left){
				ship.setDirection(Ship.Direction.DOWNLEFT);
			}
			if(shoot){
				bullets.add(makeBullet());
			}
			if (up == false && right == false && down == false && left == false){
				ship.setDirection(Ship.Direction.NONE);
			}
		}

	}
}




