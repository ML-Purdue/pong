import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControlState implements KeyListener {
	public HashMap<Controls, Key> controls;
	// used to ignore repeated key presses a while key is held down
	private Key possiblyReleasedKey;
	
	public ControlState() {
		controls = new HashMap<Controls, Key>();
		// key is assigned when a key is released
		possiblyReleasedKey = null;
	}
	
	public void addControl(Controls control, int keyCode) {
		// prevent duplicate controls to avoid the situation in which
		// two different keys activate or deactivate the same control
		Iterator iterator = controls.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<Controls, Key> pairs = (Map.Entry<Controls, Key>)iterator.next();
	        if (pairs.getKey() == control) {
	        	throw new IllegalArgumentException("Duplicate control " + control.toString());
	        }
	        
	        // prevent duplicate keys to avoid the situation in which
			// a single key activates or deactivates multiple controls simultaneously
			if (pairs.getValue().keyCode == keyCode) {
				throw new IllegalArgumentException("Duplicate key " + keyCode);
			}
	    }

		controls.put(control, new Key(keyCode));
	}

	public void keyPressed(KeyEvent e) {
		// find the control corresponding to the key pressed
		for (Key key : controls.values()) {
	        if (key.keyCode == e.getKeyCode()) {
	        	key.isPressed = true;
		        
		        // the key has not been released
		        if (possiblyReleasedKey != null && possiblyReleasedKey.appearsReleased == true) {
					possiblyReleasedKey.appearsReleased = false;
				}
	        }
		}
	}

	public void keyReleased(KeyEvent e) {
		// find the control corresponding to the key released
		Iterator iterator = controls.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<Controls, Key> pairs = (Map.Entry<Controls, Key>)iterator.next();
	        if (pairs.getValue().keyCode == e.getKeyCode()) {
				// the key might not have actually been released because this
				// event could have been caused by auto-repeat
				possiblyReleasedKey = pairs.getValue();
				// key merely appears to have been released
				possiblyReleasedKey.appearsReleased = true;
				
				// use a new thread to check whether or not the key is
				// auto-pressed within a short time
				new Thread(new KeyReleaser(possiblyReleasedKey, 1)).start();
	        }
	    }
	}
	
	// unused but required by the KeyListener interface
	public void keyTyped(KeyEvent e) { }
}
