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
 Paddle lPaddle;
 Paddle rPaddle;
 Ball ball;
 final int PADDLE_SPEED = 100;
 final int PADDLE_LENGTH = 20;
 final int ARENA_WIDTH = 200;
 final int ARENA_HEIGHT = 200;
 final int BALL_RADIUS = 1;
 final int BALL_SPEED = 200;
 int lBounces;
 int lStreak;
 int lMisses;
 int rBounces;
 int rStreak;
 int rMisses;
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
 /*
 public Pong() {
     setupGraphics();
     
     ball = new Ball(BALL_SPEED, BALL_RADIUS);
     lPaddle = new SimpleCPUPaddle(ball, PADDLE_LENGTH, 10, ARENA_HEIGHT);
     rPaddle = new SimpleCPUPaddle(ball, PADDLE_LENGTH, ARENA_WIDTH-10, ARENA_HEIGHT);
     
     random = new Random();
     controls = new ControlState();
     controls.addControl(Controls.exit, KeyEvent.VK_ESCAPE);
     controls.addControl(Controls.movePaddleUp, KeyEvent.VK_UP);
     controls.addControl(Controls.movePaddleDown, KeyEvent.VK_DOWN);
     
     tickTime = (long)(1.0 / 30 * 1000);
     
     //Allow key presses to work both if the frame and canvas are in focus
     canvas.addKeyListener(controls);
     frame.addKeyListener(controls);
     
     canvas.addMouseMotionListener(this);
     
     isRunning = true;
     respawn(ball);
     start();
 }
 
 public Pong(Paddle left, Paddle right){
     setupGraphics();
     
     ball = new Ball(BALL_SPEED, BALL_RADIUS);
     rPaddle = right;
     lPaddle = left;
     
     random = new Random();
     controls = new ControlState();
     controls.addControl(Controls.exit, KeyEvent.VK_ESCAPE);
     controls.addControl(Controls.movePaddleUp, KeyEvent.VK_UP);
     controls.addControl(Controls.movePaddleDown, KeyEvent.VK_DOWN);
     
     //limit speed to 30 fps
     tickTime = (long)(1.0 / 30 * 1000);
     
     //Allow key presses to work both if the frame and canvas are in focus
     canvas.addKeyListener(controls);
     frame.addKeyListener(controls);
     
     canvas.addMouseMotionListener(this);
     
     isRunning = true;
     respawn(ball);
     start();
 }*/
 
 //common codes of constructor, except for start()
 private void setupPong(){
  setupGraphics();
  ball = new Ball(BALL_SPEED, BALL_RADIUS);
  random = new Random();
  controls = new ControlState();
  controls.addControl(Controls.exit, KeyEvent.VK_ESCAPE);
  controls.addControl(Controls.movePaddleUp, KeyEvent.VK_UP);
  controls.addControl(Controls.movePaddleDown, KeyEvent.VK_DOWN);

  tickTime = (long)(1.0 / 30 * 1000);

  //Allow key presses to work both if the frame and canvas are in focus
  canvas.addKeyListener(controls);
  frame.addKeyListener(controls);

  canvas.addMouseMotionListener(this);
  
  isRunning = true;
  respawn(ball);
 }
 //Adapted from Pong()
 public Pong() {
  setupPong();
  
  lPaddle = new SimpleCPUPaddle(ball, PADDLE_LENGTH, 10, ARENA_HEIGHT);
  rPaddle = new WallPaddle(ball, ARENA_HEIGHT, ARENA_WIDTH-10, ARENA_HEIGHT);
  
  start();
 }
 //Adapted from Pong(Paddle left, Paddle right)
 public Pong(String leftName, String rightName, String path){
   setupPong();

   //Select paddles by names
   String left = leftName.toLowerCase();
   String right = rightName.toLowerCase();

   if ( left.equals("human") )
     lPaddle = new HumanPaddle(controls, PADDLE_LENGTH, 10, ARENA_HEIGHT);
   else if ( left.equals("cpu") )
     lPaddle = new SimpleCPUPaddle(ball, PADDLE_LENGTH, 10, ARENA_HEIGHT);
   else if ( left.equals("wall") )
     lPaddle = new WallPaddle(ball, ARENA_HEIGHT, 10, ARENA_HEIGHT);
   else
     throw new Error("Error: Valid paddle name " + leftName);
   
   if ( right.equals("human") )
     rPaddle = new HumanPaddle(controls, PADDLE_LENGTH, ARENA_WIDTH - 10, ARENA_HEIGHT);
   else if ( right.equals("cpu") )
     rPaddle = new SimpleCPUPaddle(ball, PADDLE_LENGTH, ARENA_WIDTH - 10, ARENA_HEIGHT);
   else if ( right.equals("wall") )
     rPaddle = new WallPaddle(ball, ARENA_HEIGHT, ARENA_WIDTH - 10, ARENA_HEIGHT);
   else 
     throw new Error("Error: Valid paddle name " + rightName);

   //If the game needs to be saved
   if ( path.length() != 0 ) {
       //Path could be invalid
   }
   
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
  //Push the buffer to the screen
  graphics.dispose();
  graphics = null;
  try{
   strategy.show();
   Toolkit.getDefaultToolkit().sync();
   return !strategy.contentsLost();//return if we indeed pushed the buffer to the screen
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
   switch (rPaddle.getMove()) {
    case up: rPaddle.yPosition -= PADDLE_SPEED * (tickTime / 1000.0);
     break;
    case down: rPaddle.yPosition += PADDLE_SPEED
      * (tickTime / 1000.0); break;
   }
   switch (lPaddle.getMove()) {
    case up: lPaddle.yPosition -= PADDLE_SPEED * (tickTime / 1000.0);
     break;
    case down: lPaddle.yPosition += PADDLE_SPEED
      * (tickTime / 1000.0); break;
   }
   
   //Update ball position
   ball.position.x += ball.velocity.x * (tickTime / 1000.0);
   ball.position.y += ball.velocity.y * (tickTime / 1000.0);
   
   //If the paddle hit the edge of the arena, stop it
   if (rPaddle.yPosition + rPaddle.length / 2 > ARENA_HEIGHT) {
    rPaddle.yPosition = ARENA_WIDTH - rPaddle.length / 2;
   } else if (rPaddle.yPosition - rPaddle.length / 2 < 0) {
    rPaddle.yPosition = 0 + rPaddle.length / 2;
   }
   if (lPaddle.yPosition + lPaddle.length / 2 > ARENA_HEIGHT) {
    lPaddle.yPosition = ARENA_WIDTH - lPaddle.length / 2;
   } else if (lPaddle.yPosition - lPaddle.length / 2 < 0) {
    lPaddle.yPosition = 0 + lPaddle.length / 2;
   }
   
   //If the ball is behind the paddle's location
   if (ball.position.x < lPaddle.xPosition) {
    //If the ball hit the paddle
    if (ball.position.y > lPaddle.yPosition - lPaddle.length / 2
     && ball.position.y < lPaddle.yPosition + lPaddle.length / 2) {
     lPaddle.bounce(ball);
     lBounces++;
    } else {
     respawn(ball);
     lStreak = (lBounces > lStreak) ? lBounces : lStreak;
     lBounces = 0;
     lMisses++;
    }
   }
   if (ball.position.x > rPaddle.xPosition) {
    //If the ball hit the paddle
    if (ball.position.y > rPaddle.yPosition - rPaddle.length / 2
     && ball.position.y < rPaddle.yPosition + rPaddle.length / 2) {
     rPaddle.bounce(ball);
     rBounces++;
    } else {
     respawn(ball);
     rStreak = (rBounces > rStreak) ? rBounces : rStreak;
     rMisses++;
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
   }while(!updateScreen());//keep trying to push to screen if it didn't work

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
  //draw background
  g.setColor(Color.BLACK);
  g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

  //draw paddles and ball
  g.setColor(Color.WHITE);
  rPaddle.draw(g);
  lPaddle.draw(g);
  ball.draw(g);
  
  //draw score
  g.setFont(new Font("Arial", Font.PLAIN, 10));
  g.setColor(new Color(113, 159, 235));
  g.drawString(""+lStreak, 20, 10);
  g.drawString(""+rStreak, ARENA_WIDTH-20, 10);
  g.setColor(new Color(214, 88, 88));
  g.drawString(""+lMisses, 20, 20);
  g.drawString(""+rMisses, ARENA_WIDTH-20, 20);
 }

 public void mouseDragged(MouseEvent e) {
  frame.setLocation((int)(frame.getX() + (e.getX() - lastMousePosition.getX())),
   (int)(frame.getY() + (e.getY() - lastMousePosition.getY())));
 }

 public void mouseMoved(MouseEvent e) {
  lastMousePosition = e.getPoint();
 }
}

