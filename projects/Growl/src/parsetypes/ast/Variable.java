
package parsetypes.ast;

import compiler.Lexer;
import lextypes.LiteralType;

/**
 *
 * @author mihir
 */
public class Variable extends Expression {
    LiteralType type;
    String id;

    Variable() {
        tree = null;
    }

    Variable(Lexer.Lexeme lexeme) {
        this();
        assert(lexeme.type == Lexer.LexType.ID);
        type = ((LiteralType) lexeme.subType);
        id = lexeme.token;
    }
}