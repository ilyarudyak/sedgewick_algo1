package part1.ass3;

/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.introcs.StdDraw;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder(); // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {

    	// degenerate line segment (between a point and itself)
    	if (this.x == that.x && this.y == that.y)	return Double.NEGATIVE_INFINITY;
    		
    	// horizontal line
    	else if (this.y == that.y) 					return 0.0;
    	
    	// vertical line
    	else if (this.x == that.x)					return Double.POSITIVE_INFINITY;
    	
    	// normal case
    	else 										return (double) (that.y - this.y) 
    																/ (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) 							return -1;
        else if (this.y == that.y && this.x < that.x) 	return -1;
        else if (this.y == that.y && this.x == that.x)	return  0;
        else /*this.y > that.y*/						return  1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class SlopeOrder implements Comparator<Point> {
  	    public int compare(Point q, Point r) {
  	    	Point p = new Point(x, y);
  	    	if 		(p.slopeTo(q) < p.slopeTo(r))			return -1;
  	    	else if (p.slopeTo(q) == p.slopeTo(r))			return  0;
  	    	else 											return  1;
       }
	}

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}// end of class
















