package Model.Statement;

import Exception.*;
import Model.*;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Containers.StackInter;
import Model.Expression.Expr;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStmt implements IStmt {

    private Expr exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Expr e, IStmt t, IStmt el) {exp = e;
    thenS = t;
    elseS = el;}
    public ProgState execute(ProgState state) throws PancakeException
    {
        MapInter<String, Value> sym = state.getSymTable();
        StackInter<IStmt> stk = state.getExeStack();
        HeapInter heap = state.getHeap();
        Value v;
        v = exp.eval(sym, heap);
        if(v.getType().equals(new BoolType()))
        {
            BoolValue val = (BoolValue)v;
            if(val.getVal() == true)
            {
                stk.push(thenS);
            }
            else stk.push(elseS);
        }
        else throw new PancakeException("If requires bool expresion");

        return null;
    }
    public  String toString(){ return "IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+")";}

    @Override
    public IStmt deepcopy() {
        IfStmt evl = new IfStmt(exp.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeExp=exp.typecheck(typeEnv);
        if(typeExp.equals(new BoolType()))
        {
            MapInter<String,Type> thenEnv,elseEnv;
            thenEnv=thenS.typecheck(typeEnv);
            elseEnv=elseS.typecheck(typeEnv);
            return typeEnv;
        }
        else throw new PancakeException("The condition of IF has not the type boolean as an exp!");
    }
}
