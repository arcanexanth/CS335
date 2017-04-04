package edu.uwec.cs.hamlingl8131.assignment4.graphcoloring;

import java.util.ArrayList;
import java.util.List;

public class GraphColoringState implements State {
	/* Class Variables */
	private boolean[][] graph;
	private int[] nodeColor; /** Color on Node **/
	private List<WeightedColor> colorsAndWeights; /** Potential colors **/
	private int nextNode;
	private int nextColor;
	private int lowestWeight;
	
	/** Constructor **/
	/** Pair colors and their weights **/
	public GraphColoringState(boolean[][] graph, List<WeightedColor> colorsAndWeights) {
		this.graph = graph;
		this.nodeColor = new int[graph.length];
		for(int i = 0; i < this.nodeColor.length; i++){
			this.nodeColor[i] = -1; /** Impossible color **/
		}
		this.colorsAndWeights = colorsAndWeights;
		this.lowestWeight = Integer.MAX_VALUE; /** Terrible & Impossible Weight **/
		for(WeightedColor weightedColor : colorsAndWeights){
			if(weightedColor.getWeight() < lowestWeight){
				lowestWeight = weightedColor.getWeight();
			}
		}
		this.nextNode = 0;
		this.nextColor = 0;
		
	}
	
	/** Copy Constructor **/
	public GraphColoringState(GraphColoringState orig){
		this.graph = new boolean[orig.graph.length][orig.graph.length];
		for(int i = 0; i < orig.graph.length; i++){
			for(int j = 0; j < orig.graph.length; j++){
				this.graph[i][j] = orig.graph[i][j];
			}
		}
		
		this.nodeColor = new int [orig.nodeColor.length];
		for(int i = 0; i < orig.nodeColor.length; i++){
			this.nodeColor[i] = orig.nodeColor[i];
		}
		
		this.colorsAndWeights = new ArrayList<WeightedColor>();
		for(WeightedColor weightedColor : orig.colorsAndWeights){
			this.colorsAndWeights.add(weightedColor);
		}
		this.lowestWeight = orig.lowestWeight;
		this.nextNode = orig.nextNode;
		this.nextColor = orig.nextColor;
	}
	

	@Override
	public boolean hasMoreChildren() {
		boolean hasMore = (this.nextColor < this.colorsAndWeights.size());
		return hasMore;
	}
	
	/** returns the next child **/
	@Override
	public State nextChild() {
		//Set up child//
		GraphColoringState child = new GraphColoringState(this);
		child.nodeColor[this.nextNode] = this.nextColor;
		this.nextColor++;
		
		//prep the next child//
		child.nextNode++;
		child.nextColor = 0;
		return child;
	}

	@Override
	public boolean isFeasible() {
		boolean isFeasible = true;
		int currentNode = this.nextNode - 1;
		for(int i = 0; i < this.graph.length; i++){
			if(i != currentNode && this.graph[currentNode][i] == true && this.nodeColor[currentNode] == this.nodeColor[i]){
				isFeasible = false;
			}
		}
		return isFeasible;
	}

	@Override
	public boolean isSolved() {
		boolean isSolved = (this.nextNode == this.graph.length);
		return isSolved;
	}

	@Override
	public int getBound() {
		int bound = 0;
		int currentNode = this.nextNode - 1;
		for(int i = currentNode; i >= 0; i--){
			bound += this.colorsAndWeights.get(this.nodeColor[i]).getWeight();
		}
		bound += (this.nodeColor.length - this.nextNode) * this.lowestWeight;
		return bound;
	}

	@Override
	public int getLevel() {
		int level = this.nextNode;
		return level;
	}
	
	/** print the tree **/
	public String toString(){
		String tree = "(";
		
		for(int nodeColorIndex : nodeColor){
			/** States that there is no color **/
			if(nodeColorIndex == -1){
				
				tree += "null ";
				
			}else{
				
				tree += this.colorsAndWeights.get(nodeColorIndex).getType() + ", " + this.colorsAndWeights.get(nodeColorIndex).getWeight() + " ";
			}
		}
		
		tree += ")";
		return tree;
	}
}
