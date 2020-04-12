
package parsetypes.ast;

import compiler.Lexer;
import lextypes.*;
import parsetypes.enums.OpType;

/**
 * Binary operator
 * 
 * @author mihir
 */
class BinaryOp<T extends AST> extends Op<T> {

    T op1;
    T op2;

    @Override
    OpType type() {
        return OpType.BINARY;
    }

    BinaryOp(Lexer.Lexeme lexeme) {
        Operator ope = (Operator) lexeme.subType;
        operator = ope;
    }
}
