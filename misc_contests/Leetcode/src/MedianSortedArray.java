
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author mihir
 */
public class MedianSortedArray {
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        while(true) {
            int M = f.nextInt();
            int N = f.nextInt();
            
            int[] arr1 = new int[M];
            int[] arr2 = new int[N];
            
            for(int i = 0; i<M; ++i) {
                arr1[i] = f.nextInt();
            }
            
            for(int i = 0; i<N; ++i) {
                arr2[i] = f.nextInt();
            }
            
            // O(lg m + lg n)
            // assume odd m+n.
            
            if(arr1.length > arr2.length) {
                int[] temp = arr2;
                arr2 = arr1;
                arr1 = temp;
            }
            
            int tgt = (arr1.length+arr2.length)/2;
            int mLow = 0;
            int mHigh = arr1.length;
            int nLow = 0;
            int nHigh = arr2.length;
            int nMed;
            
            while(nLow < nHigh-1) {
                nMed = (nLow+nHigh) >>> 1;
                int res = Arrays.binarySearch(arr1, mLow, mHigh, arr2[nMed]);
                // what if elements aren't distinct...
                
            }
            
            System.out.printf("Mlow: %d, Mhigh=%d, Nlow=%d, Nhigh=%d%n", mLow, mHigh, nLow, nHigh);
        }
    }
}
