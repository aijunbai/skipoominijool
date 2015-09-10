package edu.ustc.cs.compile.skipoominijool;


public class SymbolKey {
  private boolean is_func;
  private String name;

  public SymbolKey (String id) {
    name = id;
    is_func = false;
  }

  public SymbolKey (String id, boolean func) {
    name = id;
    is_func = func;
  }

  public String toString() {
	  return (is_func? "#F ": "#V ") + name;
  }

  public int hashCode(){
	  return toString().hashCode();
  }
}
