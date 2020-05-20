package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Expr {

    private Value e;
    public ValueExp(Value ex) { e=ex; }
    public Value eval(MapInter<String, Value> tbl, HeapInter heap)
    {
        return e;
    }
    public String toString()
    {
        return e.toString() ;
    }

    @Override
    public Expr deepcopy() {
        ValueExp evl = new ValueExp(e.deepcopy());
        return evl;
    }

    @Override
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        return e.getType();
    }
}