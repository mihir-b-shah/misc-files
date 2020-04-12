
package parsetypes.ast;

import lextypes.DataType;

/**
 *
 * @author mihir
 */
public class Primitive extends Value {
        
    public DataType.BaseType type;
    public Expression supplier;
}
