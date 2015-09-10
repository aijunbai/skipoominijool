package edu.ustc.cs.compile.skipoominijool;

import java.util.*;

public abstract class MDeclaration {
	boolean func;
	public boolean is_func(){
		return func;
	}
  public String getType(){
	  return "void";
  }
}

class MVariableDeclaration extends MDeclaration {
  public MType declaration;
  public MVariableDeclaration ( MType t ) { declaration=t; func = false;}
  public String getType(){
	  return declaration.dump();
  }
}

class MExpression extends MDeclaration {
  public MType type;
  public MExpression ( MType t ) { type=t; func = false;}
  public String getType(){
	  return type.dump();
  }
}

class MFunctionDeclaration extends MDeclaration {
  public MType result;
  public LinkedList parameters;
  public MFunctionDeclaration ( MType t, LinkedList tl ) { result=t; parameters=tl; func = true;}
  public String getType(){
	  return result.dump();
  }
}
