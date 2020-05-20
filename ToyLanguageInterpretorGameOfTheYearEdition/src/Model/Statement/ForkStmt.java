package Model.Statement;
import Exception.PancakeException;
import Model.Containers.*;
import Model.ProgState;
import Model.Type.Type;
import Model.Value.Value;

public class ForkStmt implements IStmt {
    private IStmt stmt;

    public ForkStmt(IStmt st) { stmt = st; }

    public ProgState execute(ProgState state) throws PancakeException {
        ProgState child = new ProgState(new MyStack<IStmt>(),  new MyMap<String, Value>(), new MyList<Value>(), stmt);

        StackInter<IStmt> nou = new MyStack<IStmt>();
        nou.push(stmt);
        child.setExeStack(nou);
        child.setSymTable(state.getSymTable().deepcopy());
        child.setFileTable(state.getFileTable());
        child.setOut(state.getOut());
        child.setHeap(state.getHeap());
        return child;
    }

    @Override
    public IStmt deepcopy() {
        ForkStmt evl = new ForkStmt(stmt.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        MapInter<String,Type> typEnv1 = stmt.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() { return "FORK( " + stmt.toString() + ") "; }
}
