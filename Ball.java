import java.awt.Graphics;

public class Ball {
    public Vector position;
    public Vector velocity;
    public int radius;
    public int speed;
    
    public Ball() {
        this(new Vector(), new Vector(), 50, 1);
    }
    
    public Ball(int speed, int radius) {
        this(new Vector(), new Vector(), speed, radius);
    }
    
    public Ball(Vector position, Vector velocity) {
        this(position, velocity, 50, 1);
    }
    
    public Ball(Vector position, Vector velocity, int speed, int radius) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
        this.radius = radius;
    }
    
    public void draw(Graphics g) {
        g.drawOval((int)position.x - radius, (int)position.y - radius, radius * 2, radius * 2);
    }
}
