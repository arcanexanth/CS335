package edu.uwec.cs.stevende.btframework;

public interface State {

	public boolean hasMoreChildren();
	public State nextChild();
	public boolean isFeasible();
	public boolean isSolved();
	public int getBound();
	
}
