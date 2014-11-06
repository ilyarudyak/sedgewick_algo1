package part1.ass4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Solver {
	
	private MinPQ<Node> pq;
	private MinPQ<Node> pq2;
	private boolean isSolvable;
	
	private Node result;
	private Node result2;
	
	private class Node implements Comparable<Node> {
		
		private Board board;
		private int moves;
		private int priority;
		private Node prev;
		
		public Node(Board b, int m, Node p) {
			board = b;
			moves = m;
			priority = b.manhattan() + moves;
			prev = p;
		}

		@Override
		public int compareTo(Node that) {
			return this.priority - that.priority;
		}
		
		
	}
		
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		
		pq = new MinPQ<>();
		pq2 = new MinPQ<>();
		isSolvable = false;
		
		// insert the initial search node
		pq.insert(new Node(initial, 0, null));
		pq2.insert(new Node(initial.twin(), 0, null));
		
		while (true) {
			
			// delete from the priority queue the search node with the minimum priority
			result = pq.delMin();
			result2 = pq2.delMin();
			//System.out.println("result\n" + result.board);
			//if (result.prev != null)
				//System.out.println("prev\n" + result.prev.board);
			
			//Repeat this procedure until the search node dequeued corresponds to a goal board
			if (result.board.manhattan() == 0) {
				isSolvable = true;
				break;
			}
			
			if (result2.board.manhattan() == 0) 
				break;
			
			//System.out.println("neighbors");
			// insert onto the priority queue all neighboring search nodes of result
			for (Board  b: result.board.neighbors()) {
				
				// critical optimization
				// don't enqueue a neighbor with the same board as result
				if (result.prev != null) {
					if (!b.equals(result.prev.board)) {
						pq.insert(new Node(b, result.moves + 1, result));
						//System.out.println(b);
					}
				}
				else 
					pq.insert(new Node(b, result.moves + 1, result));
			}
			
			for (Board  b: result2.board.neighbors()) {
				
				// critical optimization
				// don't enqueue a neighbor with the same board as result
				if (result2.prev != null) {
					if (!b.equals(result2.prev.board)) {
						pq2.insert(new Node(b, result2.moves + 1, result2));
						//System.out.println(b);
					}
				}
				else 
					pq2.insert(new Node(b, result2.moves + 1, result2));
			}
		}
	}
	
	// is the initial board solvable?
    public boolean isSolvable() {
    	return isSolvable;
    }
	
	// min number of moves to solve initial board; -1 if no solution
    public int moves() {
    	if (isSolvable())
    		return result.moves;
    	else
    		return -1;
    }
	
	// sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
    	if (isSolvable()) {
    		
    		Stack<Board> stack = new Stack<>();
    		stack.push(result.board);
    		
    		Node cur = result;
    		while(cur.prev != null) {
    			stack.push(cur.prev.board);
    			cur = cur.prev;
    		}
    		return stack;
    	}
    	else
    		return null;
    }
    
    public static void main(String[] args) {
    	
        // create initial board from file
        In in = new In("data/ass4/puzzle36.txt");
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
        	StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }// end of main
}



















