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

public class NewStmt implements IStmt {
    private String var_name;
    private Expr expr;
    public NewStmt(String var, Expr e)
    {
        var_name = var;
        expr=e;
    }

    public ProgState execute(ProgState state) throws PancakeException
    {
        MapInter<String, Value> sym = state.getSymTable();
        HeapInter heap  = state.getHeap();
        if(sym.containsKey(var_name))
        {
            Value val = sym.get(var_name);
            if(val.getType() instanceof RefType)
            {
                Value val2 = expr.eval(sym, heap);
                if(val2.getType().equals(((RefValue)val).getLocationType()))
                {
                    heap.put(val2);
                    int key = heap.getAddr();
                    RefValue nou = new RefValue(key, val2.getType());
                    sym.replace(var_name, nou);
                }
                else
                    throw new PancakeException(var_name + " and " + expr.toString() +" not the same type");
            }
            else
                throw new PancakeException(var_name + " is not ref type");

        }
        else
            throw new PancakeException(var_name + " is not in symbol table");

        return null;

    }
    public String toString()
    {
        return "new " + var_name + " "+ expr.toString();
    }
    public IStmt deepcopy()
    {
        NewStmt evl = new NewStmt(var_name, expr.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeVar=typeEnv.get(var_name);
        Type typeExp=expr.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else throw new PancakeException("Heap Allocation:the type of variable name and type are different!");
    }
}