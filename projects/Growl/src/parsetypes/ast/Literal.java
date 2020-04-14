
package parsetypes.ast;

import compiler.Lexer;
import debug.Debug;
import lextypes.LiteralType;

/**
 *
 * @author mihir
 */
public class Literal extends Expression {
    public LiteralType type;
    public String value;

    public Literal() {
    }

    public Literal(Lexer.Lexeme lexeme) {
        assert(lexeme.type == Lexer.LexType.LITERAL);
        type = ((LiteralType) lexeme.subType);
        value = lexeme.token;
    }

    @Override
    public int evalConstExpr() {
        return Integer.parseInt(value);
    }

    @Override
    public String display(int pos, int width) {
       return value;
    }
}
