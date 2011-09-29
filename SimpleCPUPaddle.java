import java.awt.Graphics;

public class SimpleCPUPaddle extends Paddle {
    Ball ball;
    
    public SimpleCPUPaddle(Ball ball, int length, int x, int y) {
        xPosition = x;
        yPosition = y;
        this.length = length;
        this.ball = ball;
    }

    public Move getMove() {
        if((ball.position.x > xPosition && ball.velocity.x < 0) ||
                (ball.position.x < xPosition && ball.velocity.x > 0)){
            if (ball.position.y < yPosition) {
                return Move.up;
            } else if (ball.position.y > yPosition) {
                return Move.down;
            } else return Move.none;
        }else{
            return Move.none;
        }
    }

    public void bounce(Ball ball) {
        super.bounce(ball);
    }
    
    public void draw(Graphics g) {
        g.drawLine((int)xPosition, (int)yPosition - length / 2, (int)xPosition, (int)yPosition + length / 2);
    }
}
