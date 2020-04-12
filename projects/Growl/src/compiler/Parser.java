package compiler;

import java.util.HashMap;
import java.util.List;

import lextypes.*;
import parsetypes.ast.*;
import parsetypes.enums.*;

/**
 * A recursive descent parser
 * 
 * @author mihir
 */
public class Parser {

    static List<Lexer.Lexeme> lexemes;
    static HashMap<String, Variable> symbolTable;

    /*
    Currently scans for these control flow patterns:
    
    1] If-else statement (nested for now)
    
    Token stream: IF ( [expr] ) { [body] } ELSE { [body] }

    2] While loop
    
    Token stream: WHILE ( [expr] ) { [body] }
    
    3] Function definition
    
    Token stream: TYPE ID ( TYPE , ID , ... ) { [body] RETURN ID OR LITERAL}
    
    4] Labeled block
    
    Token stream: ID : { [body] }
    
    5] Struct definition
    
    Token stream: STRUCT ID { TYPE , ID ; TYPE , ID ;...};
    
    6] Switch block
    
    Token stream: SWITCH { case LITERAL : [comp]... } 
    
    7] Expression/Computation    
    */
    public static AST parse(List<Lexer.Lexeme> lexemes) {
        Parser.lexemes = lexemes;
        Parser.symbolTable = new HashMap<>();
        return parseAST(0, lexemes.size());
    }
    
