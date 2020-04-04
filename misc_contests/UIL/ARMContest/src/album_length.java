
import java.util.*;
import java.io.*;

public class album_length {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("album_length.dat"));
        int a = f.nextInt();
        f.nextLine();
        int val = 0;
        for(int i = 0; i < a; i++){
            String[] dat = f.nextLine().split("\\D+");
            val += Integer.parseInt(dat[1]) * 60 + Integer.parseInt(dat[2]);
        }
 
        System.out.printf("The album length is %d:%02d\n", val / 60, val% 60);
        f.close();
    }
}

