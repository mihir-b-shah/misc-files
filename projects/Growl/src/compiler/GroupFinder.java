package compiler;


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
    
    static class Case {
        final int pos;
        final int lvl;
        
        Case(int p, int l) {
            pos = p;
            lvl = l;
        }
    }

    static class StringFinder {
        
        final String find;
        final String document;
        int pos;
        int ret;
        
        StringFinder(String find, String document) {
            this.find = find;
            this.document = document;
        }
        
        boolean hasNext() {
            ret = document.indexOf(find, pos);
            pos = ret+find.length();
            return ret != -1 && pos < document.length()-find.length();
        }
        
        int next() {
            return ret;
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
        
        StringFinder caseFinder = new StringFinder("case", document);
        // not optimal, could use an arraylist with skip spots.
        HashMap<Integer, Integer> currCase = new HashMap<>();
        int casePos = -1;
        
        for(int i = 0; i<document.length(); ++i) {
            if(casePos < i && caseFinder.hasNext()) {
                casePos = caseFinder.next();
            } else if(casePos == i) {
                int lvl = bracketStack.size();
                Integer prCase = currCase.get(lvl);
                if(prCase == null) {
                    currCase.put(lvl, casePos);
                } else {
                    lookup.put(prCase, casePos);
                    currCase.put(lvl, casePos);
                }
            }
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
    
    public static int findNextCase(int pos, int stop) {
        Integer ret = lookup.get(pos);
        return ret == null || ret >= stop ? stop : ret;
    }
}