
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class billboards {

    public static void main(String[] args) throws Exception {
        BufferedReader f = new BufferedReader(new FileReader("billboard.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("billboard.out"));

        int[] b1 = new int[]{nextInt(f, false), nextInt(f, false), nextInt(f, false), nextInt(f, true)};
        int[] b2 = new int[]{nextInt(f, false), nextInt(f, false), nextInt(f, false), nextInt(f, true)};
        int[] tr = new int[]{nextInt(f, false), nextInt(f, false), nextInt(f, false), nextInt(f, true)};

        int overlap_1 = findSize(b1) - findArea(b1, tr);
        int overlap_2 = findSize(b2) - findArea(b2, tr);

        int output = overlap_1 + overlap_2;
        int dec = findArea(tr, vlapRect(b1, b2));
        
        if(dec != 0)
            output -= findSize(tr) - dec;
        
        String outStr = Integer.toString(output);
        outStr += "\n";

        out.write(outStr, 0, outStr.length());

        out.flush();
        out.close();
        f.close();

    }

    public static int nextInt(BufferedReader f, boolean end) throws Exception {
        int ch = -2;
        int num = 0;
        boolean neg = false;

        do {

            while ((ch = f.read()) != 32 && ch != 10 && ch != 13 && ch != -1) {
                if(ch != 45)
                    num = num * 10 + (ch - 48);
                else
                    neg = true;
            }

        } while (ch != 32 && ch != 13 && ch != 10 && ch != -1);
        
        return neg ? -1*num : num;
    }

    public static int findSize(int[] b1) {
        int size = (b1[3] - b1[1]) * (b1[2] - b1[0]);
        return size;
    }

    public static int findArea(int[] b1, int[] b2) {
        
        if(b1 == null || b2 == null)
            return 0;
        
        int intv_1 = intvLap(b1[0], b1[2], b2[0], b2[2]);
        int intv_2 = intvLap(b1[1], b1[3], b2[1], b2[3]);
        return intv_1 * intv_2;
    }

    public static int intvLap(int low_1, int high_1, int low_2, int high_2) {
        if (Math.max(low_1, low_2) < Math.min(high_1, high_2)) {
            return Math.min(high_1, high_2) - Math.max(low_1, low_2);
        } else {
            return 0;
        }
    }

    public static int[] vlapRect(int[] b1, int[] b2) {

        if (Math.max(b1[0], b1[2]) > Math.min(b2[0], b2[2])) {
            if (Math.max(b1[1], b1[3]) > Math.min(b2[1], b2[3])) {

                int x2 = Math.min(b2[0], b2[2]);
                int x1 = Math.max(b1[0], b1[2]);
                int y2 = Math.min(b2[1], b2[3]);
                int y1 = Math.max(b1[1], b1[3]);

                return new int[]{x1, y1, x2, y2};
            }
        }

        return null;

    }

}
