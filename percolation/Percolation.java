import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    WeightedQuickUnionUF qu;
    int[][] grid;
    int len;
    int virtt;
    int virtb;

    // creates n-by-n grid, with all sites initially blocked 
    // (0 in the grid = blocked; 1 = open)
    public Percolation(int n) 
    {
        len = n;
        qu = new WeightedQuickUnionUF(n * n + 2);

        for(int i = 0; i < n; i++) 
        {
            for(int j = 0; j < n; i++)
                grid[i][j] = 0;
        }
        
        // connect from the top and bottom to the virtual site

        //virtual sites indexes
        virtt = n * n;
        virtb = n * n + 1;
        
        //connect
        for(int i = 0; i < n; i++)
        {
            int top = i;
            int bottom = (n - 1) * (n - 1) + i;
            
            qu.union(virtt, top);
            qu.union(virtb, bottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if(grid[row][col] != 1) { grid[row][col] = 1; }
        
        // union with open neighbours
        // VVVVVVVVV
        
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        int index = (len - 1) * row + (col - 1);
        return qu.find(index) == virtt;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        int sum;
        for(int i = 0; i < len; i ++)
        {
            for(int j = 0; j < len; j++)
                if(grid[i][j] == 1) { sum++; }
        }

        return sum;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return qu.find(virtt) == qu.find(virtb);
    }

    // test client (optional)
    public static void main(String[] args)
}