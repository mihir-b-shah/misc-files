
import java.util.ArrayList;
import java.util.List;

public class ParseTypes {

    public static class AST {

    }

    static enum OpType {
        UNARY, BINARY;
    }
    
    abstract static class Op<T extends AST> extends AST {
        
        LexTypes.Operator operator;
        abstract OpType type();
    }

    // T allows customizing to a constexpr
    static class UnaryOp<T extends AST> extends Op<T> {
        
        T op;
        OpType type() {
            return OpType.UNARY;
        }
    }

    static class BinaryOp<T extends AST> extends Op<T> {

        T op1;
        T op2;
        OpType type() {
            return OpType.BINARY;
        }
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
            type = LexTypes.DataType.BaseType.BOOL;
        }
    }

    static class Primitive extends Value {
        
        LexTypes.DataType.BaseType type;
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
        Op<ConstExpr> tree;
    }
}
