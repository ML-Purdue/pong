/********************************************************
 * Abstract class which defines the common variables
 * and functions which every paddle should have/implement
 *
 * Actual Paddles should extend this generic Paddle
 *
 * *******************************************************/
import java.awt.Point;

public abstract class Paddle {
    public Point location;

    public abstract void move();
}
