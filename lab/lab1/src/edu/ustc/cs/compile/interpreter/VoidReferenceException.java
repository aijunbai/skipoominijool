package edu.ustc.cs.compile.interpreter;

import edu.ustc.cs.compile.platform.interfaces.InterpreterException;

/**
 * <p>
 * 此异常处理类说明程序试图在表达式中使用一个void值。
 * </p><p>
 * SimpleMiniJOOL程序中的print和read语句在处理时有些特殊，它们在AST中
 * 表示成方法调用，当表达式中试图引用print或read的返回值时抛出此异常。
 * 你在完成实验时不需修改此类
 * </p>
 */
public class VoidReferenceException extends InterpreterException {
}