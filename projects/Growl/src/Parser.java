
import java.util.List;

/**
 * A recursive descent parser
 * 
 * @author mihir
 */
public class Parser {
    
    private static final String SEMICOLON = ";";
    
    static List<Lexer.Lexeme> lexemes;


    /*
    Currently scans for these control flow patterns:
    
    1] If-else statement (nested for now)
    
    Token stream: IF ( [expr] ) { [body] } ELSE { [body] }

    2] While loop
    
    Token stream: WHILE ( [expr] ) { [body] }
    
    3] Function definition
    
    Token stream: TYPE ID ( TYPE , ID , ... ) { [body] RETURN ID OR LITERAL}
    
    4] Labeled block
    
    Token stream: ID : { [body] }
    
    5] Struct definition
    
    Token stream: STRUCT ID { TYPE , ID ; TYPE , ID ;...};
    
    6] Switch block
    
    Token stream: SWITCH { case LITERAL : [comp]... } 
    
    7] Expression/Computation
    
    */
    public static ParseTypes.AST parse(List<Lexer.Lexeme> lexemes) {
        Parser.lexemes = lexemes;
        return parseHelper(0, lexemes.size());
    }
    
    // inclusive, exclusive bounds
    private static ParseTypes.AST parseHelper(int start, int end) {
        final Lexer.Lexeme lex1 = lexemes.get(start);
        final Lexer.Lexeme lex2;
        switch(lex1.type) {
            case ID:
                // i know this is inefficient but its good coding practice
                if(start>end-2) {
                    break;
                }
                lex2 = lexemes.get(start+1);
                if(lex2.subType.getClass() == LexTypes.Group.class
                        && ((LexTypes.Group) lex2.subType) == LexTypes.Group.COLON) {
                    // labeled block
                }
                break;
            case CONTROL:
                switch((LexTypes.Control) lex1.subType) {
                    case IF:
                        // if/else statement
                        break;
                    case SWITCH:
                        // switch statement
                        break;
                    case WHILE:
                        // while statement
                        break;
                }
            case TYPE:
                if(start>end-3) {
                    break;
                }
                lex2 = lexemes.get(start+1);
                final Lexer.Lexeme lex3 = lexemes.get(start+2);
                if(lex2.subType.getClass() == LexTypes.Id.class 
                        && lex3.subType.getClass() == LexTypes.Group.class 
                        && ((LexTypes.Group) lex3.subType) == LexTypes.Group.OPEN_PAREN) {
                    // function definition
                } else if(((LexTypes.DataType) lex1.subType).base 
                        == LexTypes.DataType.BaseType.STRUCT) {
                    // struct declaration
                }
                break;
        }
        return null;
    }
}
