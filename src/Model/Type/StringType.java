package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {

    public boolean equals(Object another)
    {
        if(another instanceof StringType)
            return true;
        return false;
    }
    public String toString()
    {
        return "string";
    }
    public Value defaultValue(){return new StringValue("");}

    @Override
    public Type deepcopy() {
        return new StringType();
    }
}
