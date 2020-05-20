package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.Type;
import Model.Value.Value;

public class VarExp implements Expr {

    private String id;
    public VarExp(String v)
    {
        id=v;
    }
    public Value eval(MapInter<String, Value> tbl, HeapInter heap)
    {
        return tbl.get(id);

    }
    public String toString()
    {
        return id;
    }

    @Override
    public Expr deepcopy() {
        VarExp evl = new VarExp(id);
        return evl;
    }

    @Override
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        return typeEnv.get(id);
    }
}