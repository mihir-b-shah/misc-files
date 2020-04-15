
import java.util.Scanner;

/**
 *
 * @author mihir
 */
public class MedianSortedArray {
    
    public static double median(int[] M, int[] N) {
        int nLow = 0; 
        int nHigh = N.length;
        int mLow = 0;
        int mHigh = M.length;
        int nMed;
        int mMed;
        int tgt = (M.length+N.length)/2;
        
        boolean flag = true;
        
        while(flag) {
            nMed = (nLow + nHigh) >>> 1;
            mMed = (mLow + mHigh) >>> 1;
            
            if(M[mMed] < N[nMed]) {
                if(mMed < tgt) {
                    mLow = mMed+1;
                    nHigh = nMed;
                }
                tgt -= mMed;
            } else if(M[mMed] > N[nMed]) {
                if(nMed + M.length > tgt) {
                    mHigh = mMed;
                    nLow = nMed+1;
                }
                tgt -= nMed;
            } else {
                break;
            }
        }
        return 0.0;
    }
    
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
            
            double med = median(arr1, arr2);
        }
    }
}
