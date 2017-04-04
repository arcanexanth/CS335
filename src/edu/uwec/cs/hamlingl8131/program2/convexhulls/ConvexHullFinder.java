/**
 * 
 */
package edu.uwec.cs.hamlingl8131.program2.convexhulls;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @author George
 * @Function: Switches the algorithms between quick and merge
 */
public interface ConvexHullFinder {
	public List<Point2D> computeHull( List<Point2D> points);	
}
