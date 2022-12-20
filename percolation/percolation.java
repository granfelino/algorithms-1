class percolation
{
    int[][] grid;
    int len;
    WeightedQuickUnion wqu;
    int virtt;
    int virtb;
    
    //constructor: initialize variables, populate grid and union virtual sites
    public percolation(int n)
    {
        //initialize variables
        grid = new int[n][n];
        len = n;
        wqu = new WeightedQuickUnion(n * n + 2);
        virtt = n * n + 1; // virtual top site to unionize with the top row
        virtb = n * n;     // virtual bottom site to unionize with the bottom row

        //populate the grid
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                grid[i][j] = 0;
            }
        }

        //union with the virtual top and bottom sites
        for(int i = 0; i < n; i++)
        {
            wqu.union(i, virtt);
            wqu.union((n * n - 1) - i, virtb);
        }
    }

    public void printGrid()
    {
        for(int i = 0; i < len; i++)
        {
            for(int j = 0; j < len; j++)
            {
                System.out.print(" " + grid[i][j] + " ");
                if(j == (len - 1)) { System.out.println(); }
            }
        }
    }

    public void open(int row, int col)
    {
        //-----grid part-----
        if(isOpen(row, col)){
            return;
        }

        grid[row - 1][col - 1] = 1;

        // -----wqu.id part-----
        int idIndex = (row - 1) * (len) + (col - 1); 
        
        //top
        if( ((row - 1) != 0) && (grid[row - 2][col - 1] == 1) )
        {
            int topIndex = (row - 2) * (len) + (col - 1);
            wqu.union(idIndex,topIndex);
        }

        //bottom
        if((row - 1 != len - 1) && grid[row][col - 1] == 1)
        {
            int bottomIndex = (row) * (len) + (col - 1);
            wqu.union(idIndex, bottomIndex);
        }

        //left
        if((col - 1 != 0) && (grid[row - 1][col - 2] == 1))
        {
            int leftIndex = (row - 1) * (len) + (col - 2);
            wqu.union(idIndex, leftIndex);
        }

        //right
        if((col - 1) != (len - 1) && grid[row - 1][col] == 1)
        {
            int rightIndex = (row - 1) * (len) + (col);
            wqu.union(idIndex, rightIndex);
        }
    }

    public boolean isOpen(int row, int col)
    {
        return grid[row - 1][col - 1] == 1;
    }

    public int numberOfOpenSites()
    {
        int sum = 0;
        for(int i = 0; i < len; i++)
        {
            for(int j = 0; j < len; j++)
            {
                if(grid[i][j] == 1) { sum++; }
            }
        }
        return sum;
    }

    //i.e. is the site connected to the virtual top row?
    public boolean isFull(int row, int col)
    {
        int idIndex = (row - 1) * (len) + (col - 1);
        return wqu.connected(idIndex, virtt);
    }
    
    public boolean perlocates()
    {
        return wqu.connected(virtt, virtb);
    }
    
    
    
    public static void main(String[] args)
    {
        
        percolation p = new percolation(5);
        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        for(int i = 1; i < 6; i++) {
            p.open(4, i);
        }
        p.open(5, 2);

        p.printGrid();
        System.out.println(p.wqu.connected(0, 15));
        System.out.println(p.isFull(5,1));
        System.out.println(p.perlocates());
    }
}