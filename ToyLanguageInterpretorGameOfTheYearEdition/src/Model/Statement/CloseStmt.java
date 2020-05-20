package Model.Statement;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Expression.Expr;
import Model.ProgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseStmt implements IStmt {

    private Expr exp;
    public CloseStmt(Expr e)
    {
        exp = e;
    }

    public ProgState execute(ProgState state) throws PancakeException
    {
        MapInter<String, Value> sym = state.getSymTable();
        MapInter<StringValue, BufferedReader> ftbl = state.getFileTable();
        HeapInter heap = state.getHeap();
        Value val = exp.eval(sym, heap);
        if(val.getType().equals(new StringType()))
        {
            StringValue filename = (StringValue)val;
            if(ftbl.containsKey(filename))
            {
                try {
                    BufferedReader br = ftbl.get(filename);
                    br.close();
                    ftbl.remove(filename);
                }
                catch (IOException e)
                {
                    throw new PancakeException(e.toString());
                }

            }
            else
                throw new PancakeException(filename + " is not a valid file name");
        }
        else
            throw new PancakeException("not string when I need string");

        return null;
    }
    public String toString()
    {
        return "close " + exp.toString();
    }
    public IStmt deepcopy()
    {
        CloseStmt evl = new CloseStmt(exp.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeExp=exp.typecheck(typeEnv);
        return typeEnv;
    }
}