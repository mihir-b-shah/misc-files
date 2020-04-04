
import java.util.*;
import java.io.*;

public class Sort {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("sort.dat"));
        ArrayList<String> list = new ArrayList<String>();
        while(f.hasNextLine()){
            list.add(f.nextLine());
        }
        
        String[] array = new String[list.size()];
        list.toArray(array);

        int front = 0;
        int back = array.length/2;
        
        while(front != back) {
            Arrays.sort(array, front, back);
            front = back;
            back = (array.length-back)/2 + back;
        }
        
        for(String s: array) {
            String repl = s.replaceAll("(\\w+)(\\s)(\\w+)", "$3$2$1");
            System.out.println(repl);
        }
        f.close();
    }
        
    }