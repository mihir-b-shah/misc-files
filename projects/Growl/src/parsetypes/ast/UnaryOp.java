/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsetypes.ast;

import parsetypes.ast.Op;
import lextypes.*;
import compiler.*;
import parsetypes.enums.OpType;

/**
 *
 * @author mihir
 */
// T allows customizing to a constexpr
class UnaryOp<T extends AST> extends Op<T> {

    T op;

    @Override
    OpType type() {
        return OpType.UNARY;
    }

    UnaryOp(Lexer.Lexeme lexeme) {
        Operator ope = (Operator) lexeme.subType;
        operator = ope;
    }
}
