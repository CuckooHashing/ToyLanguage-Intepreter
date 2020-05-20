package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

import java.util.Objects;

public class RefValue implements Value {

    int address;
    Type locationType;
    public RefValue(int a, Type l)
    {
        address = a;
        locationType = l;
    }
    public String toString()
    {
        return Integer.toString(address) + " " + locationType.toString();
    }
    public Value deepcopy()
    {
        RefValue evl = new RefValue(address,locationType);
        return evl;
    }
    public boolean equals(Object a)
    {
        return super.equals(a) & Objects.equals(a,this);
    }
    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}

    public Type getLocationType() {
        return locationType;
    }
}