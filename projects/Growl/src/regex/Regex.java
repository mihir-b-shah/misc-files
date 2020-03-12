
package regex;

import utils.vector.IntStack;
import utils.vector.SmallArray;
import utils.queue.IntQueue;
import java.util.Arrays;

public class Regex {
    
    private static final int CHAR_SHIFT = 8;
    private static final int CHAR_MASK = 0xff;
    private static final char EPS_TRANSITION = '\0';
    
    /**
     * Growth factor 1.5, dynamic list.
     */
    static class NFAPool {
        private SmallArray[] nfas;
        private int ptr;
        
        public NFAPool() {
            nfas = new SmallArray[6];
        }
        
        public final int add(SmallArray nfa) {
            if(nfas.length == ptr) {
                SmallArray[] aux = new SmallArray[(int) (ptr*1.5)];
                System.arraycopy(nfas, 0, aux, 0, ptr);
                nfas = aux;
            }
            nfas[ptr] = nfa;
            return ptr++;
        }
        
        public final SmallArray get(int i) {
            return nfas[i];
        }
        
        public final int size() {
            return ptr;
        }
        
        @Override
        public String toString() {
            return Arrays.toString(nfas);
        }
    }

    public static final NFAPool genAutomaton(String regex) {
        NFAPool nfas = new NFAPool();
        nfas.add(new SmallArray()); //initial nfa
        int currNFA = 0;
        char curr;
        
        IntStack positions = new IntStack(regex.length());
        
        for(int pos = 0; pos<regex.length(); ++pos) {
            curr = regex.charAt(pos);
            switch(curr) {
                case '(':
                    positions.push(pos);
                    break;
                case ')':
                    int st = positions.pop()+1;
                    // from [st,pos) gen automaton
                    break;
                case '+':
                    nfas.get(currNFA).add(regex.charAt(pos-1) + (currNFA << CHAR_SHIFT));
                    break;
                case '?':
                    nfas.get(currNFA-1).add(EPS_TRANSITION + (currNFA << CHAR_SHIFT));
                    break;
                case '|':
                    break;
                default:
                    // concatenate with current NFA
                    SmallArray sm = new SmallArray();
                    int place = nfas.add(sm);
                    nfas.get(currNFA).add(curr + (place << CHAR_SHIFT));
                    currNFA = place;
                    break;
            }
        }
        
        return nfas;
    }
    
    public static final boolean check(String string, NFAPool nfa) {
        SmallArray curr;
        char ch;
        int packet;
        int currVal;
        
        IntQueue queue = new IntQueue(nfa.size());
        queue.push(0);

        while(queue.size() > 0) {
            packet = queue.pop();
            
            /* 
            System.out.printf("String pos: %d/%d, NFA pos: %d/%d%n", 
            packet >>> CHAR_SHIFT, string.length(), 
            packet & CHAR_MASK, nfa.size());
            */
            
            if(packet >= (string.length() << CHAR_SHIFT) + nfa.size()-1) {
                return true;
            }

            ch = packet >>> CHAR_SHIFT < string.length() 
                    ? string.charAt(packet >>> CHAR_SHIFT) : '\0';
            curr = nfa.get(packet & CHAR_MASK);
            
            for(int i = 0; i<curr.size(); ++i) {
                if(((currVal = curr.get(i)) & CHAR_MASK) == ch) {
                    queue.push((currVal >>> CHAR_SHIFT)
                                +(1+(packet >>> CHAR_SHIFT) << CHAR_SHIFT));
                } else if((currVal & CHAR_MASK) == 0) {
                    queue.push((currVal >>> CHAR_SHIFT)
                                +(packet >>> CHAR_SHIFT << CHAR_SHIFT));
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        String regex = "ab+cqe?";
        NFAPool gen = genAutomaton(regex);
        System.out.println(gen);
        System.out.println(check("abbbbbcqee", gen));
    }
}
