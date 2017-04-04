package edu.uwec.cs.hamlingl8131.assingment5.gametree;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public interface TwoPlayerGameBoard {
	public boolean hasMoreChildren(); //returns true if there are more children
	public TwoPlayerGameBoard nextChild(); //returns next feasible child (combination of isFeasible and NextChild
	public double staticEvaluation(); //Evaluates the board and returns -1, 0, or +1
	
	/** Methods for drawing **/
	public void draw(Graphics g); //Draws the board
	
	/** Method for gameplay **/
	public boolean isComputerWinner(); //returns true if the computer wins
	public boolean isDraw(); //returns true if neither user or computer wins
	public boolean isUserWinner(); //returns true if the player wins
	
	/** Method for handling user input **/
	public void placeUserMove(Point2D mouseLocation) throws Exception;
}
