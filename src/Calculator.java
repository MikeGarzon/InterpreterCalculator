import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
  private String expression;
  private HashMap operators;
  private Context ctx;

  public Calculator() {
    operators = new HashMap();
    operators.put("+","1");
    operators.put("-","1");
    operators.put("/","2");
    operators.put("*","2");
    operators.put("(","0");

    //Add priority for new expressions
    operators.put("^","3");
    operators.put("%","2");
  }
  public void setContext(Context c) {
    ctx = c;
  }
  public void setExpression(String expr) {
    expression = expr;
  }
  public int evaluate() {
    //infix to Postfix
    String pfExpr = infixToPostFix(expression);

    //build the Binary Tree
    Expression rootNode = buildTree(pfExpr);

    //Evaluate the tree
    return rootNode.evaluate(ctx);
  }

  private NonTerminalExpression getNonTerminalExpression(
    String operation, Expression l, Expression r) {
    if (operation.trim().equals("+")) {
      return new AddExpression(l, r);
    }
    if (operation.trim().equals("-")) {
      return new SubtractExpression(l, r);
    }
    if (operation.trim().equals("*")) {
      return new MultiplyExpression(l, r);
    }

    // Add new operation cases for the new expressions on NewExpressions
    if (operation.trim().equals("/")) {
      return new DivisionExpression(l, r);
    }
    if (operation.trim().equals("%")) {
      return new ModuleExpression(l, r);
    }
    if (operation.trim().equals("^")) {
      return new PowerExpression(l, r);
    }
    return null;
  }

  private Expression buildTree(String expr) {
    Stack s = new Stack();

    for (int i = 0; i < expr.length(); i++) {
      String currChar = expr.substring(i, i + 1);

      if (isOperator(currChar) == false) {
        Expression e = new TerminalExpression(currChar);
        s.push(e);
      } else {
        Expression r = (Expression) s.pop();
        Expression l = (Expression) s.pop();
        Expression n =
          getNonTerminalExpression(currChar, l, r);
        s.push(n);
      }
    }//for
    return (Expression) s.pop();
  }

  private String infixToPostFix(String str) {
    Stack s = new Stack();
    String pfExpr = "";
    String tempStr = "";

    String expr = str.trim();
    for (int i = 0; i < str.length(); i++) {

      String currChar = str.substring(i, i + 1);

      if ((isOperator(currChar) == false) &&
          (!currChar.equals("(")) &&
          (!currChar.equals(")"))) {
        pfExpr = pfExpr + currChar;
      }
      if (currChar.equals("(")) {
        s.push(currChar);
      }
      //for ')' pop all stack contents until '('
      if (currChar.equals(")")) {
        tempStr = (String) s.pop();
        while (!tempStr.equals("(")) {
          pfExpr = pfExpr + tempStr;
          tempStr = (String) s.pop();
        }
        tempStr = "";
      }
      //if the current character is an
      // operator
      if (isOperator(currChar)) {
        if (s.isEmpty() == false) {
          tempStr = (String) s.pop();
          String strVal1 =
            (String) operators.get(tempStr);
          int val1 = new Integer(strVal1).intValue();
          String strVal2 =
            (String) operators.get(currChar);
          int val2 = new Integer(strVal2).intValue();

          while ((val1 >= val2)) {
            pfExpr = pfExpr + tempStr;
            val1 = -100;
            if (s.isEmpty() == false) {
              tempStr = (String) s.pop();
              strVal1 = (String) operators.get(
                          tempStr);
              val1 = new Integer(strVal1).intValue();

            }
          }
          if ((val1 < val2) && (val1 != -100))
            s.push(tempStr);
        }
        s.push(currChar);
      }//if

    }// for
    while (s.isEmpty() == false) {
      tempStr = (String) s.pop();
      pfExpr = pfExpr + tempStr;
    }
    return pfExpr;
  }

  private boolean isOperator(String str) {
    return ((str.equals("+")) || (str.equals("-")) ||
            (str.equals("*")) || (str.equals("/")) || //Add new operators
            (str.equals("%")) || (str.equals("^")));
  }
} // End of class

class Context {
  private HashMap varList = new HashMap();
  private Properties p;
  public void assign(String var, int value) {
    varList.put(var, new Integer(value));
  }
  public int getValue(String var) {
    Integer objInt = (Integer) varList.get(var);
    return objInt.intValue();
  }
  public Context(String s) {
    initialize(s);
  }

  private void initialize(String s) {
    this.varList.clear();
    this.p = new Properties();

    try {
      this.p.load(new StringReader(s));
      Enumeration<Object> keys = this.p.keys();

      while(keys.hasMoreElements()) {
        Object key = keys.nextElement();
        this.assign(key.toString(), Integer.parseInt((String)this.p.get(key)));
      }
    } catch (IOException var4) {
      Logger.getLogger(Context.class.getName()).log(Level.SEVERE, (String)null, var4);
    }

}
}
