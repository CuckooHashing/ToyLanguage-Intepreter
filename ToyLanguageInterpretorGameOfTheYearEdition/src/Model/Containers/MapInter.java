package Model.Containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MapInter<K, T> {
    public void put(K k, T t);
    public boolean containsKey(K k);
    public void replace(K k, T v);
    public T get(K k);
    public String toString();
    public void remove(K k);
    public Collection<T> values();
    public MapInter<K, T> deepcopy();
    public Map<K, T> getContent();
}