package Model.Statement;

import Exception.*;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Containers.*;
import Model.Expression.Expr;
import Model.ProgState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt {

    private Expr exp;

    public PrintStmt(Expr e) {exp = e;}

    public ProgState execute(ProgState state) throws PancakeException {
        ListInter<Value> o = state.getOut();
        MapInter<String, Value> sym = state.getSymTable();
        HeapInter heap = state.getHeap();
        o.add(exp.eval(sym, heap));
        return null;
    }
    public String toString(){ return "print " + exp.toString();}

    @Override
    public IStmt deepcopy() {
        PrintStmt evl = new PrintStmt(exp.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type type=exp.typecheck(typeEnv);
        return typeEnv;
    }
}
