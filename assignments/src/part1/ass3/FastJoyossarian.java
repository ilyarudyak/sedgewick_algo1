package part1.ass3;

import java.util.Arrays;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class FastJoyossarian {
	
	private Point[] points;
	private Point[] sortedPoints;
	
	private static void drawLineSeg(Point[] segPoints) {
		String output = new String();
		int len = segPoints.length;
		for (int i = 0; i < len; ++i) {
			output += segPoints[i].toString();
			if (i != len - 1) {
				output += " -> ";
			}
//			segPoints[i].draw();
		}
		StdOut.println(output);
//		segPoints[0].drawTo(segPoints[len - 1]);
	}

	private void outputSeg(Point p0,
			int startIndex, int adj) {
		Point[] segPoints = new Point[adj + 1];
		for (int i = 0; i < adj; ++i) {
			segPoints[i] = sortedPoints[startIndex + i];
		}
		segPoints[adj] = p0;
		Arrays.sort(segPoints);
		if (p0.compareTo(segPoints[0]) == 0)
			drawLineSeg(segPoints);
	}

	private  void checkSeg(Point p0) {
		double lastSlope = p0.slopeTo(sortedPoints[1]);
		int adj = 1;
		int startIndex = 1;
		for (int i = 2; i < sortedPoints.length; ++i) {
			double curSlope = p0.slopeTo(sortedPoints[i]);
			if (curSlope == lastSlope) {
				++adj;
			} else if (p0.compareTo(sortedPoints[i]) == 0) {
				++adj;
			} else if (adj >= 3) {
				// output seg
				outputSeg(p0, startIndex, adj);
				adj = 1;
				lastSlope = curSlope;
				startIndex = i;
			} else {
				adj = 1;
				lastSlope = curSlope;
				startIndex = i;
			}
		}
		if (adj >= 3) {
			// output seg
			outputSeg(p0, startIndex, adj);
		}
	}

	public static void main(String[] args) {
		
		FastJoyossarian f = new FastJoyossarian();
		f.read();
		

		for (int i = 0; i < f.points.length; ++i) {
			Point p0 = f.points[i];
			Arrays.sort(f.sortedPoints, p0.SLOPE_ORDER);
			f.checkSeg(p0);
		}
	}

	private void read() {
		String filename = "data/input10.txt";
		In input = new In(filename);
		int N = input.readInt();
		
		
		points = new Point[N];
		sortedPoints = new Point[N];
		for (int i = 0; i < N; ++i) {
			int x = input.readInt();
			int y = input.readInt();
			points[i] = new Point(x, y);
			sortedPoints[i] = points[i];
		}
		
		// rescale coordinates and turn on animation mode
//		StdDraw.setXscale(0, 32768);
//		StdDraw.setYscale(0, 32768);
	}
}