    // inclusive, exclusive bounds
    private static AST parseAST(int start, int end) {
        int parsePtr = start;
        while(parsePtr < end) {
            final Lexer.Lexeme lex1 = lexemes.get(parsePtr);
            final Lexer.Lexeme lex2;
            int match;
            AST result;
            int parenClose,brackClose;

            switch(lex1.type) {
                case ID:
                    if(start>end-2) {
                        break;
                    }
                    lex2 = lexemes.get(parsePtr+1);
                    if(lex2.subType.getClass() == Group.class
                            && ((Group) lex2.subType) == Group.COLON) {
                        // labeled block
                        Label block = new Label(); 
                        // correct type will parse until the curly brace open/close
                        match = GroupFinder.findMatch(parsePtr+2);
                        result = parseAST(parsePtr+3, match);
                        block.block = result;
                        block.label = lex1.token;
                        parsePtr = match+1;
                    }
                    break;
                case CONTROL:
                    switch((Control) lex1.subType) {
                        case IF:
                            // if/else statement
                            Branch block = new Branch(); 
                            // correct type will parse until the curly brace open/close
                            parenClose = GroupFinder.findMatch(parsePtr+1);
                            Bool filter = new Bool();
                            filter.supplier = parseExpr(parsePtr+2, parenClose);
                            // curly brace next.
                            brackClose = GroupFinder.findMatch(parenClose+1);
                            AST body = parseAST(parenClose+2, brackClose);
                            block.condition = filter;
                            block.ifBranch = body;
                            parsePtr = brackClose+1;
                            if(lexemes.get(parsePtr).type == Lexer.LexType.CONTROL
                                && (((Control)(lexemes.get(parsePtr)).subType))
                                    == Control.ELSE) {
                                // continue parsing the else statement
                                brackClose = GroupFinder.findMatch(parsePtr+1);
                                body = parseAST(parsePtr+2, brackClose);
                                block.elseBranch = body;
                                parsePtr = brackClose+1;
                            }
                            break;
                        case SWITCH:
                            // switch statement
                            parenClose = GroupFinder.findMatch(parsePtr+1);
                            Switch switchBlock = new Switch();
                            Primitive switchExpr = 
                                    (Primitive) parseExpr(parsePtr+2, parenClose);
                            switchBlock.switchVal = switchExpr;
                            int switchPtr = parenClose+2;
                            int endPtr;
                            brackClose = GroupFinder.findMatch(parenClose+1);
                            while(switchPtr < brackClose && 
                                    lexemes.get(switchPtr).subType == Control.class
                                 && ((Control) lexemes.get(switchPtr).subType) 
                                    == Control.CASE) {
                                Lexer.Lexeme literal = lexemes.get(switchPtr+1);
                                switchBlock.cases.add(new Literal(literal));
                                endPtr = GroupFinder.findNextCase(switchPtr+2, brackClose);
                                AST jump = parseAST(switchPtr+3, endPtr);
                                switchBlock.jumps.add(jump);
                                if(endPtr == brackClose) {
                                    break;
                                }
                                switchPtr = endPtr;
                            }
                            // check for default case
                            if(switchPtr < brackClose) {
                                AST jump = parseAST(switchPtr+2, brackClose);
                                switchBlock.jumps.add(jump);
                            }
                            break;
                        case WHILE:
                            // while statement
                            Loop whileBlock = new Loop(); 
                            // correct type will parse until the curly brace open/close
                            parenClose = GroupFinder.findMatch(parsePtr+1);
                            Bool whileFilter = new Bool();
                            whileFilter.supplier = parseExpr(parsePtr+2, parenClose);
                            // curly brace next.
                            brackClose = GroupFinder.findMatch(parenClose+1);
                            AST whileBody = parseAST(parenClose+2, brackClose);
                            whileBlock.condition = whileFilter;
                            whileBlock.body = whileBody;
                            parsePtr = brackClose+1;
                            break;
                    }
                case TYPE:
                    if(parsePtr>end-3) {
                        break;
                    }
                    lex2 = lexemes.get(parsePtr+1);
                    final Lexer.Lexeme lex3 = lexemes.get(parsePtr+2);
                    if(lex2.subType.getClass() == Id.class 
                            && lex3.subType.getClass() == Group.class 
                            && ((Group) lex3.subType) == Group.OPEN_PAREN) {
                        // function definition
                        FunctionDef func = new FunctionDef();
                        int paramEnd = GroupFinder.findMatch(parsePtr+3);
                        parsePtr += 4;
                        while(parsePtr < paramEnd) {
                            Parameter param = new Parameter();
                            param.type = (DataType) (lexemes.get(parsePtr).subType);
                            param.id = lexemes.get(parsePtr+1).token;
                            func.parameters.add(param);
                            parsePtr += 2;
                        }
                        brackClose = GroupFinder.findMatch(paramEnd+1);
                        AST funcBody = parseAST(paramEnd+2, brackClose);
                        func.body = funcBody;
                        parsePtr = brackClose+1;
                        // fix func.return() statement
                    } else if(((DataType) lex1.subType).base 
                            == DataType.BaseType.STRUCT) {
                        // struct declaration
                        StructDef struct = new StructDef();
                        struct.name = lex1.token.substring(7);
                        brackClose = GroupFinder.findMatch(parsePtr+1);
                        while(parsePtr < brackClose) {
                            Parameter param = new Parameter();
                            param.type = (DataType) (lexemes.get(parsePtr).subType);
                            param.id = lexemes.get(parsePtr+1).token;
                            struct.params.add(param);
                            parsePtr += 2;
                        }
                        parsePtr = brackClose+1;
                    }
                    break;
            }
        }

        return null;
    }
    
    public static Expression parseExpr(List<Lexer.Lexeme> lexemes) {
        Parser.lexemes = lexemes;
        return parseExpr(0, lexemes.size());
    }
    
