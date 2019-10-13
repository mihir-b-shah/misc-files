
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class lifeguards {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("lifeguards.in"));
        PrintWriter out = new PrintWriter("lifeguards.out");
        
        f.nextInt();
        int max = 0;
        
        while(f.hasNextInt()) {
            int num = f.nextInt();
            if(num > max) {max = num;}
        }
        
        f.close();
        f = new Scanner(new File("lifeguards.in"));
        
        int toCome = f.nextInt();        
        boolean[][] ranges = new boolean[toCome][max];
        
        for(int i = 0; i<toCome; i++) {
            int low = f.nextInt();
            int hi = f.nextInt();
            
            for(int j = low; j< hi; j++) {
                ranges[i][j] = true;
            }
        }
        
        int[] scores = new int[toCome];
        int min_score = Integer.MAX_VALUE;
        boolean found;
        boolean[] cov = new boolean[max];
        int c = 0;
        
        for(int i = 0; i<toCome; i++) {
            for(int j = 0; j<max; j++) {
                
                if(ranges[i][j] && !cov[j]) {
                    c++;
                    cov[j] = true;
                }
                
                found = false;
                if(ranges[i][j]) {
                    for(int k = 0; k<toCome; k++) {
                        if(k != i) {
                            if(ranges[k][j]) {
                                found = true;
                                break;
                            }
                        }
                    }
                    
                    if(!found)
                        scores[i]++;
                } 
                
            }
            
            if(scores[i] < min_score)
                min_score = scores[i];
        }
        
        out.println(c - min_score);
        out.close();
        
        f.close();
    }
}
