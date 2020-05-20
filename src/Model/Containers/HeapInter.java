package Model.Containers;

import Model.Value.Value;

import java.util.Collection;
import java.util.Map;

public interface HeapInter {
    public void put(Value s);
    public boolean containsKey(int k);
    public void replace(int k, Value v);
    public Value get(int k);
    public String toString();
    public void remove(int k);
    public int getAddr();
    public Collection<Value> values();
    public void setContent(Map<Integer, Value> m);
    public Map<Integer,Value> getContent();
}
