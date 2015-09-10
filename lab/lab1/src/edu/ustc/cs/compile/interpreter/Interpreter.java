/**
 * 这个文件提供了一个解释器的框架代码。你需要补全这些代码，完成一个能够
 * 正确工作的SimpleMiniJOOL程序解释器。
 */
package edu.ustc.cs.compile.interpreter;

import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.interfaces.InterpreterInterface;
import edu.ustc.cs.compile.platform.interfaces.InterpreterException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import java.util.*;

/**
 * <p>解释器类。</p>
 * <p>它是实验平台提供的解释器接口InterpreterInterface的一个实现，用于从实验平台获取表示
 * SimpleMiniJOOL程序的中间表示，然后调用解释器的代码解释执行这个程序。在我们提供的框架代
 * 码中，实际解释SimpleMiniJOOL程序的代码封装在类InterpVisitor中。</p>
 */
public class Interpreter implements InterpreterInterface {
    /**
     * 在接口InterpreterInterface中要求实现的方法。用于从实验平台获取表示SimpleMiniJOOL
     * 程序的中间表示，调用解释器代码解释执行这个程序。
     * @param ir 需要执行的SimpleMiniJOOL程序的中间表示
     * @throws InterpreterException 抛出这个异常可以通知实验平台解释器发生错误，
     *                              实验平台捕获这个异常后会终止执行。
     */
    public void interpret(InterRepresent ir) throws InterpreterException {
        InterpVisitor visitor = new InterpVisitor();
        try {
            ((CompilationUnit)ir.getIR()).accept(visitor);  // 调用实际解释SimpleMiniJOOL程序的代码
        } catch (IllegalArgumentException e) {
            // TODO: 添加你自己的异常处理代码
            e.printStackTrace();
            throw new InterpreterException();
        } 
    }
}

class Identifier {
	int type;
	String name;
	Object value;
}

/**
 * 封装实际解释执行SimpleMiniJOOL程序的代码。实际是ASTVisitor的
 * 一个子类，通过遍历AST完成解释器的功能。以下只是一个代码框架，
 * 你需要自行补全。
 */
class InterpVisitor extends ASTVisitor {
	int indents;

	Vector ids;

	int cur_id;
	double cur_val;
	int cur_type;

	public InterpVisitor() {
		indents = 0;
		ids = new Vector();
	}

	public void print_spaces() {
		for (int i = 1; i < indents; ++i) {
			System.out.print(" ");
		}
	}

    public boolean visit(CompilationUnit n) {
        // TODO: 添加代码解释CompilationUnit
		indents ++; 
		print_spaces();
		System.out.println(">> CompilationUnit");
		//n.getNearestNode().accept(this);

		ids.clear();
		// nothing more...

        return true;
    }
    public void endVisit(CompilationUnit n) {
		print_spaces();
		System.out.println("<< CompilationUnit");
		indents --; 
    }

    public boolean visit(TypeDeclaration n) {
        // TODO: 添加代码解释TypeDeclaration
		indents ++; 
		print_spaces();
		System.out.println(">> TypeDeclaration");
        return true;
    }
    public void endVisit(TypeDeclaration n) {
		print_spaces();
		System.out.println("<< TypeDeclaration");
		indents --; 
    }

    public boolean visit(MethodDeclaration n) {
        // TODO: 添加代码解释MethodDeclaration
		indents ++; 
		print_spaces();
		System.out.println(">> MethodDeclaration");
        return true;
    }
    public void endVisit(MethodDeclaration n) {
		print_spaces();
		System.out.println("<< MethodDeclaration");
		indents --; 
    }

    public boolean visit(Block n) {
        // TODO: 添加代码解释Block
		indents ++; //缩进 
		print_spaces();
		System.out.println(">> Block");
        return true;
    }
    public void endVisit(Block n) {
		print_spaces();
		System.out.println("<< Block");
		indents --; 
    }

    public boolean visit(EmptyStatement n) {
        // TODO: 添加代码解释EmptyStatement
		print_spaces();
		System.out.println(">> EmptyStatement");
        return true;
    }
    public void endVisit(EmptyStatement n) {
		print_spaces();
		System.out.println("<< EmptyStatement");
		indents --; 
    }

    public boolean visit(IfStatement n) {
        // TODO: 添加代码解释IfStatement
		indents ++; 
		print_spaces();
		System.out.println(">> IfStatement");
        return true;
    }
    public void endVisit(IfStatement n) {
		print_spaces();
		System.out.println("<< IfStatement");
		indents --; 
    }

    public boolean visit(WhileStatement n) {
        // TODO: 添加代码解释WhileStatement
		indents ++; 
		print_spaces();
		System.out.println(">> WhileStatement");
        return true;
    }
    public void endVisit(WhileStatement n) {
		print_spaces();
		System.out.println("<< WhileStatement");
		indents --; 
    }

