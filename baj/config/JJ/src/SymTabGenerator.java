package edu.ustc.cs.compile.skipoominijool;

import java.util.*;
import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.util.ir.HIR;

/**
 * used to generator the field variables' symbol table int first scan round
 * @author Bai Aijun
 * @version 1.0
 */
public class SymTabGenerator {
	public int error_num = 0;
    public int warning_num = 0;
    public boolean has_main = false;
    public MSymTable getSymTable(HIR ir) {
        CompilationUnit cu = (CompilationUnit)ir.getIR();
        SymTabGeneratorVisitor visitor = new SymTabGeneratorVisitor();
        if (cu != null) {
            cu.accept(visitor);
        }
        MSymTable top = visitor.getSymTab();
        //visitor.calculateFinalList();
        visitor.calculateFieldList();
		error_num = visitor.error_num;
        warning_num = visitor.warning_num;
		has_main = visitor.has_main;

		return top;
    }
}

/**
 * an ASTVisitor for check routine
 * @author Bai Aijun
 * @version 1.0
 */
class SymTabGeneratorVisitor extends ASTVisitor { //只产生全局变量和函数定义的符号表
  MSymTable top;
  MType base_type;
  public LinkedList finalList = new LinkedList();
  public LinkedList fieldList = new LinkedList();

  public boolean has_main = false;
  public int error_num = 0;
  public int warning_num = 0;

  public void error(String msg){
		System.err.println("Error " + error_num + ": " + msg);
		error_num += 1;
  }
  public void warning(String msg){
		System.err.println("Warning " + warning_num + ": " + msg);
		warning_num += 1;
  }
  public SymTabGeneratorVisitor () {
	  base_type = new MType();
  }
    
  public MSymTable getSymTab() {
    if (!has_main){
      error("method \"main\" not found");
    }
    return this.top;
  }

  void calculateFieldList(){ //一遍能全部计算出来
      if (error_num != 0){
          return;
      }
      for (Iterator<VariableDeclarationFragment> i = fieldList.iterator(); i.hasNext();) {
          VariableDeclarationFragment fragment = i.next();
          Object value = fragment.getProperty("value");
          if (value == null){
              Expression exp = fragment.getInitializer();
              String variable_name = fragment.getName().getIdentifier();
              SymbolKey key = new SymbolKey(variable_name);
              Symbol sym = top.get(key);
              if (sym != null){
                  String type = sym.type();
                  if (type.equals("int")){
                      if (exp != null){
                          Integer _value = (Integer)IntergerConstExpression(exp);
                          if (_value != null){
                              fragment.setProperty("value", _value);
                              sym.value = _value;
                          }
                      }
                      else {
                          if (sym.isFinal){
                              error("final variable should be explicitly initialized when declaring" + position(fragment));
                          }
                          fragment.setProperty("value", 0);
                          sym.value = new Integer(0);
                      }
                  }
                  else if (type.equals("boolean")){
                      if (exp != null){
                          Boolean _value = (Boolean)BooleanConstExpression(exp);
                          if (_value != null){
                              fragment.setProperty("value", _value);
                              sym.value = _value;
                          }
                      }
                      else {
                          if (sym.isFinal){
                              error("final variable should be explicitly initialized when declaring" + position(fragment));
                          }
                          fragment.setProperty("value", false);
                          sym.value = new Boolean(false);
                      }
                  }
                  else if (type.equals("String")){
                      if (exp != null){
                          String _value = (String)StringConstExpression(exp);
                          if (_value != null){
                              fragment.setProperty("value", _value);
                              sym.value = _value;
                          }
                      }
                      else {
                          if (sym.isFinal){
                              error("final variable should be explicitly initialized when declaring" + position(fragment));
                          }
                          fragment.setProperty("value", "");
                          sym.value = new String("");
                      }
                  }
                  else {
                      fragment.setProperty("value", 0);
                  }
              }
              else {
                  System.err.println("bug");
              }
          }
      }
  }
  
  public boolean visit(CompilationUnit n) {
	top = null;
    return true;
  }
  public void endVisit(CompilationUnit n) {
	//print:
	String method_name = "print";
	LinkedList args = null;
	MFunctionDeclaration method_declaration = new MFunctionDeclaration(base_type, args);
	Symbol s = new Symbol(method_name, method_declaration);
    s.isField = true;
	top.put(s);

	method_name = "read";
	s = new Symbol(method_name, method_declaration);
    s.isField = true;
	top.put(s);
  }

