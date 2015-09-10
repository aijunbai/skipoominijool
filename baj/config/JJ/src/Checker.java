package edu.ustc.cs.compile.skipoominijool;

import org.eclipse.jdt.core.dom.*;
import java.util.*;

import edu.ustc.cs.compile.platform.util.ir.HIR;
import edu.ustc.cs.compile.platform.interfaces.CheckerInterface;
import edu.ustc.cs.compile.platform.interfaces.CheckerException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;

/**
 * semantic checker used for SkipOOMiniJOOL language
 * @author Bai Aijun
 * @version 1.0
 */
public class Checker implements CheckerInterface {
  SymTabGenerator top_gen;
  MSymTable top;

  /**
   * interfaces for the test platform
   * @param ir the inter-represent of SkipOOMiniJOOL source code
   * which is a eclipse AST
   * @return whether ths check is success
   */
  public boolean check(InterRepresent ir) throws CheckerException {
    System.out.println("#SkipOOMiniJOOL Checker - by baj@mail.ustc.edu.cn (c) 2008");
    top_gen  = new SymTabGenerator();
    top = top_gen.getSymTable((HIR)ir);
    
    CompilationUnit cu = (CompilationUnit)ir.getIR();
    CheckerVisitor visitor = new CheckerVisitor(top);
    visitor.error_num = top_gen.error_num;
    visitor.warning_num = top_gen.warning_num;
    visitor.success = top_gen.has_main;
    visitor.setIR(ir);
    cu.accept(visitor);
    return visitor.success();
  }
}


/**
 * an ASTVisitor for check routine
 * @author Bai Aijun
 * @version 1.0
 */
class CheckerVisitor extends ASTVisitor {
    // 如果语义检查时发现错误，将success置为false。
  public boolean success = true;
  private boolean in_method_declaration = false, hasReturn = false;
  public int error_num = 0;
  public int warning_num = 0;
  MSymTable top, saved, saved2; //在遍历过程中将为每个表达式都建立符号表，构成定型环境
  MType base_type;
  CompilationUnit root;
  
  private InterRepresent ir = null;

  /**
   * a utils function to dump error message
   */
  public void error(String msg){
    System.err.println("Error " + error_num + ": " + msg);
    error_num += 1;
    success = false;
  }

  /**
   * a utils function to dump warning message
   */
  public void warning(String msg){
      System.err.println("Warning " + warning_num + ": " + msg);
      warning_num += 1;
  }
  
  public boolean success() {
    return success;
  }
  
  public void setIR(InterRepresent ir) {
    this.ir = ir;
  }
  
  public CheckerVisitor(MSymTable s) {
    top = s;
    base_type = new MType();
  }
  
  private String position(ASTNode n){
      Integer line = (Integer)(n.getProperty("line"));
      Integer column = (Integer)(n.getProperty("column"));
      if (line != null && column != null){
          return " at line " + line + " column " + column;
      }
      else return new String();
  }

  public boolean visit(CompilationUnit cu){
    root = cu;
    return true;
  }
  
  public boolean visit(Block n) {
    saved = top;
    if ( !in_method_declaration){
      top = new MSymTable(top);
    }
    else {
      in_method_declaration = false;
    }
    //System.out.println(">> block");
    return true;
  }
  public void endVisit(Block n) {
    //top.dump();
    top = saved;
    //System.out.println("<< block");
    //top.dump();
  }
  
  public void endVisit(Assignment n) {
    Assignment.Operator op = n.getOperator();
    String left_string = n.getLeftHandSide().toString();
    String right_string = n.getRightHandSide().toString();
    SymbolKey left_key = new SymbolKey(left_string);
    SymbolKey right_key = new SymbolKey(right_string);
    Symbol left_sym = top.get(left_key);
    Symbol right_sym = top.get(right_key);
    if (left_sym != null && right_sym != null){
      if (left_sym.isFinal){
          error("assignment with final variable \"" + left_string + "\"" + position(n));
        return;
      }
      String left_type = left_sym.type();
      String right_type = right_sym.type();
      //System.out.println(left_type);
      //System.out.println(right_type);
      
      if ( op != Assignment.Operator.ASSIGN && (!left_type.equals("int") || !right_type.equals("int"))){
      //只有int可以+=等
        error( "\"" + n.toString() + "\" do not support operator \"" + op.toString() + "\"" + position(n));
      }
      else if ( !left_type.equals(right_type) /*&& !(left_type.equals("boolean") && right_type.equals("int"))*/)
      {
          error( "type mismatch in assignment \"" + n.toString() + "\"" + position(n));
      }
      else if (right_type.indexOf("[]") != -1){
          error("invalid assignment with array type" + position(n));
      }
      else { //type check correct
        String exp = n.toString();
        MType exp_type = base_type.newType(left_sym.type());
        MExpression exp_dec = new MExpression(exp_type);
        Symbol s = new Symbol(exp, exp_dec);
        top.put(s);
        //System.out.println( "\nadd a new assign exp " + exp);
        //top.dump();
      }
    }
    else {
        if (left_sym == null){
            error("undefined reference to left oprand expression \"" + left_string + "\"" + position(n));
        }
        if (right_sym == null){
            error("undefined reference to right oprand expression \"" + right_string + "\"" + position(n));
        }
    }
  }
  
