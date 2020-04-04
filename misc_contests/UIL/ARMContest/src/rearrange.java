
import java.util.*;
import java.io.*;

public class rearrange {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("rearrange.dat"));
        while(f.hasNextLine()){
            String[] dat = f.nextLine().split(" ");
            for(int i = 0; i < dat.length; i++){
                if(dat[i].length() >= 3){
                    if(dat[i].length() %2 ==1){
                        System.out.print(dat[i].charAt(dat[i].length()-1) + dat[i].substring(1, dat[i].length()-1) + dat[i].charAt(0));
                    }else{
                        char[] word = dat[i].toCharArray();
                        
                        char temp = word[0];
                        char temp1 = word[word.length-1];
                        word[0] = word[word.length/2-1];
                        word[word.length-1] = word[word.length/2];
                        word[word.length/2-1] = temp;
                        word[word.length/2] = temp1;
                        String out = "";
                        for(char c: word){
                            out +=c;
                        }
                        System.out.print(out);
                       
                    }
                } else{
                    System.out.print(dat[i]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        f.close();
    }
}
