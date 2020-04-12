
package parsetypes.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihir
 */
public class FunctionDef extends UserDef {

    Value returnVal;
    AST body;
    List<Parameter> parameters = new ArrayList<>();
}