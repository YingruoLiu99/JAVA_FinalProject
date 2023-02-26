package com.main;

import java.awt.BasicStroke;
import java.awt.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private PreparedStatement insertStmt;
	private Statement Stmt;
	private PreparedStatement queryStmtLastName;

	public final static int WIDTH = 1000;
	public final static int HEIGHT = WIDTH * 9 / 16; 

	public boolean running = false; 
	private Thread gameThread; 

	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;

	private MainMenu menu; 

	public Game() {

		canvasSetup();

		new Window("Pong Game (5 hit win)", this);
		   
		PreparedStatement pstmt = insertStmt;
		
		initialise();

		this.addKeyListener(new KeyInput(leftPaddle, rightPaddle));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.setFocusable(true);

	}

	private void initialise() {
		ball = new Ball();
		leftPaddle = new Paddle(Color.green, true);
		rightPaddle = new Paddle(Color.red, false);
		menu = new MainMenu(this);
	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void run() {

		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
				draw();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
	}

	public synchronized void start() {
		gameThread = new Thread(this);
		gameThread.start(); // start thread
		running = true;
	}

	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void draw() {

		BufferStrategy buffer = this.getBufferStrategy(); 
	

		if (buffer == null) { 
			this.createBufferStrategy(3); 
			return;
		}

		Graphics g = buffer.getDrawGraphics(); 

		drawBackground(g);

		if (menu.active) {
			menu.draw(g);
		}else {

		}

		ball.draw(g);
		leftPaddle.draw(g);
		rightPaddle.draw(g);
		g.dispose(); // Disposes of this graphics context and releases any system resources that it
						// is using
		buffer.show(); 

	}

	private void drawBackground(Graphics g) {
		// black background
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Dotted line in the middle
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g; 
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	}

	public void update() {

		if (!menu.active) {
			boolean gameover =true;
			if(ball.gameOver == false) {
				gameover = false;
			}
			boolean win;
			win = ball.update(leftPaddle, rightPaddle, gameover);
			if(win) {
				//System.out.println("updated");
			leftPaddle.update(ball);
			rightPaddle.update(ball);
			
			}else {

			}
		}
	}

	public static int ensureRange(int value, int min, int max) {
		return Math.min(Math.max(value, min), max);
	}

	public static int sign(double d) {
		if (d <= 0)
			return -1;

		return 1;
	}
	
	public void gameOver() {
		
		
	}
	public MainMenu getMenu() {
		return this.menu;
	}

	public static void main(String[] args) {
		new Game();
		
	}




}