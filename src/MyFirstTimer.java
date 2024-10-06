import java.awt.event.ActionEvent;
import java.awt.event.*;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

public class MyFirstTimer extends GraphicsProgram implements ActionListener{
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int MAX_STEPS = 20;
	private GLabel myLabel;
	
	private int numTimes;
	
	private Timer timer = new Timer(1000, this);

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		numTimes = 0;
		myLabel = new GLabel("# of times called?", 0, 100);
		add(myLabel);
		
		timer.setInitialDelay(3000);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		myLabel.move(5, 0);
		myLabel.setLabel("times called? " + numTimes);
		if (numTimes == 10) {
			timer.stop();
		}
	}
	
	public static void main(String[] args) {
		new MyFirstTimer().start();
	}
}