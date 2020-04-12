
package parsetypes.ast;

import compiler.Lexer;
import lextypes.*;
import parsetypes.enums.OpType;

/**
 * Binary operator
 * 
 * @author mihir
 * @param <T> tag
 */
public class BinaryOp<T extends AST> extends Op<T> {

    public T op1;
    public T op2;

    @Override
    public OpType type() {
        return OpType.BINARY;
    }

    public BinaryOp(Lexer.Lexeme lexeme) {
        Operator ope = (Operator) lexeme.subType;
        operator = ope;
    }
}
