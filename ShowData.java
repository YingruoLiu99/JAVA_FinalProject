package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ShowData {
	private JTextArea textQueryArea;
	private Connection conn;
	private PreparedStatement queryStmtLastName;
	final int AREA_ROWS = 20;
	final int AREA_COLUMNS = 40;
   
	public ShowData() {
	JFrame frame1 = new JFrame("Game History");
	System.out.println("show data");
	
	frame1.setSize(700, 700);
	textQueryArea = new JTextArea(
	            AREA_ROWS, AREA_COLUMNS);
	textQueryArea.setEditable(false);
	frame1.add(textQueryArea);
	frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame1.setVisible(true);
	
	printText();
	
	}
	
	public void printText() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:Pong1.db");
			queryStmtLastName = conn.prepareStatement("Select * from PlayerScoreWin");
			
		} catch (SQLException e) {
			System.err.println("Connection error: " + e);
			System.exit(1);
		}
		
		   try {
			   textQueryArea.setText("");
			   PreparedStatement stmt = queryStmtLastName;
			   //String lastNameText = lastNameQuery.getText();
				//stmt.setString(1, lastNameText);
			   ResultSet rset;
					 //Connection connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
			   stmt = conn.prepareStatement("SELECT * FROM PlayerScoreWin");
			   rset = stmt.executeQuery();
		
				ResultSetMetaData rsmd = rset.getMetaData();
				int numColumns = rsmd.getColumnCount();
				//System.out.println("numcolumns is "+ numColumns);
	
				String rowString = "";
				while (rset.next()) {
					for (int i=1;i<=numColumns;i++) {
						Object o = rset.getObject(i);
						rowString += o.toString() + "\t";
					}
					rowString += "\n";
				}
				System.out.print("rowString  is  " + rowString);
				textQueryArea.setText(rowString);
				//lastNameQuery.setText("");
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	}
}
