package Model.Expression;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class HeapReadingExp implements Expr {
    private Expr expr;

    public HeapReadingExp(Expr e)
    {
        expr = e;
    }

    public Value eval(MapInter<String, Value> tbl, HeapInter heap) throws PancakeException
    {
        Value val = expr.eval(tbl, heap);
        if(val.getType() instanceof RefType)
        {
            RefValue rval = (RefValue)val;
            int addr = rval.getAddr();
            if(heap.containsKey(addr))
            {
                return heap.get(addr);
            }
            else throw new PancakeException(addr + " not a valid address in heap");

        }
        else throw new PancakeException("Please input a ref value");
    }
    public String toString()
    {
        return "read from heap " + expr.toString();
    }
    public Expr deepcopy()
    {
        HeapReadingExp evl = new HeapReadingExp(expr.deepcopy());
        return evl;
    }

    @Override
    public Type typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type type=this.expr.typecheck(typeEnv);
        if(type instanceof RefType)
        {
            RefType refType=(RefType) type;
            return refType.getInner();
        }
        else throw new PancakeException("the heapReading exp argument is not a reftype!");
    }
}
