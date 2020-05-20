package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

import java.util.Objects;

public class BoolValue implements Value {

    boolean val;
    public BoolValue(boolean v){val =v;}
    public Type getType()
    {
        return new BoolType();
    }

    public boolean getVal(){return val;}
    public String toString()
    {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) & Objects.equals(obj,this);
    }

    @Override
    public Value deepcopy() {
        BoolValue bvl = new BoolValue(val);
        return bvl;
    }
}
