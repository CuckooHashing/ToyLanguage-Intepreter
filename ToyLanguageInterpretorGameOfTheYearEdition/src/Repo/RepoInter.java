package Repo;

import Exception.*;
import Model.ProgState;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface RepoInter {
    public void add(ProgState p);
    public void logPrgStateExec(ProgState prgs) throws PancakeException;

    public List<ProgState> getPrgList();
    public void setPrgList(List<ProgState> prgList);
}