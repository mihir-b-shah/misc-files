
import java.util.List;

/**
 * A recursive descent parser
 * 
 * @author mihir
 */
public class Parser {

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
        return parseAST(0, lexemes.size());
    }
    
    // inclusive, exclusive bounds
    private static ParseTypes.AST parseAST(int start, int end) {
        int parsePtr = start;
        while(parsePtr < end) {
            final Lexer.Lexeme lex1 = lexemes.get(parsePtr);
            final Lexer.Lexeme lex2;
            int match;
            ParseTypes.AST result;

            switch(lex1.type) {
                case ID:
                    if(start>end-2) {
                        break;
                    }
                    lex2 = lexemes.get(parsePtr+1);
                    if(lex2.subType.getClass() == LexTypes.Group.class
                            && ((LexTypes.Group) lex2.subType) == LexTypes.Group.COLON) {
                        // labeled block
                        ParseTypes.Label block = new ParseTypes.Label(); 
                        // correct type will parse until the curly brace open/close
                        match = GroupFinder.findMatch(parsePtr+2);
                        result = parseAST(parsePtr+3, match);
                        block.block = result;
                        block.label = lex1.token;
                        parsePtr = match+1;
                    }
                    break;
                case CONTROL:
                    switch((LexTypes.Control) lex1.subType) {
                        case IF:
                            // if/else statement
                            ParseTypes.Branch block = new ParseTypes.Branch(); 
                            // correct type will parse until the curly brace open/close
                            int parenClose = GroupFinder.findMatch(parsePtr+1);
                            ParseTypes.Bool filter = new ParseTypes.Bool();
                            filter.supplier = parseExpr(parsePtr+2, parenClose);
                            // curly brace next.
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
        }

        return null;
    }
    
    private static ParseTypes.Expression parseExpr(int start, int end) {
        return null;
    }
}
