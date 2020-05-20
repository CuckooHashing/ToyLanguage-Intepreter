package Model.Statement;

import Exception.*;
import Model.*;
import Model.Containers.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.util.Map;

public class VarDeclStmt implements IStmt {

    private String name;
    private Type typ;
    public VarDeclStmt(String n, Type t)
    {
        name = n;
        typ = t;
    }
    public String toString()
    {
        return typ.toString() + " " + name;

    }
    public ProgState execute(ProgState prg) throws PancakeException
    {

        MapInter<String, Value> tbl = prg.getSymTable();

        IntType a = new IntType();
        BoolType b = new BoolType();
        StringType s = new StringType();

        if(typ.equals(a))
        {
            IntValue v = (IntValue) a.defaultValue();
            tbl.put(name,v);
        }
        else if(typ.equals(s))
        {
            StringValue str = (StringValue) s.defaultValue();
            tbl.put(name, str);
        }
        else if(typ instanceof RefType)
        {
            Type ref = ((RefType)typ).getInner();
            Value v = new RefType(ref).defaultValue();
            tbl.put(name,v);
        }
        else
        {
            BoolValue v2 = (BoolValue) b.defaultValue();
            tbl.put(name,v2);
        }

        return null;
    }

    @Override
    public IStmt deepcopy() {
        VarDeclStmt evl = new VarDeclStmt(name, typ);
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        MapInter<String,Type> newEnv=new MyMap<>();
        //making a deepcopy for typeEnv
        for(Map.Entry<String,Type> entry:typeEnv.getContent().entrySet())
        {
            newEnv.put(entry.getKey(),entry.getValue().deepcopy());
        }
        newEnv.put(name, typ);
        return newEnv;
    }
}