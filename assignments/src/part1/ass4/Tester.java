package part1.ass4;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Tester {

    public static void main(String[] args) {
    	
    	for (int k = 0; k < 35; k++) {
	        // create initial board from file
    		In in;
    		if (k < 10)
    			in = new In("data/ass4/puzzle0" + k + ".txt");
    		else
    			in = new In("data/ass4/puzzle" + k + ".txt");
    			
	        int N = in.readInt();
	        int[][] blocks = new int[N][N];
	        for (int i = 0; i < N; i++)
	            for (int j = 0; j < N; j++)
	                blocks[i][j] = in.readInt();
	        Board initial = new Board(blocks);
	
	        // solve the puzzle
	        Solver solver = new Solver(initial);
	
	        // print solution to standard output
	        if (!solver.isSolvable())
	            StdOut.println("No solution possible");
	        else {
	            StdOut.println(k + ": " + solver.moves());
	            /*for (Board board : solver.solution())
	                StdOut.println(board);*/
	        }
    	}
    } // end of main

}
