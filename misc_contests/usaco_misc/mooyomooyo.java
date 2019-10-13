
/*

ID: mihirsh1
PROB:
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class mooyomooyo {
    
    public static boolean[][] visited;
    public static int[][] lookup;
    public static ArrayList<int[]> toDelete;
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader f = new BufferedReader(new FileReader("mooyomooyo.in"));
        PrintWriter out = new PrintWriter("mooyomooyo.out");
        
        int N = nextInt(f);
        int K = nextInt(f);
        //f.skip(1); // Remove
        
        int[][] board = new int[N][10];
        visited = new boolean[N][10];
        
        for(int i = 0; i<board.length; i++) {
            for(int j = 0; j<board[0].length; j++) {
                int read = f.read();
                board[i][j] = read - 48;
            }
            f.skip(1); // Remove
        }
        
        toDelete = new ArrayList<>();

        int numRooms = 0;
        
        do {
        
            numRooms = 0;
            
            for(int i = 0; i<board.length; i++) {
                for(int j = 0; j<board[0].length; j++) {
                    if(!visited[i][j] && board[i][j] != 0) {
                        floodFill(board[i][j], i, j, board);

                        if(toDelete.size() >= K) {
                            for(int k = 0; k<toDelete.size(); k++) {
                                board[toDelete.get(k)[0]][toDelete.get(k)[1]] = -1;
                                numRooms++;
                            }
                        }

                        toDelete.clear();
                    }
                }
            }  

            delete(board);
            visited = new boolean[N][10];
            
        } while (numRooms != 0);
        
        for(int i = 0; i<board.length; i++) {
            for(int j = 0; j<board[0].length; j++) {
                out.print(board[i][j]);
            }
            
            out.println();
        }
        
        out.flush();
        out.close();
        f.close();
    }
    
    public static void delete(int[][] array) {
        
        for(int i = 0; i<array[0].length; i++) {
            for(int j = 0; j<array.length; j++) {
                if(array[j][i] == -1) {
                    
                    for(int k = j; k > 0; k--) {
                        array[k][i] = array[k-1][i];
                    }
                    
                }
            }
        }
        
    }
    
    public static void floodFill(int toFind, int X, int Y, int[][] array) {
        
        int[] pair = new int[2];
        pair[0] = X; pair[1] = Y;
        toDelete.add(pair);
        visited[X][Y] = true;
        
        if(X < array.length - 1 && array[X+1][Y] == toFind && !visited[X+1][Y]) {
            floodFill(toFind, X + 1, Y, array); // South
        }
        
        if(Y < array[0].length -1 && array[X][Y + 1] == toFind && !visited[X][Y + 1]) {
            floodFill(toFind, X, Y + 1, array); // East
        }
            
        if(Y > 0 && array[X][Y - 1] == toFind && !visited[X][Y - 1]) {
            floodFill(toFind, X, Y - 1, array); // West
        }
            
        if(X > 0 && array[X - 1][Y] == toFind && !visited[X-1][Y]) {
            floodFill(toFind, X - 1, Y, array); // North
        }
        
        
    }
    
    public static int nextInt(BufferedReader f) throws Exception {
        int ch = -2;
        int num = 0;

        do {

            while ((ch = f.read()) != 32 && ch != 10 && ch != 13 && ch != -1) {
                num = num * 10 + (ch - 48);
            }

        } while (ch != 32 && ch != 13 && ch != 10 && ch != -1);

        return num;
    }
}
