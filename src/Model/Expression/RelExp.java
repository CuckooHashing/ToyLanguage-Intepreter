package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelExp implements Expr {
    private Expr exp1;
    private Expr exp2;
    private String op;

    public RelExp(String o, Expr e1, Expr e2)
    {
        exp1 = e1;
        exp2 = e2;
        op = o;
    }

    public Value eval(MapInter<String, Value> tbl, HeapInter heap) throws PancakeException
    {
        Value v1, v2;
        v1 = exp1.eval(tbl, heap);

        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op.equals("<")) return new BoolValue(n1<n2);
                if (op.equals("<=")) return new BoolValue(n1<=n2);
                if(op.equals("==")) return new BoolValue(n1==n2);
                if(op.equals("!=")) return new BoolValue(n1!=n2);
                if(op.equals(">")) return new BoolValue(n1>n2);
                if(op.equals(">=")) return new BoolValue(n1>=n2);

            }
            else
                throw new PancakeException("second operand is not an integer");
        }
        else
            throw new PancakeException("first operand is not an integer");

        return new IntValue(0);
    }
    public String toString(){
        return exp1.toString() + " " + op + " " + exp2.toString();
    }
    public Expr deepcopy()
    {
        RelExp evl = new RelExp(op, exp1.deepcopy(),exp2.deepcopy());
        return evl;
    }

    @Override
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type type1,type2;
        type1=exp1.typecheck(typeEnv);
        type2=exp2.typecheck(typeEnv);
        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return new BoolType();
            else throw new PancakeException("second operand is not an int!");
        }
        else throw new PancakeException("RE:first operand is not a int!");
    }
}