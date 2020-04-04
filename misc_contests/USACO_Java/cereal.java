
import java.util.*;
import java.io.*;

public class cereal {

    static class Choice {
        int first;
        int second;
        
        Choice(int f, int s) {
            first = f;
            second = s;
        }
    }
    
    static class Mapping {
        int cow;
        int cereal;
        
        Mapping(int co, int ce) {
            cow = co;
            cereal = ce;
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("cereal.in"));
        PrintWriter out = new PrintWriter("cereal.out");

        int N = f.nextInt();
        int M = f.nextInt();
        
        // matching[i] is who has claimed this cereal.
        int[] matching = new int[M+1];
        Choice[] choices = new Choice[N+1];
        int[] answers = new int[N+1];
        
        for(int i = 0; i<N; ++i) {
            choices[i+1] = new Choice(f.nextInt(), f.nextInt());
        }
        f.close();
        
        ArrayDeque<Mapping> stack = new ArrayDeque<>();

        for(int i = N-1; i>-1; --i) {
            answers[i] = 1+answers[i+1];
            /*
            choices maps cow to cereal
            matching maps cereal to cow
            */

            int myChoiceCereal = choices[i+1].first;
            stack.push(new Mapping(i+1, myChoiceCereal));
            
            while(matching[myChoiceCereal] != 0) {
                int pastCow = matching[myChoiceCereal];
                // add index requirement
                if(choices[pastCow].first == myChoiceCereal) {
                    // switch to second choice
                    int comp = matching[choices[pastCow].second] == 0 
                            ? 0x7f7f7f7f : matching[choices[pastCow].second];
                    if(pastCow > comp) {
                        --answers[i];
                        break;
                    } 
                    stack.push(new Mapping(pastCow, choices[pastCow].second));
                    myChoiceCereal = choices[pastCow].second;
                } else {    
                    --answers[i];
                    break;
                }
            }
            while(!stack.isEmpty()) {
                Mapping mapping = stack.pop();
                matching[mapping.cereal] = mapping.cow;
            }
        }
        
        for(int i = 0; i<answers.length-1; ++i) {
            out.println(answers[i]);
        }
        out.close();
    }
}
