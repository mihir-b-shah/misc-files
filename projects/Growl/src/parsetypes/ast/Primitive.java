
package parsetypes.ast;

import debug.Debug;
import lextypes.DataType;
import lextypes.Operator;

/**
 *
 * @author mihir
 */
public class Primitive extends Value {
        
    public DataType.BaseType type;
    public Expression supplier;

    @Override
    public int evalConstExpr() {
        return Operator.UNSUPPORTED;
    }

    @Override
    public String display(int pos, int width) {
        return Debug.UNSUPPORTED;
    }
}