    // supports operators on NUMERICAL types.
    // so operands can be i8 (bool), i32, i64, f64
    // operators supported: (,),++,--!,~,*,/,%,+,-,<<,>>,>>>,<,>,==,&,|,^,&&,||
    // expression tree consists solely of operators
    private static Expression parseExpr(int start, int end) {
        Expression expr = new Expression();
        Stack<Expression> stack = new Stack<>();
        stack.push(expr);
        for(int ptr = start; ptr<end; ++ptr) {
            Lexer.Lexeme lexeme = lexemes.get(ptr);
            switch(lexeme.type) {
                case GROUP:
                    int parenClose = GroupFinder.findMatch(ptr);
                    Expression loc = parseExpr(ptr+1, parenClose);
                    ptr = parenClose;
                    stack.push(loc);
                    break;
                case ID:
                    Variable var = symbolTable.get(lexeme.token);
                    stack.push(var);
                    break;
                case LITERAL:
                    Literal lit = new Literal();
                    lit.type = (LiteralType) lexeme.subType;
                    lit.value = lexeme.token;
                    stack.push(lit);
                    break;
                case OPERATOR:
                    if(ptr < end-1 && lexemes.get(ptr+1).subType.getClass() == Group.class && 
                            ((Group) lexemes.get(ptr+1).subType) == Group.OPEN_PAREN) {
                        parenClose = GroupFinder.findMatch(ptr+1);
                        stack.push(parseExpr(ptr+2, parenClose));
                        break;
                    }
                    switch(((Operator) lexeme.subType).type()) {
                        case UNARY:
                            if(ptr < end-2 && lexemes.get(ptr+2).subType.getClass() == Operator.class) {
                                Operator second = (Operator) lexemes.get(ptr+2).subType;
                                Operator first = (Operator) lexemes.get(ptr).subType;
                                if(first.precedence() == second.precedence()) {
                                    switch(first.associate()) {
                                        case LEFT_TO_RIGHT:
                                        case NONE:
                                            Expression operand = 
                                                    Expression.genLitVal(lexemes.get(ptr+1));
                                            UnaryOp<Expression> comp 
                                                    = new UnaryOp<>(lexeme);
                                            comp.op = operand;
                                        case RIGHT_TO_LEFT:
                                            // to be implemented
                                    }
                                } else if(first.precedence() < second.precedence()) {
                                    Expression operand = 
                                            Expression.genLitVal(lexemes.get(ptr+1));
                                    UnaryOp<Expression> comp 
                                            = new UnaryOp<>(lexeme);
                                    comp.op = operand;
                                } else {
                                    stack.push(new UnaryOp<>(lexemes.get(ptr)));
                                }
                            }
                        case BINARY:
                            if(ptr < end-2 && lexemes.get(ptr+2).subType.getClass() == Operator.class) {
                                Operator second = (Operator) lexemes.get(ptr+2).subType;
                                Operator first = (Operator) lexemes.get(ptr).subType;
                                if(first.precedence() == second.precedence()) {
                                    switch(first.associate()) {
                                        case LEFT_TO_RIGHT:
                                        case NONE:
                                            Expression operand1 = stack.pop();
                                            Expression operand2 = 
                                                    Expression.genLitVal(lexemes.get(ptr+1));
                                            BinaryOp<Expression> comp 
                                                    = new BinaryOp<>(lexeme);
                                            comp.op1 = operand1;
                                            comp.op2 = operand2;
                                        case RIGHT_TO_LEFT:
                                            // to be implemented
                                    }
                                } else if(first.precedence() == second.precedence()) {
                                    Expression operand1 = stack.pop();
                                    Expression operand2 = 
                                            Expression.genLitVal(lexemes.get(ptr+1));
                                    BinaryOp<Expression> comp 
                                            = new BinaryOp<>(lexeme);
                                    comp.op1 = operand1;
                                    comp.op2 = operand2;
                                } else {
                                    stack.push(new BinaryOp<>(lexemes.get(ptr)));
                                }
                            }
                        default:
                            break;
                    }
            }
        }
        return stack.pop();
    }
    
    public static void main(String[] args) {
        String program = "14/2%5";
        GroupFinder.initialize(program);
        List<Lexer.Lexeme> lexes = Lexer.lex(program);
        Lexer.printLexemes(lexes);
        Expression expr = parseExpr(lexes);
    }
}
