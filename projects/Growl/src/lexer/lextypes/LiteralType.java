
package lexer.lextypes;

import java.util.regex.Pattern;

/**
 * @author mihir
 */
public enum LiteralType {
    BOOL_TRUE, BOOL_FALSE, INT, STRING, CHAR, FLOAT;

    public static final Pattern LITERAL_REGEX = Pattern.compile("(true)|(false)|(\".*\")"
            + "|('(\\\\)?.')|(((\\-)?(0b[01]+)|((\\-)?0x[0-9a-f]+)|((\\-)?[0-9]+))[SL]?)|((\\-)?\\d+(\\.\\d+)?)");

    public static LiteralType createLiteral(String s) {
        switch (s.charAt(0)) {
            case 't':
                return LiteralType.BOOL_TRUE;
            case 'f':
                return LiteralType.BOOL_FALSE;
            case '\'':
                return LiteralType.CHAR;
            case '"':
                return LiteralType.STRING;
            default:
                if (s.indexOf('.') == -1) {
                    return LiteralType.INT;
                } else {
                    return LiteralType.FLOAT;
                }
        }
    }
}