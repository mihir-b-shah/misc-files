
import java.util.ArrayList;
import java.util.List;

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
        List<Literal> cases;
        List<AST> jumps;
        
        Switch() {
            cases = new ArrayList<>();
            jumps = new ArrayList<>();
        }
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

    static class Parameter {
        String id;
        LexTypes.DataType type;
    }
    
    static class StructDef extends UserDef {
        
        String name;
        List<Parameter> params;
        
        StructDef() {
            params = new ArrayList<>();
        }
    }

    static class FunctionDef extends UserDef {

        Value returnVal;
        AST body;
        List<Parameter> parameters;
        
        FunctionDef() {
            parameters = new ArrayList<>();
        }
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
    
    static class Literal {
        LexTypes.LiteralType type;
        String value;
        
        Literal() {
        }
        
        Literal(Lexer.Lexeme lexeme) {
            assert(lexeme.type == Lexer.LexType.LITERAL);
            type = ((LexTypes.LiteralType) lexeme.subType);
            value = lexeme.token;
        }
    }
    
    static class ConstExpr extends Expression {
        
    }
}
