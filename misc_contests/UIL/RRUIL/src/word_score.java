
import java.util.*;
import java.io.*;

public class word_score {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("word_score.dat"));
        while(f.hasNextLine()) {
            String str = f.nextLine();
            String[] split = str.split(" ");
            
            String maxStr = null;
            int maxScore = -1;
            
            for(String s: split) {
                int score = s.length()/2
                        +s.replaceAll("[^AaEeIiOoUu]+", "").length()
                        +s.replaceAll("[^Zz]+", "").length()*2
                        +s.replaceAll("[^XxQq]+", "").length()*3;
                if(score > maxScore) {
                    maxStr = s;
                    maxScore = score;
                }
            }
           
            System.out.printf("%s - %d%n", maxStr, maxScore);
        }
        f.close();
    }
}
