import java.awt.event.KeyEvent;

public class KeyReleaser implements Runnable {
	public Key key;
	long delay;
	
	// key to check and delay ms
	public KeyReleaser(Key key, long delay) {
		this.key = key;
		this.delay = delay;
	}
	
	public void run() {
		try {
			// allow time for the the possible auto-repeat to send a key press
			// which will set keyControl.appearsReleased to false
			Thread.sleep(delay);
		} catch (InterruptedException e) { return; }
		
		// if the key still appears released, it most likely is
		// if the key no longer appears released, it is pressed
    	if (key.appearsReleased) { System.out.println(KeyEvent.getKeyText(key.keyCode) + " Released."); }
		key.isPressed = !key.appearsReleased;
	}
}