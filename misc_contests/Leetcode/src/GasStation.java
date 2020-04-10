
import java.util.ArrayList;
import java.util.Scanner;

public class GasStation {
    
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        loop: while(true) {
            int N = f.nextInt();
            if(N == -1) {
                break;
            }
            int[] gas = new int[N];
            int[] cost = new int[N];
            for(int i = 0; i<N; ++i) {
                gas[i] = f.nextInt();
            }
            for(int i = 0; i<N; ++i) {
                cost[i] = f.nextInt();
            }
            int[] aux = new int[1+gas.length*2];
            for(int i = 0; i<gas.length; ++i) {
                aux[1+i] = gas[i]-cost[i]+aux[i];
                aux[1+i+gas.length] = gas[i]-cost[i];
            }
            for(int i = 1+gas.length; i<aux.length; ++i) {
                aux[i] += aux[i-1];
            }

            ArrayList<Integer> deque = new ArrayList<>();
            int removePtr = 0;
            
            for(int i = 1; i<gas.length+1; ++i) {
                while(deque.size() > 0 && aux[deque.get(deque.size()-1)] >= aux[i]) {
                    deque.remove(deque.size()-1);
                }
                deque.add(i);
            }

            int min = aux[deque.get(removePtr)];
            if(min >= 0) {
                System.out.println(0);
                continue;
            }
            
            for(int i = 1; i<gas.length; ++i) {
                if(deque.get(removePtr) == i) {
                    ++removePtr;
                }
                while(deque.size() > removePtr && aux[deque.get(deque.size()-1)] >= aux[i+gas.length]) {
                    deque.remove(deque.size()-1);
                }
                deque.add(i+gas.length);
                min = aux[deque.get(removePtr)]-aux[i];
                if(min >= 0) {
                    System.out.println(i);
                    continue loop;
                }
            }
            System.out.println(-1);
        }
    }
}
