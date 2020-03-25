
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class stringsort {
    
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);       
        
        int N = f.nextInt();
        int K = f.nextInt();
        
        TreeSet<Integer>[] out = new TreeSet[N];

        for(int i = 0; i<N; ++i) {
            out[i] = new TreeSet<>();
        }
        
        int prev = f.nextInt();
        for(int i = 1; i<N; ++i) {
            int num = f.nextInt();
            out[prev-1].add(num-1);
            prev = num;
        }
        
        prev = f.nextInt();
        for(int i = 1; i<N; ++i) {
            int num = f.nextInt();
            out[prev-1].add(num-1);
            prev = num;
        }
        
        char[] pos = new char[N];
        Arrays.fill(pos, (char) 0xFF);
        boolean oops = false;
        pos[0] = 'a';
        
        outer: for(int i = 0; i<N; ++i) {
            if(!out[i].isEmpty()) {
                for(int j: out[i]) {
                    if(pos[j] < pos[i]+1) {
                        break outer;
                    } else {
                        pos[j] = (char) (pos[i]+1);
                    }
                }
            }
        }

        System.out.println("YES");
        System.out.println(new String(pos));
    }
    
}
