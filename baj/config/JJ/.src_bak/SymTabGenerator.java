package edu.ustc.cs.compile.skipoominijool;

import java.util.*;
import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.util.ir.HIR;

public class SymTabGenerator {
    public MSymTable getSymTable(HIR ir) {
        CompilationUnit cu = (CompilationUnit)ir.getIR();
        SymTabGeneratorVisitor visitor = new SymTabGeneratorVisitor();
        if (cu != null) {
            cu.accept(visitor);
        }
        return visitor.getSymTab();
    }
}

class SymTabGeneratorVisitor extends ASTVisitor { //只产生全局变量和函数定义的符号表
  MSymTable top;
  MType base_type;
  boolean has_main = false;

  public SymTabGeneratorVisitor () {
	  base_type = new MType();
  }
    
  public MSymTable getSymTab() {
    if (!has_main){
      System.err.println("ERROR: method main not found");
      return null;
    }
    return this.top;
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
  
  public boolean visit(TypeDeclaration n) {
	top = new MSymTable(top);
    return true;
  }

  public boolean visit(MethodDeclaration n) {
    String method_name = n.getName().getIdentifier();
    int modifiers = n.getModifiers();
    if (!Modifier.isStatic(modifiers)){
      System.err.println("WARNING: method " + method_name + " should be static");
      n.modifiers().add(n.getAST().newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
    }

	MType return_type = base_type.newType(n.getReturnType2().toString());
    String return_type_s = return_type.dump();
    if (method_name.equals("main")){
      has_main = true;
      if (!return_type_s.equals("void")){
        System.err.println("ERROR: method " + method_name + " should return void");
      }
    }
    if (!return_type_s.equals("int") && !return_type_s.equals("void") && !return_type_s.equals("boolean")){
      System.err.println("ERROR: method " + method_name + " return type error");
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
      System.err.println("ERROR: method declaration " + method_name + " duplicated");
    }
    return true;
  }

  public boolean visit(FieldDeclaration n) {
    int modiffers = n.getModifiers();
    if (!Modifier.isStatic(modiffers)){
      System.err.println("WARNING: member variables " + n.toString() + " should be static");
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
            //System.err.println("DUMP: final " + variable_name);
          }
          s.isField = true;
		  top.put(s);
        }
        else {
          System.err.println("ERROR: field variable declaration " + variable_name + " duplicated");
        }
	}
	return true;
  }
}
