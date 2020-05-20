package Model.Containers;

import java.util.LinkedList;

public class MyList<T> implements ListInter<T> {
    private LinkedList<T> list = new LinkedList<T>();
    @Override
    public boolean isEmpty() {
        if(this.list.isEmpty())
            return true;
        else return false;
    }

    @Override
    public boolean add(T o)
    {
        return list.add(o);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public String toString()
    {
        return list.toString();
    }

}
