package edu.uwec.cs.stevende.btframework;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		BacktrackerIterative bt = new BacktrackerIterative();
		
		State s = new NQueensState(4);
		
		State result = bt.backtrack(s);
		
		System.out.println(result);
	}

}
