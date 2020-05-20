package Model.Value;

import Model.Type.Type;

import java.util.Objects;

public interface Value {
    public Type getType();
    public String toString();
    public Value deepcopy();
    public boolean equals(Object a);
}
