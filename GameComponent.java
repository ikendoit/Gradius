import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GameComponent extends JComponent {
	ArrayList<Timer> timers;
	Timer timer;
	ShipImpl ship;
	ArrayList<Asteroid> asteroids;
	static String keyCombination="";

	public GameComponent() {
		//key Adapter
		setFocusable(true);
		addKeyListener(new ShipKeyListener());

		//starts Asteroids
		asteroids = new ArrayList<Asteroid>();
		
		//starts ship
		ship = new ShipImpl(10, GameFrame.HEIGHT/2);
		
		

		//timer task
		Timer timerAsteroids = new Timer(1000/4,(e) -> { 
			for (int i = 0 ; i < 2 ; i ++){
				asteroids.add(makeAsteroid()); 
			}
			
		});
		timer = new Timer(1000/60,(e) -> {update(); }); 
		timers = new ArrayList<Timer>();
		timers.add(timerAsteroids);
		timers.add(timer);
	}

//*******************Paint Component ******************* */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintComponent(g2);
	}

	//draw components
	private void paintComponent(Graphics2D g) {
		//draw asteroids
		for(Asteroid asteroid : asteroids){
			asteroid.draw(g);
		}

		//draw ship
		ship.draw(g);
	}

//*******************update and start ************************
	private void update(){
		requestFocusInWindow();
		ship.move(); 
		moveAsteroid();
		checkAsteroid();
		repaint();
	}

	//start timer
	public void start() {
		AsteroidFactory.getInstance().setStartBounds(getWidth(),0,getHeight());
		for (Timer timerInternal : timers){
			timerInternal.start();
		}
		ship.setMovementBounds(new Rectangle2D.Double(0,0,getWidth(),getHeight()));
		
	}

//*******************Asteroid Methods**************************** */
	public Asteroid makeAsteroid() {
		return AsteroidFactory.getInstance().makeAsteroid();
	}

	//move Asteroids
	public void moveAsteroid(){
		for (Asteroid asteroid : asteroids){
			asteroid.move();
		}
	}

	public void checkAsteroid() throws ConcurrentModificationException{
		try {
			for (Asteroid asteroid : asteroids){
				if (!asteroid.isVisible()){
					asteroids.remove(asteroid);
				}
			}
		} catch (Exception e){}
	}

//*******************ShipkeyListener********************* */
	class ShipKeyListener extends KeyAdapter{	
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e){ 
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP: 
				case KeyEvent.VK_W:  
				case KeyEvent.VK_KP_UP :
					ship.setDirection(Ship.Direction.UP);
					keyCombination += "U";
					break;
				case KeyEvent.VK_RIGHT:  
				case KeyEvent.VK_D:  
				case KeyEvent.VK_KP_RIGHT:
					ship.setDirection(Ship.Direction.RIGHT);
					keyCombination+="R";
					break;
				case KeyEvent.VK_LEFT:  
				case KeyEvent.VK_A:  
				case KeyEvent.VK_KP_LEFT:
					ship.setDirection(Ship.Direction.LEFT);
					keyCombination +="L";
					break;
				case KeyEvent.VK_DOWN:  
				case KeyEvent.VK_S:  
				case KeyEvent.VK_KP_DOWN:
					ship.setDirection(Ship.Direction.DOWN);
					keyCombination+="D";
					break;
			}
			if (keyCombination.contains("U") && keyCombination.contains("R")){
				ship.setDirection(Ship.Direction.UPRIGHT);
			}
			if (keyCombination.contains("U") && keyCombination.contains("L")){
				ship.setDirection(Ship.Direction.UPLEFT);
			}
			if (keyCombination.contains("D") && keyCombination.contains("R")){
				ship.setDirection(Ship.Direction.DOWNRIGHT);
			}
			if (keyCombination.contains("D") && keyCombination.contains("L")){
				ship.setDirection(Ship.Direction.DOWNLEFT);
			}

		}

		@Override
		public void keyReleased(KeyEvent e){
			ship.setDirection(Ship.Direction.NONE);
			keyCombination = "";
		}

	}
}




