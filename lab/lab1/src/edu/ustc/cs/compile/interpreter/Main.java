package edu.ustc.cs.compile.interpreter;

import java.io.File;

import org.eclipse.jdt.core.dom.CompilationUnit;
import edu.ustc.cs.compile.parser.simpleminijool.Parser;
import edu.ustc.cs.compile.platform.handler.MsgHandler;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.interfaces.InterpreterException;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.interfaces.ParserInterface;
import edu.ustc.cs.compile.platform.util.ASTView.core.*;

/**<p>
 * Main类，运行Interpreter的代码。
 * </p><p>
 * 在完成实验的过程中，您可以任意修改此函数以测试您的解释器
 * </p>
 */
public class Main {

    /**
     * 程序的入口
     * @param args 程序的输入参数
     */
    public static void main(String[] args) {
        //表示是否从语法分析器中得到AST
        //当您与我们提供的语法分析器一块测试解释器时请改为true
        boolean fromParser = false, debug = false;
        boolean viewAST = false;
        ParserInterface parser;
        InterRepresent ir;
        File inFile = null;
        if (fromParser) {
            // 使用我们提供的分析器
			System.out.println("from Parser\n");
            parser = new Parser();
            // 选择正确的源代码文件
            inFile = new File("test/syntax.mj");
        } else {
            //
			System.out.println("from TestCase\n");
            parser = new TestCase();
        }
        try {
            ir = parser.doParse(inFile);
            
            if (viewAST) {
				CompilationUnit cu = (CompilationUnit)(ir.getIR());
            	ASTViewer astviewer = new ASTViewer(cu);
            	astviewer.show();
            }

            Interpreter it = new Interpreter();

            try{
                it.interpret(ir);
            }catch (InterpreterException e) {
                MsgHandler.errMsg("Exception happened in interpreter.");
                if (debug) {
                    e.printStackTrace();
                }
            }
        } catch (ParserException e) {                   
            MsgHandler.errMsg("Exception happened in parser.");
            if (debug) {
                e.printStackTrace();
            }
        }
    }
}
