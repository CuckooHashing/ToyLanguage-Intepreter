package Model.Statement;

import Model.Containers.MapInter;
import Model.ProgState;
import Model.Type.Type;
import Exception.PancakeException;

public class NopStmt implements IStmt {

    public NopStmt()
    {}
    public ProgState execute(ProgState prg)
    {
        return null;
    }
    public String toString()
    {
        return "nop";
    }

    @Override
    public IStmt deepcopy() {
        NopStmt evl= new NopStmt();
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        return null;
    }
}