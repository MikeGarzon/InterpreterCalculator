class ModuleExpression extends NonTerminalExpression{

    public ModuleExpression(Expression l, Expression r) {
        super(l, r);
    }
    public int evaluate(Context c) {
        if  (getRightNode().evaluate(c) == 0){
            System.out.println(" Not valid expression: Division by 0");
            return -99999999;
        }
        System.out.println(getRightNode().evaluate(c));
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

//Integer Division
class DivisionExpression extends NonTerminalExpression{

    public DivisionExpression(Expression l, Expression r) {
        super(l, r);
    }
    public int evaluate(Context c) {
        if  (getRightNode().evaluate(c) == 0){
            System.out.println(" Not valid expression: Division by 0");
            return -99999999;
        }
        return  getLeftNode().evaluate(c) / getRightNode().evaluate(c);
    }

}