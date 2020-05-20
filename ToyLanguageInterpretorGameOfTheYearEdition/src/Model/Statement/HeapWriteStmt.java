package Model.Statement;

import Exception.*;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Expression.Expr;
import Model.ProgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class HeapWriteStmt implements IStmt {
    private String var_name;
    private Expr expr;

    public HeapWriteStmt(String s, Expr e)
    {
        var_name = s;
        expr=e;
    }


    public ProgState execute(ProgState state) throws PancakeException
    {
        MapInter<String, Value> sym = state.getSymTable();
        HeapInter heap = state.getHeap();

        if(sym.containsKey(var_name))
        {
            Value val = sym.get(var_name);
            if(val.getType() instanceof RefType)
            {
                int addr = ((RefValue)val).getAddr();

                if(heap.containsKey(addr))
                {
                    Value evaluated = expr.eval(sym, heap);
                    if(evaluated.getType().equals(((RefValue)val).getLocationType()))
                    {
                        heap.replace(addr, evaluated);
                    }
                    else
                        throw new PancakeException("Two different types");
                }
                else
                    throw new PancakeException("Address is not in heap");            }
            else
                throw new PancakeException(val.toString() + " is not RefValue");
        }
        else
            throw new PancakeException(var_name + " not defined in symbol table");

        return null;
    }
    public String toString()
    {
        return "write to heap "+var_name+" "+expr.toString();
    }
    public IStmt deepcopy()
    {
        HeapWriteStmt evl = new HeapWriteStmt(var_name,expr.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeVar=typeEnv.get(var_name);
        Type typeExp=expr.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else throw new PancakeException("Heap Writing:the type of variable name and type are different!");
    }
}