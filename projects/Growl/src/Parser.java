
import java.util.List;

/**
 * A recursive descent parser
 * 
 * @author mihir
 */
public class Parser {
    public static class SyntaxTree {
      
    }
    /*
    Currently scans for these control flow patterns:
    
    1] If-else statement (nested for now)
    
    Token stream: IF ( [expr] ) { [body] } ELSE { [body] }
    
    2] While loop
    
    Token stream: WHILE ( [expr] ) { [body] }
    
    3] Function
    
    Token stream: TYPE ID ( TYPE , ID , ... ) { [body] RETURN ID OR LITERAL}
    
    4] Labeled block
    
    Token stream: ID : { [body] }
    
    5] Struct
    
    Token stream: STRUCT ID { TYPE , ID ; TYPE , ID ;...};
    
    6] Expression/Computation
    
    */
    public static SyntaxTree parse(List<Lexer.Lexeme> lexemes) {
        int ptr = 0;
        while(ptr < lexemes.size()) {
            Lexer.Lexeme lexeme = lexemes.get(ptr);
            switch(lexeme.type) {
                case CONTROL:
                    break;
                case ID:
                    break;
                case TYPE:
            }
        }
        
    }
}
