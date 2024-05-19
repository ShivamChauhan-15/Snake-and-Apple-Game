package company;
import javax.swing.*;
import java.awt.*;
public class SnakeGame extends JFrame{
	SnakeGame(){
		super("Snake Game"); 
		add(new Board());
		pack();
		setSize(300,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new SnakeGame();

	}

}
