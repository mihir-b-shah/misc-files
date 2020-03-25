
/*

ID: mihirsh1
PROB:
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class countcross {
    
    public static final int EAST = 0;
    public static final int NORTH = 1;
    public static final int WEST = 2;
    public static final int SOUTH = 3;
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("countcross.in"));
        PrintWriter out = new PrintWriter("countcross.out");
        
        int N = f.nextInt();
        int K = f.nextInt();
        int R = f.nextInt();
        
        int[][] roads = new int[N][N];
        
        for(int i = 0; i<R; i++) {
            int r = f.nextInt();
            int c = f.nextInt();
            int r_ = f.nextInt();
            int c_ = f.nextInt();
            
            //print2DArr(roads);
            
            if(r != r_) {
                if(r < r_) {
                    roads[r-1][c-1] += 1 << SOUTH; 
                    roads[r_-1][c_-1] += 1 << NORTH;
                } else {
                    roads[r-1][c-1] += 1 << NORTH; 
                    roads[r_-1][c_-1] += 1 << SOUTH;
                }
            } else {
                if(c < c_) {
                    roads[r-1][c-1] += 1 << EAST;
                    roads[r_-1][c_-1] += 1 << WEST;
                } else {
                    roads[r-1][c-1] += 1 << WEST;
                    roads[r_-1][c_-1] += 1 << EAST;
                }
            }
        }
        
        //print2DArr(roads);
        
        for(int i = 0; i<N; i++) {
            for(int j = 0; j<N; j++) {
                if(i == 0) 
                    roads[i][j] += (1 << NORTH);
                if(i == N-1)
                    roads[i][j] += (1 << SOUTH);
                if(j == 0)
                    roads[i][j] += (1 << WEST);
                if(j == N-1)
                    roads[i][j] += (1 << EAST);
            }
            
            //print2DArr(roads);
        }
        
        //print2DArr(roads);
        boolean[][] cowLocs = new boolean[N][N];
        int[][] cows = new int[K][2];
        
        for(int i = 0; i<K; i++) {
            
            int X = f.nextInt();
            int Y = f.nextInt();
            
            cowLocs[X - 1][Y - 1] = true;
            cows[i][0] = X - 1;
            cows[i][1] = Y - 1;
        }
        
        boolean[][] visited = new boolean[N][N];
        
        int counter = 0;
        
        for(int i = 0; i<cows.length; i++) {
            floodFill(visited, cows[i][0], cows[i][1], roads);
            
            for(int j = 0; j<cows.length; j++) {
                if(i != j) {
                    if(!visited[cows[j][0]][cows[j][1]]) {
                        counter++;
                    }
                }
            }
            
            visited = new boolean[N][N];
        }
        
        out.println(counter/2);
        out.flush();
        out.close();
        f.close();
    }
    
    public static void print2DArr(int[][] arr) {
        for(int i = 0; i<arr.length; i++) {
            for(int j = 0; j<arr[0].length; j++) {
                System.out.printf("%3d", arr[i][j]);
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    public static void floodFill(boolean[][] visited, int X, int Y, int[][] walls) {
        
        visited[X][Y] = true;
        
        if((walls[X][Y] & (1 << SOUTH)) == 0 && !visited[X+1][Y]) {
            floodFill(visited, X + 1, Y, walls); // South
        }
        
        if((walls[X][Y] & (1 << EAST)) == 0 && !visited[X][Y + 1]) {
            floodFill(visited, X, Y + 1, walls); // East
        }
            
        if((walls[X][Y] & (1 << WEST)) == 0 && !visited[X][Y - 1]) {
            floodFill(visited, X, Y - 1, walls); // West
        }
            
        if((walls[X][Y] & (1 << NORTH)) == 0 && !visited[X-1][Y]) {
            floodFill(visited, X - 1, Y, walls); // North
        }
    }
    
}
