public class ModuleExpression extends NonTerminalExpression{

    public ModuleExpression(Expression l, Expression r) {
        super(l, r);
    }
    public int evaluate(Context c) {
        return  getLeftNode().evaluate(c) % getRightNode().evaluate(c);
    }
}

class PowerExpression extends NonTerminalExpression{

    public PowerExpression(Expression l, Expression r) {
        super(l, r);
    }

    public int evaluate(Context c) {
        return (int)Math.pow((double)this.getLeftNode().evaluate(c),
                (double)this.getRightNode().evaluate(c));
    }
}

//Integer Divison
class DivisionExpression extends NonTerminalExpression{

    public DivisionExpression(Expression l, Expression r) {
        super(l, r);
    }
    public int evaluate(Context c) {
        return  getLeftNode().evaluate(c) / getRightNode().evaluate(c);
    }
}