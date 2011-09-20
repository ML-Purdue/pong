public abstract class Paddle {
    double xPosition;
    double yPosition;
    int length;
    
    abstract void draw(java.awt.Graphics g);
    abstract Move getMove();
    public void bounce(Ball ball) {
        ball.position.x = xPosition;
        double value = (ball.position.y - yPosition) / length;
        double angle = Math.PI / 3 * Math.sqrt(Math.abs(value));
        if (value < 0) angle *= -1;
        ball.velocity = new Vector(ball.speed * Math.cos(angle),
                                   ball.speed * Math.sin(angle));
    }
}
