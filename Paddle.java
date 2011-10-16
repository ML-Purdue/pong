import java.util.Random;

public abstract class Paddle {
 public double position;
 public double width;
 public double speed;
 
 public Paddle(double position, double width, double speed) {
  this.position = position;
  this.width = width;
  this.speed = speed;
 }
 
 public double getMove(Ball ball) {
  return 0.0;
 }

 public void bounce(Ball ball, Random random) {
  double angle = Math.PI / 3 * 
   (ball.yPosition - position) / (width / 2);
  angle = angle + (2 * random.nextDouble() - 0.5) / 10;
  ball.xVelocity = ball.speed * Math.cos(angle);
  ball.yVelocity = ball.speed * Math.sin(angle);
 }
}