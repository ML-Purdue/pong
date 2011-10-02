public class HumanPaddle extends Paddle {
	ControlState controlState;
	
	public HumanPaddle(ControlState controlState) {
		this(0.3, controlState);
		this.controlState = controlState;
	}
	
	public HumanPaddle(double width, ControlState controlState) {
		super(0.5, width, 1);
		this.width = width;
		this.controlState = controlState;
	}
	
	public double getMove(Ball ball) {
		if (controlState.isPressed(ControlState.Function.moveUp)) {
			return -1.0;
		}
		else if (controlState.isPressed(ControlState.Function.moveDown)) {
			return 1.0;
		} else {
			return 0.0;
		}
	}
}