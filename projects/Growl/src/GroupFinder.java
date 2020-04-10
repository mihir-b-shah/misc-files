
import java.util.HashMap;

/**
 * Find instances of puntctuation
 * 
 * @author mihir
 */
public class GroupFinder {
    
    private static String document;
    private static final HashMap<Integer, Integer> lookup;

    static {
        lookup = new HashMap<>();
    }
    
    static class Stack<T> {
        private T[] array;
        private int ctr;

        Stack() {
            array = (T[]) new Object[10];
        }

        void push(T obj) {
            if(ctr == array.length) {
                T[] aux = (T[]) new Object[ctr << 1];
                System.arraycopy(array, 0, aux, 0, ctr);
                array = aux;
            }
            array[ctr++] = obj;
        }

        T pop() {
            return array[--ctr];
        }
    }
    
    public static void initialize(String document) {
        GroupFinder.document = document;

        // note this is my stack not java.util.Stack
        // not optimizing this for primitives noooooooooo
        Stack<Integer> parenStack = new Stack<>();
        Stack<Integer> bracketStack = new Stack<>();
        
        int commaPos = -1;
        int semiPos = -1;
        int pos;
        
        for(int i = 0; i<document.length(); ++i) {
            switch(document.charAt(i)) {
                case '(':
                    parenStack.push(i);
                    break;
                case ')':
                    pos = parenStack.pop();
                    lookup.put(pos, i);
                    break;
                case '{':
                    bracketStack.push(i);
                    break;
                case '}':
                    pos = bracketStack.pop();
                    lookup.put(pos, i);
                    break;
                case ',':
                    lookup.put(commaPos, i);
                    commaPos = i;
                    break;
                case ';':
                    lookup.put(semiPos, i);
                    semiPos = i;
                    break;
            }
        }
        // remove the random key we inserted
        lookup.remove(-1);
    }

    public static int findMatch(int pos) {
        return lookup.get(pos);
    }
}