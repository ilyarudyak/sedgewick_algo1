package part1.ass5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.introcs.In;

public class PointSET {

	private SET<Point2D> set;
	
	// construct an empty set of points
	public PointSET() {
		set = new SET<>();
	}
	   
	// is the set empty?   
	public boolean isEmpty() {
		return set.isEmpty();
	}
	   
	// number of points in the set   
	public int size() {
		return set.size();
	}
	   
	// add the point p to the set (if it is not already in the set)   
	public void insert(Point2D p) {
		set.add(p);
	}
	   
	// does the set contain the point p?   
	public boolean contains(Point2D p) {
		return set.contains(p);
	}
	   
	// draw all of the points to standard draw   
	public void draw() {
		for (Point2D p : set) {
			p.draw();
		}
	}
	   
	// all points in the set that are inside the rectangle   
	public SET<Point2D> range(RectHV rect) {
		
		SET<Point2D> s = new SET<>();
		for (Point2D p : set) {
			if (rect.contains(p))
				s.add(p);
		}
		return s;
	}
	   
	// a nearest neighbor in the set to p; null if set is empty   
	public Point2D nearest(Point2D p) {
		
		if (isEmpty())
			return null;
		else {
			Point2D np = set.min();
			double d = p.distanceTo(np);
			for (Point2D point : set) {
				double d0 = p.distanceTo(point);
				if (d0 < d) {
					np = point;
					d = d0;
				}
			}
			return np;
		}
	}

	public static void main(String[] args) {
		
		PointSET ps = new PointSET();
		In in = new In("data/ass5/circle100.txt");
		while (!in.isEmpty()) {
			ps.set.add(new Point2D(in.readDouble(), in.readDouble()));
		}
//		ps.draw();
		
		Point2D p0 = new Point2D(0, 0);
		System.out.println(ps.nearest(p0));
		
//		RectHV r = new RectHV(0.7, 0, 1.0, 0.4);
//		r.draw();
//		System.out.println(ps.range(r).size());
	}

}














