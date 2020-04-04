
import java.util.*;
import java.io.*;

public class Words {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("words.dat"));
        String[] s = f.nextLine().split(" ");   
        /*
        String ans = "";
        for(String x: s){
            x = x.replaceAll("[0-9]", "");
            String punc = "";
            while(!x.matches("\\W")){
                punc += x.substring(x.length()-1)+ punc;
                x = x.substring(0, x.length()-1);
            }
            
            StringBuilder temp = new StringBuilder(x);
            ans += temp.reverse() + punc + " ";
        }
        System.out.println(ans);
        */
        StringBuilder sb = new StringBuilder();
        for(String str: s) {
            str = str.replaceAll("\\d", "");
            String aux = str.replaceAll("\\W+", "");
            str = (new StringBuilder(aux)).reverse().toString()+str.substring(aux.length());
            sb.append(String.format("%s ", str));
        }
        
        sb.trimToSize();
        System.out.println(sb.toString());
        
        f.close();
    }
}
