package Model.Statement;

import Exception.*;
import Model.Containers.HeapInter;
import Model.Containers.MapInter;
import Model.Containers.StackInter;
import Model.Expression.Expr;
import Model.ProgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.util.Objects;

public class WhileStmt implements IStmt {

    private Expr expr;
    private IStmt statement;

    public WhileStmt(Expr e, IStmt s)
    {
        expr = e;
        statement = s;
    }

    public ProgState execute(ProgState state) throws PancakeException
    {
        MapInter<String, Value> sym = state.getSymTable();
        HeapInter heap = state.getHeap();
        StackInter<IStmt> stack = state.getExeStack();
        Value val = expr.eval(sym, heap);
        if(val.getType().equals(new BoolType()))
        {
            boolean evaluated = ((BoolValue)val).getVal();
            if(Objects.equals(evaluated, true))
            {
                stack.push(this);
                statement.execute(state);
            }
        }
        else
            throw new PancakeException("Expression must be boolean to fit the while statement");
        return null;
    }
    public String toString()
    {
        return "while (" + expr.toString() + ") execute {" + statement.toString() + "}";
    }
    public IStmt deepcopy()
    {
        WhileStmt evl = new WhileStmt(expr.deepcopy(), statement.deepcopy());
        return evl;
    }

    @Override
    public MapInter<String, Type> typecheck(MapInter<String, Type> typeEnv) throws PancakeException {
        Type typeExp=expr.typecheck(typeEnv);
        if(typeExp.equals(new BoolType()))
        {
            MapInter<String,Type> typEnv1 = statement.typecheck(typeEnv);
            return typeEnv;
        }
        else throw new PancakeException("While Statement:the expression can't be evaluated to boolean!");
    }


}