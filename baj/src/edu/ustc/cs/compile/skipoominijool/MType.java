package edu.ustc.cs.compile.skipoominijool;

public class MType {
	public static MType newType(String s){
		MType type = new MType();
		boolean array = s.indexOf("[]") != -1; //array type
		if (s.indexOf("int") != -1){
			type = new IntegerType();
		}
		else if (s.indexOf("boolean") != -1){
			type = new BooleanType();
		}
		else if (s.indexOf("String") != -1){
			type = new StringType();
		}
		else if (s.indexOf("void") != -1){
			;
		}
		else {
			System.err.println("error type " + s);
		}
		if (array) {
			type = new MArrayType(type);
		}
		return type;
	}
	public String dump(){
		return "void";
	}
}

class IntegerType extends MType {
  public IntegerType () {}
  public String dump(){
	  return "int";
  }
}

class BooleanType extends MType {
  public BooleanType () {}
  public String dump(){
	  return "boolean";
  }
}

class StringType extends MType {
  public StringType () {}
  public String dump(){
	  return "String";
  }
}

class MArrayType extends MType {
  public MType element;
  public MArrayType ( MType et ) { element=et; }
  public String dump(){
	  return element.dump() + "[]";
  }
}

