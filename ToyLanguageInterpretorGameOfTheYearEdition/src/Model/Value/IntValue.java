package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

import java.util.Objects;

public class IntValue implements Value {

    int val;
    public IntValue(int v){val =v;}
    public Type getType()
    {
        return new IntType();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) & Objects.equals(obj, this);
    }

    public int getVal(){return val;}
    public String toString()
    {
        return Integer.toString(val);
    }
    public Value deepcopy()
    {
        IntValue ivl = new IntValue(val);

        return ivl;
    }

}