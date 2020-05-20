package Model.Containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @param <K>
 * @param <T>
 */
public class MyMap<K,T>  implements  MapInter<K,T>{

    private ConcurrentHashMap<K,T> map;
    static int count = 0;

    public MyMap()
    {
        map = new ConcurrentHashMap<K,T>();
    }

    public void put(K k, T t)
    {
        map.put(k, t);
    }
    public boolean containsKey(K k)
    {
        return map.containsKey(k);
    }
    public void replace(K k, T v)
    {
        map.replace(k,v);
    }
    public T get(K k)
    {
        return map.get(k);
    }

    public void remove(K k)
    {
        map.remove(k);
    }

    public String toString()
    {
        return map.toString();
    }

    public Collection<T> values()
    {
        return map.values();
    }

    @Override
    public MapInter<K, T> deepcopy() {
        MapInter<K, T> nou = new MyMap<K, T>();
        Iterator hmIterator = map.entrySet().iterator();
        while(hmIterator.hasNext())
        {
            HashMap.Entry elem = (HashMap.Entry)hmIterator.next();
            nou.put((K)elem.getKey(), (T)elem.getValue());
        }
        return nou;
    }
    public Map<K, T> getContent() {
        Map<K,T> map=new ConcurrentHashMap<>();
        map=this.map;
        return map;
    }
}