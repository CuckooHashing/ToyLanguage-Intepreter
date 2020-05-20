package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {
    public boolean equals(Object a)
    {
        if(a instanceof BoolType)
            return true;
        return false;
    }
    public String toString()
    {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        BoolValue val = new BoolValue(false);
        return val;
    }

    @Override
    public Type deepcopy() {
        return new BoolType();
    }
}
