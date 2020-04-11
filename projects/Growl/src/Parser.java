
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
            int parenClose,brackClose;

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
                            parenClose = GroupFinder.findMatch(parsePtr+1);
                            ParseTypes.Bool filter = new ParseTypes.Bool();
                            filter.supplier = parseExpr(parsePtr+2, parenClose);
                            // curly brace next.
                            brackClose = GroupFinder.findMatch(parenClose+1);
                            ParseTypes.AST body = parseAST(parenClose+2, brackClose);
                            block.condition = filter;
                            block.ifBranch = body;
                            parsePtr = brackClose+1;
                            if(lexemes.get(parsePtr).type == Lexer.LexType.CONTROL
                                && (((LexTypes.Control)(lexemes.get(parsePtr)).subType))
                                    == LexTypes.Control.ELSE) {
                                // continue parsing the else statement
                                brackClose = GroupFinder.findMatch(parsePtr+1);
                                body = parseAST(parsePtr+2, brackClose);
                                block.elseBranch = body;
                                parsePtr = brackClose+1;
                            }
                            break;
                        case SWITCH:
                            // switch statement
                            parenClose = GroupFinder.findMatch(parsePtr+1);
                            ParseTypes.Switch switchBlock = new ParseTypes.Switch();
                            ParseTypes.Primitive switchExpr = 
                                    (ParseTypes.Primitive) parseExpr(parsePtr+2, parenClose);
                            switchBlock.switchVal = switchExpr;
                            int switchPtr = parenClose+2;
                            int endPtr;
                            brackClose = GroupFinder.findMatch(parenClose+1);
                            while(switchPtr < brackClose && 
                                    lexemes.get(switchPtr).subType == LexTypes.Control.class
                                 && ((LexTypes.Control) lexemes.get(switchPtr).subType) 
                                    == LexTypes.Control.CASE) {
                                Lexer.Lexeme literal = lexemes.get(switchPtr+1);
                                switchBlock.cases.add(new ParseTypes.Literal(literal));
                                endPtr = GroupFinder.findNextCase(switchPtr+2, brackClose);
                                ParseTypes.AST jump = parseAST(switchPtr+3, endPtr);
                                switchBlock.jumps.add(jump);
                                if(endPtr == brackClose) {
                                    break;
                                }
                                switchPtr = endPtr;
                            }
                            // check for default case
                            if(switchPtr < brackClose) {
                                ParseTypes.AST jump = parseAST(switchPtr+2, brackClose);
                                switchBlock.jumps.add(jump);
                            }
                            break;
                        case WHILE:
                            // while statement
                            ParseTypes.Loop whileBlock = new ParseTypes.Loop(); 
                            // correct type will parse until the curly brace open/close
                            parenClose = GroupFinder.findMatch(parsePtr+1);
                            ParseTypes.Bool whileFilter = new ParseTypes.Bool();
                            whileFilter.supplier = parseExpr(parsePtr+2, parenClose);
                            // curly brace next.
                            brackClose = GroupFinder.findMatch(parenClose+1);
                            ParseTypes.AST whileBody = parseAST(parenClose+2, brackClose);
                            whileBlock.condition = whileFilter;
                            whileBlock.body = whileBody;
                            parsePtr = brackClose+1;
                            break;
                    }
                case TYPE:
                    if(parsePtr>end-3) {
                        break;
                    }
                    lex2 = lexemes.get(parsePtr+1);
                    final Lexer.Lexeme lex3 = lexemes.get(parsePtr+2);
                    if(lex2.subType.getClass() == LexTypes.Id.class 
                            && lex3.subType.getClass() == LexTypes.Group.class 
                            && ((LexTypes.Group) lex3.subType) == LexTypes.Group.OPEN_PAREN) {
                        // function definition
                        ParseTypes.FunctionDef func = new ParseTypes.FunctionDef();
                        int paramEnd = GroupFinder.findMatch(parsePtr+3);
                        parsePtr += 4;
                        while(parsePtr < paramEnd) {
                            ParseTypes.Parameter param = new ParseTypes.Parameter();
                            param.type = (LexTypes.DataType) (lexemes.get(parsePtr).subType);
                            param.id = lexemes.get(parsePtr+1).token;
                            func.parameters.add(param);
                            parsePtr += 2;
                        }
                        brackClose = GroupFinder.findMatch(paramEnd+1);
                        ParseTypes.AST funcBody = parseAST(paramEnd+2, brackClose);
                        func.body = funcBody;
                        parsePtr = brackClose+1;
                        // fix func.return() statement
                    } else if(((LexTypes.DataType) lex1.subType).base 
                            == LexTypes.DataType.BaseType.STRUCT) {
                        // struct declaration
                        ParseTypes.StructDef struct = new ParseTypes.StructDef();
                        struct.name = lex1.token.substring(7);
                        brackClose = GroupFinder.findMatch(parsePtr+1);
                        while(parsePtr < brackClose) {
                            ParseTypes.Parameter param = new ParseTypes.Parameter();
                            param.type = (LexTypes.DataType) (lexemes.get(parsePtr).subType);
                            param.id = lexemes.get(parsePtr+1).token;
                            struct.params.add(param);
                            parsePtr += 2;
                        }
                        parsePtr = brackClose+1;
                    }
                    break;
            }
        }

        return null;
    }
    
    // supports all operators on INTEGER types.
    // so operands can be i8, i32, i64, f64
    private static ParseTypes.Expression parseExpr(int start, int end) {
        
        return null;
    }
}
