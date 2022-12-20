import java.util.Random;

public class percolation_final {
    private int N;
    private WeightedQuickUnion wqu;
    private boolean[] grid;
    private int vtop;
    private int vbot; 
    public percolation_final(int N) {
        this.N = N;
        wqu = new WeightedQuickUnion(N * N + 2);
        grid = new boolean[N * N];
        vtop = N * N;
        vbot = N * N + 1;
        
        //initializing the grid
        for(int i = 0 ; i < (N * N); i++) {
            grid[i] = false;
        }
    }

    private void boundsCheck(int row, int col) {
        if(!(row >= 0 && row <= N && col >=0 && row <= N)) {
            throw new IndexOutOfBoundsException("----index out of bounds----");
        }
    }

    private int getIndex(int row, int col) {
        boundsCheck(row, col);
        return (row - 1) * N + (col - 1);
    }

    public void open(int row, int col) {
        boundsCheck(row, col);
        if(grid[getIndex(row, col)]) { return; }

        int index = getIndex(row, col);

        if(row == 1) { wqu.union(vtop, index); }
        if(row == N) { wqu.union(vbot, index); }

        //union with adjecent -open- tiles

        // top
        if(row != 1 && grid[getIndex(row - 1, col)]) { 
            wqu.union(index, getIndex(row - 1, col));
        }

        // bottom
        if(row != N && grid[getIndex(row + 1, col)]) {
            wqu.union(index, getIndex(row + 1, col));
        }

        // left
        if(col != 1 && grid[getIndex(row, col - 1)]) {
            wqu.union(index, getIndex(row, col - 1));
        }

        // right
        if(col != N && grid[getIndex(row, col + 1)]) {
            wqu.union(index, getIndex(row, col + 1));
        }
        
    }

    public boolean percolates() {
        return wqu.connected(vtop, vbot);
    }

    public int numberOfOpenSites() {
        int sum = 0; 
        for(int i = 0; i < N * N; i ++) {
            if(grid[i]) { sum++; }
        }

        return sum;
    }

    public void printGrid() {
        for(int i = 0; i < N * N; i++) {
            if(grid[i]) {
                System.out.print(" " + 1 + " ");
            } else {
                System.out.print(" " + 0 + " ");
            }

            if(i % (N - 1) == 0) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        int size = 10;
        percolation_final p = new percolation_final(size);
        Random rand = new Random();

        while(!p.percolates()) {
            p.open(rand.nextInt(size) + 1, rand.nextInt(size) + 1);
        }
        p.printGrid();
        System.out.println("Number of open sites: " + p.numberOfOpenSites());
    }
}
