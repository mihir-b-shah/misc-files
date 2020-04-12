
package parsetypes.ast;

import lextypes.*;
import parsetypes.enums.OpType;

/**
 * Base class for operator.
 * 
 * @author mihir
 */
abstract class Op<T extends AST> extends Expression {
        
    Operator operator;
    abstract OpType type();
}
