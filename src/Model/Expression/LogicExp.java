package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements Expr {

    private Expr e1;
    private Expr e2;
    private char op;

    public LogicExp(char o, Expr ex1, Expr ex2)
    {
        op = o;
        e1=ex1;
        e2=ex2;
    }

    public Value eval(MapInter<String, Value> tbl, HeapInter heap) throws PancakeException
    {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if(v1.getType().equals(new BoolType()))
        {
            v2 = e2.eval(tbl, heap);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;

                boolean b1, b2;
                b1 = i1.getVal();
                b2 = i2.getVal();

                if(op == '&') return new BoolValue(b1&b2);
                if(op == '|') return new BoolValue(b1|b2);


            }
            else throw new PancakeException("Not bool when it must be bool");
        }
        else throw new PancakeException("Not bool when it must be bool");

        return null;
    }
    public String toString()
    {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Expr deepcopy() {

        LogicExp evl = new LogicExp(op, e1.deepcopy(), e2.deepcopy());
        return evl;
    }

    @Override
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type type1,type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
                return new BoolType();
            else throw new PancakeException("second operand is not a boolean!");
        }
        else throw new PancakeException("first operand is not a boolean!");
    }
}