
public class ParseTypes {

    public static class AST {

    }

    abstract static class Op extends AST {
    }

    static class UnaryOp extends Op {

        LexTypes.Operator operator;
        AST op;
    }

    static class BinaryOp extends Op {

        LexTypes.Operator operator;
        AST op1;
        AST op2;
    }

    abstract static class Control extends AST {
    }

    static class Branch extends Control {

        Bool condition;
        AST ifBranch;
        AST elseBranch;
    }

    static class Switch extends Control {

        Primitive switchVal;
        LexTypes.LiteralType[] cases;
        AST[] jumps;
    }

    static class Loop extends Control {

        Bool condition;
        AST body;
    }

    static class Label extends Control {

        String label;
        AST block;
    }

    abstract class UserDef extends AST {
    }

    static class StructDef extends UserDef {
        
        String name;
        String[] ids;
        Value[] values;
    }

    static class FunctionDef extends UserDef {

        Value returnVal;
        AST body;
        Value[] parameters;
    }
    
    static class Expression extends AST {
    }
    
    abstract static class Value extends Expression {
    }

    static class Bool extends Primitive {
        // just a marker class for the loop/branch conditions
        Bool() {
            type.base = LexTypes.DataType.BaseType.BOOL;
            type.ptrLvl = 0;
        }
    }

    static class Primitive extends Value {
        
        LexTypes.DataType type;
        Expression supplier;
    }

    static class Struct extends Value {

        Expression supplier;
        StructDef definition;
    }
}
