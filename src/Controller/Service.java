package Controller;

import Exception.PancakeException;
import Model.Containers.*;
import Model.Statement.*;
import Model.ProgState;
import Model.Value.RefValue;
import Model.Value.*;
import Repo.RepoInter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {

    private RepoInter repo;
    private ExecutorService executor;
    private List<ProgState> prgList;

    public Service(RepoInter r)
    {
        repo = r;
        //prgList = removeCompletedPrg(repo.getPrgList());
        //executor = Executors.newFixedThreadPool(2);
    }

    public ProgState oneStep(ProgState state) throws PancakeException
    {
        //Stack<IStmt> stk=state.getExeStack();
        StackInter<IStmt> stk = state.getExeStack();
        if(stk.isEmpty()){
            throw  new PancakeException("Empty exec stack");}
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public List<ProgState> getProgramList()
    {
//        List<ProgState> lista = new ArrayList<ProgState>();
//        for(int i = 0; i<repo.getPrgList().size(); i++)
//        {
//            if(repo.getPrgList().get(i).getExeStack().isEmpty() == false)
//                lista.add(repo.getPrgList().get(i));
//        }
//        return lista;
        return repo.getPrgList();
    }

    public void oneStepForAllPrg(List<ProgState> programStateList) {
        programStateList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (PancakeException e) {
                e.printStackTrace();
            }
        });
        List<Callable<ProgState>> callableList = programStateList.stream()
                .map((ProgState p) -> (Callable<ProgState>) (p::oneStep))
                .collect(Collectors.toList());
        try {
            List<ProgState> newProgList = executor.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            //add the new created threads to the list of existing threads
            programStateList.addAll(newProgList);
            programStateList.forEach(prg -> {
                try {
                    repo.logPrgStateExec(prg);
                } catch (PancakeException e) {
                    e.printStackTrace();
                }
            });
            repo.setPrgList(programStateList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   public void allSteps() throws PancakeException, IOException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgState> programs = removeCompletedPrg(repo.getPrgList());
        if(programs.size()>0)
        {
            execGarbageCollector(programs);
            oneStepForAllPrg(programs);
            //programs = removeCompletedPrg(repo.getPrgList());
            executor.shutdownNow();
        }
//        execGarbageCollector(prgList);
//        oneStepForAllPrg(prgList);
//            prgList=removeCompletedPrg(repo.getPrgList());
//        if(prgList.size() <= 0) {
//            executor.shutdownNow();
//            repo.setPrgList(prgList);
//        }
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues)
    {
        List<Integer> symtable= symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue val=(RefValue)v;return val.getAddr();})
                .collect(Collectors.toList());
        return symtable;
    }

    private void execGarbageCollector(List<ProgState> completedProgramList)
    {
        List<Integer> symtable=completedProgramList.stream()
                .map(e->e.getSymTable().getContent().values())
                .map(e->getAddrFromSymTable(e))
                .reduce(Stream.of(0).collect(Collectors.toList()), (s1,s2)-> Stream.concat(s1.stream(),s2.stream()).collect(Collectors.toList()));
        Collection<Value> heapTableValues=completedProgramList.get(0).getHeap().getContent().values();
        List<Integer> heap=heapTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue val=(RefValue)v;return val.getAddr();})
                .collect(Collectors.toList());
        symtable.addAll(heap);
        completedProgramList.get(0).getHeap().setContent(unsafeGarbageCollector(symtable,completedProgramList.get(0).getHeap().getContent()));
    }
    List<ProgState> removeCompletedPrg(List<ProgState> inPrgList)
    {
        return inPrgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

}