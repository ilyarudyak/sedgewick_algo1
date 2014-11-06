package part1.ass3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Fast {

	private Point[] originalPoints;
	private Point[] points;
	private List<List<Point>> collinears;
	
	private void read() {
		
        In in = new In("data/rs1423.txt");
        int n = in.readInt();
        points = new Point[n];
        originalPoints = new Point[n];
        
        int x, y, i = 0;
        while (!in.isEmpty()) {
            x = in.readInt();
            y = in.readInt();
            points[i++] = new Point(x, y);
        }
        
        originalPoints = Arrays.copyOf(points, points.length);
        collinears = new ArrayList<>();
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        draw();
	}
	
    private void draw(){

    	for (Point p : points) {
			p.draw();
		}
    }
	
	private void findCollinears() {
		
		for (Point p: originalPoints) 
			find(p);
	}
	
	private void find(Point p0) {
		
		// sort array by slope to point p 
		Arrays.sort(points, p0.SLOPE_ORDER);
//		System.out.print(Arrays.toString(points));
		
		Double slope = p0.slopeTo(p0);
		int i0 = 0, j0 = 0;
		int count = 1;
		for (int i = 1; i < points.length; i++) {
			
			Point p = points[i];
				
			if (Double.valueOf(p.slopeTo(p0)).equals(slope) ) {
				count++;
			}
			else if (!Double.valueOf(p.slopeTo(p0)).equals(slope)
					&& count >= 3){
				j0 = i - 1;
				addPoints(i0, j0, p0);
				count = 1;
				i0 = 0;
				j0 = 0;
			}
			else if (!Double.valueOf(p.slopeTo(p0)).equals(slope)){
				slope = p.slopeTo(p0);
				count = 1;
			}
			
			if (count == 3)
				i0 = i - 2;
			
		}
		if (count >= 3 && j0 == 0)
			j0 = points.length - 1;
		
//		System.out.println(" i0=" + i0 + " j0=" + j0);
		if (j0 > 0)
			addPoints(i0, j0, p0);
		
	}

	private void addPoints(int i0, int j0, Point p0) {
		
		if (j0 > 0) {
			
			List<Point> list0 = new ArrayList<>();
			list0.add(p0);
			for (int i = i0; i <= j0; i++) {
				list0.add(points[i]);
			}
			
			Collections.sort(list0);
			
			
			
			/*Iterator it = collinears.iterator();
			while (it.hasNext()) {
				List<Point> list = (List<Point>) it.next();
				if (list.containsAll(list0))
					return;
				else if (list0.containsAll(list))
					it.remove();
			}
			for (List<Point> list : collinears) {
				if (list.containsAll(list0))
					return;
			}*/
				
			if (p0.compareTo(list0.get(0)) == 0) 
				collinears.add(list0);
		}
	}
	

	private void printAllPoints(){
		for (List<Point> list: collinears)
			printPoints(list);
	}
	
	private void printPoints(List<Point> list) {
		
		for (int i = 0; i < list.size() - 1; i++) {
			StdOut.print(list.get(i) + " -> ");
		}
		StdOut.println(list.get(list.size() - 1));
		
		list.get(list.size() - 1).drawTo(list.get(0));
	}

	
	public static void main(String[] args) {
		
		Fast f = new Fast();
		f.read();
//		System.out.println(Arrays.toString(f.points));
		
		f.findCollinears();
		f.printAllPoints();
	}

}


















