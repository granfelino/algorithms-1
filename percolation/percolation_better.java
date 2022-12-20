import java.util.Random;

// ALL ROW AND COLS NUMBERS ARE PASSED IN AS 
// FUNCTION ARGUMENTS IN STANDARD FORM I.E.
// BETWEEN (1, N)

class percolation_better {
    private WeightedQuickUnion wqu;
    private int N;
    private boolean[][] grid;
    private int vbot;
    private int vtop;
    
    public percolation_better(int len) {
        grid = new boolean[len][len];
        N = len;
        wqu = new WeightedQuickUnion(len * len + 2);
        vbot = len * len + 1;
        vtop = len * len;


        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                grid[i][j] = false;
            }
        }
    } 

    private boolean isValid(int row, int col) {
        return (row >= 0 && row <= N) && (col >= 0 && col <= N);
    }

    private void boundsEx() {
        throw new IndexOutOfBoundsException("Index out of bounds.");
    }

    private int getIndex(int row, int col) {
        if(!isValid(row, col)) { boundsEx(); }
        return (row - 1) * N + (col - 1);
    }

    public boolean isOpen(int row, int col) {
        if(!isValid(row, col)) { boundsEx(); }
        return grid[row - 1][col - 1];
    }

    public void open(int row, int col) {
        if(!isValid(row, col)) { boundsEx(); }
        if(isOpen(row, col)) { return; }


        grid[row - 1][col - 1] = true;
        int index = getIndex(row, col);
        
        //top and bottom row vsites union
        if(row == N) { wqu.union(vbot, index); }
        if(row == 1) { wqu.union(vtop, index); }

        //union with any adjecent open tiles
        // top
        if(row != 1 && grid[row - 2][col - 1]) {
            int topindex = getIndex(row - 1, col);
            wqu.union(index, topindex);
        }

        // bottom
        if(row != N && grid[row][col - 1]) {
            int botindex = getIndex(row, col - 1);
            wqu.union(index, botindex);
        }

        // left
        if(col != 1 && grid[row - 1][col - 2]) {
            int leftindex = getIndex(row, col - 1);
            wqu.union(index, leftindex);
        }

        // right
        if(col != N && grid[row - 1][col]) {
            int rightindex = getIndex(row - 1, col);
            wqu.union(index, rightindex);
        }
        
    }

    public boolean isFull(int row, int col) {
        return wqu.connected(vtop, getIndex(row, col));
    }

    public int numberOfOpenSites() {
        int sum = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j ++) {
                if(grid[i][j]) { sum++; }
            }
        }
        return sum;
    }

    public boolean percolates() {
        return wqu.connected(vtop, vbot);
    }

    public void printGrid() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(grid[i][j]) { 
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }

                if(j == N - 1) {
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {
        int size = 10;
        percolation_better p = new percolation_better(size);
        Random rand = new Random(); 
        
        while(!p.percolates()) {
            int randRow = rand.nextInt(size) + 1;
            int randCol = rand.nextInt(size) + 1;

            p.open(randRow, randCol);
        }

        p.printGrid();
        System.out.println("Number of open sites: " + p.numberOfOpenSites());
    }
}