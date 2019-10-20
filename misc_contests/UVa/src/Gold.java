
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Gold {

    public static int compAir;
    public static int[][] treasures;

    public static void main(String[] args) throws Exception {
        BufferedReader f = new BufferedReader(new FileReader("gold.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("gold.out"));

        compAir = nextInt(f, false);
        int w = nextInt(f, true);
        int toCome = nextInt(f, true);
        treasures = new int[toCome][2];

        for (int i = 0; i < toCome; i++) {
            treasures[i][0] = 3 * w * nextInt(f, false);
            treasures[i][1] = nextInt(f, true);
        }

        int res = knapSack(compAir, treasures.length);
        out.write(Integer.toString(res));
        
        out.flush();
        out.close();
        f.close();
    }

    public static int knapSack(int W, int n) {
        int i, w;
        int K[][] = new int[n + 1][W + 1];

        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    K[i][w] = 0;
                } else if (treasures[i - 1][0] <= w) {
                    K[i][w] = Math.max(treasures[i - 1][1] + K[i - 1][w - treasures[i - 1][0]], K[i - 1][w]);
                } else {
                    K[i][w] = K[i - 1][w];
                }
            }
        }

        return K[n][W];
    }

    public static void printMat(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.printf("%4d", array[i][j]);
            }
            System.out.println();
        }
    }

    public static int multiMax(ArrayList<Integer> nums) {

        int max;

        if (nums.isEmpty()) {
            return 0;
        } else if (nums.size() == 1) {
            return nums.get(0);
        } else {
            max = Math.max(nums.get(0), nums.get(1));

            for (int i = 2; i < nums.size(); i++) {
                max = Math.max(max, nums.get(i));
            }
        }

        return max;

    }

    public static int nextInt(BufferedReader f, boolean end) throws Exception {
        int ch = -2;
        int num = 0;

        do {

            while ((ch = f.read()) != 32 && ch != 10 && ch != 13 && ch != -1) {
                num = num * 10 + (ch - 48);
            }

        } while (ch != 32 && ch != 13 && ch != 10 && ch != -1);

        if (end) {
            f.skip(1);
        }

        return num;
    }

}
