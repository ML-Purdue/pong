import java.awt.Point;

public class Ball {
    Point location;
    Vector direction;
    double speed;

    public Ball(Point location, Vector direction, double speed){
        this.location = location;
        this.direction = direction;
        this.speed = speed;
    }
}
