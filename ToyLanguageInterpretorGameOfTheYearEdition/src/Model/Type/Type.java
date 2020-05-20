package Model.Type;

import Model.Value.Value;

public interface Type {
    public boolean equals(Object another);
    public String toString();
    public Value defaultValue();

    Type deepcopy();
}
