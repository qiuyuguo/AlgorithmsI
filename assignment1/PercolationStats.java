package assignment1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double[] thresh;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        
        thresh = new double[trials];
        for (int i = 0; i< trials; i++){
            Percolation perc = new Percolation(n);
            int count = 0;
            while (!perc.percolates()){
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                while (perc.isOpen(row,col)){
                    row = StdRandom.uniform(1,n+1);
                    col = StdRandom.uniform(1,n+1);
                }
                perc.open(row, col);
                count++;
            }
            thresh[i] = 1.0 * count/n/n;
        }
        
    }
    
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(thresh);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(thresh);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-1.96*Math.pow(stddev()/thresh.length, 0.5);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+1.96*Math.pow(stddev()/thresh.length, 0.5);
    }
    
    // test client (described below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trials);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());
    }
    
 }
