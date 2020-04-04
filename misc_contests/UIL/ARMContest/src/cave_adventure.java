
import java.util.*;
import java.io.*;

public class cave_adventure {
    static char[][] grid;
    static boolean check;
    
    static int[] dx = {1,0,-1,0};
    static int[] dy = {0,1,0,-1};
    static int[] dx2 = {2,0,-2,0};
    static int[] dy2 = {0,2,0,-2};
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("cave_adventure.dat"));
        while(f.hasNextLine()){
            check = false;
            grid = new char[8][8];
            int row = 0;
            int col = 0;
            for(int i = 0; i < 8; i++){
                grid[i] = f.nextLine().toCharArray();
            }
            if(f.hasNextLine()){
                f.nextLine();
            }
            
            for(int r = 0; r < 8; r++){
                for(int c = 0; c <8; c++){
                    if(grid[r][c] == 'S'){
                        row = r;
                        col = c;
                    }
                }
            }
            
            recur(row, col);
            System.out.println(check ? "Solvable" : "No Solution");
        }
        f.close();
    }
    
    public static void recur(int r, int c){
        if(grid[r][c] == 'W'){
            return;
        }
        if(grid[r][c] == 'E'){
            check = true;
            return;
        }
        if(grid[r][c] == '-') {
            grid[r][c] = 'W';
        }
        for(int i = 0; i<4; ++i) {
            if(r+dx[i] >= 0 && r+dx[i] < 8 && c+dy[i] >= 0 && c+dy[i] < 8 && (grid[r+dx[i]][c+dy[i]] == '-' || grid[r+dx[i]][c+dy[i]] == 'E')) {
                recur(r+dx[i],c+dy[i]);
            }
            if(r+dx[i] >= 0 && r+dx[i] < 8 && c+dy[i] >= 0 && c+dy[i] < 8 && r+dx2[i] >= 0 && r+dx2[i] < 8 && c+dy2[i] >= 0 && c+dy2[i] < 8 
                    && grid[r+dx[i]][c+dy[i]]=='G' && (grid[r+dx2[i]][c+dy2[i]]=='-' || grid[r+dx2[i]][c+dy2[i]]=='E')) {
                recur(r+dx2[i],c+dy2[i]);
            }
        }
        /*
        if(r < 0 || c < 0 || r > grid.length-1 || c > 8){
            return;
        }
        if(grid[r][c] == 'W'){
            return;
        }
        if(grid[r][c] == 'E'){
            check = true;
            return;
        }
        if(r < grid.length-2 && grid[r+1][c] == 'G' && grid[r][c] == 'G'){
            return;
        } else{
            recur(r+1, c);
        }
        if(r > 0 && grid[r-1][c] == 'G' && grid[r][c] == 'G'){
            return;
        } else{
            recur(r-1, c);
        }
        if(c < 6 && grid[r][c+1] == 'G' && grid[r][c] == 'G'){
            return;
        } else{
            recur(r, c+1);
        }
        if(r > 0 && grid[r][c-1] == 'G' && grid[r][c] == 'G'){
            return;
        } else{
            recur(r, c-1);
        }
        recur(r+1, c);
        recur(r-1, c);
        recur(r, c+1);
        recur(r, c-1);
*/
    }
}
