package edu.uwec.cs.hamlingl8131.assignment4.graphcoloring;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.sql.StatementEventListener;

public class BacktrackerIterative {
	
	private int numberExpanded = 0;
	private boolean beenPruned = false;
	public State backtrack(State s) {
		
		State soln = null;
		
		int bestSolnCost = Integer.MAX_VALUE;
		
		List<State> statesToProcess = new LinkedList<State>();
		statesToProcess.add(0, s);  // push
		
		while (!statesToProcess.isEmpty()) {
			
			System.out.println(statesToProcess);
			
			// Pop a feasible state from the list
			State currentState = statesToProcess.remove(0);
			numberExpanded++;
			
			if (currentState.isSolved()) {
				
				System.out.println("Solved: " + currentState);
				
				if (currentState.getBound() < bestSolnCost) {
					bestSolnCost = currentState.getBound();
					soln = currentState;
				}
				//result.add(s);
				
			} else {
							
				while((currentState.hasMoreChildren()) &&
				      (currentState.getBound() < bestSolnCost)) { //&& result.isEmpty()) {
						
					State child = currentState.nextChild();
				
					if (child.isFeasible()) {
						statesToProcess.add(child);
					}	
				}
			}
		}
		return soln;
	}
	/** Depth Search. Ha! Just discovered blue comments **/
	/** Is a stack **/
	public State backTrackDepth(State s) {
		State solution = null;
		//Set to some terrible solution. Should be impossible to hit.
		int bestSolutionCost = Integer.MAX_VALUE; 
		List<State> statesToProcess = new LinkedList<State>();
		statesToProcess.add(0, s);
		
		/** run through tree **/
		while (!statesToProcess.isEmpty()){
			
			State currentState = statesToProcess.remove(0);
			numberExpanded++;
				
			if(currentState.getBound() < bestSolutionCost){
				if(currentState.isSolved()){
					bestSolutionCost = currentState.getBound();
					solution = currentState;
				}else{
					while(currentState.hasMoreChildren()){
						State child = currentState.nextChild();
						if(child.isFeasible()){
							statesToProcess.add(0, child);
						}
					}
				}
			}
		}
		System.out.println("Total Nodes Expanded: " + numberExpanded);
		System.out.println(solution);
		return solution;
	}
	/** Breadth Search **/
	/** Is a queue **/
	public State backTrackBreadth(State s) {
		State solution = null;
		int bestSolutionCost = Integer.MAX_VALUE;
		List<State> statesToProcess = new LinkedList<State>();
		statesToProcess.add(0, s);
		
		while(!statesToProcess.isEmpty()){
			
			State currentState = statesToProcess.remove(0);
			numberExpanded++;
			
			if(currentState.getBound() < bestSolutionCost){
				if(currentState.isSolved()){
					bestSolutionCost = currentState.getBound();
					solution = currentState;
				}else{
					while(currentState.hasMoreChildren()){
						State child = currentState.nextChild();
						if(child.isFeasible()){
							/** Tack onto the end like a queue **/
							statesToProcess.add(child);
						}
					}
				}
			}
		} 
		System.out.println("Total Nodes Expanded: " + numberExpanded);
		System.out.println(solution);
		return solution;
	}
	/** Best Search **/
	/** Mix of both depth and breadth **/
	public State backTrackBest(State s) {
		State solution = null;
		int bestSolutionCost = Integer.MAX_VALUE;
		List<State> statesToProcess = new LinkedList<State>();
		statesToProcess.add(0, s);
		
		while(!statesToProcess.isEmpty() && beenPruned == false){
			
			State currentState = statesToProcess.remove(0);
			numberExpanded++;
			
			if(currentState.getBound() < bestSolutionCost){
				if(currentState.isSolved()){
					bestSolutionCost = currentState.getBound();
					solution = currentState;
				}else{
					while(currentState.hasMoreChildren()){
						State child = currentState.nextChild();
						if(child.isFeasible()){
							bestAdd(statesToProcess, child);
						}
					}
				}
			}else{
				beenPruned = true;
			}
		}
		System.out.println("Total Nodes Expanded: " + numberExpanded);
		System.out.println(solution);
		return solution;
	}
	
	public static void bestAdd(List<State> states, State state){
		StateComparator comparator = new StateComparator();
		
		int index = Collections.binarySearch(states, state, comparator);
		
		if(index < 0){
			index = ~index; 
		}
		states.add(index, state);
	}
}

class StateComparator implements Comparator<State>{

	@Override
	public int compare(State o1, State o2) {
		if(o1.getBound() > o2.getBound()){
			return 1;
		}else if(o1.getBound() < o2.getBound()){
			return -1;
		}else{
			if(o1.getLevel() < o2.getLevel()){
				return 1;
			}else if(o1.getLevel() > o2.getLevel()){
				return -1;
			}else{
				return 0;
			}
		}
	}
}