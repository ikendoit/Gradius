import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class GameFrame extends JFrame {
	
	private final static int WIDTH = 900;
	public final static int HEIGHT = 700;	
	private final GameComponent comp;
	
	/**
	 * contruct the gameFrame
	 */
	public GameFrame() {
		setResizable(false);
		comp = new GameComponent();
		add(comp);
	}
	public GameFrame(String s) {
		this();
		setTitle(s);
	}
	
	public static void main(String[] args) {
		GameFrame frame = new GameFrame("Gradius by Ken");
	
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.comp.start();
	}

}