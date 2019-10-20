
package old;
import java.util.Scanner;

class Main {
/*
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        
        while(s.hasNextLine())
        {
            char[] x = s.nextLine().toCharArray();
            boolean addLast = true;
            int index = 0;
            
            for(int i = 0; i<x.length; i++)
            {
                if(x[i] == '[')
                {
                    addLast = false;
                    index = 0;
                } else if(x[i] == ']') {
                    addLast = true;
                    index = 0;
                } else {
                    if(addLast) {
                        sb.append(x[i]);
                    } else {
                        sb.insert(index, x[i]);
                        index++;
                    }
                }
            }
            
            System.out.println(sb);
            sb.delete(0, sb.length());
        }
        
        s.close();
        
    }
}
    
    
