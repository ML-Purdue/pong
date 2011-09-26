import java.awt.Graphics;

public class HumanPaddle extends Paddle {
    ControlState controls;
    
    public HumanPaddle(ControlState controls, int length) {
        this.controls = controls;
        xPosition = 10;
        yPosition = 50;
        this.length = length;
    }

    public HumanPaddle(ControlState controls, int length, int x, int y) {
        this.controls = controls;
        xPosition = x;
        yPosition = y;
        this.length = length;
    }
        
    public Move getMove() {
        if (controls.controls.get(Controls.movePaddleUp).isPressed) {
            return Move.up;
        } else if (controls.controls.get(Controls.movePaddleDown).isPressed) {
            return Move.down;
        } else return Move.none;
    }

    public void bounce(Ball ball) {
        super.bounce(ball);
    }
    
    public void draw(Graphics g) {
        g.drawLine((int)xPosition, (int)yPosition - length / 2, (int)xPosition, (int)yPosition + length / 2);
    }
}