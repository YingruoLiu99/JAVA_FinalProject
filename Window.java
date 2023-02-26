package com.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.sound.sampled.*;

public class Window extends JFrame implements ActionListener{
	static JMenuBar mb;
	static JMenu x;
	static JMenuItem m1, m2;
	private PreparedStatement insertStmt;
	private File file;
	private AudioInputStream audioStream;
	private Clip clip;
	private Game thisgame;
	public Window(String title, Game game) {
		//menuobj = game.getMenu();
		//thisgame = &game;
		JFrame frame = new JFrame(title);
		 x = new JMenu("File"); 
	      mb = new JMenuBar();
	      JMenuItem fileExitItem2 = new JMenuItem("Show game record");
	      fileExitItem2.addActionListener(this);
	      mb.add(fileExitItem2);
	      x.add(fileExitItem2);
	      mb.add(x);
	      JMenuItem fileExitItem3 = new JMenuItem("Clear game record");
	      fileExitItem3.addActionListener(this);
	      mb.add(fileExitItem3);
	      x.add(fileExitItem3);
	      JMenuItem fileExitItem4 = new JMenuItem("Stop background music");
	      fileExitItem4.addActionListener(this);
	      mb.add(fileExitItem4);
	      x.add(fileExitItem4);
	      JMenuItem fileExitItem5 = new JMenuItem("Play background music");
	      fileExitItem5.addActionListener(this);
	      mb.add(fileExitItem5);
	      x.add(fileExitItem5);

	      JMenuItem fileExitItem6 = new JMenuItem("Start a new game");
	      fileExitItem6.addActionListener(this);
	      mb.add(fileExitItem6);
	      x.add(fileExitItem6);

	      JMenuItem fileExitItem = new JMenuItem("Exit");
	      fileExitItem.addActionListener(this);
	      mb.add(fileExitItem);
	      x.add(fileExitItem);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setJMenuBar(mb);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		file = new File("Symphony No.6 (1st movement).wav");
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		game.start();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem item = (JMenuItem)e.getSource();
		String label2 = item.getText();
		if(label2 == "Exit") {
			System.exit(0);
		}else if(label2 == "Clear game record"){
			try {
			Connection connection;
				connection = DriverManager.getConnection("jdbc:sqlite:Pong1.db");
			   String insertData = "DELETE FROM PlayerScoreWin";
			    PreparedStatement pstmt = insertStmt;
				pstmt = connection.prepareStatement(insertData);
			    pstmt.executeUpdate();
			connection.close();
			}
			catch(Exception exp) {
				
			}
			//this.run();
	
		}else if(label2 == "Stop background music") {
			clip.stop();
		}
		else if(label2 == "Play background music") {	
			clip.start();
		}
		else if(label2 == "Start a new game") {
			new Game();
		}
		else {
			//show the record 
			new ShowData();
		}

		
	}
}
