
package parsetypes.ast;

/**
 *
 * @author mihir
 */
class Branch extends Control {

    Bool condition;
    AST ifBranch;
    AST elseBranch;
}
