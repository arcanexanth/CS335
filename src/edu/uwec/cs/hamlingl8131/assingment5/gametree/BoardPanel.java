package edu.uwec.cs.hamlingl8131.assingment5.gametree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private TwoPlayerGameBoard board = null;
	
	public void setBoard(TwoPlayerGameBoard board) {
		this.board = board;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (this.board != null) {
			
			Font messageFont = new Font("Monospaced", Font.BOLD, 30);
			
			
			if (this.board.isDraw()) {
				g.setColor(Color.WHITE);
				g.setFont(messageFont);
				g.drawString("It's a draw!", 75, 350);
				this.board.draw(g);
			
			} else if (this.board.isComputerWinner()) {
				g.setColor(Color.white);
				g.setFont(messageFont);
				g.drawString("You have lost.", 75, 350);
				this.board.draw(g);
				
			} else if (this.board.isUserWinner()) {
				g.setColor(Color.WHITE);
				g.setFont(messageFont);
				g.drawString("You have won!", 75, 350);
				this.board.draw(g);		
			
			} else {
				g.setColor(Color.WHITE);
				g.setFont(messageFont);
				g.drawString("Running...", 75, 350);
				this.board.draw(g);		
			}
		}
	}
}