  public void endVisit(MethodInvocation n) {
    String method_name = n.getName().getIdentifier();
    SymbolKey key = new SymbolKey(method_name, true);
    Symbol s = top.get(key);
    if (s == null){
        error( "miss definetion of method \"" +  method_name + "\"" + position(n));
      return;
    }
    
    List args = n.arguments();
    List pars = ((MFunctionDeclaration)s.dec).parameters;
    if (!method_name.equals("print") && !method_name.equals("read")){
      if ( args != null && pars != null && args.size() == pars.size()){
        boolean match = true;
        Iterator<Expression> j = args.iterator();
        for (Iterator<MType> i = pars.iterator(); i.hasNext();) {
          MType type = i.next();
          Expression arg = j.next();
          String arg_exp = arg.toString();
          SymbolKey arg_key = new SymbolKey(arg_exp);
          Symbol arg_sym = top.get(arg_key);
          if (arg_sym != null){
            String arg_type = arg_sym.type();
            //System.out.println(type.dump() + " " + arg_type);
            if (!arg_type.equals(type.dump())){
                error( "type mismatch in method invocation of \"" +  method_name + "\" for argument \"" + arg_exp + "\"" + position(n));
              match = false;
            }
          }
          else {
              error("undefined reference to argument expression \"" + arg_exp + "\"" + position(n));
            match = false;
          }
        }
        if (match){
          String exp = n.toString();
          String ret_type = s.type();
          MType exp_type = base_type.newType(ret_type);
          MExpression exp_dec = new MExpression(exp_type);
          Symbol sym = new Symbol(exp, exp_dec);
          top.put(sym);
          //System.out.println( "\nadd a new method invocation exp " + exp);
          //top.dump();
        }
      }
      else if ( pars == null && args == null){
        ;
      }
      else {
          error( "arguments list size mismatch in method invocation of \"" +  method_name + "\"" + position(n));
        //top.dump();
      }
    }
  }
  
  public void endVisit(PrefixExpression n) {
    PrefixExpression.Operator op = n.getOperator();
    String opr_string = n.getOperand().toString();
    SymbolKey opr_key = new SymbolKey(opr_string);
    Symbol opr_sym = top.get(opr_key);
    if (opr_sym != null){
      String opr_type = opr_sym.type();
      if (opr_type.equals("String")){
          error( "prefix exp \"" + n.toString() + "\" don't support oprand type of String" + position(n));
      }
      else if (opr_type.indexOf("[]") != -1){
          error( "prefix exp \"" + n.toString() + "\" don't support oprand type of array" + position(n));
      }
      else if (opr_type.equals("boolean") && op != PrefixExpression.Operator.NOT){
          error( "prefix exp \"" + n.toString() + "\" don't support oprand type of boolean" + position(n));
      }
      else { //type check correct
        String exp = n.toString();
        MType exp_type;
        if (op == PrefixExpression.Operator.NOT) {
          exp_type = base_type.newType("boolean");
        }
        else {
          exp_type = base_type.newType("int");
        }
        MExpression exp_dec = new MExpression(exp_type);
        Symbol s = new Symbol(exp, exp_dec);
        top.put(s);
            //System.out.println( "\nadd a new prefix exp " + exp);
            //top.dump();
      }
    }
    else {
        error("undefined reference to oprand expression \"" + opr_string + "\"" + position(n));
    }
  }
  
