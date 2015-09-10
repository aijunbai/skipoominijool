package edu.ustc.cs.compile.skipoominijool;

import org.eclipse.jdt.core.dom.*;
import java.util.*;

import edu.ustc.cs.compile.platform.util.ir.HIR;
import edu.ustc.cs.compile.platform.interfaces.CheckerInterface;
import edu.ustc.cs.compile.platform.interfaces.CheckerException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;

public class Checker implements CheckerInterface {
  SymTabGenerator top_gen;
  MSymTable top;
  
  public boolean check(InterRepresent ir) throws CheckerException {
    System.out.println("#SkipOOMiniJOOL Checker - by baj@mail.ustc.edu.cn (c) 2008");
    top_gen  = new SymTabGenerator();
    top = top_gen.getSymTable((HIR)ir);
    //System.out.println( "\ninit");
    //if (top != null)	top.dump();
    
    CompilationUnit cu = (CompilationUnit)ir.getIR();
    CheckerVisitor visitor = new CheckerVisitor(top);
    visitor.setIR(ir);
    cu.accept(visitor);
    //System.out.println( "\nfinal");
    //if (top != null)	top.dump();
    return visitor.success();
  }
}

class CheckerVisitor extends ASTVisitor {
    // 如果语义检查时发现错误，将success置为false。
  private boolean success = true;
  private boolean method_declaration = false;
  MSymTable top, saved, saved2; //在遍历过程中将为每个表达式都建立符号表，构成定型环境
  MType base_type;
  CompilationUnit root;
  
  private InterRepresent ir = null;

  public void error(String msg){
    System.err.println("ERROR: " + msg);
    //success = false;
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

  public boolean visit(CompilationUnit cu){
    root = cu;
    return true;
  }
  
  public boolean visit(Block n) {
    saved = top;
    if ( !method_declaration){
      top = new MSymTable(top);
    }
    else {
      method_declaration = false;
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
        error("variable " + left_string + " is final");
        return;
      }
      String left_type = left_sym.type();
      String right_type = right_sym.type();
      if ( op != Assignment.Operator.ASSIGN && (!left_type.equals("int") || !right_type.equals("int"))){
      //只有int可以+=等
        error( "assign exp " + n.toString() + " type error");
      }
      else if ( !left_type.equals(right_type) && !(left_type.equals("boolean") && right_type.equals("int")))
      {
        error( "assign exp " + n.toString() + " type dosen't match");
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
      error( "assign exp " + n.toString() + " type error");
    }
  }
  
  public void endVisit(MethodInvocation n) {
    String method_name = n.getName().getIdentifier();
    SymbolKey key = new SymbolKey(method_name, true);
    Symbol s = top.get(key);
    if (s == null){
      error( "method " +  method_name + " not found");
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
            if (!arg_type.equals(type.dump())){
              error( "method " +  method_name + " arg " + arg_exp +" type dosen't match");
              match = false;
            }
          }
          else {
            error( "method " +  method_name + " arg " + arg_exp +" type dosen't match");
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
        error( "method " +  method_name + " args size dosen't match");
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
        error( "prefix exp " + n.toString() + " don't support String oprand");
      }
      else if (opr_type.indexOf("[]") != -1){
        error( "prefix exp " + n.toString() + " don't support array oprand");
      }
      else if (opr_type.equals("boolean") && op != PrefixExpression.Operator.NOT){
        error( "prefix exp " + n.toString() + " don't support boolean oprand");
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
      error( "prefix exp " + n.toString() + " type error");
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
        error( "infix exp " + n.toString() + " don't support String oprand");
      }
      else if (left_type.indexOf("[]") != -1 || right_type.indexOf("[]") != -1){
        error( "infix exp " + n.toString() + " don't support array oprand");
      }
      else if ((left_type.equals("boolean") || right_type.equals("boolean"))
               && ( op != InfixExpression.Operator.CONDITIONAL_AND && op != InfixExpression.Operator.CONDITIONAL_OR)
              ){
        error( "infix exp " + n.toString() + " don't support boolean oprand");
      }
      else { //type check correct
        String exp = n.toString();
        MType exp_type = base_type.newType("int");
        if (op == InfixExpression.Operator.CONDITIONAL_AND || op == InfixExpression.Operator.CONDITIONAL_OR){
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
      error( "infix exp " + n.toString() + " type error");
    }
  }
  
  public boolean visit(SimpleName n) {
    if (!n.isDeclaration()){ //引用变量
      String name = n.getIdentifier();
      if (name.equals("String")) {
        return true;
      }
      SymbolKey key = new SymbolKey(name, false);
      Symbol s = top.get(key);
      SymbolKey key2 = new SymbolKey(name, true);
      Symbol s2 = top.get(key2);
      if (s == null && s2 == null){
        error( "id " +  name + " not found");
      }
    }
    return true;
  }
  
  public boolean visit(PrimitiveType n) {
    String type = n.toString();
    if (!type.equals("int") && !type.equals("boolean") && !type.equals("void")){
      error( "type " + type + " not support");
    }
    return true;
  }
  
  public boolean visit(Modifier n) {
    if (!n.toString().equals("final") && !n.toString().equals("static")){
      error( "modiffer " + n.toString() + " not support");
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
            error("in array initializer " + n.toString() + " " + exp + " type dosen't match");
            match = false;
          }
        }
      }
      else {
        error("array initializer " + n.toString() + " type error");
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
    List list = n.fragments();
    MType type = base_type.newType(n.getType().toString());
    if (type.dump().equals("void")){
      error("declaration " + n.toString() + " type can not be void");
      return;
    }
    for (Iterator<VariableDeclarationFragment> i = list.iterator(); i.hasNext();) {
      VariableDeclarationFragment fragment = i.next();
      String variable_name = fragment.getName().getIdentifier();
      MVariableDeclaration variable_declaration = new MVariableDeclaration(type);
      Symbol s = new Symbol(variable_name, variable_declaration);
      Symbol found = top.curget(s.key()); //同一生存周期，变量重复定义
      if (found != null){
        error( "id " + variable_name + " duplicated in same scope");
        return;
      }
      else {
        found = top.get(s.key());
        if (found != null){
          if (found.isField == false){
            error ( "local id " + variable_name + " duplicated"); //不允许 block 间，有同名变量
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
              error("declaration " + fragment.toString() + " type dosen't match");
              match = false;
            }
          }
          else {
            error("declaration " + fragment.toString() + " type error");
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
      error("declaration " + n.toString() + " type can not be void");
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
            error("field declaration " + fragment.toString() + " type dosen't match");
          }
        }
        else {
          error("field declaration " + fragment.toString() + " type error");
        }
      }
    }
  }
  
