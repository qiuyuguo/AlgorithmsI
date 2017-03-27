package assignment1;

//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] open2d;
    private WeightedQuickUnionUF u;
    int N;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        N = n;
        if (N < 1){
            throw new java.lang.IllegalArgumentException("invalid input!");
        }
        open2d = new int[N][N];
        for (int i = 0; i< N; i++){
            for (int j = 0; j < N; j++){
                open2d[i][j] = 0;
            }
        }
        u = new WeightedQuickUnionUF(N*N);
    }
    
    // open site (row, col) if it is not open already
    // open(row, col) is called outside, so the row and col values have to be adjusted
    public void open(int row, int col){
        open2d[row-1][col-1] = 1;
        // isOpen(row, col) actually check row-1, col-1
        // shouldn't union node to full once opened; should union only when it is full
        if (row-1 > 0){
            if (isOpen(row-1, col)){
                u.union(xyTo1d(row-1, col-1), xyTo1d(row-2, col-1));
            }
        }
        if (row-1 < N-1){
            if (isOpen(row+1, col)){
                u.union(xyTo1d(row-1, col-1), xyTo1d(row, col-1));
            }
        }
        if (col-1 > 0){
            if (isOpen(row, col-1)){
                u.union(xyTo1d(row-1, col-1), xyTo1d(row-1, col-2));
            }
        }
        if (col-1 < N-1){
            if (isOpen(row, col+1)){
                u.union(xyTo1d(row-1, col-1), xyTo1d(row-1, col));
            }
        }
    }
    
    private int xyTo1d(int x, int y){
        return x*N+y;
    }
    
    public boolean isConnectedToOpenTop(int row, int col){
        for (int j = 0; j < N; j++){
            if (open2d[0][j] == 1 && u.connected(xyTo1d(row-1, col-1),xyTo1d(0,j))){
                return true;
            }
        }
        return false;
    }
    
    // is site (row, col) open?
    // isOpen(row, col) is called both inside and outside, there would be conflict of index
    public boolean isOpen(int row, int col){
        return open2d[row-1][col-1] == 1;
    }
    
    // is site (row, col) full?
    // isFull is also called outside, so the index must be adjusted
    public boolean isFull(int row, int col){
        
        // find root index of the current node, and check if it is full (logN)
        int rootIdx = u.find(xyTo1d(row-1, col-1));
        if (open2d[rootIdx / N][rootIdx % N] == 1){
            return true;
        }
        return false;
        
    }
    
    // number of open sites
    public int numberOfOpenSites(){
        int sum = 0;
        for (int i = 0; i< N; i++){
            for (int j = 0; j< N; j++){
                sum += open2d[i][j];
            } 
        }
        return sum;
    }
    
    // does the system percolate?
    public boolean percolates(){
        for (int i = 1; i<= N; i++){
            if(isFull(N,i)){
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        // test client (optional)
        Percolation test = new Percolation(5);
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                test.open(i, j);
                System.out.println(test.isFull(i, j));
                System.out.println(test.percolates());
            }
        }
    }
 }
