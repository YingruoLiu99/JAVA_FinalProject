package com.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ball {

	public static final int SIZE = 16;

	private int x, y; // position of top left corner of square
	private int xVel, yVel; // either 1 or -1
	private int speed = 3; // speed of the ball
	public boolean gameOver = true;

	public Ball() {
		reset();
	}

	private void reset() {
		// initial position
		x = Game.WIDTH / 2 - SIZE / 2;
		y = Game.HEIGHT / 2 - SIZE / 2;

		// initial velocity
		xVel = Game.sign(Math.random() * 2.0 - 1);
		yVel = Game.sign(Math.random() * 2.0 - 1);
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, SIZE, SIZE);
	}

	public boolean update(Paddle lp, Paddle rp, boolean gameover) {

		x += xVel * speed;
		y += yVel * speed;

		if (y + SIZE >= Game.HEIGHT || y <= 0)
			changeYDir();
		
		if(gameover == false) {
			return false;
		}
		boolean win = false;
		if (x + SIZE >= Game.WIDTH) { 
			win = lp.addPoint("Left player");
			if(!win)
			reset();
			else {
				gameOver=false;
				return false;
			}
		}
		if (x <= 0) { 
			win = rp.addPoint("Right player");
			if(!win)
			reset();
			else {
				gameOver = false;
				return false;
			}
		}
		return true;
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void changeXDir() {
		xVel *= -1;
	}

	public void changeYDir() {
		yVel *= -1;
	}

}