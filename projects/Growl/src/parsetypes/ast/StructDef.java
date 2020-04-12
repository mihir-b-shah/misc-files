
package parsetypes.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihir
 */
public class StructDef extends UserDef {
        
    String name;
    List<Parameter> params = new ArrayList<>();
}
