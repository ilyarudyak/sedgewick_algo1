package part1.ass5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

public class KdTree {
	
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

	private Node root;
	private int size;
	
	private Point2D pmin;
	private double dmin;
	
	// construct an empty set of points
	public KdTree() {
		root = null;
		size = 0;
	}
	   
	// is the set empty?   
	public boolean isEmpty() {
		return size == 0;
	}
	   
	// number of points in the set   
	public int size() {
		return size;
	}
	   
	// add the point p to the set (if it is not already in the set)   
	public void insert(Point2D p) {
		size++;
		root = insert(root, p, VERTICAL, null);
	}
	   
	private Node insert(Node node, Point2D p, boolean t, Node parent) {
		
		if (node == null) {
			if (parent == null)
				return new Node(p, new RectHV(0,0,1,1), t);
			else {
				
				double xmin = parent.rect.xmin();
				double ymin = parent.rect.ymin();
				double xmax = parent.rect.xmax();
				double ymax = parent.rect.ymax();
				
				if (parent.type == VERTICAL) {
					
					if (p.x() < parent.p.x())
						return new Node(p, new RectHV(xmin,         ymin,         parent.p.x(), ymax        ), t);
					else
						return new Node(p, new RectHV(parent.p.x(), ymin,         xmax,         ymax        ), t);
				}
				
				else { // parent.type == HORIZONTAL
					if (p.y() < parent.p.y())
						return new Node(p, new RectHV(xmin,         ymin,         xmax,         parent.p.y()), t);
					else 
						return new Node(p, new RectHV(xmin,         parent.p.y(), xmax,         ymax        ), t);
				}
			}
				
		}
		
		if (comp(node, p) == -1)
			node.lb = insert(node.lb, p, !t, node);
		else if (comp(node, p) == 0) {
			if (node.p.equals(p)) {
				size--;
				return node;
			}
			else
				node.rt = insert(node.rt, p, !t, node);
		}
		else	
			node.rt = insert(node.rt, p, !t, node);

		
		return node;
	}
	
	private int comp(Node node, Point2D p) {
		
		if (node.type == VERTICAL) {
			if (p.x() < node.p.x())
				return -1;
			else if (p.x() == node.p.x())
				return 0;
			else
				return 1;
		}
		else {
			if (p.y() < node.p.y())
				return -1;
			else if (p.y() == node.p.y())
				return 0;
			else
				return 1;
		}
	}
	
	// does the set contain the point p?   
	public boolean contains(Point2D p) {
		return contains(root, p);
	}
	
	private boolean contains(Node node, Point2D p) {
		
		if (node == null)
			return false;
		
		if (comp(node, p) == 0) {
			if (node.p.equals(p))
				return true;
			else
				return contains(node.rt, p);
		}
		
		else if (comp(node, p) == -1)
			return contains(node.lb, p);
		else 
			return contains(node.rt, p);
	}
	
	   
	// draw all of the points to standard draw   
	public void draw() {
		
		draw(root);
	}
	
	private void draw(Node node) {
		
		if (node == null)
			return;
		
		draw(node.lb);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(node.p.x(), node.p.y());
		StdDraw.setPenRadius();
		
		if(node.type == VERTICAL) {
			
			double ymin = node.rect.ymin();
			double ymax = node.rect.ymax();
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.p.x(), ymin, node.p.x(), ymax);
		}
		
		else {
			
			double xmin = node.rect.xmin();
			double xmax = node.rect.xmax();
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(xmin, node.p.y(), xmax, node.p.y());
		}
			
		draw(node.rt);
	}
	   
	// all points in the set that are inside the rectangle   
	public Iterable<Point2D> range(RectHV rect) {
		
		return range(rect, root);
	}
	   
	private SET<Point2D> range(RectHV rect, Node node) {
		
		SET<Point2D> set = new SET<>();
		if (node == null)
			return set;
		
		if (rect.intersects(node.rect)) {
			if (rect.contains(node.p))
				set.add(node.p);
			set = set.union(range(rect, node.lb));
			set = set.union(range(rect, node.rt));
		}
		
		return set;
	}
	
	// a nearest neighbor in the set to p; null if set is empty   
	public Point2D nearest(Point2D p) {
		
		if (isEmpty())
			return null;
		
		pmin = root.p;
		dmin = p.distanceSquaredTo(pmin);
		nearest(p, root);
		return pmin;
	}
	
	private void nearest(Point2D p, Node node) {
		
		if (node == null)
			return;
		
		// if the closest point discovered so far (p0) is closer than the distance 
		// between the query point (pq) and the rectangle corresponding to a node, 
		// there is no need to explore that node
		if (node.rect.distanceSquaredTo(p) >= dmin)
			return;
		
		else {
			
			double d = p.distanceSquaredTo(node.p);
			if (d < dmin) {
				
				// update pmin and dmin
				dmin = d;
				pmin = node.p;
			}
			
			// always choose the subtree that is on the same side of the splitting
			// line as the query point as the first subtree to explore
			if (comp(node, p) <= 0) {
				nearest(p, node.lb);
				nearest(p, node.rt);
			}
			
			else {
				nearest(p, node.rt);
				nearest(p, node.lb);
			}
		}
	}// end of nearest
	

	
	private static class Node {
		
		// the point
		private Point2D p;      
		// the axis-aligned rectangle corresponding to this node   
		private RectHV rect;    
		// the left/bottom subtree   
		private Node lb;        
		// the right/top subtree   
		private Node rt;
		// orientation
		private boolean type;
		
		public Node(Point2D p, RectHV rect, boolean type) {
			this.p = p;
			this.rect = rect;
			this.type = type;
		}
		
		public String toString() {
			
			if (type == HORIZONTAL)
				return "[" + p + ", H]";
			else
				return "[" + p + ", V]";
		}
		
		}

	public static void main(String[] args) {

		KdTree kdt = new KdTree();
		In in = new In("data/ass5/input10K.txt");
		while (!in.isEmpty()) {
			kdt.insert(new Point2D(in.readDouble(), in.readDouble()));
		}
		
//		RectHV r = new RectHV(0.7, 0, 1.0, 0.4);
//		StdDraw.square(0.5, 0.5, 0.5);;
//		kdt.draw();
//		System.out.println(kdt.range(r).size());
		
		Point2D p = new Point2D(0, 0);
		System.out.println(kdt.nearest(p));
//		System.out.println(kdt.size());
	}

}
















