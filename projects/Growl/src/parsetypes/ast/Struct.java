
package parsetypes.ast;

import debug.Debug;
import lextypes.Operator;

/**
 *
 * @author mihir
 */
public class Struct extends Value {

    Expression supplier;
    StructDef definition;

    @Override
    public int evalConstExpr() {
        return Operator.UNSUPPORTED;
    }

    @Override
    public String display(int pos, int width) {
        return Debug.UNSUPPORTED;
    }
}
