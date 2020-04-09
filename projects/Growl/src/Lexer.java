


import java.util.*;
import java.util.regex.*;

public class Lexer {

    public static enum LexType {
        OPERATOR, CONTROL, GROUP, TYPE, ID, LITERAL;
    }

    private static final LexType[] LEXTYPE_TABLE = LexType.values();

    public static class Lexeme {

        LexType type;
        Object subType; // either class or enum
        String token;
        int start;
        int end;

        Lexeme(LexType type, String token, int start, int end) {
            this.type = type;
            this.token = token;
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return String.format("%s-%s, %s", type.toString(), subType != null
                    ? subType.toString() : "Id", token);
        }
    }
    
    private static void printLexemes(List<Lexeme> lexemes) {
        for(Lexeme lex: lexemes) {
            System.out.println(lex);
        }
    }
    
    public static List<Lexeme> lex(String program) {
        List<Lexeme> lexemes = new ArrayList<>();
        final Matcher[] matchers = {LexTypes.Operator.OPERATOR_REGEX.matcher(program),
                                    LexTypes.Control.CONTROL_REGEX.matcher(program),
                                    LexTypes.Group.GROUP_REGEX.matcher(program),
                                    LexTypes.DataType.TYPE_REGEX.matcher(program),
                                    LexTypes.Id.ID_REGEX.matcher(program),
                                    LexTypes.LiteralType.LITERAL_REGEX.matcher(program)};

        for (int i = 0; i < matchers.length; ++i) {
            Matcher matcher = matchers[i];
            int ptr = 0;
            while (matcher.find(ptr)) {
                lexemes.add(new Lexeme(LEXTYPE_TABLE[i], matcher.group(),
                        matcher.start(), matcher.end()));
                ptr = matcher.end();
            }
        }

        Comparator<Lexeme> comp = (lex1, lex2) -> {
            if (lex1.start == lex2.start) {
                if (lex1.end == lex2.end) {
                    return lex1.type.ordinal() - lex2.type.ordinal();
                } else {
                    return lex2.end - lex1.end;
                }
            } else {
                return lex1.start - lex2.start;
            }
        };

        Collections.sort(lexemes, comp);
        List<Lexeme> out = new ArrayList<>();

        int ptr = 0;
        int lim;

        while (ptr < lexemes.size()) {
            out.add(lexemes.get(ptr));
            lim = 1 + lexemes.get(ptr++).end;
            while (ptr < lexemes.size() && lexemes.get(ptr).end < lim) {
                ++ptr;
            }
        }

        specifyLexemes(out);
        return out;
    }
    
    // either i dont understand java enums or theyre really screwed up
    private static void specifyLexemes(List<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {
            switch (lexeme.type) {
                case OPERATOR:
                    lexeme.subType = LexTypes.Operator.createOperator(lexeme.token);
                    break;
                case CONTROL:
                    lexeme.subType = LexTypes.Control.createControl(lexeme.token);
                    break;
                case GROUP:
                    lexeme.subType = LexTypes.Group.createGroup(lexeme.token);
                    break;
                case TYPE:
                    lexeme.subType = LexTypes.DataType.createDataType(lexeme.token);
                    break;
                case LITERAL:
                    lexeme.subType = LexTypes.LiteralType.createLiteral(lexeme.token);
                    break;
                default:
                    break;
            }
        }
    }
}