  public void endVisit(InfixExpression n) {
    InfixExpression.Operator op = n.getOperator();
    String left_string = n.getLeftOperand().toString();
    String right_string = n.getRightOperand().toString();
    SymbolKey left_key = new SymbolKey(left_string);
    SymbolKey right_key = new SymbolKey(right_string);
    Symbol left_sym = top.get(left_key);
    Symbol right_sym = top.get(right_key);
    if (left_sym != null && right_sym != null){
      String left_type = left_sym.type();
      String right_type = right_sym.type();
        //System.out.println( "DUMP: " + left_type + " " + right_type);
      if (left_type.equals("String") || right_key.equals("String")){
          error( "infix exp \"" + n.toString() + "\" don't support oprand type of String" + position(n));
      }
      else if (left_type.indexOf("[]") != -1 || right_type.indexOf("[]") != -1){
          error( "infix exp \"" + n.toString() + "\" don't support oprand type of array" + position(n));
      }
      else if ((left_type.equals("boolean") || right_type.equals("boolean"))
               && ( op != InfixExpression.Operator.CONDITIONAL_AND && op != InfixExpression.Operator.CONDITIONAL_OR
                && op != InfixExpression.Operator.NOT_EQUALS && op != InfixExpression.Operator.EQUALS)
              ){
          error( "infix exp \"" + n.toString() + "\" don't support oprand type of boolean" + position(n));
      }
      else { //type check correct
        String exp = n.toString();
        MType exp_type = base_type.newType("int");
        if (
            op == InfixExpression.Operator.CONDITIONAL_AND
            || op == InfixExpression.Operator.CONDITIONAL_OR
           ){
          if (!left_type.equals("boolean") || !right_type.equals("boolean")){
              error( "type mismatch in boolean expression \"" + n.toString() + "\"" + position(n));
          }
          exp_type = base_type.newType("boolean");
        }
        else if (
            op == InfixExpression.Operator.LESS
            || op == InfixExpression.Operator.GREATER
            || op == InfixExpression.Operator.GREATER_EQUALS
            || op == InfixExpression.Operator.LESS_EQUALS
            || op == InfixExpression.Operator.EQUALS
            || op == InfixExpression.Operator.NOT_EQUALS
        ){
            exp_type = base_type.newType("boolean");
        }
        MExpression exp_dec = new MExpression(exp_type);
        Symbol s = new Symbol(exp, exp_dec);
        top.put(s);
            //System.out.println( "\nadd a new infix exp " + exp);
            //top.dump();
      }
    }
    else {
        if (left_sym == null){
            error("undefined reference to left oprand expression \"" + left_string + "\"" + position(n));
        }
        if (right_sym == null){
            error("undefined reference to right oprand expression \"" + right_string + "\"" + position(n));
        }
    }
  }

  public void endVisit(ParenthesizedExpression n) {
      String exp = n.getExpression().toString();
      SymbolKey key = new SymbolKey(exp);
      Symbol sym = top.get(key);
      if (sym != null){
          String pexp = n.toString();
          MType pexp_type = base_type.newType(sym.type());
          MExpression pexp_dec = new MExpression(pexp_type);
          Symbol s = new Symbol(pexp, pexp_dec);
          top.put(s);
      }
      else {
          error("undefined reference to expression \"" + exp + "\"" + position(n));
      }
  }
  
//   public boolean visit(SimpleName n) {
//     if (!n.isDeclaration()){ //引用变量
//       String name = n.getIdentifier();
//       if (name.equals("String")) {
//         return true;
//       }
//       if (name.equals("length") /*&& n.getParent().isSimpleName()*/){
//           if (n.getParent().toString().indexOf(".") != -1){
//               return true;
//           }
//           else {
//               error( "can not access length field of non array type \"" +  name + "\"" + position(n));
//             return true;
//           }
//       }
//       
// //       SymbolKey key = new SymbolKey(name, false);
// //       Symbol s = top.get(key);
// //       SymbolKey key2 = new SymbolKey(name, true);
// //       Symbol s2 = top.get(key2);
// //       if (s == null && s2 == null){
// //           error( "undefined reference to symbol \"" +  name + "\"" + position(n));
// //       }
//     }
//     return true;
//   }
  
  public boolean visit(PrimitiveType n) {
    String type = n.toString();
    if (!type.equals("int") && !type.equals("boolean") && !type.equals("void")){
      error( "primitive types don't support type of \"" + type + "\"" );
    }
    return true;
  }
  
