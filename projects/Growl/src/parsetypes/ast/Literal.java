
package parsetypes.ast;

import compiler.Lexer;
import lextypes.LiteralType;

/**
 *
 * @author mihir
 */
public class Literal extends Expression {
    public LiteralType type;
    public String value;

    public Literal() {
        tree = null;
    }

    public Literal(Lexer.Lexeme lexeme) {
        this();
        assert(lexeme.type == Lexer.LexType.LITERAL);
        type = ((LiteralType) lexeme.subType);
        value = lexeme.token;
    }
}
