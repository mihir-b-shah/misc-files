
package lextypes;

import java.util.regex.Pattern;

/**
 * Group enum
 * 
 * @author mihir
 */
public enum Group {
    OPEN_PAREN, CLOSE_PAREN, COLON, OPEN_BRACKET,
    CLOSE_BRACKET, COMMA, SEMICOLON;

    public static final Pattern GROUP_REGEX = Pattern.compile(":|\\{|\\}|,|\\(|\\)|;");

    public static Group createGroup(String s) {
        switch(s) {
            case "(":
                return Group.OPEN_PAREN;
            case ")":
                return Group.CLOSE_PAREN;
            case ":":
                return Group.COLON;
            case "{":
                return Group.OPEN_BRACKET;
            case "}":
                return Group.CLOSE_BRACKET;
            case ",":
                return Group.COMMA;
            case ";":
                return Group.SEMICOLON;
            default:
                return null;
        }
    }
}