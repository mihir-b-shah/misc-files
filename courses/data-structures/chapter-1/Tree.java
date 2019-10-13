
import java.util.Scanner;
import java.util.Stack;

public class Tree {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        Stack<Integer> visited = new Stack<>();
        
        int toCome = s.nextInt();
        int maxHeight = 0;
        int[] dp = new int[toCome];

        int[] nodeArr = new int[toCome];

        for (int i = 0; i < toCome; i++) {
            nodeArr[i] = s.nextInt();
        }
        
        int pointer = 0;
        int count = 0;

        do {
            count = 0;

            while (pointer != -1) {
                if(dp[pointer] == 0) {
                    count++;
                    visited.add(pointer);
                    pointer = nodeArr[pointer];
                } else {
                    count += dp[pointer];
                    break;
                }
            }

            for(int i = count-visited.size()+1; i<=count; i++) {
                dp[visited.pop()] = i;
            }
            
            if (count > maxHeight) {
                maxHeight = count;
            }

        } while ((pointer = checkVisited(dp)) != -1);

        System.out.println(maxHeight);
    }

    public static int checkVisited(int[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == 0) {
                return i;
            }
        }

        return -1;
    }
}
