package edu.uwec.cs.hamlingl8131.assingment5.gametree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TicTacToeBoard implements TwoPlayerGameBoard {
	
	private static final int boardSize = 9;
	
	private char[] board;
	private int nOpenPosition;
	private boolean isComputerTurn;
	
	public TicTacToeBoard() {
		this.board = new char[boardSize];
		for (int i = 0; i < boardSize; i++){
			this.board[i] = '_';
		}
		
		this.nOpenPosition = 0;
		this.isComputerTurn = false;
	}
	
	/** for testing **/	
	public TicTacToeBoard(char[] board, int nOpenPosition, boolean isComputerTurn){
		this.board = board;
		this.nOpenPosition = nOpenPosition;
		this.isComputerTurn = isComputerTurn;
	}
	
	/**
	 * copy constructor
	 */
	
	public TicTacToeBoard(TicTacToeBoard orig){
		this.board = new char[boardSize];
		for (int i = 0; i < boardSize; i++){
			this.board[i] = orig.board[i];
		}
		this.nOpenPosition = orig.nOpenPosition;
		this.isComputerTurn = orig.isComputerTurn;
	}
	
	@Override
	public boolean hasMoreChildren() {
		return (nOpenPosition < boardSize);
	}

	@Override
	public TwoPlayerGameBoard nextChild() {
		TicTacToeBoard child = new TicTacToeBoard(this);
		child.board[nOpenPosition] = (isComputerTurn) ? 'X' : 'O';
		
		do {
			this.nOpenPosition++;
		} while ((this.nOpenPosition < boardSize) && (this.board[this.nOpenPosition] != '_'));
		
		child.isComputerTurn = !child.isComputerTurn;
		child.nOpenPosition = 0;
		while ((child.nOpenPosition < boardSize) && (child.board[child.nOpenPosition] != '_')){
			child.nOpenPosition++;
		}
		
		return child;
	}

	@Override
	public double staticEvaluation() {
		boolean hasSomeoneWon = hasVerticalWin() || hasHorizontalWin() || hasDiagonalWin();
		if (isComputerTurn && hasSomeoneWon) {
			return -1.0;
		} else if (!isComputerTurn && hasSomeoneWon) {
			return 1.0;
		} else {
			return 0.0;
		}
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.white);
		g.drawLine(100, 0, 100, 300);
		g.drawLine(200, 0, 200, 300);
		g.drawLine(0, 100, 300, 100);
		g.drawLine(0, 200, 300, 200);
		
		try {
			File xImageFile = new File("X2.png");
			BufferedImage xImage = ImageIO.read(xImageFile);

			File oImageFile = new File("O.png");
			BufferedImage oImage = ImageIO.read(oImageFile);
			
			//runs through every square on the board, drawing x's and o's on the screen when necessary
			for (int i = 0; i < board.length; i++) {
				if (board[i] == 'X') {
					g.drawImage(xImage, (i % 3) * 100 + 5, (i / 3) * 100 + 5, 90, 90, null);					
				} else if (board[i] == 'O') {
					g.drawImage(oImage, (i % 3) * 100 + 5, (i / 3) * 100 + 5, 90, 90, null);
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isComputerWinner() {
		return (!this.isComputerTurn && (hasVerticalWin() || hasHorizontalWin() || hasDiagonalWin()));
	}

	@Override
	public boolean isDraw() {
		boolean gameFinished = true;
		for(int i = 0; i < this.board.length; i++){
			if(this.board[i] == '_'){
				gameFinished = false;
			}
		}
		return(gameFinished && !hasVerticalWin() && !hasHorizontalWin() && !hasDiagonalWin());
	}

	@Override
	public boolean isUserWinner() {
		return (this.isComputerTurn && (hasVerticalWin() || hasHorizontalWin() || hasDiagonalWin()));
	}

	@Override
	public void placeUserMove(Point2D mouseLocation) throws Exception {
		int row = (int)mouseLocation.getY() / 100;
		int col = (int)mouseLocation.getX() / 100;
		
		int boardIndex = row * 3 + col;
		
		//throw an exception if row or col are not between 0 and 2, or if a move has already been made at the chosen square
		if (row > 2 || col > 2 || row < 0 || col < 0 || board[boardIndex] == 'O' || board[boardIndex] == 'X') {
			throw new Exception();
		}
		
		this.board[boardIndex] = 'O';
		
		this.isComputerTurn = !this.isComputerTurn;
		
		this.nOpenPosition = 0;
		while (this.nOpenPosition < boardSize && this.board[this.nOpenPosition] != '_') {
			this.nOpenPosition++;
		} 
	}
	

	private boolean hasDiagonalWin() {
		boolean hasWon = false;

		char upperLeft = this.board[0];
		char upperRight = this.board[2];
		char center = this.board[4];
		char lowerLeft = this.board[6];
		char lowerRight = this.board[8];
		
		
		if ((upperLeft != '_' && upperLeft == center && upperLeft == lowerRight) || 
			(upperRight != '_' && upperRight == center && upperRight == lowerLeft)) {
			hasWon = true;
		}
		
		return hasWon;
	}

	private boolean hasHorizontalWin() {
		boolean hasWon = false;
		int rowLen = (int)Math.sqrt(boardSize);
		char left, middle, right;
		
		
		for (int i = 0; i < rowLen; i++) {
			left = this.board[i * rowLen];
			middle = this.board[i * rowLen + 1];
			right = this.board[i * rowLen + 2];
			if (left != '_' && middle == left && right == left) {
				hasWon = true;
			}
		}
		return hasWon;	
	}

	private boolean hasVerticalWin() {
		boolean hasWon = false;
		int rowLen = (int)Math.sqrt(boardSize);
		char top, middle, bottom;


		for (int i = 0; i < rowLen; i++) {
			top = this.board[i];
			middle = this.board[i + rowLen];
			bottom = this.board[i + 2 * rowLen];
			if (top != '_' && middle == top && bottom == top) {
				hasWon = true;
			}
		}
		return hasWon;
	}

	
	public String toString() {
		String boardString = "";
		int rowLen = (int)Math.sqrt(boardSize);
		
		for (int i = 0; i < boardSize; i++) {
			boardString += board[i] + "\t";
			
			//we have reached the end of a row, go to a new line
			if ((i + 1) % rowLen == 0) {
				boardString += "\n";
			}
		}
		return boardString;
	}

}
