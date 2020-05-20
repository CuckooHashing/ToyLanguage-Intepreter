package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {

    public boolean equals(Object a)
    {

        if(a instanceof IntType)
            return true;
        return false;
    }

    public String toString()
    {
        return "int";
    }

    @Override
    public Value defaultValue() {
        IntValue val = new IntValue(0);
        return val;
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }
}
