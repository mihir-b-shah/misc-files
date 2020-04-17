
package parser.parsetypes.ast;

import lexer.LexType;
import lexer.Lexeme;
import lexer.lextypes.LiteralType;
import lexer.token.StringToken;

/**
 * @author mihir
 */
public class Literal extends Expression {
    public LiteralType type;
    public String value;

    public Literal() {
    }

    public Literal(Lexeme lexeme) {
        assert(lexeme.type == LexType.LITERAL);
        type = ((LiteralType) lexeme.subType);
        value = ((StringToken) lexeme.token).token;
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
