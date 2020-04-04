
import java.util.*;
import java.io.*;

public class roads {
    public static char[][] grid;
    public static int ctr;
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("roads.dat"));
        do{
            ctr = 0;
            int x = 0;
            int y =0;
            grid = new char[10][10];
            for(int i = 0; i < 10; i++){
                grid[i] = f.nextLine().toCharArray();
                
            }
            for(int r= 0; r < 10; r++){
                for(int c = 0; c <10; c++){
                    if(grid[r][c] == '*'){
                        ctr ++;
                    }
                }
            }
            outer: for(int r = 0; r < grid.length;r ++){
                inner: for(int c = 0;c < grid[r].length; c++){
                        if(grid[r][c] != 'I'){
                            x = r;
                            y = c;
                            break outer;
                        
                        }
                        
                }
                    
                }
            if(f.hasNextLine()){
                f.nextLine();
            }
            recur(x,y);
            if(ctr == 0){
                System.out.println("Accept");
            }
            else {
                System.out.println("Reject");
            }
        } while(f.hasNextLine());
        f.close();
    }
    
    public static void recur(int r, int c){
        if(r < 0 || c < 0 || r >= grid.length || c >= grid.length){
            return;
        }
        if(grid[r][c] == 'I'){
            return;
        }
        if(grid[r][c] == '*'){
            ctr--;
        }
        grid[r][c] = 'I';
        recur(r+1, c);
        recur(r-1, c);
        recur(r, c+1);
        recur(r, c-1);
    }
}
