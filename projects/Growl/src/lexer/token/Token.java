
package lexer.token;

/**
 * Superclass for tokens. Easier way to represent and operate
 * other than strings.
 * 
 * @author mihir
 */
public abstract class Token {

    private static final char QUOTE = '"';
    private static final String FLOAT_REGEX = "(\\-)?\\d+\\.\\d+";
    
    private static boolean isString(String str) {
        return str.charAt(0) == QUOTE && str.indexOf(QUOTE, 1) == str.length()-1;
    }
    
    private static boolean isFloat(String str) {
        return str.matches(FLOAT_REGEX);
    }
    
    public static Token genToken(String str) {
        if(isString(str)) {
            return new StringToken(str);
        } else if(isFloat(str)) {
            return new FloatToken(str);
        } else {
            return new IntToken(str);
        }
    }
}
