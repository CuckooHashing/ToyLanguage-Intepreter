package Model.Statement;

import Exception.*;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Expression.Expr;
import Model.ProgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadStmt implements IStmt {

    private Expr exp;
    private String var_name;

    public ReadStmt(Expr e, String n)
    {
        exp = e;
        var_name = n;
    }
    public ProgState execute(ProgState state) throws PancakeException
    {
        MapInter<String, Value> sym = state.getSymTable();
        MapInter<StringValue, BufferedReader> ftbl = state.getFileTable();
        HeapInter heap = state.getHeap();
        if(sym.containsKey(var_name))
        {
            Value v = sym.get(var_name);
            if(v.getType().equals(new IntType()))
            {
                Value v2 = exp.eval(sym, heap);
                if(v2.getType().equals(new StringType()))
                {
                    StringValue filename = (StringValue)v2;
                    if(ftbl.containsKey(filename))
                    {
                        BufferedReader br = ftbl.get(filename);
                        try {
                            String line = br.readLine();
                            if(line == null )
                            {
                                Value val = new IntValue(0);
                                sym.replace(var_name,val);
                            }
                            else
                            {
                                try {
                                    Value val = new IntValue(Integer.parseInt(line));
                                    sym.replace(var_name, val);
                                }
                                catch (NumberFormatException e)
                                {
                                    Value val = new IntValue(0);
                                    sym.replace(var_name,val);
                                }
                            }


                        }
                        catch (IOException e)
                        {
                            throw new PancakeException(e.toString());
                        }
                    }
                    else throw new PancakeException("File not in file table");
                }
                else throw new PancakeException("Must be string");
            }
            else throw new PancakeException("Not int when it must be int");
        }
        else throw new PancakeException(var_name + " not defined");


        return null;
    }
    public String toString()
    {
        return "reading " + var_name + " "+ exp.toString();
    }
    public IStmt deepcopy()
    {
        ReadStmt evl = new ReadStmt(exp.deepcopy(), var_name);
        return evl;

    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeVar=typeEnv.get(var_name);
        Type typeExp=exp.typecheck(typeEnv);
        if(typeVar.equals(new IntType()) && typeExp.equals(new StringType()))
            return typeEnv;
        else throw new PancakeException("Reading File:the type of variable name or exp are wrong!");
    }

}