package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.Type;
import Model.Value.Value;

public interface Expr {

    public Value eval(MapInter<String, Value> tbl, HeapInter heap) throws PancakeException;
    public String toString();
    public Expr deepcopy();
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException;
}