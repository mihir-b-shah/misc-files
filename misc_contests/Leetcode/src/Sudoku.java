
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Solves a sudoku puzzle.
 * 
 * @author mihir
 */
public class Sudoku {
    
    static char[][] board;
    static short[] rows;
    static short[] cols;
    static short[][] grids;
    static boolean done;
    
    static short genMask(int i, int j) {
        return (short) (~(rows[i]|cols[j]|grids[i/3][j/3]) & 0x1ff);
    }
    
    static boolean check() {
        int accm;
        // row check
        for(int i = 0; i<9; ++i) {
            accm = 0;
            for(int j = 0; j<9; ++j) {
                accm |= 1 << board[i][j]-'1';
            }
            if(accm != 0b111_111_111)
                return false;
        }
        for(int i = 0; i<9; ++i) {
            accm = 0;
            for(int j = 0; j<9; ++j) {
                accm |= 1 << board[j][i]-'1';
            }
            if(accm != 0b111_111_111)
                return false;
        }
        int[][] grid = new int[3][3];
        for(int i = 0; i<9; ++i) {
            for(int j = 0; j<9; ++j) {
                grid[i/3][j/3] |= 1 << board[j][i]-'1';
            }
        }
        for(int[] arr: grid) {
            for(int i: arr) {
                if(i != 0b111_111_111)
                    return false;
            }
        }
        return true;
    }
    
    static int findNext(int i, int j) {
        int ctr = 9*i+j;
        ++ctr;
        while(ctr < 81 && board[ctr/9][ctr%9] != '.') {
            ++ctr;
        }
        if(ctr == 81) return -1;
        return (ctr/9 << 16) + (ctr%9);
    }

    static void solve(int i, int j, char repl) {  
        /*
        for(char[] arr: board) {
            System.out.println(new String(arr));
        }
        System.out.println(); */
        short rowCache = rows[i];
        short colCache = cols[j];
        short gridCache = grids[i/3][j/3];
        
        if(!done) {
            board[i][j] = repl;
            rows[i] |= 1 << repl-'1';
            cols[j] |= 1 << repl-'1';
            grids[i/3][j/3] |= 1 << repl-'1'; 
        }

        int ctr = 0;
        int next = findNext(i,j);
        branch: if(next == -1) {
            done = true;
            return;
        } else if(next == -1) {
            done = true;
        } else {
            int nextI = next >>> 16;
            int nextJ = next & 0xffff;
            short mask = genMask(nextI, nextJ);
            if(mask == 0) {
                break branch;
            }

            while(mask > 0) {
                if((mask&1) == 1) {
                    solve(nextI, nextJ, (char) ('1'+ctr));
                    if(done) {
                        return;
                    } 
                }
                ++ctr;
                mask >>>= 1;
            }
        }
        if(!done) {
            board[i][j] = '.';
            rows[i] = rowCache;
            cols[j] = colCache;
            grids[i/3][j/3] = gridCache;
        }
    }
    
    static void solve() {
        int next = findNext(0,-1);
        int nextJ = next & 0xffff;
        int nextI = next >>> 16;
        if(next == -1) {
            return;
        }
        short mask = genMask(nextI,nextJ);
        int ctr = 0;
        if(mask == 0) return;
        while(mask > 0) {
            if((mask&1) == 1) {
                solve(nextI, nextJ, (char) ('1'+ctr));
            }
            ++ctr;
            mask >>>= 1;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner f = new Scanner(new File("sudoku.txt"));
        board = new char[9][9];
        rows = new short[9]; // technically only need 9 bits
        cols = new short[9];
        grids = new short[3][3];
        
        for(int i = 0; i<9; ++i) {
            for(int j = 0; j<9; ++j) {
                char c = f.next().charAt(0);
                if(c != '.') {
                    rows[i] |= 1 << c-'1';
                    cols[j] |= 1 << c-'1';
                    grids[i/3][j/3] |= 1 << c-'1';
                }
                board[i][j] = c;
            }
        }
        
        solve();
        for(char[] arr: board) {
            System.out.println(new String(arr));
        }
    }
}
