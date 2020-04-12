
package parsetypes.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihir
 */
public class Switch extends Control {

    Primitive switchVal;
    List<Literal> cases;
    List<AST> jumps;

    Switch() {
        cases = new ArrayList<>();
        jumps = new ArrayList<>();
    }
}