package edu.uwec.cs.hamlingl8131.assignment4.graphcoloring;

public class WeightedColor {
	private String type;
	private int weight;
	
	public WeightedColor(String type, int weight){
		this.type = type;
		this.weight = weight;
	}
	
	public String getType(){
		return type;
	}
	
	public int getWeight(){
		return weight;
	}
}
