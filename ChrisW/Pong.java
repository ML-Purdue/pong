import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;

public class Pong extends JFrame implements MouseMotionListener {
    Random random;
    ControlState controls;
	Paddle paddle;
	Ball ball;
	final int PADDLE_SPEED = 100;
	final int PADDLE_LENGTH = 20;
	final int ARENA_WIDTH = 100;
	final int ARENA_HEIGHT = 100;
	final int BALL_RADIUS = 1;
	final int BALL_SPEED = 150;
	int bounces;
	int misses;
	long tickTime;
	Point lastMousePosition;
	
	public Pong() {
	    random = new Random();
        controls = new ControlState();
		paddle = new HumanPaddle(controls, PADDLE_LENGTH);
		ball = new Ball(BALL_SPEED, BALL_RADIUS);
		tickTime = 10;
		lastMousePosition = new Point();
		addMouseMotionListener(this);
		addKeyListener(controls);
		controls.addControl(Controls.exit, KeyEvent.VK_ESCAPE);
		controls.addControl(Controls.movePaddleUp, KeyEvent.VK_UP);
		controls.addControl(Controls.movePaddleDown, 
		        KeyEvent.VK_DOWN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(ARENA_WIDTH, ARENA_HEIGHT);
		setUndecorated(true);
		setVisible(true);
		
		respawn(ball);
		run();
	}
	
	private void run() {
	    while (true) {
	        if (controls.controls.get(Controls.exit).isPressed) {
	            System.exit(0);
	        }
	        
	        switch (paddle.getMove()) {
	            case up: paddle.yPosition -= PADDLE_SPEED * (tickTime / 1000.0);
	                break;
	            case down: paddle.yPosition += PADDLE_SPEED
	                    * (tickTime / 1000.0); break;
	        }
	        
	        ball.position.x += ball.velocity.x * (tickTime / 1000.0);
	        ball.position.y += ball.velocity.y * (tickTime / 1000.0);
	        
	        if (paddle.yPosition + paddle.length / 2 > ARENA_HEIGHT) {
	            paddle.yPosition = ARENA_WIDTH - paddle.length / 2;
	        } else if (paddle.yPosition - paddle.length / 2 < 0) {
                paddle.yPosition = 0 + paddle.length / 2;
            }
            
            if (ball.position.x < paddle.xPosition) {
                if (ball.position.y > paddle.yPosition - paddle.length / 2
                 && ball.position.y < paddle.yPosition + paddle.length / 2) {
                    paddle.bounce(ball);
                    bounces++;
                } else {
                    respawn(ball);
                    misses++;
                }
            }
	        
	        if (ball.position.x > ARENA_WIDTH) {
	            ball.position.x = ARENA_WIDTH;
	            ball.velocity.x *= -1;
	        } else if (ball.position.y > ARENA_HEIGHT) {
                ball.position.y = ARENA_HEIGHT;
                ball.velocity.y *= -1;
            } else if (ball.position.y < 0) {
                ball.position.y = 0;
                ball.velocity.y *= -1;
            }
	        
	        try {
	            Thread.sleep(tickTime);
	        } catch (Exception e) { }
	        
	        repaint();
	    }
	}
	
	private void respawn(Ball ball) {
	    ball.position = new Vector(ARENA_WIDTH / 2, ARENA_HEIGHT / 2);
        double angle = random.nextDouble() * Math.PI / 2 - Math.PI / 4;
        ball.velocity = new Vector(BALL_SPEED * Math.cos(angle),
                                   BALL_SPEED * Math.sin(angle));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.WHITE);
		paddle.draw(g);
		ball.draw(g);
		
		g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(new Color(113, 159, 235));
		g.drawString("Bounces: " + bounces, 20, 10);
        g.setColor(new Color(214, 88, 88));
		g.drawString("Misses: " + misses, 20, 20);
	}

    public void mouseDragged(MouseEvent e) {
        setLocation((int)(this.getX() + (e.getX() - lastMousePosition.getX())),
                (int)(this.getY() + (e.getY() - lastMousePosition.getY())));
    }

    public void mouseMoved(MouseEvent e) {
        lastMousePosition = e.getPoint();
    }
}