  public boolean visit(Modifier n) {
    if (!n.toString().equals("final") && !n.toString().equals("static")){
      error( "modifiers don't support modifier of \"" + n.toString() + "\"");
    }
    return true;
  }

  public void endVisit(ArrayInitializer n){
    List list = n.expressions();
    boolean first = true;
    String first_type = new String();
    boolean match = true;
    for (Iterator<Expression> i = list.iterator(); i.hasNext();) {
      String exp = i.next().toString();
      SymbolKey exp_key = new SymbolKey(exp);
      Symbol exp_sym = top.get(exp_key);
      if (exp_sym != null){
        String exp_type = exp_sym.type();
        if (first == true){
          first = false;
          first_type = exp_type;
        }
        else {
          if (!exp_type.equals(first_type)){
              error("type mismatch in array initializer \"" + n.toString() + "\"" + position(n));
            match = false;
          }
        }
      }
      else {
          error("undefined reference to array initializer expression \"" + exp + "\"" + position(n));
      }
    }
    if (match){
      String array_initializer = n.toString();
      //System.err.println("DUMP: array_initializer " + array_initializer);
      //System.err.println("DUMP: first_type " + first_type);
      MType array_initializer_type = base_type.newType(first_type + "[]");
      MExpression array_initializer_exp = new MExpression(array_initializer_type);
      Symbol array_initializer_sym = new Symbol(array_initializer, array_initializer_exp);
      top.put(array_initializer_sym);
    }
  }
  
  public void endVisit(VariableDeclarationStatement n) {
      //top.dump();
    List list = n.fragments();
    MType type = base_type.newType(n.getType().toString());
    if (type.dump().equals("void")){
        error("type of declaration \"" + n.toString() + "\" can not be void" + position(n));
      return;
    }
    for (Iterator<VariableDeclarationFragment> i = list.iterator(); i.hasNext();) {
      VariableDeclarationFragment fragment = i.next();
      String variable_name = fragment.getName().getIdentifier();
      MVariableDeclaration variable_declaration = new MVariableDeclaration(type);
      Symbol s = new Symbol(variable_name, variable_declaration);

      if (n.getType().getProperty("size") != null /*&& type.dump().indexOf("[]") != -1*/){
          s.size = (Integer)n.getType().getProperty("size");
          //System.out.println("size " + n.getType().getProperty("size"));
      }

      Symbol found = top.curget(s.key()); //同一生存周期，变量重复定义
      if (found != null){
          error( "duplicated identifier \"" + variable_name + "\" in same scope" + position(fragment));
        return;
      }
      else {
        found = top.get(s.key());
        if (found != null){
          if (found.isField == false){
              error ( "duplicated local identifier \"" + variable_name + "\" between blocks" + position(n));
               //不允许 block 间，有同名变量
            return;
          }
        }
        
        //检查初始化是否正确
        boolean match = true;
        Expression initializer_exp = fragment.getInitializer();
        if (initializer_exp != null){
          String initializer = initializer_exp.toString();
          //top.dump();
          //System.out.println("DUMP: " + initializer);
          SymbolKey initializer_key = new SymbolKey(initializer);
          Symbol initializer_sym = top.get(initializer_key);
          if (initializer_sym != null){
            String initializer_type = initializer_sym.type();
            if (!type.dump().equals(initializer_type)){
              error("type mismatch in initializer \"" + fragment.toString() + "\"" + position(fragment));
              match = false;
            }
            if (initializer_type.indexOf("[]") != -1){
                if (initializer.indexOf("{") == -1){ //array assignment
                    error("invalid assignment with array type" + position(fragment));
                }
            }
          }
          else {
            error("undefined reference to initializer expression \"" + initializer + "\"" + position(n));
            match = false;
          }
          if (match){
            top.put(s);
            //top.dump();
          }
        }
        else {
          top.put(s);
          //top.dump();
        }
      }
    }
  }

