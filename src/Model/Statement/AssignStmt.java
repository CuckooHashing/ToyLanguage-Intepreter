package Model.Statement;

import Exception.PancakeException;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Containers.StackInter;
import Model.Expression.Expr;
import Model.ProgState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {

    private String id;
    private Expr exp;

    public AssignStmt(String i, Expr e)
    {
        id = i;
        exp = e;
    }
    public String toString() {
        return id + "=" + exp.toString();
    }

    public ProgState execute(ProgState state) throws PancakeException {
        StackInter<IStmt> stk = state.getExeStack();
        MapInter<String, Value> symTbl = state.getSymTable();
        HeapInter heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if (symTbl.containsKey(id)) {
            Type typId = (symTbl.get(id)).getType();
            if (val.getType().equals(typId))
                symTbl.replace(id, val);
            else
                throw new PancakeException("declared type of variable " + id + " and type of the assigned expression do not match");
        } else
            throw new PancakeException("the used variable " + id + " was not declared before");


        return null;
    }

    @Override
    public IStmt deepcopy() {
        AssignStmt evl = new AssignStmt(id,exp.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeVar=typeEnv.get(id);
        Type typeExp=exp.typecheck(typeEnv);
        if(typeVar.equals(typeExp))
            return typeEnv;
        else throw new PancakeException("Assignment:right hand side and left hand side have different types.");
    }
}