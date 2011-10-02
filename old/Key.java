public class Key {
	int keyCode;
	boolean isPressed;
	// used to check for auto-repeat
	boolean appearsReleased;
	
	public Key(int keyCode) {
		this.keyCode = keyCode;
		isPressed = false;
		appearsReleased = true;
	}
}