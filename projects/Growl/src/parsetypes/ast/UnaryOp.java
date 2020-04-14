/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsetypes.ast;

import lextypes.*;
import compiler.*;
import debug.Debug;
import parsetypes.enums.OpType;

/**
 *
 * @author mihir
 * @param <T> type
 */
// T allows customizing to a constexpr
public class UnaryOp<T extends AST> extends Op<T> {

    public T op;

    @Override
    public OpType type() {
        return OpType.UNARY;
    }

    public UnaryOp() {
    }
    
    public UnaryOp(Lexer.Lexeme lexeme) {
        Operator ope = (Operator) lexeme.subType;
        operator = ope;
    }
    
    @Override
    public int evalConstExpr() {
        if(op instanceof Expression) {
            int res = operator.evaluateUnary(((Expression) op).evalConstExpr());
            return res;
        }
        return Operator.UNSUPPORTED;
    }

    @Override
    public String display(int pos, int width) {
        return Debug.UNSUPPORTED;
    }
}
