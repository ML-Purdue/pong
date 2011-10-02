public class Ball {
	public double xPosition, yPosition, xVelocity, yVelocity, speed;
	
	public Ball() {
		xPosition = 0.0;
		yPosition = 0.0;
		xVelocity = 0.0;
		yVelocity = 0.0;
		this.speed = 1.0;
	}

	public void respawn(double xPosition, double yPosition, double angle) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		xVelocity = speed * Math.cos(angle);
		yVelocity = speed * Math.sin(angle);
	}
}
