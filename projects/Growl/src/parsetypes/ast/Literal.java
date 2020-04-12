
package parsetypes.ast;

import compiler.Lexer;
import lextypes.LiteralType;

/**
 *
 * @author mihir
 */
public class Literal extends Expression {
    LiteralType type;
    String value;

    Literal() {
        tree = null;
    }

    Literal(Lexer.Lexeme lexeme) {
        this();
        assert(lexeme.type == Lexer.LexType.LITERAL);
        type = ((LiteralType) lexeme.subType);
        value = lexeme.token;
    }
}
