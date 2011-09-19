import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Pong extends JFrame {
	Paddle paddle;
	ControlState controls;
	
	public Pong(Paddle paddle) {
		this.paddle = paddle;
		controls = new ControlState();
		addKeyListener(controls);
		controls.addControl(ControlsEnumeration.exit, KeyEvent.VK_ESCAPE);
		controls.addControl(ControlsEnumeration.movePaddleUp, KeyEvent.VK_D);
		controls.addControl(ControlsEnumeration.movePaddleDown, KeyEvent.VK_F);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(500, 500);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillOval(50, 50, 50, 50);
	}
}