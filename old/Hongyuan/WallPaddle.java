// A simple wall. Just for test purpose.
import java.awt.Graphics;

public class WallPaddle extends Paddle {
    Ball ball;
    
    public WallPaddle(Ball ball, int length, int x, int y) {
        xPosition = x;
        yPosition = 0;
        this.length = length;
        this.ball = ball;
    }

    public Move getMove() {
        return Move.none;
    }

    /* There are several choices of rule for bounce.
    // long paddle
    public void bounce(Ball ball) {
        super.bounce(ball);
    }*/

    // high wall
    // slight possibility for a ball to bounce between two paddles horizontally
    public void bounce(Ball ball) {
        ball.velocity.x = - ball.velocity.x;
    }

    
    public void draw(Graphics g) {
        g.drawLine((int)xPosition, (int)yPosition - length / 2, (int)xPosition, (int)yPosition + length / 2);
    }
}
