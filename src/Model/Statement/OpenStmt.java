package Model.Statement;

import Exception.*;
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
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpenStmt implements IStmt {

    private Expr exp;
    public OpenStmt(Expr e)
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
            if(!(ftbl.containsKey(filename)))
            {
                try {
                    String leString = filename.getVal();
                    BufferedReader br = Files.newBufferedReader(Paths.get(leString));
                    ftbl.put(filename, br);
                }
                catch (IOException e)
                {
                    throw new PancakeException(e.toString());
                }
            }
            else throw new PancakeException("Give file was already opened");
        }
        else throw new PancakeException("Filename must be a string");

        return null;

    }
    public String toString(){
        return "opening " + exp.toString();
    }
    public IStmt deepcopy()
    {
        OpenStmt evl = new OpenStmt(exp.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeExp=exp.typecheck(typeEnv);
        return typeEnv;
    }
}