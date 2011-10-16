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
     double y = ball.yPosition;
     
     if (ball.xVelocity>0){
        
         y += ( 2.0 - ball.xPosition ) / ball.xVelocity * ball.yVelocity;
         while(true){
             if (y > 1)
                 y= 2.0 - y;
             else if(y<0)
                 y=-y;
             else
                 break;
         }
          return 0;
     } else {
         y += ball.xPosition / ball.xVelocity * ball.yVelocity;
         while(true){
             if (y > 1)
                 y = 2.0 - y;
             else if(y<0)
                 y=-y;
             else
                 break;
         }
     }
     position = ball.yPosition + ball.xPosition / ball.xVelocity * ball.yVelocity;
     return 0;
     //return (y - position)/2;
 }
}