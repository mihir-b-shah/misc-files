
package parsetypes.ast;

import compiler.Lexer;
import debug.Debug;
import lextypes.LiteralType;
import lextypes.Operator;

/**
 *
 * @author mihir
 */
public class Variable extends Expression {
    public LiteralType type;
    public String id;

    public Variable() {
    }

    public Variable(Lexer.Lexeme lexeme) {
        assert(lexeme.type == Lexer.LexType.ID);
        type = ((LiteralType) lexeme.subType);
        id = lexeme.token;
    }

    @Override
    public int evalConstExpr() {
        return Operator.UNSUPPORTED;
    }

    @Override
    public String display(int pos, int width) {
        return Debug.UNSUPPORTED;
    }
}