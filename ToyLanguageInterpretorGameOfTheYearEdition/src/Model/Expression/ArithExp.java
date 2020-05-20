package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Expr {

    private Expr e1;
    private Expr e2;
    private char op;
    public ArithExp(char o, Expr ex1, Expr ex2)
    {
        op = o;
        e1=ex1;
        e2=ex2;
    }

    public Value eval(MapInter<String, Value> tbl, HeapInter heap) throws PancakeException
    {
        Value v1, v2;
        v1= e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op == '+') return new IntValue(n1+n2);
                if (op == '-') return new IntValue(n1-n2);
                if(op == '*') return new IntValue(n1*n2);
                if(op == '/')
                    if(n2 == 0) throw new PancakeException("division by zero");
                    else return new IntValue(n1/n2);
            }
            else
                throw new PancakeException("second operand is not an integer");
        }
        else
            throw new PancakeException("first operand is not an integer");

        return new IntValue(0);
    }

    public String toString() {
        String o;
        if(op==1)
            o="+";
        else if (op==2)
            o="-";
        else if(op==3)
            o="*";
        else o="/";

        return e1.toString() + o + e2.toString();
    }

    @Override
    public Expr deepcopy() {
        ArithExp evl = new ArithExp(op,e1.deepcopy(), e2.deepcopy());
        return evl;
    }

    @Override
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type type1,type2;
        type1=e1.typecheck(typeEnv);
        type2=e2.typecheck(typeEnv);
        if (type1.equals(new IntType()))
        {
            if (type2.equals(new IntType()))
                return new IntType();
            else throw new PancakeException("second operand is not an integer!");
        }
        else throw new PancakeException("AE:first operand is not an integer!");
    }
}