  public void endVisit(FieldDeclaration n) { //全局变量已经在符号表里有记录了，这里只检查初始化
    List list = n.fragments();
    MType type = base_type.newType(n.getType().toString());
    if (type.dump().equals("void")){
        error(" type of declaration \"" + n.toString() + "\" can not be void" + position(n));
      return;
    }
    //System.out.println("\ncheck in");
    //top.dump();
    for (Iterator<VariableDeclarationFragment> i = list.iterator(); i.hasNext();) {
      VariableDeclarationFragment fragment = i.next();
      String variable_name = fragment.getName().getIdentifier();
      MVariableDeclaration variable_declaration = new MVariableDeclaration(type);
      Symbol s = new Symbol(variable_name, variable_declaration);
      //检查初始化是否正确
      Expression initializer_exp = fragment.getInitializer();
      if (initializer_exp != null){
        String initializer = initializer_exp.toString();
        //top.dump();
        //System.out.println("DUMP: " + initializer);
        SymbolKey initializer_key = new SymbolKey(initializer);
        Symbol initializer_sym = top.get(initializer_key);
        if (initializer_sym != null){
          String initializer_type = initializer_sym.type();
          if (!type.dump().equals(initializer_type)){
              error("type mismatch in field initializer \"" + fragment.toString() + "\"" + position(fragment));
          }
        }
      }
    }
  }
  
  public boolean visit(MethodDeclaration n) { //函数内的参数定义，视为一个新 block
    hasReturn = false;
    saved2 = top;
    top = new MSymTable(top);
    //System.out.println(">> method declaration");
    in_method_declaration = true; //MethodDeclaration 后紧跟的一个 block 就不用新建符号表了
    List list = n.parameters();
    for (Iterator<SingleVariableDeclaration> i = list.iterator(); i.hasNext();) {
      SingleVariableDeclaration variable_declaration = i.next();
      String name = variable_declaration.getName().getIdentifier();
      MType type = base_type.newType(variable_declaration.getType().toString());
      MVariableDeclaration declaration = new MVariableDeclaration(type);
      Symbol s = new Symbol(name, declaration);
      top.put(s);
    }
    return true;
  }
  
  public void endVisit(MethodDeclaration n) {
    //top.dump();
    top = saved2;
    //System.out.println("<< method declaration");
    //top.dump();
    if (hasReturn == false){
      if (!n.getReturnType2().toString().equals("void")){
          error("miss return statement in method \"" + n.getName().getIdentifier() + "\"" + position(n));
      }
    }
  }
  
  public void endVisit(ArrayAccess n){
    //System.out.println( "DUMP: array access " + n);
    String idx = n.getIndex().toString();
    //System.out.println( "DUMP: index " + idx);
    SymbolKey idx_key = new SymbolKey(idx);
    Symbol idx_sym = top.get(idx_key);
    if (idx_sym != null){
      String idx_type = idx_sym.type();
      //System.out.println( "DUMP: index type " + idx_type);
      if (idx_type.equals("String")){
          error( "array access expression \"" + n.toString() + "\" don't support oprand type of String" + position(n));
      }
      else if (idx_type.indexOf("[]") != -1){
          error( "array access expression \"" + n.toString() + "\" don't support oprand type of array" + position(n));
      }
      else if (idx_type.equals("boolean")){
          error( "array access expression \"" + n.toString() + "\" don't support oprand type of boolean" + position(n));
      }
      else if (idx_type.equals("void")){
          error( "array access expression \"" + n.toString() + "\" don't support oprand of void" + position(n));
      }
      else { //type check correct
        String array = n.getArray().toString();
        //System.out.println( "DUMP: array " + array);
        //top.dump();
        SymbolKey array_key = new SymbolKey(array);
        Symbol array_sym = top.get(array_key);
        if (array_sym != null){
          String array_type = array_sym.type();
          //System.out.println( "DUMP: array type " + array_type);
          MType exp_type;
          if (array_type.indexOf("[]") == -1){
              error("undefined array \"" + array + "\"" + position(n));
            return;
          }
          if (array_type.indexOf("int") != -1){
            exp_type = base_type.newType("int");
          }
          else if (array_type.indexOf("boolean") != -1){
            exp_type = base_type.newType("boolean");
          }
          else if (array_type.indexOf("String") != -1){
            exp_type = base_type.newType("String");
          }
          else {
              error( "unsupported type in array access expression \"" + n.toString() + "\"" + position(n));
            return;
          }
          MExpression exp_dec = new MExpression(exp_type);
          Symbol s = new Symbol(n.toString(), exp_dec);
          top.put(s);
          //System.out.println( "\nadd a new array access exp " + n.toString());
          //top.dump();

          //check outbound
          //System.out.println(n.getArray().toString());
          //System.out.println(n.getIndex().toString());
          Integer size = array_sym.size;
          if (size != null){
              Integer index = ConstExpression(n.getIndex());
              if (index != null){
                  if (index < 0){
                      error("out of the lower bound");
                  }
                  else if (index >= size){
                      error("out of the upper bound");
                  }
              }
          }
        }
        else {
            error("undefined array \"" + array + "\"" + position(n));
        }
      }
    }
    else {
        error("undefined reference to array index expression \"" + idx + "\"" + position(n));
    }
  }
  public boolean visit(NumberLiteral n){
    String num = n.getToken();
    MType num_type = base_type.newType("int");
    MExpression num_exp = new MExpression(num_type);
    Symbol s = new Symbol(num, num_exp);
    if (top.get(s.key()) == null){
      top.put(s);
        //System.err.print("\n");
        //top.dump();
    }
    return true;
  }
  public boolean visit(BooleanLiteral n){
    String bool = n.booleanValue()? "true": "false";
    MType bool_type = base_type.newType("boolean");
    MExpression bool_exp = new MExpression(bool_type);
    Symbol s = new Symbol(bool, bool_exp);
    if (top.get(s.key()) == null){
      top.put(s);
      //System.err.print("\n");
      //top.dump();
    }
    return true;
  }
  public boolean visit(StringLiteral n){
    String str = n.getEscapedValue();
    MType str_type = base_type.newType("String");
    MExpression str_exp = new MExpression(str_type);
    Symbol s = new Symbol(str, str_exp);
    if (top.get(s.key()) == null){
      top.put(s);
      //System.err.print("\n");
      //top.dump();
    }
    return true;
  }