  private String position(ASTNode n){
      Integer line = (Integer)(n.getProperty("line"));
      Integer column = (Integer)(n.getProperty("column"));
      if (line != null && column != null){
          return " at line " + line + " column " + column;
      }
      else return new String();
  }
  
  public boolean visit(TypeDeclaration n) {
	top = new MSymTable(top);
    return true;
  }

  public boolean visit(MethodDeclaration n) {
    String method_name = n.getName().getIdentifier();
    int modifiers = n.getModifiers();
    if (!Modifier.isStatic(modifiers)){
      warning("method \"" + method_name + "\" should be static" + position(n));
      n.modifiers().add(n.getAST().newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
    }

	MType return_type = base_type.newType(n.getReturnType2().toString());
    String return_type_s = return_type.dump();
    if (method_name.equals("main")){
      has_main = true;
      if (!return_type_s.equals("void")){
        error("method \"" + method_name + "\" should have return type void" + position(n));
      }
    }
    if (!return_type_s.equals("int") && !return_type_s.equals("void") && !return_type_s.equals("boolean")){
       error("unsuppored return type of method \"" + method_name + "\"" + position(n));
	}
    
	LinkedList args = new LinkedList();
	List list = n.parameters();
	for (Iterator<SingleVariableDeclaration> i = list.iterator(); i.hasNext();) {
		SingleVariableDeclaration variable_declaration = i.next();
		MType type = base_type.newType(variable_declaration.getType().toString());
		args.add(type);
	}
	MFunctionDeclaration method_declaration = new MFunctionDeclaration(return_type, args);
	Symbol s = new Symbol(method_name, method_declaration);
    Symbol found = top.get(s.key());
    if (found == null){
      s.isField = true;
      top.put(s);
    }
    else {
      error("duplicated method declaration \"" + method_name + "\"" + position(n));
    }
    return true;
  }

  public boolean visit(FieldDeclaration n) {
    int modiffers = n.getModifiers();
    if (!Modifier.isStatic(modiffers)){
        warning("member variables \"" + n.toString().replace(';', ' ').replace('\n', '\b') + "\" should be static" + position(n));
      n.modifiers().add(n.getAST().newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
    }
	List list = n.fragments();
	MType type = base_type.newType(n.getType().toString());
	for (Iterator<VariableDeclarationFragment> i = list.iterator(); i.hasNext();) {
		VariableDeclarationFragment fragment = i.next();
		String variable_name = fragment.getName().getIdentifier();
		MVariableDeclaration variable_declaration = new MVariableDeclaration(type);
		Symbol s = new Symbol(variable_name, variable_declaration);
        Symbol found = top.get(s.key());
        if (found == null){
          if (Modifier.isFinal(modiffers)){
            s.isFinal = true;
          }
          fieldList.add(fragment); //肯定可以算出来
          s.isField = true;
		  top.put(s);
        }
        else {
            error("duplicated field variable declaration \"" + variable_name + "\"" + position(fragment));
        }
	}
	return true;
  }

  String StringConstExpression(Expression n){
      if (n == null)
          return null;
      if (n.getNodeType() == ASTNode.SIMPLE_NAME)
      {
          String id = n.toString();
          SymbolKey key = new SymbolKey(id);
          Symbol sym = top.get(key);
          if (sym != null){
            String value = (String)(sym.value);
            return value;
          }
          else {
              error("undefined reference to symbol \"" + id + "\" in constant expression" + position(n));
          }
      }
      else if (n.getNodeType() == ASTNode.STRING_LITERAL){
          return ((StringLiteral)n).getEscapedValue();
      }
      return null;
  }

  Boolean BooleanConstExpression(Expression n){
      if (n == null)
          return null;
      if (n.getNodeType() == ASTNode.SIMPLE_NAME)
      {
          String id = n.toString();
          SymbolKey key = new SymbolKey(id);
          Symbol sym = top.get(key);
          if (sym != null){
            Boolean value = (Boolean)(sym.value);
            return value;
          }
          else {
              error("undefined reference to symbol \"" + id + "\" in constant expression" + position(n));
          }
      }
      else if (n.getNodeType() == ASTNode.ARRAY_ACCESS){
          return true;
      }
      else if (n.getNodeType() == ASTNode.BOOLEAN_LITERAL)
      {
          return ((BooleanLiteral)n).booleanValue();
      }
      else if (n.getNodeType() == ASTNode.PREFIX_EXPRESSION)
      {
          if (((PrefixExpression)n).getOperator() == PrefixExpression.Operator.NOT)
          {
              return !BooleanConstExpression(((PrefixExpression)n).getOperand());
          }
      }
      else if (n.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION)
      {
            
          return BooleanConstExpression(((ParenthesizedExpression)n).getExpression());
      }
      else if (n.getNodeType() == ASTNode.INFIX_EXPRESSION)
      {
          if (((InfixExpression)n).getOperator() == InfixExpression.Operator.CONDITIONAL_OR || ((InfixExpression)n).getOperator() == InfixExpression.Operator.CONDITIONAL_AND){
              Boolean lvalue = (Boolean)BooleanConstExpression(((InfixExpression)n).getLeftOperand());
              Boolean rvalue = (Boolean)BooleanConstExpression(((InfixExpression)n).getRightOperand());
              if (lvalue != null && rvalue != null)
              {
                  if (((InfixExpression)n).getOperator() == InfixExpression.Operator.CONDITIONAL_OR)
                      return lvalue || rvalue;
                  else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.CONDITIONAL_AND)
                      return lvalue && rvalue;
              }
          }
          else {
              Integer lvalue = (Integer)IntergerConstExpression(((InfixExpression)n).getLeftOperand());
              Integer rvalue = (Integer)IntergerConstExpression(((InfixExpression)n).getRightOperand());
              if (lvalue != null && rvalue != null)
              {
                  if (((InfixExpression)n).getOperator() == InfixExpression.Operator.LESS)
                      return lvalue < rvalue;
                  else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.LESS_EQUALS)
                      return lvalue <= rvalue;
                  else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.GREATER)
                      return lvalue > rvalue;
                  else if (((InfixExpression)n).getOperator() == InfixExpression.Operator.GREATER_EQUALS)
                      return lvalue >= rvalue;
              }
          }
      }
      else {
          error("not a constant expression with \"" + "\"" + position(n));
      }
        
