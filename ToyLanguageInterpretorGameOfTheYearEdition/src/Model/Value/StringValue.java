package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

import java.util.Objects;

public class StringValue implements Value {

    String str;

    public StringValue(String v)
    {
        str = v;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) & Objects.equals(obj,this);
    }

    public Type getType()
    {
        return new StringType();
    }
    public String getVal()
    {
        return str;
    }
    ///public value getValue();
    public String toString()
    {
        return str;
    }
    public Value deepcopy()
    {
        StringValue s = new StringValue(str);
        return s;
    }
}
