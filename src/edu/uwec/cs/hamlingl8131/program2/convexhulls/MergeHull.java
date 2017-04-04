package edu.uwec.cs.hamlingl8131.program2.convexhulls;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/*
 * Authors: George Hamlin and Michael Phillips
 */
public class MergeHull implements ConvexHullFinder {

	class pointComparator implements Comparator<Point2D>{
		public int compare(Point2D p1, Point2D p2){
			if(p1.getX() < p2.getX()){
				return -1;
			} else if (p1.getX() > p2.getX()){
				return 1;
			}else if(p1.getY() < p2.getY()){
				return -1;
			}else if(p1.getY() > p2.getY()){
				return 1;
			}else
				return 0;
		}
	}
	@Override
	public List<Point2D> computeHull(List<Point2D> points) {
		List<Point2D> hull = new ArrayList<Point2D>();
		Collections.sort(points, new pointComparator());
		
		/*Removes any double points*/
		for(int i =0; i < points.size()-1; i++){
			if(points.get(i).equals(points.get(i+1))){
				points.remove(i+1);
				i--;
			}
		}
		
		hull = mergeRecursive(points);
		return hull;
	}
	private List<Point2D> mergeRecursive(List<Point2D> points) {
		//System.out.println("Got to mergeRecursive.");
		if (points.size()<= 2){
			return points;
		}
		/*cut points in half creating left and right hull areas*/
		List<Point2D> leftHull = mergeRecursive(points.subList(0, points.size()/2));
		List<Point2D> rightHull = mergeRecursive(points.subList(points.size()/2, points.size()));
		
		return mergeHulls(leftHull, rightHull);
	}
	private List<Point2D> mergeHulls(List<Point2D> leftHull, List<Point2D> rightHull) {
		//System.out.println("Got to MergeHulls");
		List<Point2D> hullPoints = new ArrayList<Point2D>();
		
		int rPointLHull = findRightPoint(leftHull);
		int lPointRHull = findLeftPoint(rightHull);
		
		
		int[] upperTan = findTangent(rightHull, leftHull, lPointRHull, rPointLHull);
		int[] lowerTan = findTangent(leftHull, rightHull, rPointLHull, lPointRHull);
		
		int i = upperTan[1];
		
		while(i != lowerTan[0]){		
	
			hullPoints.add(leftHull.get(i));
			i = nextIndex(leftHull, i);
		}
		//System.out.println("Got past add upper");
		hullPoints.add(leftHull.get(lowerTan[0]));
		i = lowerTan[1];
		while(i != upperTan[0]){
			hullPoints.add(rightHull.get(i));
			i = nextIndex(rightHull, i);
		}
		//System.out.println("Got past add lower");
		hullPoints.add(rightHull.get(upperTan[0]));
		//System.out.println("Done with mergeHulls");
		return hullPoints;
	}
	private int[] findTangent(List<Point2D> leftHull, List<Point2D> rightHull,
											int rPointLHull, int lPointRHull) {
		//System.out.println("Got to findTangent.");
		//System.out.println("Line 89.");
		
		int[] tPoints = {rPointLHull, lPointRHull};
		Line2D tan = new Line2D.Double(leftHull.get(tPoints[0]), rightHull.get(tPoints[1]));
		
		//System.out.println("Line 91");
		boolean again;
		do{
			//System.out.println("Inside of while loop. Line 94");
			again = false;
			while(tan.relativeCCW(leftHull.get(nextIndex(leftHull, tPoints[0]))) > 0 || 
					tan.relativeCCW(leftHull.get(prevIndex(leftHull, tPoints[0]))) > 0){
				//System.out.println("Line 97");
				tPoints[0] = prevIndex(leftHull, tPoints[0]);
				again = true;
				tan = new Line2D.Double(leftHull.get(tPoints[0]) , rightHull.get(tPoints[1]));
			}
			
			while(tan.relativeCCW(rightHull.get(nextIndex(rightHull, tPoints[1]))) > 0 || 
					tan.relativeCCW(rightHull.get(prevIndex(rightHull, tPoints[1]))) > 0){
				//System.out.println("Line 105");
				tPoints[1]= nextIndex(rightHull, tPoints[1]);
				again = true;
				tan = new Line2D.Double(leftHull.get(tPoints[0]), rightHull.get(tPoints[1]));
			}
		}while(again);
		//System.out.println("Got done with find tangent");
		return tPoints;
	}
	private int prevIndex(List<Point2D> points, int i) {
		i +=(points.size()-1);
		i %= points.size();
		return i;
	}
	private int nextIndex(List<Point2D> points, int i) {
		i++;
		i %= points.size();
		return i;
	}
	private int findLeftPoint(List<Point2D> rightHull) {
		//System.out.println("Inside find left point");
		
		Point2D leftPoint = new Point2D.Double(Double.MIN_VALUE, 0.0);
		int index = 0, i = 0;
		while(i < rightHull.size()){
			if(rightHull.get(i).getX()> leftPoint.getX()){
				index = i;
				
			}
			i++;
		}
		return index;
	}

	
	private int findRightPoint(List<Point2D> leftHull) {
		//System.out.println("Inside find right point");
		
		Point2D rightPoint = new Point2D.Double(Double.MIN_VALUE, 0.0);
		int index = 0, i = 0;
		while(i < leftHull.size()){
			if(leftHull.get(i).getX()> rightPoint.getX()){
				
				index = i;
				
			}
			i++;
		}
		return index;
	}

}
