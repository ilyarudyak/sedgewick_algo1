package part1.ass4;

import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

/**
 * 
 * 
 * @author IRudyak
 *
 */
public class Board {
	
	private final int[][] blocks;
	private int manhattan;
	private int N;
	
	
	public int[][] getBlocks() {
		return blocks;
	}

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] a) {
		
		N = a.length;
		blocks = new int[N][N];
				
		for (int i = 0; i < a.length; i++) 
			blocks[i] = Arrays.copyOf(a[i], N);
		
		manhattan = manhattanReal();
	}
             
	private int[][] buildGoal() {
		
		int[][] goal = new int[N][N];
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j++) {
				goal[i][j] = i * N + (j + 1);
			}
		}
		goal[N-1][N-1] = 0;
		return goal;
	}
	
	// board dimension N
	public int dimension() {
		return N;
	}
	
	// number of blocks out of place
	public int hamming() {
		
		int h = 0;
		int[][] goal = buildGoal();
				
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j++) {
				if (i == N - 1 && j == N - 1)
					break;
				if (goal[i][j] != blocks[i][j])
					h++;
			}
		}
		return h;
	}  
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		return manhattan;
	}   
	
	private int manhattanReal() {
		int h = 0;
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j++) {
				
				int d = manhattan(blocks[i][j], i, j);
				if (blocks[i][j] != 0) {
					h += d;
				}
			}
		}
		
		return h;
	}
	
	private int manhattan(int n, int i, int j) {
		
		int i0 = n / N;
		if (n % N == 0) 
			i0 -= 1;
		
		int j0 = (n - i0 * N) - 1;
		return Math.abs(i - i0) + Math.abs(j - j0);
	}
	
	
	// is this board the goal board?
	public boolean isGoal() {
		return manhattan == 0;
	}  
	
	// a board obtained by exchanging two adjacent blocks 
	// in the same row - swap (0,0) and (0,1)
	public Board twin() {
		
		int[][] twinBlocks = new int[N][N];
		for (int i = 0; i < N; i++) 
			twinBlocks[i] = Arrays.copyOf(blocks[i], N);
		
		if (blocks[0][0] != 0 && blocks[0][1] != 0) {
			twinBlocks[0][0] = blocks[0][1];
			twinBlocks[0][1] = blocks[0][0];
		}
		else {
			twinBlocks[1][0] = blocks[1][1];
			twinBlocks[1][1] = blocks[1][0];
		}
		
		return new Board(twinBlocks);
	}   
	
	// does this board equal y?
	@Override
	public boolean equals(Object x) {
        if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        Board that = (Board) x;
        
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != that.blocks[i][j])
					return false;
			}
		}
        return true;
	} 
	
	// all neighboring boards - we use stack as mentioned in FAQ
	public Iterable<Board> neighbors() {
		Stack<Board> stack = new Stack<Board>();
		
		int i0 = 0;
		int j0 = 0;
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++) {
	        	if (blocks[i][j] == 0){
	        		i0 = i;
	        		j0 = j;
	        		break;
	        	}
	        }
	    }
	    
	    if (isValid(i0    , j0 - 1)) stack.push(move(i0    , j0 - 1, i0, j0));
	    if (isValid(i0 - 1, j0    )) stack.push(move(i0 - 1, j0    , i0, j0));
	    if (isValid(i0    , j0 + 1)) stack.push(move(i0    , j0 + 1, i0, j0));
	    if (isValid(i0 + 1, j0    )) stack.push(move(i0 + 1, j0    , i0, j0));
	    
		return stack;
	}
	
	private boolean isValid(int i, int j) {
		return 0 <= i && i < N && 0 <= j && j < N;
	}
	
	private Board move(int i, int j, int i0, int j0) {
		
		int[][] newBlocks = new int[N][N];
		for (int k = 0; k < N; k++) 
			newBlocks[k] = Arrays.copyOf(blocks[k], N);
		
		// move from (i, j) to (i0, j0)
		newBlocks[i0][j0] = blocks[i][j];
		newBlocks[i][j] = 0;
		
		return new Board(newBlocks);
	}
	
	
	// string representation of the board 
	// (in the output format specified below)
	@Override
	public String toString() {
	    StringBuilder s = new StringBuilder();
	    s.append(N + "\n");
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++) {
	            s.append(String.format("%2d ", blocks[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}              

	public static void main(String[] args) {

		int[][] a = { 
						{8, 1, 3},
						{4, 0, 2},
						{7, 6, 5}
					};
		Board b = new Board(a);
		
		System.out.println(b);
		System.out.println();
		
		System.out.println(b.twin());
		
		for (int i = 0; i < b.N; i ++) {
			System.out.println(Arrays.toString(b.buildGoal()[i]));
		}
		System.out.println();
		
		System.out.println("hamming = " + b.hamming());
		System.out.println("manhattan = " + b.manhattan());
		
		// ---------------------------------------------------
		
		System.out.println("-----------------------");
		System.out.println();
		int[][] a2 = { 
				{8, 1, 3},
				{4, 2, 0},
				{7, 6, 5}
			};
		
		Board b2 = new Board(a2);
		
		System.out.println(b2);
		System.out.println();
		
		for (Board board: b2.neighbors()) {
			System.out.print(board);
			if (b.equals(board))
				System.out.println("disallow");
			System.out.println();
		}
		
	}

}











