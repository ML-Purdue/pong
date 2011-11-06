import java.util.Random;

public class CPUPaddle extends Paddle {
 public CPUPaddle() {
  this(0.3);
 }
 
 public CPUPaddle(double width) {
  this(0.3, 1.0);
 }
 
 public CPUPaddle(double width, double speed) {
  super(0.5, width, speed);
 }
 
 public double getMove(Ball ball) {
     return ball.yPosition - position;
 }
}
