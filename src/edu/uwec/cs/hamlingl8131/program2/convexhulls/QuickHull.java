package edu.uwec.cs.hamlingl8131.program2.convexhulls;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class QuickHull implements ConvexHullFinder {
	/*
	 * Auhors: George Hamlin & Michael Phillips
	 * Take a look at this mess! It's the best!
	 */

	@Override
	public List<Point2D> computeHull(List<Point2D> points) {
		List<Point2D> hull = new ArrayList<Point2D>();
		
		Point2D leftPoint = findLeft(points);
		//System.out.println("Left point is: " + leftPoint);
		Point2D rightPoint = findRight(points);
		//System.out.println("right point is: " + rightPoint);
		
		Line2D lineAB = new Line2D.Double(leftPoint, rightPoint);
		List<Point2D> top = getPoints(points, lineAB);
		
		Line2D lineBA = new Line2D.Double(rightPoint, leftPoint);
		List<Point2D> bottom = getPoints(points, lineBA);

		
		List<Point2D> hullT = quickRecursive(lineAB, points);
//		for(int i = 0; i < hullT.size(); i++){
//			System.out.println("HullT is: " + hullT.get(i));
//		}
		
		List<Point2D> hullB = quickRecursive(lineBA, points);
//		for(int i = 0; i < hullB.size(); i++){
//			System.out.println("HullB is:" + hullB.get(i));
//		}
		
		hullB.remove(0);
		hullT.addAll(hullB);
		hullT.remove(hullT.size()-1);
		//System.out.println("HullT size is: "+ hullT.size());
		hull.addAll(hullT);
		return hull;
	}
	
	private List<Point2D> quickRecursive(Line2D line, List<Point2D> points) {
		
		List<Point2D> oPoints = outerMost(line, points);
		
		/*
		 * If only two points so no possible C 
		 */
		if(oPoints.size() == 0){
			System.out.println("Got into basecase");
			ArrayList<Point2D> hull = new ArrayList();
			hull.add(line.getP1());
			hull.add(line.getP2());
			return hull;
		}
		
		Point2D c = findC(line, oPoints);
		System.out.println("C is : " + c);
		List<Point2D> halfH = quickRecursive(new Line2D.Double(line.getP1(), c) , oPoints);
		List<Point2D> halfH2 = quickRecursive(new Line2D.Double(c, line.getP2()), oPoints);
		
		halfH2.remove(0);
		halfH.addAll(halfH2);
		return halfH;
		
		
	}

	private Point2D findC(Line2D line, List<Point2D> points) {
		Point2D c =  points.get(0);
		for(Point2D point : points)
			if(line.ptLineDist(point) > line.ptLineDist(c))
				c = point;
		return c;
		
	}

	private List<Point2D> outerMost(Line2D line, List<Point2D> points) {
		
		ArrayList<Point2D> outerPoints = new ArrayList();
		for (Point2D point : points){
			if(line.relativeCCW(point)>0){
				outerPoints.add(point);
			}
		}
//		for(int i = 0; i < outerPoints.size(); i++){
//			System.out.println("OuterPoints are :" + outerPoints.get(i));
//		}
		return outerPoints ;
	}

	private List<Point2D> getPoints(List<Point2D> points, Line2D line) {
		List<Point2D> vals = new ArrayList<Point2D>();
		for (Point2D point : points){
			if(line.relativeCCW(point) == 1){
				vals.add(point);
			}
		}
		return vals;
	}

	/*
	 * Finds rightmost point
	 */
	private Point2D findRight(List<Point2D> points) {
		Point2D rightPoint = new Point2D.Double(Double.MIN_VALUE, 0);
		for (Point2D point : points){
			if(point.getX() > rightPoint.getX()){
				rightPoint = point;
			}
		}
		return rightPoint;
	}
	
	/*
	 * Finds leftmost point
	 */
	private Point2D findLeft(List<Point2D> points) {
		Point2D leftPoint = new Point2D.Double(Double.MAX_VALUE, 0);
		for (Point2D point : points){
			if(point.getX()<leftPoint.getX()){
				leftPoint = point;
			}
		}
		return leftPoint;
	}

}
