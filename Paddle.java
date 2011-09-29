public abstract class Paddle {
	double xPosition;
	double yPosition;
	int length;
	
	abstract void draw(java.awt.Graphics g);
	abstract Move getMove();
	public void bounce(Ball ball) {
		double value = (ball.position.y - yPosition) / length;//distance from center of paddle
		double angle = Math.PI / 3 * Math.sqrt(Math.abs(value));//angle based on distance from center of paddle
		if (value < 0) angle *= -1;//if we struck the bottom half then reflect angle downwards

		if(ball.position.x < xPosition){//Ball bounced off right side of paddle
			ball.velocity = new Vector(ball.speed * Math.cos(angle), ball.speed * Math.sin(angle));
		}else if(ball.position.x > xPosition){//Ball bounced off left side of paddle
			ball.velocity = new Vector(-ball.speed * Math.cos(angle), ball.speed * Math.sin(angle));
		}
		ball.position.x = xPosition;//Reset ball to be in contact with the paddle
	}
}
