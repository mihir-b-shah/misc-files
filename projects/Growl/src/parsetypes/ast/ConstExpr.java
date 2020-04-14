
package parsetypes.ast;

import lextypes.Operator;
import debug.Debug;

/**
 *
 * @author mihir
 */
public class ConstExpr extends Expression {
    Op<ConstExpr> tree;

    @Override
    public int evalConstExpr() {
        return Operator.UNSUPPORTED;
    }

    @Override
    public String display(int pos, int width) {
        return Debug.UNSUPPORTED;
    }
}
