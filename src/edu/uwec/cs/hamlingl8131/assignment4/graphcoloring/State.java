package edu.uwec.cs.hamlingl8131.assignment4.graphcoloring;

public interface State {

	public boolean hasMoreChildren();
	public State nextChild();
	public boolean isFeasible();
	public boolean isSolved();
	public int getBound();
	public int getLevel();
	
}
