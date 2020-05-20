package Model.Containers;

import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Heap implements HeapInter {

    private Map<Integer, Value> heap;
    private Integer count;
    public Heap()
    {
        heap = new ConcurrentHashMap<Integer, Value>();
        count = 0;
    }

    public void put(Value elem)
    {
        count = count+1;
        heap.put(count,elem);
    }

    public boolean containsKey(int k)
    {
        Integer i = k;
        return heap.containsKey(i);
    }
    public void replace(int k, Value v)
    {
        Integer i=k;
        heap.replace(i,v);
    }
    public Value get(int k)
    {
        return heap.get(k);
    }
    public String toString()
    {
        return heap.toString();
    }
    public void remove(int k)
    {
        heap.remove(k);
    }
    public int getAddr() {
        return count;
    }
    public Collection<Value> values()
    {
        return heap.values();
    }
    public void setContent(Map<Integer, Value> m) {
        heap = m;
    }
    public Map<Integer, Value> getContent()
    {
        return heap;
    }
}