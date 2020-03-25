
import java.util.*;

public class Bogosort {
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        for(int i = 0; i<N; ++i) {
            int len = f.nextInt();
            int[] array = new int[len];
            for(int j = 0; j<len; ++j) {
                array[j] = f.nextInt();
            }
            boolean flag = true;
            while(flag) {
                flag = false;
                for(int j = 0; j<len-1; ++j) {
                    int comp = j-array[j];
                    for(int k = j+1; k<len; ++k) {
                        if(k-array[k] == comp) {
                            // swap
                            int temp = array[j];
                            array[j] = array[k];
                            array[k] = temp;
                            flag = true;
                        }
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j<len; ++j) {
                sb.append(array[j]); sb.append(' ');
            }
            System.out.println(sb.toString().trim());
        }
    }
}