  public void endVisit(IfStatement n){
    String exp = n.getExpression().toString();
    SymbolKey exp_key = new SymbolKey(exp);
    Symbol exp_sym = top.get(exp_key);
    if (exp_sym != null){
      String exp_type = exp_sym.type();
      if (!exp_type.equals("boolean")){
          error("if statement condition expression \"" + exp + "\" should be boolean" + position(n));
      }
    }
    else {
        error( "undefined reference to condition expression \"" +  exp + "\"" + position(n));
    }
  }

  public void endVisit(WhileStatement n){
    //top.dump();
    String exp = n.getExpression().toString();
    SymbolKey exp_key = new SymbolKey(exp);
    Symbol exp_sym = top.get(exp_key);
    if (exp_sym != null){
      String exp_type = exp_sym.type();
      if (!exp_type.equals("boolean")){
          error("while statement condition expression \"" + exp + "\" should be boolean" + position(n));
      }
    }
    else {
        error( "undefined reference to condition expression \"" +  exp + "\"" + position(n));
    }
    //top.dump();
  }

  public void endVisit(ReturnStatement n){
    hasReturn = true;
    Expression return_exp = n.getExpression();
    String exp_type = "void";

    if (return_exp != null){
        String exp = return_exp.toString();
        SymbolKey exp_key = new SymbolKey(exp);
        Symbol exp_sym = top.get(exp_key);
        if (exp_sym != null){
            exp_type = exp_sym.type();
        }
    }
    
    ASTNode parent = n.getParent();

    while(parent.getNodeType() != ASTNode.METHOD_DECLARATION){
        parent = parent.getParent();
        if (parent.getNodeType() == ASTNode.COMPILATION_UNIT){
            parent = null;
            break;
        }
    }
      
    if (parent != null){
        MethodDeclaration method_declaration = (MethodDeclaration)(parent);
        String return_type = method_declaration.getReturnType2().toString();
        if (!exp_type.equals(return_type)){
            error("type mismatch in return statement \"" + n.toString().replace(';', ' ').replace('\n', '\b') + "\"" + position(n));
        }
    }
    else {
        error("return statement in non function block" + position(n));
    }
  }

  public boolean visit(QualifiedName n){
      String id = n.getQualifier().toString();
      SymbolKey array_key = new SymbolKey(id);
      Symbol array_sym = top.get(array_key);
      if (array_sym != null){
          String array_type = array_sym.type();
          if (array_type.indexOf("[]") == -1){
              error("non array type \"" + id + "\" in array size access" + position(n));
              return true;
          }
          String exp = n.toString();
          MType exp_type = base_type.newType("int");
          MExpression exp_dec = new MExpression(exp_type);
          Symbol s = new Symbol(exp, exp_dec);
          top.put(s);
      }
      else {
          error("undefined reference to array type \"" + id + "\" in array size access" + position(n));
      }
      return true;
  }

