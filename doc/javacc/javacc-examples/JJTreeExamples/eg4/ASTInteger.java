/* Generated By:JJTree: Do not edit this line. ASTInteger.java */

public class ASTInteger extends SimpleNode {
  public ASTInteger(int id) {
    super(id);
  }

  public ASTInteger(eg4 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(eg4Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
