package edu.uwec.cs.hamlingl8131.assignment4.graphcoloring;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		BacktrackerIterative bt = new BacktrackerIterative(); 
		// Define the graph from the ppt 
		boolean[][] graph =  
		{{false, true, false, false, false, true}, 
		 { true, false, true, false, false, true}, 
		 {false, true, false, true, true, false}, 
		 {false, false, true, false, true, false}, 
		 {false, false, true, true, false, true}, 
		 { true, true, false, false, true, false}}; 
		
		boolean[][] threeNode = {{true, true, true}, {true, true, true}, {true, true, true}};
		
		// Define the colors used in the ppt 
		List<WeightedColor> colors = new ArrayList<WeightedColor>(); 
		colors.add(new WeightedColor("Red", 2)); 
		colors.add(new WeightedColor("Green", 3)); 
		colors.add(new WeightedColor("Blue", 5));
		colors.add(new WeightedColor("Yellow", 2));
		
		State s = new GraphColoringState(threeNode, colors);
		//State depth = bt.backTrackDepth(s);
		//State breadth = bt.backTrackBreadth(s);
		State best = bt.backTrackBest(s); 
		
		//System.out.println(depth);
		//System.out.println(breadth);
		System.out.println(best);
	}

}
