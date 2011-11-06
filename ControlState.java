import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class ControlState implements KeyListener {
	private ArrayList<Control> controls;
	private ArrayList<Listener> listeners;
	private int releaseDelay;
	
	public enum Function {
	    moveUp, moveDown, exit, speedUp, slowDown, tickUp, tickDown
	}
	
	public class Control implements ActionListener {
        private ControlState controlState;
		public int keyCode;
		public Function function;
		public boolean isPressed;
		public Timer timer;
		public long when;
		
		public Control(Function control, int keyCode, 
		        int releaseDelay, ControlState controlState) {
            this.controlState = controlState;
			this.function = control;
			this.keyCode = keyCode;
			isPressed = false;
			timer = new Timer(releaseDelay, this);
			when = 0;
		}

		public void actionPerformed(ActionEvent e) {
			timer.stop();
			isPressed = false;
			when = System.currentTimeMillis();
			controlState.controlReleased(this);
		}
	}
	
	public interface Listener {
		public void controlPressed(ControlState.Control control);
		public void controlReleased(ControlState.Control control);
	}
	
	public ControlState(Component component) {
		this(component, 15);
	}
	
	public ControlState(Component component, int RELEASE_DELAY) {
		controls = new ArrayList<Control>();
		listeners = new ArrayList<Listener>();
		component.addKeyListener(this);
		this.releaseDelay = RELEASE_DELAY;
	}

	public void add(Function control, int keyCode) {
		for (int i = 0; i < controls.size(); i++)
			if (controls.get(i).function == control)
				throw new IllegalArgumentException
				("Duplicate control " + control);
			else if (controls.get(i).keyCode == keyCode)
				throw new IllegalArgumentException
				("Duplicate key code " + keyCode);
		
		controls.add(new Control(control, keyCode, releaseDelay, this));
	}
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	public boolean isPressed(Function control) {
		for (int i = 0; i < controls.size(); i++)
	        if (controls.get(i).function == control)
	        	return controls.get(i).isPressed;
		
		return false;
	}

	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < controls.size(); i++) {
	        if (controls.get(i).keyCode == e.getKeyCode()) {
	        	controls.get(i).timer.stop();
	        	
	        	if (!controls.get(i).isPressed) {
		        	controls.get(i).isPressed = true;
		        	controls.get(i).when = e.getWhen();
		        			        	
	        		for (int j = 0; j < listeners.size(); j++)
	        			listeners.get(j).controlPressed(controls.get(i));
	        	}
	        }
		}
	}

	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < controls.size(); i++)
	        if (controls.get(i).keyCode == e.getKeyCode()) {
	        	controls.get(i).timer.restart();
	        	controls.get(i).when = e.getWhen();
	        }
	}
	
	public void controlReleased(Control control) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).controlReleased(control);
	}
	
	public void keyTyped(KeyEvent e) { }
}
