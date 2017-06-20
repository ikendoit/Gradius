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
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

public class GameComponent extends JComponent {
	Timer timer;
	ShipImpl ship;
	ArrayList<Asteroid> asteroids;

	public GameComponent() {
		
		//key Adapter
		setFocusable(true);
		addKeyListener(new ShipKeyListener());

		//starts Asteroids
		asteroids = new ArrayList<Asteroid>();
		
		//starts ship
		ship = new ShipImpl(10, GameFrame.HEIGHT/2);

		//timer task
		timer = new Timer(1000/60,(e) -> {update(); }); 
	}

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

	//update in real-time
	private void update(){
		requestFocusInWindow();
		ship.move(); 
		repaint();
	}

	//start timer
	public void start() {
		AsteroidFactory.getInstance().setStartBounds(getWidth()-30,0,getHeight()-30);
		timer.start();
		asteroids.add(makeAsteroid());

	}

	//make asteroid
	public Asteroid makeAsteroid(){
		return AsteroidFactory.getInstance().makeAsteroid();
	}

	//move Asteroids
	public void moveAsteroid(){
		for (Asteroid asteroid : asteroids){
			asteroid.move();
		}
	}

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
				case KeyEvent.VK_RIGHT:  
				case KeyEvent.VK_D:  
				case KeyEvent.VK_KP_RIGHT:
					ship.setDirection(Ship.Direction.UPRIGHT);
					break;
				case KeyEvent.VK_LEFT:  
				case KeyEvent.VK_A:  
				case KeyEvent.VK_KP_LEFT:
					ship.setDirection(Ship.Direction.UPLEFT);
					break;
				case KeyEvent.VK_DOWN:  
				case KeyEvent.VK_S:  
				case KeyEvent.VK_KP_DOWN:
					ship.setDirection(Ship.Direction.DOWN);
				
			}

		}

		@Override
		public void keyReleased(KeyEvent e){
			ship.setDirection(Ship.Direction.NONE);
		}

	}
}




