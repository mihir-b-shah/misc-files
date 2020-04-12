
package parsetypes.ast;

import compiler.Lexer;

/**
 *
 * @author mihir
 */
public class Expression extends AST {
    public Op<Expression> tree;

    public static Expression genLitVal(Lexer.Lexeme lexeme) {
        switch(lexeme.type) {
            case LITERAL:
                return new Literal(lexeme);
            case ID:
                return new Variable(lexeme);
            default:
                return null;
        }
    }
}