    public boolean visit(ExpressionStatement n) {
        // TODO: 添加代码解释ExpressionStatement
		indents ++; 
		print_spaces();
		System.out.println(">> ExpressionStatement");
        return true;
    }
    public void endVisit(ExpressionStatement n) {
		print_spaces();
		System.out.println("<< ExpressionStatement");
		indents --; 
    }

    public boolean visit(Assignment n) {
        // TODO: 添加代码解释Assignment。你可以参考以下代码框架。
		indents ++; 
		print_spaces();
		System.out.println(">> Assignment");
        
        Assignment.Operator op = n.getOperator();
        // NOTE: 你需要考虑操作数是print()的情况。此时，你可以考
        //       虑抛出异常VoidReferenceException。
        if (op == Assignment.Operator.ASSIGN) {
            
        } else if (op == Assignment.Operator.PLUS_ASSIGN) {
        
        } else if (op == Assignment.Operator.MINUS_ASSIGN) {
        
        } else if (op == Assignment.Operator.TIMES_ASSIGN) {
        
        } else if (op == Assignment.Operator.DIVIDE_ASSIGN) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        } else if (op == Assignment.Operator.REMAINDER_ASSIGN) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        } // 继续...
        return true;
    }
    public void endVisit(Assignment n) {
		print_spaces();
		System.out.println("<< Assignment");
		indents --; 
    }

    public boolean visit(MethodInvocation n) {
        // TODO: 添加代码解释MethodInvocation
		indents ++; 
		print_spaces();
		System.out.println(">> MethodInvocation");
        return true;
    }
    public void endVisit(MethodInvocation n) {
		print_spaces();
		System.out.println("<< MethodInvocation");
		indents --; 
    }

    public boolean visit(NumberLiteral n) {
        // TODO: 添加代码解释NumberLiteral
		indents ++; 
		print_spaces();
		System.out.println(">> NumberLiteral");
        return true;
    }
    public void endVisit(NumberLiteral n) {
		print_spaces();
		System.out.println("<< NumberLiteral");
		indents --; 
    }

    public boolean visit(PrefixExpression n) {
        // TODO: 添加代码解释PostfixExpression。你可以参考以下代码框架。
		indents ++; 
		print_spaces();
		System.out.println(">> PrefixExpression");
        
        PrefixExpression.Operator op = n.getOperator();
        // NOTE: 你需要考虑操作数是print()的情况。此时，你可以考
        //       虑抛出异常VoidReferenceException。
        if (op == PrefixExpression.Operator.PLUS) {
        
        } else if (op == PrefixExpression.Operator.MINUS) {
        
        } else if (op == PrefixExpression.Operator.NOT) {
        
        } // 继续...
        return true;
    }
    public void endVisit(PrefixExpression n) {
		print_spaces();
		System.out.println("<< PrefixExpression");
		indents --; 
    }

    public boolean visit(InfixExpression n) {
        // TODO: 添加代码解释InfixExpression。你可以参考以下代码框架。
		indents ++; 
		print_spaces();
		System.out.println(">> InfixExpression");
        
        InfixExpression.Operator op = n.getOperator();
        // NOTE: 你需要考虑操作数是print()的情况。此时，你可以考
        //       虑抛出异常VoidReferenceException。
        if (op == InfixExpression.Operator.AND) {
        
        } else if (op == InfixExpression.Operator.MINUS) {
        
        } else if (op == InfixExpression.Operator.TIMES) {
        
        } else if (op == InfixExpression.Operator.DIVIDE) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        } else if (op == InfixExpression.Operator.REMAINDER) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        } // 继续....
        return true;
    }
    public void endVisit(InfixExpression n) {
		print_spaces();
		System.out.println("<< InfixExpression");
		indents --; 
    }

    public boolean visit(SimpleName n) {
        // TODO: 添加代码解释SimpleName
		indents ++; 
		print_spaces();
		System.out.println(">> SimpleName");
        return true;
    }
    public void endVisit(SimpleName n) {
		print_spaces();
		System.out.println("<< SimpleName");
		indents --; 
    }

    public boolean visit(PrimitiveType n) {
        // TODO: 添加代码解释PrimitiveType
		indents ++; 
		print_spaces();
		System.out.println(">> PrimitiveType");
        return true;
    }
    public void endVisit(PrimitiveType n) {
		print_spaces();
		System.out.println("<< PrimitiveType");
		indents --; 
    }

    public boolean visit(Modifier n) {
        // TODO: 添加代码解释Modifier
		indents ++; 
		print_spaces();
		System.out.println(">> Modifier");
        return true;
    }
    public void endVisit(Modifier n) {
		print_spaces();
		System.out.println("<< Modifier");
		indents --; 
    }

    // TODO: 添加代码解释其它需要解释的AST节点
}
