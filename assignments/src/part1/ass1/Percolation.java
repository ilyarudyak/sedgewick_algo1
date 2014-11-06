package part1.ass1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int N;
	private boolean[][] grid;
	private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
	
	private final int TOP;
    private final int TOP2;
	private final int BOTTOM;
	
	
	

	// create N-by-N grid, with all sites blocked
	public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException();
		
		N = n;
		TOP = 0;
        TOP2 = 0;
		BOTTOM = N * N + 1;
		grid = new boolean[N][N];
		
		// initialize uf and create 2 virtual points
		uf = new WeightedQuickUnionUF(N * N + 2);
		for (int j = 1; j <= N; j++){ 
			uf.union(map(1, j), TOP);
			uf.union(map(N, j), BOTTOM);
		}

        // initialize uf2 and create only top virtual point
        uf2 = new WeightedQuickUnionUF(N * N + 1);
        for (int j = 1; j <= N; j++){ 
			uf2.union(map(1, j), TOP);
        }

	}
	

	// open site (row i, column j) if it is not already
	// and add to union find 
	public void open(int i, int j) {
		
		if (i <= 0 || i > N || j <= 0 || j > N) 
			throw new IndexOutOfBoundsException();
		
		grid[i - 1][j - 1] = true;
		
		// add to union find
		addToUF(i, j, uf);
        addToUF(i, j, uf2);
	}
	
	private void addToUF(int i, int j, WeightedQuickUnionUF uf) {
		
		int site = map(i, j);
		if (isValid(i - 1) && isOpen(i - 1, j    )) uf.union(map(i - 1, j    ), site);
		if (isValid(i + 1) && isOpen(i + 1, j    )) uf.union(map(i + 1, j    ), site);
		if (isValid(j - 1) && isOpen(i    , j - 1)) uf.union(map(i    , j - 1), site);
		if (isValid(j + 1) && isOpen(i    , j + 1)) uf.union(map(i    , j + 1), site);
			
	}
	
	private boolean isValid(int k) {
		
		if ( 1 <= k && k <= N) return true;
		else return false;
	}
	
	
	// mapping 2D coordinates to 1D coordinates
	private int map(int i, int j) {
		
		if (i <= 0 || i > N || j <= 0 || j > N) 
			throw new IndexOutOfBoundsException();
		
		return N * (i - 1) + j;
	}
	
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		
		if (i <= 0 || i > N || j <= 0 || j > N) 
			throw new IndexOutOfBoundsException();
		return grid[i - 1][j - 1];
	}
	
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		
		if (i <= 0 || i > N || j <= 0 || j > N) 
			throw new IndexOutOfBoundsException();

        if (!percolates()) {
            if (uf.connected(map(i, j), TOP) && 
                isOpen(i, j))
			    return true;
        }
        else {
            if (uf2.connected(map(i, j), TOP) && 
                isOpen(i, j))
			    return true;    
        }

        return false;
	}
	
	// does the system percolate?
	public boolean percolates() {

        if (N == 1) 
            return isOpen(1, 1);
        else if (N == 2) 
            return (isOpen(1, 1) && isOpen(2, 1)) ||
                   (isOpen(1, 2) && isOpen(2, 2));

        else return uf.connected(TOP, BOTTOM);
	}
	

}
















