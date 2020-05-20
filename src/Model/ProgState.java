package Model;
import Model.Containers.*;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import Exception.PancakeException;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class ProgState {

    private StackInter<IStmt> exeStack ;
    private MapInter<String, Value> symTable;
    private ListInter<Value> out ;
    private MapInter<StringValue, BufferedReader> FileTable = new MyMap<StringValue, BufferedReader>();
    private HeapInter heap;
    private IStmt originalProgram;
    private int id = 0;
    private static int global_id = 0;

    public ProgState(StackInter<IStmt> stk, MapInter<String, Value> symtbl, ListInter<Value> ot, IStmt prg){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg.deepcopy();
        heap = new Heap();
        stk.push(prg);
        id = getGlobal_id();
    }

    private synchronized int getGlobal_id() { global_id++;
        return global_id; }

    public int getId() { return id; }
    public void setId(int i) { id = i; }
    public StackInter<IStmt> getExeStack() {return exeStack;}
    public MapInter<String, Value> getSymTable() {return  symTable;}
    public ListInter<Value> getOut(){return out;}
    public IStmt getOriginalProgram(){return originalProgram;}
    public MapInter<StringValue, BufferedReader> getFileTable(){return FileTable;}
    public HeapInter getHeap(){return heap;}
    public void setExeStack(StackInter<IStmt> stk) {exeStack=stk;}
    public void setSymTable(MapInter<String , Value> ceva)
    {
        symTable = ceva;
    }
    public void setOut(ListInter<Value> o)
    {
        out = o;
    }
    public void setOriginalProgram(IStmt op)
    {
        originalProgram=op;
    }
    public void setFileTable(MapInter<StringValue, BufferedReader> tbl) { FileTable = tbl; }
    public void setHeap (HeapInter h) { heap = h; }
    public boolean isNotCompleted() { return !(exeStack.isEmpty()); }
    public ProgState oneStep() throws PancakeException {
        if(exeStack.isEmpty())
            throw new PancakeException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
    public String toString()
    {
        return "\nThread id: " + Integer.toString(id) + "\nExecution Stack -> "+ exeStack.toString() +"\nSymbols Table -> " + symTable.toString() + "\nOut -> " + out.toString() + "\nFileTable -> " + FileTable.toString() + "\n" +
                "Heap -> " + heap.toString() + "\n";
    }

}