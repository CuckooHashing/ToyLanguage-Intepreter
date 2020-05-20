package Model.Statement;

import Exception.PancakeException;
import Model.Containers.MapInter;
import Model.ProgState;
import Model.Type.Type;

public interface IStmt {

    public ProgState execute(ProgState state) throws PancakeException;
    public String toString();
    public IStmt deepcopy();
    MapInter<String,Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException;
}

