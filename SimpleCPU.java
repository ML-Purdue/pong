import java.awt.Point;

public class SimpleCPU extends Paddle {
    public SimpleCPU(){
        location = new Point();
    }
    public void move(){
        location.x++;
        location.y++;
    }
}
