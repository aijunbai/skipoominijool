/*
  This program parses Calc programs and prints the resulting parse tree.
  Calc programs should be entered on standard input.
  The resulting parse tree is printed on standard output.
*/

class CalcDumpTree {

  public static void main(String args[]) {
  
    /*
      Create a parser which reads from standard input
    */
    CalcParser parser = new CalcParser(System.in);
    
    try {
    
      /*
        Start parsing from the nonterminal "Start".
      */
      SimpleNode parseTree = parser.Start();
      
      /*
        If parsing completed without exceptions, print the resulting
        parse tree on standard output.
      */
      parseTree.dump("");
      
    } catch (Exception e) {
    
      /*
        An exception occurred during parsing.
        Print the error message on standard output.
      */
      System.out.println("---------------------------");
      System.out.println("Sorry, couldn't parse that.");
      System.out.println(e.getMessage());
      System.out.println("---------------------------");
      
      /*
        Print the call stack 
      */
      System.out.println("Call stack:");
      e.printStackTrace();
      System.out.println("---------------------------");
    }
  }
  
}
