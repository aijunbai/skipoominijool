package edu.ustc.cs.compile.skipoominijool;

import java.util.*;

public class Symbol {
  public String name;
  public MDeclaration dec;
  public boolean isFinal = false;
  public boolean isField = false; //标记是否为全局的符号
  public Object value = null; //标记变量的值
  public Integer size = null; //如果是数组类型，就表示维数

  public Symbol(String id, MDeclaration d){
    name = id;
	dec = d;
  }
  
  public SymbolKey key(){
    boolean func = dec.is_func();
    SymbolKey key = new SymbolKey(name, func);
    return key;
  }
  
  public String name() {return name;}
  
  public String type() {
	  return dec.getType();
  }

  public String toString() {
	  if (dec.is_func()){
		  String out = "#F " + name() + " " + type() + "( ";
		  List list = ((MFunctionDeclaration)dec).parameters;
		  if (list != null) {
			  for (Iterator<MType> i = list.iterator(); i.hasNext();) {
				  MType type = i.next();
				  out += type.dump() + " ";
			  }
		  }
		  out += ")";
		  return out;
	  }
	  else return "#V " + name() + " " + type();
  }
}
