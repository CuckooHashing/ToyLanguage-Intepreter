package Model.Containers;

import Model.Statement.IStmt;

public interface StackInter<T> {
    public void push(T st);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public StackInter<T> deepcopy();
    public String toString();
}