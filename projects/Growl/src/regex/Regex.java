
package regex;

import utils.SmallArray;
import java.util.Arrays;

public class Regex {
    
    private static final int CHAR_SHIFT = 8;
    private static final int CHAR_MASK = 0xff;
    
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
        
        for(int pos = 0; pos<regex.length(); ++pos) {
            curr = regex.charAt(pos);
            switch(curr) {
                case '(':
                    break;
                case ')':
                    break;
                case '*':
                    break;
                case '?':
                    break;
                case '|':
                    break;
                default:
                    SmallArray sm = new SmallArray();
                    int place = nfas.add(sm);
                    nfas.get(currNFA).add(curr + (place << CHAR_SHIFT));
                    ++currNFA;
                    break;
            }
        }
        
        return nfas;
    }
    
    public static final boolean check(String string, NFAPool nfa) {
        SmallArray curr = nfa.get(0);
        // have we reached end yet
        int idx = 0;
        char ch;
        IntQueue queue = new IntQueue(nfa.size());
        while(curr.size() != 0) {
            ch = string.charAt(idx);
            for(int i = 0; i<curr.size(); ++i) {
                if((curr.get(i) & CHAR_MASK) == ch) {
                    
                }
            }
        }
    }
    
    public static void main(String[] args) {
        String regex = "abcde";
        NFAPool gen = genAutomaton(regex);
        System.out.println(gen);
    }
}
