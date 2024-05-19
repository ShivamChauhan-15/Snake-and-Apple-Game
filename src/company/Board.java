package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
	private int dots;
	private boolean inGame=true;
	private final int dotSize=10;
	private final int allDots=90000;
	private final int randomPos=29;
	private int apple_x;
	private int apple_y;
	private Timer timer;

	private final int x[]=new int[allDots];
	private final int y[]=new int[allDots];
	private boolean leftDir=false;
	private boolean rightDir=true;
	private boolean upDir=false;
	private boolean downDir=false;
	private int cScore = 0;
	Board(){
		addKeyListener(new TAdapter());
		setPreferredSize(new Dimension(300,300));
		setBackground(Color.BLACK);
		setFocusable(true);
		game();
	}
	public void game() {
		dots=3;
		for(int i=0;i<dots;i++)
		{
			y[i]=50;
			x[i]=50-i*dotSize;
		}
		locateApple();
		timer=new Timer(150,this);
		timer.start();
	}
	public void locateApple(){
		int r=(int)(Math.random()*randomPos);
		apple_x=r*dotSize;
		r=(int)(Math.random()*randomPos);
		apple_y=r*dotSize;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    draw(g);
	}
	public void draw(Graphics g) {
		if(inGame)
		{
			g.setColor(Color.green);
			g.fillOval(apple_x, apple_y,dotSize,dotSize);
			for(int i=0;i<dots;i++) 
			{
				if(i==0)
					g.setColor(Color.red);
				else 
					g.setColor(Color.green);
				g.fillOval(x[i],y[i],dotSize,dotSize);
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else 
			gameOver(g);
	}
	public void gameOver(Graphics g)
	{
		String msg="Game Over!!";
		String score = "Your Score: "+cScore;
		Font font =new Font("SAN-SERIF",Font.BOLD,16);
		FontMetrics metrice=getFontMetrics(font);
		g.setColor(Color.red);
		g.setFont(font);
		g.drawString(msg,(300-metrice.stringWidth(msg))/2 ,120);
		g.drawString(score,(300-metrice.stringWidth(score))/2, 145);
	}
	public void move(){
		for(int i=dots; i>0; i--){
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		if(leftDir)
			x[0]-=dotSize;
		if(rightDir)
			x[0]+=dotSize;
		if(upDir)
			y[0]-=dotSize;
		if(downDir)
			y[0]+=dotSize;
	}
	public void checkApple(){
		if((x[0]==apple_x) && (y[0]==apple_y)){
			dots++;
			cScore++;
			locateApple();
		}
	}
	public void checkCollision(){
		for(int i=dots; i>0; i--)
		{
			if((i>4) && (x[0]==x[i]) && (y[0]==y[i]))
				inGame=false;
		}
		if(x[0]>=300)
			inGame=false;
		if(y[0]>=300)
			inGame=false;
		if(x[0]<0)
			inGame=false;
		if(y[0]<0)
			inGame=false;
		if(!inGame)
			timer.stop();
	}
	public void actionPerformed(ActionEvent e)
	{
		if(inGame){
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}
	public class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			int key=e.getKeyCode();
			if(key==KeyEvent.VK_LEFT && (!rightDir)){
				leftDir=true;
				upDir=false;
				downDir=false;
			}
			if(key==KeyEvent.VK_RIGHT && (!leftDir)){
				rightDir=true;
				upDir=false;
				downDir=false;
			}
			if(key==KeyEvent.VK_UP && (!downDir)){
				leftDir=false;
				rightDir=false;
				upDir=true;
			}
			if(key==KeyEvent.VK_DOWN && (!upDir)){
				leftDir=false;
				rightDir=false;
				downDir=true;
			}
		}
	}
}
