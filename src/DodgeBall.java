import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
	private ArrayList<GOval> balls;
	private ArrayList<GRect> enemies;
	private GLabel text;
	private Timer movement;
	private RandomGenerator rgen;
	private GLine line;
	private GLabel score;
	
	public static final int SIZE = 25;
	public static final int SPEED = 2;
	public static final int MS = 50;
	public static final int MAX_ENEMIES = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600;
	
	private int numTimes = -1;
	private int enemyKilled = 0;
	
	public void run() {
		rgen = RandomGenerator.getInstance();
		balls = new ArrayList<GOval>();
		enemies = new ArrayList<GRect>();
		
		text = new GLabel(""+enemies.size(), 0, WINDOW_HEIGHT);
		add(text);
		
		line = new GLine(WINDOW_WIDTH / 2 + 1, 0, WINDOW_WIDTH / 2 + 1, WINDOW_HEIGHT);
		line.setColor(Color.blue);
		line.setLineWidth(2);
		add(line);
		
		score = new GLabel("Enemy killed: " + enemyKilled, WINDOW_WIDTH * 3 / 4, WINDOW_HEIGHT / 2);
		add(score);
		
		movement = new Timer(MS, this);
		movement.start();
		addMouseListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (enemies.size() >= MAX_ENEMIES) {
			movement.stop();
			for (GOval ball : balls) {
				remove(ball);
			}
			for (GRect enemy : enemies) {
				remove(enemy);
			}
			score.setLabel("You lost - Score: " + enemyKilled * 10);
			score.setColor(Color.red);
		}
		moveAllBallsOnce();
		moveAllEnemiesOnce();
		if(numTimes % 40 == 0) {
		    addAnEnemy();
		}
		numTimes++;
		for (GOval ball : balls) {
			if (getElementAt(ball.getX() + ball.getWidth() + 1, ball.getY() + ball.getHeight() + 1) instanceof GRect) {
				GObject enemy = getElementAt(ball.getX() + ball.getWidth() + 1, ball.getY() + ball.getHeight() + 1);
				remove(enemy);
				enemies.remove(enemy);
				enemyKilled++;
				score.setLabel("Enemy Killed: " + enemyKilled);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		for(GOval b:balls) {
			if(b.getX() < SIZE * 2.5) {
				return;
			}
		}
		addABall(e.getY());     
	}
	
	private void addABall(double y) {
		GOval ball = makeBall(SIZE/2, y);
		add(ball);
		balls.add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
	private void addAnEnemy() {
		GRect e = makeEnemy(rgen.nextInt(0, WINDOW_HEIGHT-SIZE/2));
		enemies.add(e);
		text.setLabel("" + enemies.size());
		add(e);
	}
	
	public GRect makeEnemy(double y) {
		GRect temp = new GRect((WINDOW_WIDTH / 2) -SIZE, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.GREEN);
		temp.setFilled(true);
		return temp;
	}

	private void moveAllBallsOnce() {
		for(GOval ball:balls) {
			ball.move(SPEED, 0);
		}
	}

	private void moveAllEnemiesOnce() {
		for(GRect enemy : enemies) {
			enemy.move(0, rgen.nextInt(-SPEED, SPEED));
		}
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String args[]) {
		new DodgeBall().start();
	}
}
