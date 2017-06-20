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
		asteroids.add(makeAsteroid(500, 500));

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
		//draw ship
		ship.draw(g);

		//draw asteroids
		for(Asteroid asteroid : asteroids){
			asteroid.draw(g);
		}
	}

	//update in real-time
	private void update(){
		requestFocusInWindow();
		ship.move(); 
		repaint();
	}

	//start timer
	public void start() {
		timer.start();
	}

	//make asteroid
	public Asteroid makeAsteroid(int x, int y){
		return AsteroidFactory.getInstance().makeAsteroid(x,y);
	}

	class ShipKeyListener extends KeyAdapter{	
		public boolean up(int keyCode){
			if (keyCode== KeyEvent.VK_UP ||keyCode== KeyEvent.VK_W ||keyCode== KeyEvent.VK_KP_UP){
				return true;
			}
			return false;
		}

		public boolean down(int keyCode){
			if (keyCode== KeyEvent.VK_DOWN ||keyCode== KeyEvent.VK_S ||keyCode== KeyEvent.VK_KP_DOWN){
				return true;
			}
			else return false;
		}

		public boolean right(int keyCode){
			if (keyCode== KeyEvent.VK_RIGHT ||keyCode== KeyEvent.VK_D ||keyCode== KeyEvent.VK_KP_RIGHT){ 
				return true;
			}
			return false;
		}

		public boolean left(int keyCode){
			if (keyCode == KeyEvent.VK_LEFT ||keyCode == KeyEvent.VK_A ||keyCode == KeyEvent.VK_KP_LEFT){ 
				return true;
			}
			return false;
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e){ 
			if (up(e.getKeyCode())) { 
				ship.setDirection("UP"); 
			} else if (down(e.getKeyCode())){
				ship.setDirection("DOWN");
			} else if (right(e.getKeyCode())){
				ship.setDirection("RIGHT");
			} else if (left(e.getKeyCode())){
				ship.setDirection("LEFT");
			} else if (down(e.getKeyCode()) && right(e.getKeyCode())){
				ship.setDirection("DOWNRIGHT");
			} else if (down(e.getKeyCode()) && left(e.getKeyCode())){
				ship.setDirection("DOWNLEFT");
			} else if (up(e.getKeyCode()) && right(e.getKeyCode())){
				ship.setDirection("UPRIGHT");
			} else if (up(e.getKeyCode()) && left(e.getKeyCode())){
				ship.setDirection("UPLEFT");
			}
		}

		@Override
		public void keyReleased(KeyEvent e){
			ship.setDirection("NONE");
		}

	}
}