      return null;
  }
    
  Integer IntergerConstExpression(Expression n){
      if (n == null)
          return null;

      if (n.getNodeType() == ASTNode.SIMPLE_NAME)
      {
          String id = n.toString();
          SymbolKey key = new SymbolKey(id);
          Symbol sym = top.get(key);
          if (sym != null){
              Integer value = (Integer)(sym.value);
              if (value == null){
                  error("\"" + id + "\" is declared after this declaration" + position(n));
              }
              return value;
          }
          else {
              error("undefined reference to symbol \"" + id + "\" in constant expression" + position(n));
          }
      }
      else if (n.getNodeType() == ASTNode.ARRAY_ACCESS){
          return 0;
      }
      else if (n.getNodeType() == ASTNode.NUMBER_LITERAL)
      {
          return Integer.decode(((NumberLiteral)n).getToken());
      }
      else if (n.getNodeType() == ASTNode.PREFIX_EXPRESSION)
      {
          if (((PrefixExpression)n).getOperator() == PrefixExpression.Operator.PLUS)
          {
              return IntergerConstExpression(((PrefixExpression)n).getOperand());
          }
          else if (((PrefixExpression)n).getOperator() == PrefixExpression.Operator.MINUS)
          {
              return -IntergerConstExpression(((PrefixExpression)n).getOperand());
          }
      }
      else if (n.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION)
      {
            
          return IntergerConstExpression(((ParenthesizedExpression)n).getExpression());
      }
      else if (n.getNodeType() == ASTNode.INFIX_EXPRESSION)
      {
          Integer lvalue = (Integer)IntergerConstExpression(((InfixExpression)n).getLeftOperand());
          Integer rvalue = (Integer)IntergerConstExpression(((InfixExpression)n).getRightOperand());
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
      else {
          error("not a constant expression with \"" + "\"" + position(n));
      }
      return null;
  }

  public boolean visit(SimpleName n) {
      if (!n.isDeclaration()){ //引用变量
          String name = n.getIdentifier();
          if (name.equals("String")) {
              return true;
          }
          if (name.equals("length") /*&& n.getParent().isSimpleName()*/){
              if (n.getParent().toString().indexOf(".") != -1){
                  return true;
              }
              else {
                  error( "can not access length field of non array type \"" +  name + "\"" + position(n));
                  return true;
              }
          }
      
          SymbolKey key = new SymbolKey(name, false);
          Symbol s = top.get(key);
          SymbolKey key2 = new SymbolKey(name, true);
          Symbol s2 = top.get(key2);
          if (s == null && s2 == null){
              //error("\"" + name + "\" is declared after this declaration" + position(n));
          }
      }
      return true;
  }
}
