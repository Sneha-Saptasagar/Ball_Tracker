package newgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;



public class Gameplay extends JPanel implements KeyListener,ActionListener {
	private boolean play=false;
	private int score=0;
	
	private int totalBricks=21;
	private Timer time;
	private int delay=8;
	private int playerX=310;
	private int ballposx=120;
	private int ballposy=350;
	private int ballxdir=-1;
	private int ballydir=-2;
	private  Mapgenerator map;
	
	public Gameplay() {
		map=new Mapgenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time=new Timer(delay,this);
		time.start();
	}
	public void paint(Graphics g)
	{    //background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		//drawing map
		map.draw((Graphics2D)g);
		//border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(0, 0, 3, 592);
		
		//paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);
		//score
		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 592, 30);
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposx, ballposy, 20, 20);
		
		if(totalBricks<=0) {
			play=false;
			ballxdir=0;
			ballydir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("YOU WON", 260, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press enter to restart", 250, 350);
			
		}
		
		if(ballposy>570) {
			play=false;
			ballxdir=0;
			ballydir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("GAME OVER:YOUR SCORE "  +score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press enter to restart", 250, 350);
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		time.start();
		//ball movement
		if(play==true) {
			//detecting intersection of ball n paddle
			if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballydir=-ballydir;
				
			}
			
		A:for(int i=0;i<map.map.length;i++)
			{
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickx=j*map.brickwidth+80;
						int bricky=i*map.brickheight+50;
						int brickwidth=map.brickwidth;
						int brickheight=map.brickheight;
						
						Rectangle rect=new Rectangle(brickx,bricky,brickwidth,brickheight);
						Rectangle ballrect = new Rectangle(ballposx,ballposy,20,20);
						Rectangle brickrect=rect;
						
						
						if(ballrect.intersects(brickrect)) {
							map.setBrickvalue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballposx+19<=brickrect.x||ballposx+1>=brickrect.x+brickrect.width)
							{
								ballxdir=-ballxdir;
								
							}else {
								ballydir=-ballydir;
							}
							break A;
							}
					}
				}
			}
			
			ballposx+=ballxdir;
			ballposy+=ballydir;
			//leftborder
			if(ballposx<0) {
				ballxdir=-ballxdir;
			}
			//top
			if(ballposy<0) {
				ballydir=-ballydir;
			}
			//rightborder
			if(ballposx>670) {
				ballxdir=-ballxdir;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX >=600) {
				playerX=600;
			}else {
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX <=10) {
				playerX=10;
			}else {
				moveLeft();
			}
		}
		if(!play) {
			play=true;
			ballposx=120;
			ballposy=350;
			ballxdir=-1;
			ballydir=-2;playerX=310;
			score=0;
			totalBricks=21;
			map=new Mapgenerator(3,7);
			repaint();
			
		}
	}
	public void moveRight() {
		play=true;
		playerX+=20;
		}
	public void moveLeft() {
		play=true;
		playerX-=20;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
