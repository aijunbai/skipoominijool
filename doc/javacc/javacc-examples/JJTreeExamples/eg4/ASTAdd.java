/* Generated By:JJTree: Do not edit this line. ASTAdd.java */

public class ASTAdd extends SimpleNode {
  public ASTAdd(int id) {
    super(id);
  }

  public ASTAdd(eg4 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(eg4Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
