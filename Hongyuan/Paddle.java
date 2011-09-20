/*
 * I still have some questions about the paddle class. So there may be some errors in my program.
 * Could you guys email me at wanghongyuan05@gmail.com, 
 * if the paddle class doesn't colaborate with the classed you are working on? 
 */ 
public class Paddle {
    double yPosition;
    double length;
    
    // Check the ControlState to figure out which direction should paddle move to
    public moveEnumeration getMove(Ball ball) {
        if ( ControlState.controls.get(ControlsEnumeration.moveUp).isPressed ) {
            return moveEnumberation.moveUp;
        } else if ( ControlState.controls.get(ControlsEnumeration.moveDown).isPressed ) {
            return moveEnumberation.moveDown;
        } else {
            return moveEnumberation.stay;
        }
    }

    public Vector bounce(Ball ball) {
        // If the ball hits the paddle
        
        /*
         * I am not sure. Should I use '<=' below?
         */
        if ( ball.location.x < 0 ) {

            // Determine the bouncing direction with the kciking position.
            if ( yPosition - length/2 <= ball.location.y && ball.location.y < yPosition - length / 6 ) {
                
                // If ball kicks at bottom of paddle, it will bounce 45 degree under horizontal
                ball.direction = new Vector(Math.sqrt(2), -Math.sqrt(2));
                
            } else if ( yPosition - length / 6 <= ball.location.y && ball.location.y < yPosition + length / 6 ) {
                
                // If ball kicks at center of paddle, it will bounce straight to right
                ball.direction = new Vector( 0, 1 );
                
            } else {
                
                // If ball kicks at top of paddle, it will bounce 45 degree above horizontal
                ball.direction = new Vector(Math.sqrt(2), Math.sqrt(2));
                
            }
        }
    }
}