  public boolean visit(MethodDeclaration n) { //函数内的参数定义，视为一个新 block
    saved2 = top;
    top = new MSymTable(top);
    //System.out.println(">> method declaration");
    method_declaration = true; //MethodDeclaration 后紧跟的一个 block 就不用新建符号表了
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
        error( "array access exp " + n.toString() + " don't support String oprand");
      }
      else if (idx_type.indexOf("[]") != -1){
        error( "array access exp " + n.toString() + " don't support array oprand");
      }
      else if (idx_type.equals("boolean")){
        error( "array access exp " + n.toString() + " don't support boolean oprand");
      }
      else if (idx_type.equals("void")){
        error( "array access exp " + n.toString() + " don't support void oprand");
      }
      else { //type check correct
        String array = n.getArray().toString();
            //System.out.println( "DUMP: array " + array);
        SymbolKey array_key = new SymbolKey(array);
        Symbol array_sym = top.get(array_key);
        if (array_sym != null){
          String array_type = array_sym.type();
                //System.out.println( "DUMP: array type " + array_type);
          MType exp_type;
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
            error( "array access exp " + n.toString() + " type error");
            return;
          }
          MExpression exp_dec = new MExpression(exp_type);
          Symbol s = new Symbol(n.toString(), exp_dec);
          top.put(s);
                //System.out.println( "\nadd a new array access exp " + n.toString());
                //top.dump();
        }
        else {
          error( "array access exp " + n.toString() + " type error");
        }
      }
    }
    else {
      error( "array access exp " + n.toString() + " type error");
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
        error("if statement exp " + exp + " should be boolean");
      }
    }
    else {
      error("if statement exp " + exp + " type error");
    }
  }

  public void endVisit(WhileStatement n){
    String exp = n.getExpression().toString();
    SymbolKey exp_key = new SymbolKey(exp);
    Symbol exp_sym = top.get(exp_key);
    if (exp_sym != null){
      String exp_type = exp_sym.type();
      if (!exp_type.equals("boolean")){
        error("while statement exp " + exp + " should be boolean");
      }
    }
    else {
      error("while statement exp " + exp + " type error");
    }
    //top.dump();
  }
}
