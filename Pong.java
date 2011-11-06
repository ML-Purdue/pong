import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Pong extends JFrame implements ControlState.Listener {
    int arenaSize;
    int margin;
    BufferedImage buffer;
    Graphics2D bufferGraphics;
    double tickDuration;
    double speedup;
    double maxFPS;
    Stopwatch frameStopwatch;
    Stopwatch gameStopwatch;
    long msPrecision;
    ControlState controls;
    Random random;
    Paddle paddle;
    boolean isAI;
    Ball ball;
    int hits;
    int misses;

    public enum PaddleType {
        Human, AI, CPU, Genetic
    }
    
    public Pong() {
        this(PaddleType.Genetic, 1/10.0, 10000);
    }
    
    public Pong(PaddleType paddleType, double tickDuration, double speedup) {
        arenaSize = 200;
        margin = 30;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(300, 300);
        setSize(arenaSize + 2 * margin, arenaSize + 2 * margin);
        buffer = new BufferedImage(getWidth(), getHeight(), 
                                   BufferedImage.TYPE_INT_RGB);
        bufferGraphics = (Graphics2D)buffer.getGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                        RenderingHints.VALUE_ANTIALIAS_ON);
        setUndecorated(true);
        
        this.tickDuration = tickDuration;
        this.speedup = speedup;
        maxFPS = 60.0;
        frameStopwatch = new Stopwatch();
        gameStopwatch = new Stopwatch();
        msPrecision = 16;
        controls = new ControlState(this);
        controls.addListener(this);
        controls.add(ControlState.Function.exit, KeyEvent.VK_ESCAPE);
        controls.add(ControlState.Function.moveUp, KeyEvent.VK_UP);
        controls.add(ControlState.Function.moveDown, KeyEvent.VK_DOWN);
        controls.add(ControlState.Function.speedUp, KeyEvent.VK_RIGHT);
        controls.add(ControlState.Function.slowDown, KeyEvent.VK_LEFT);
        controls.add(ControlState.Function.tickUp, KeyEvent.VK_T);
        controls.add(ControlState.Function.tickDown, KeyEvent.VK_Y);
        random = new Random();
        switch (paddleType) {
            case Human:
                paddle = new HumanPaddle(controls);
                isAI = false;
                break;
            case AI:
                paddle = new AIPaddle(random);
                isAI = true;
                break;
            case CPU:
                paddle = new CPUPaddle(0.3, 0.2);
                isAI = false;
                break;
            case Genetic:
                paddle = new GeneticPaddle(random, 10);
                isAI = true;
                break;
        }
        ball = new Ball();
        ball.respawn(0.5, 0.5, Math.PI / 3 * (2 * random.nextDouble() - 1));
        margin = 30;
        hits = 0;
        misses = 0;

        setVisible(true);
        
        run();
    }
    
    private void run() {
        double millisecondsPerUpdate = (double)(tickDuration / speedup * 1000);
        double nextUpdate = System.currentTimeMillis();
        double millisecondsPerDraw = (double)(1000 / maxFPS);
        double nextDraw = System.currentTimeMillis();
        
        while (true) {
            double deltaUpdate = nextUpdate - System.currentTimeMillis();
            millisecondsPerUpdate = (double)(tickDuration / speedup * 1000);
            if (deltaUpdate <= 0) {
                update();
                nextUpdate += millisecondsPerUpdate;
            } else if (deltaUpdate >= msPrecision) {
                try { Thread.sleep((long)deltaUpdate); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }

            double deltaDraw = nextDraw - System.currentTimeMillis();
            if (deltaDraw <= 0) {
                repaint();
                nextDraw += millisecondsPerDraw;
                nextDraw = Math.max(nextDraw, nextUpdate);
            }
        }
    }
    
    private void update() {
        double move = Math.min(Math.max(paddle.getMove(ball), -1.0), 1.0);
        paddle.position += move * tickDuration;
        paddle.position = Math.min(paddle.position, 1.0 - paddle.width / 2);
        paddle.position = Math.max(paddle.position, 0.0 + paddle.width / 2);
        
        double lastX = ball.xPosition;
        double lastY = ball.yPosition;
        ball.xPosition += ball.xVelocity * tickDuration;
        ball.yPosition += ball.yVelocity * tickDuration;
        
        if (ball.yPosition > 1.0) {
            ball.xPosition = lastX + (1.0 - lastY) / 
                (ball.yPosition - lastY) * (ball.xPosition - lastX);
            ball.yPosition = 1.0;
            ball.yVelocity *= -1;
        } else if (ball.yPosition < 0.0) {
            ball.xPosition = lastX - lastY / 
                (ball.yPosition - lastY) * (ball.xPosition - lastX);
            ball.yPosition = 0.0;
            ball.yVelocity *= -1;
        }
        if (ball.xPosition > 1.0) {
            ball.yPosition = lastY + (1.0 - lastX) / 
                (ball.xPosition - lastX) * (ball.yPosition - lastY);
            ball.xPosition = 1.0;
            ball.xVelocity *= -1;
        } else if (ball.xPosition < 0.0) {
            ball.yPosition = lastY - lastX / 
                (ball.xPosition - lastX) * (ball.yPosition - lastY);
            ball.xPosition = 0.0;
            ball.xVelocity *= -1;
            
            if (ball.yPosition >= paddle.position - paddle.width / 2
                    && ball.yPosition <= paddle.position + paddle.width / 2) {
                paddle.bounce(ball, random);
                hits++;
            } else {
                ball.respawn(0.5, 0.5, 
                             Math.PI / 3 * (2 * random.nextDouble() - 1));
                misses++;
                if (isAI) {
                    ((GeneticPaddle)paddle).sendPain();
                }
            }
        }
    }
    
    public void paint(Graphics g) {
        // clear
        bufferGraphics.setColor(Color.WHITE);
        bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        
        // hits and misses
        bufferGraphics.setColor(Color.BLACK);
        String percentage = String.format("%.0f", hits != 0 || misses != 0 ?
                                              100 * (double)hits / (hits + misses) : 0.0) + "%";
        bufferGraphics.drawString("Hits/Misses: " + 
                                  hits + "/" + misses + " " + percentage, margin + 5, 20);

        bufferGraphics.drawString("Tick:" + tickDuration + " Speedup: " + speedup
                                  , margin + 5, arenaSize + (2*margin) - 10);
        
        // outline
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.drawRect(margin - 1, margin - 1,
                                arenaSize + 1, arenaSize + 1);
        
        // score zone
        bufferGraphics.setColor(Color.RED);
        bufferGraphics.drawLine(margin - 1, margin,
                                margin - 1, margin - 1 + arenaSize);
        
        // paddle
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(margin - 4, margin +
                                (int)((paddle.position - paddle.width / 2) * arenaSize),
                                4, (int)(paddle.width * arenaSize));
        
        // ball
        bufferGraphics.setColor(Color.BLUE);
        bufferGraphics.fillOval(margin + (int)(ball.xPosition * arenaSize) - 3,
                                margin + (int)(ball.yPosition * arenaSize) - 3, 6, 6);
        
        g.drawImage(buffer, 0, 0, this);
    }
    
    public void controlPressed(ControlState.Control control) {
        if (control.function == ControlState.Function.exit) {
            System.exit(0);
        }

        if(control.function == (ControlState.Function.speedUp)){
            speedup *= 2;
        }
        
        if(control.function == (ControlState.Function.slowDown)){
            speedup /= 2;
        }

        if(control.function == (ControlState.Function.tickUp)){
            tickDuration *= 2;
        }

        if(control.function == (ControlState.Function.tickDown)){
            tickDuration /= 2;
        }
    }
    
    public void controlReleased(ControlState.Control control) {
    }
        public static void main(String[] parameters) {
        new Pong();
    }
}
