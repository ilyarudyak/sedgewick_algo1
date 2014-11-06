package part1.ass3;

//import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;


public class Brute {
	
	private Point[] points;
	
	private void read(){
        In in = new In("data/input8.txt");     
        points = new Point[in.readInt()];
        
        int x, y, i = 0;
        while (!in.isEmpty()) {
            x = in.readInt();
            y = in.readInt();
            points[i++] = new Point(x, y);
        }
        Merge.sort(points);
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        draw();
	}
	
    public static void main(String[] args) {
    	
    	Brute b = new Brute();
    	b.read();
        b.findCollinears();
        
        
        
    }
    
    private void draw(){

    	for (Point p : points) {
			p.draw();
		}
    }
    
    
    private  void findCollinears() {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) == 
                    		points[i].slopeTo(points[k])) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (points[i].slopeTo(points[k]) == 
                            		points[i].slopeTo(points[l])) {
                                StdOut.println(
                                		points[i] + " -> " + points[j] + " -> " + 
                                		points[k] + " -> " + points[l]);
                                points[j].drawTo(points[i]);
                                points[k].drawTo(points[j]);
                                points[l].drawTo(points[k]);
                            }
                        }
                    }
                }
            }
        }
    }
}
