
import java.util.*;
import java.io.*;

public class Cross {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("cross.dat"));
        int N = f.nextInt();
        f.nextLine();
        for(int i = 0; i < N ;i++){
            String raw[] = f.nextLine().split(" ");
            String w1 = raw[0];
            String w2 = raw[2];
            String[] w2s = w2.split("");
            int one = -1;
            int two = -1;
            boolean found = false;
            for(int k = 0; k < w2s.length; k++){
                int temp = w1.indexOf(w2s[k]);
                if(temp > -1){
                    one = temp;
                    two = k;
                }
            }
            if(one == -1){
                System.out.println("NONE");
            }else{
                for(int p = 0; p < w2.length(); p++){
                    if(p == two){
                        System.out.println(w1);
                    }else{
                        for(int y = 0; y < one; y++){
                            System.out.print(" ");
                        }
                        System.out.print(w2s[p] + "\n");
                    }
                }
            }
            System.out.println("");
            
        }
        f.close();
    }
}
