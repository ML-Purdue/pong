//Graphics imports
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//Functionality imports
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Pong extends Thread implements MouseMotionListener {
	//Graphics members
	private Canvas canvas;
	private BufferStrategy strategy;
	private BufferedImage background;
	private Graphics2D backgroundGraphics;
	private Graphics2D graphics;
	private JFrame frame;
	private GraphicsConfiguration config = GraphicsEnvironment
		.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice()
		.getDefaultConfiguration();

	//Functionality members
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
	boolean isRunning;
	Point lastMousePosition;
	
	public final BufferedImage create(final int width, final int height, final boolean alpha){
		return config.createCompatibleImage(width, height, (alpha) ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
	}

	private void setupGraphics(){
		//Set up the JFrame
		frame = new JFrame();
		frame.addWindowListener(new FrameClose());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(ARENA_WIDTH, ARENA_HEIGHT);
		frame.setUndecorated(true);
		frame.setVisible(true);

		//Set up the Canvas
		canvas = new Canvas(config);
		canvas.setSize(ARENA_WIDTH, ARENA_HEIGHT);
		frame.add(canvas, 0);

		//Set up the buffer and background 
		background = create(ARENA_WIDTH, ARENA_HEIGHT, false);
		canvas.createBufferStrategy(2);
		do {
			strategy = canvas.getBufferStrategy();
		} while(strategy == null);

	}

	public Pong() {
		setupGraphics();

		random = new Random();
		controls = new ControlState();
		ball = new Ball(BALL_SPEED, BALL_RADIUS);
		paddle = new SimpleCPUPaddle(ball, PADDLE_LENGTH);
		tickTime = (long)(1.0 / 30 * 1000);
		canvas.addKeyListener(controls);
		frame.addKeyListener(controls);
		canvas.addMouseMotionListener(this);
		controls.addControl(Controls.exit, KeyEvent.VK_ESCAPE);
		controls.addControl(Controls.movePaddleUp, KeyEvent.VK_UP);
		controls.addControl(Controls.movePaddleDown, 
				KeyEvent.VK_DOWN);
		
		isRunning = true;
		respawn(ball);
		start();
	}

	public Pong(Paddle paddle){
		setupGraphics();

		random = new Random();
		controls = new ControlState();
        this.paddle = paddle;
		tickTime = (long)(1.0 / 30 * 1000);
		canvas.addKeyListener(controls);
		frame.addKeyListener(controls);
		canvas.addMouseMotionListener(this);
		controls.addControl(Controls.exit, KeyEvent.VK_ESCAPE);
		controls.addControl(Controls.movePaddleUp, KeyEvent.VK_UP);
		controls.addControl(Controls.movePaddleDown, 
				KeyEvent.VK_DOWN);
		
		isRunning = true;
		respawn(ball);
		start();
	}

	private class FrameClose extends WindowAdapter {
		public void windowClosing(final WindowEvent e){
			isRunning = false;
		}
	}

	private Graphics2D getBuffer(){
		if(graphics == null){
			try{
				graphics = (Graphics2D)strategy.getDrawGraphics();
			}catch(IllegalStateException e){ return null; }
		}
		return graphics;
	}

	private boolean updateScreen() {
		graphics.dispose();
		graphics = null;
		try{
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			return !strategy.contentsLost();
		} catch(NullPointerException e){ return true;
		} catch(IllegalStateException e){ return true;
		}
	}

	public void run() {
		backgroundGraphics = (Graphics2D)background.getGraphics();

		while (isRunning) {
			long renderStart = System.nanoTime();

			//Do game logic
			//If the exit button is pressed then exit the game
			if (controls.controls.get(Controls.exit).isPressed) {
				System.exit(0);
			}
			
			//Get the paddle's move
			switch (paddle.getMove()) {
				case up: paddle.yPosition -= PADDLE_SPEED * (tickTime / 1000.0);
					break;
				case down: paddle.yPosition += PADDLE_SPEED
						* (tickTime / 1000.0); break;
			}
			
			//Update ball position
			ball.position.x += ball.velocity.x * (tickTime / 1000.0);
			ball.position.y += ball.velocity.y * (tickTime / 1000.0);
			
			//If the paddle hit the edge of the arena, stop it
			if (paddle.yPosition + paddle.length / 2 > ARENA_HEIGHT) {
				paddle.yPosition = ARENA_WIDTH - paddle.length / 2;
			} else if (paddle.yPosition - paddle.length / 2 < 0) {
				paddle.yPosition = 0 + paddle.length / 2;
			}
			
			//If the ball is behind the paddle's location
			if (ball.position.x < paddle.xPosition) {
				//If the ball hit the paddle
				if (ball.position.y > paddle.yPosition - paddle.length / 2
				 && ball.position.y < paddle.yPosition + paddle.length / 2) {
					paddle.bounce(ball);
					bounces++;
				} else {
					respawn(ball);
					misses++;
				}
			}
			
			//If the ball hit the edges of the arena
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
			
			//Update Graphics
			do{
				Graphics2D bg = getBuffer();
				if(!isRunning){
					System.exit(0);
				}
				renderGame(backgroundGraphics);
				bg.drawImage(background, 0, 0, null);
				bg.dispose();
			}while(!updateScreen());

			//FPS limiting
			long renderTime = (System.nanoTime() - renderStart) / 1000000;
			try{
				Thread.sleep(Math.max(0, tickTime - renderTime));
			}catch(InterruptedException e){
				Thread.interrupted();
				break;
			}
			renderTime = (System.nanoTime() - renderStart) / 1000000;
		}
		frame.dispose();
	}
	
	private void respawn(Ball ball) {
		ball.position = new Vector(ARENA_WIDTH / 2, ARENA_HEIGHT / 2);
		double angle = random.nextDouble() * Math.PI / 2 - Math.PI / 4;
		ball.velocity = new Vector(BALL_SPEED * Math.cos(angle),
								   BALL_SPEED * Math.sin(angle));
	}
	
	public void renderGame(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

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
		frame.setLocation((int)(frame.getX() + (e.getX() - lastMousePosition.getX())),
			(int)(frame.getY() + (e.getY() - lastMousePosition.getY())));
	}

	public void mouseMoved(MouseEvent e) {
		lastMousePosition = e.getPoint();
	}
}