  public boolean visit(ArrayType n){
      Expression e = (Expression)(n.getProperty("exp"));
      if (e != null){
          if (in_method_declaration){
              warning("the given length of array type \"" + n.toString() + "\" will be ignored" + position(n));
          }
          else {
              Integer size = ConstExpression(e);
              if (size != null){
                  if (size <= 0){
                      error("the length \"" + e.toString() + "\" is not a positive integer" + position(n));
                  }
                  else {
                      n.setProperty("size", size);
                      //System.out.println( n.toString() + " size " + size);
                  }
              }
              else {
                  error("unknow size of array type" + position(n));
              }
          }
      }
      return true;
  }

  public boolean visit(ArrayInitializer n)
  {
      VariableDeclarationFragment fragment = (VariableDeclarationFragment)n.getParent();
      if (fragment != null){
          ASTNode node = fragment.getParent();
          if (node != null){
              ArrayType at = null;
              if (node.getNodeType() == ASTNode.FIELD_DECLARATION){
                  at = (ArrayType)(((FieldDeclaration)node).getType());
              }
              else {
                  at = (ArrayType)(((VariableDeclarationStatement)node).getType());
              }
              if (at != null){
                  Integer size = (Integer)at.getProperty("size");
                  Integer bak = size;
                  Integer init_size = Integer.valueOf((n.expressions().size()));
                  if (size != null){
                      if (!size.equals(init_size)){
                          error("size dosen't match in array initializer \"" + n.toString() + "\" old "
                                  + size + " here " + init_size
                                  + position(n));
                      }
                  }
                  else {
                      size = init_size;
                  }
                  if (size != bak){
                    at.setProperty("size", size);
                    //System.out.println( n.toString() + " size " + size);
                  }
              }
          }
      }
      return true;
  }

  /**
    * calculate the const expression
    * @param n: ASTNode
    * @return expression's result, if result is null then it means expression is illegal
  */
  private Integer ConstExpression(Expression n){
      if (n == null)
          return null;

      if (n.getNodeType() == ASTNode.SIMPLE_NAME)
      {
          String id = n.toString();
          SymbolKey key = new SymbolKey(id);
          Symbol sym = top.get(key);
          if (sym != null){
              //System.out.println(id + position(n));
//               if (sym.isFinal == false){
//                   error("variable \"" + id + "\" in constent expression should be constent" + position(n));
//               }
//               else {
                  Integer value = (Integer)(sym.value);
                  if (value == null && sym.isField){
                      error("\"" + id + "\" is declared after this declaration" + position(n));
                  }
                  return value;
              //}
          }
          else {
              //error("undefined reference to symbol \"" + id + "\"" + position(n));
          }
      }
      if (n.getNodeType() == ASTNode.QUALIFIED_NAME)
      {
      }
      
      if (n.getNodeType() == ASTNode.NUMBER_LITERAL)
      {
          return Integer.decode(((NumberLiteral)n).getToken());
      }
      
      if (n.getNodeType() == ASTNode.PREFIX_EXPRESSION)
      {
          if (((PrefixExpression)n).getOperator() == PrefixExpression.Operator.PLUS)
          {
              return ConstExpression(((PrefixExpression)n).getOperand());
          }
          else if (((PrefixExpression)n).getOperator() == PrefixExpression.Operator.MINUS)
          {
              return -ConstExpression(((PrefixExpression)n).getOperand());
          }
      }
      
      if (n.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION)
      {
            
          return ConstExpression(((ParenthesizedExpression)n).getExpression());
      }
      
      if (n.getNodeType() == ASTNode.INFIX_EXPRESSION)
      {
          Integer lvalue = ConstExpression(((InfixExpression)n).getLeftOperand());
          Integer rvalue = ConstExpression(((InfixExpression)n).getRightOperand());
          if (lvalue != null && rvalue != null)
          {
              if (((InfixExpression)n).getOperator() == InfixExpression.Operator.PLUS)
                  return lvalue + rvalue;
              else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.MINUS)
                  return lvalue - rvalue;
              else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.TIMES)
                  return lvalue * rvalue;
              else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.DIVIDE)
                  return lvalue / rvalue;
              else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.REMAINDER)
                  return lvalue % rvalue;
          }
      }
        
      return null;
  }
}
