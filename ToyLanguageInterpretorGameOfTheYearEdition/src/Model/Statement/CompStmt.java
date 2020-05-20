package Model.Statement;

import Exception.PancakeException;
import Model.Containers.MapInter;
import Model.Containers.MyStack;
import Model.Containers.StackInter;
import Model.ProgState;
import Model.Type.Type;

public class CompStmt implements IStmt {

    private IStmt first, second;
    public CompStmt(IStmt f, IStmt s)
    {
        first = f;
        second = s;
    }

    public String toString()
    {
        return  "( " + first.toString() + " ; " + second.toString() + " )";
    }

    public ProgState execute(ProgState state) throws PancakeException
    {
        StackInter<IStmt> staque = state.getExeStack();
        staque.push(second);
        staque.push(first);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        CompStmt evl = new CompStmt(first.deepcopy(), second.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        return second.typecheck(first.typecheck(typeEnv));
    }
}