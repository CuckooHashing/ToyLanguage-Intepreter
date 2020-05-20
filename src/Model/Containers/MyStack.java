package Model.Containers;

import Model.Statement.IStmt;

import java.util.Stack;

public class MyStack<T> implements StackInter<T> {

    private Stack<T> stack;

    public MyStack()
    {
        stack = new Stack<T>();
    }

    @Override
    public void push(T st) {
        stack.push(st);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public T peek() {
        return stack.peek();
    }
    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    @Override
    public StackInter<T> deepcopy() {
        StackInter<T> nou = new MyStack<T>();
        Stack<T> aux = new Stack<T>();
        while(stack.isEmpty() == false)
        {
            aux.push(stack.pop());
        }
        while(aux.isEmpty() == false)
        {
            nou.push(aux.peek());
            stack.push(aux.peek());
            aux.pop();
        }
        return nou;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}