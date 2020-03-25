
import java.util.*;

public class ArrayShrink {
    
    static class Node {
        int dat;
        Node front;
        Node back;
        
        public Node() {
            
        }
        
        public Node(int data) {
            dat = data;
        }
    }
    
    static class LinkList {
        Node start;
        Node ptr;
        
        public LinkList() {
            ptr = new Node();
            start = ptr;
        }
        
        public void push(int val) {
            ptr.dat = val;
            Node n = new Node();
            ptr.front = n;
            n.back = ptr;
            ptr = n;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node aux = start;
            while(aux != ptr) {
                sb.append(aux.dat);
                sb.append(' ');
                aux = aux.front;
            }
            return sb.toString();
        }
        
        public int size() {
            Node aux = start;
            int ctr = 0;
            while(aux != ptr) {
                ++ctr;
                aux = aux.front;
            }
            return ctr;
        }
    }
    
    
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
    
        LinkList list = new LinkList();
        for(int i = 0; i<N; ++i) {
            list.push(f.nextInt());
        }
        
        Node start;
        Node prev;
        boolean flag;
        
        do {
            start = list.start;
            prev = null;
            flag = false;
            while(start.front != null) {
                if(start.dat == start.front.dat) {
                    ++start.front.dat;
                    if(prev != null) {
                        prev.front = start.front;
                    } else {
                        list.start = start.front;
                    }
                    start = start.front;
                    flag = true;
                }
                prev = start;
                start = start.front;
            }
        } while(flag);
        System.out.println(list.size());
        f.close();
    }
}
