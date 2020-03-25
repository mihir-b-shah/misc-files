
import java.io.*;
import java.util.*;

public class leftout {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("leftout.in"));
        PrintWriter out = new PrintWriter("leftout.out");

        int N = f.nextInt();
        f.nextLine();

        boolean[][] cows = new boolean[N][N];
        String s = f.nextLine();
        for (int j = 0; j < N; j++) {
            if (s.charAt(j) == 'R') {
                cows[0][j] = true;
            }
        }

        int row_pos = -1;
        int col_pos = -1;
        int prev = 0;

        for (int i = 1; i < N; i++) {
            s = f.nextLine();
            for (int j = 0; j < N; j++) {
                if (s.charAt(j) == 'R') {
                    cows[i][j] = true;
                }
            }

            boolean res = compare(cows[i], cows[i - 1]);
            prev = res ? 1 : 2;

            if (!res) {
                if (prev == 0) {
                    boolean res2 = compare(cows[i], cows[i + 1]);
                    if (res2) {
                        row_pos = i - 1;
                    } else {
                        row_pos = i;
                    }
                } else {
                    if (prev == 1) {
                        row_pos = i;
                    } else if (prev == 2) {
                        row_pos = i - 1;
                    }
                }

                break;
            }
        }

        prev = 0;
        // Converge on cols
        for (int i = 1; i < N; i++) {
            boolean res = compareCols(cows, i);
            prev = res ? 1 : 2;

            if (!res) {
                if (prev == 0) {
                    boolean res2 = compareCols(cows, i+1);
                    if (res2) {
                        col_pos = i - 1;
                    } else {
                        col_pos = i;
                    }
                } else {
                    if (prev == 1) {
                        col_pos = i;
                    } else if (prev == 2) {
                        col_pos = i - 1;
                    }
                }

                break;
            }
        }

        out.printf("%d %d\n", row_pos == -1 ? -1 : row_pos+1, col_pos == -1 ? -1 : col_pos+1);
        out.flush();
        out.close();
        f.close();
    }

    public static boolean compareCols(boolean[][] cows, int i) {
        if (cows[0][i] == cows[0][i - 1]) {
            for (int j = 1; j < cows.length; j++) {
                if (cows[j][i] != cows[j][i - 1]) {
                    return false;
                }
            }
        } else {
            for (int j = 1; j < cows.length; j++) {
                if (cows[j][i] == cows[j][i - 1]) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public static boolean compare(boolean[] b1, boolean[] b2) {
        if (b1[0] == b2[0]) {
            for (int i = 1; i < b1.length; i++) {
                if (b1[i] != b2[i]) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < b1.length; i++) {
                if (b1[i] == b2[i]) {
                    return false;
                }
            }
        }

        return true;
    }

}
