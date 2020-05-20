package Repo;

import Exception.*;
import Model.ProgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Repo implements RepoInter {
    List<ProgState> myList;
    String logFilePath;

    public Repo(String pth)
    {
        myList = new LinkedList<ProgState>();
        logFilePath = pth;
    }

    public void add(ProgState p)
    {
        myList.add(p);
    }

    @Override
    public void logPrgStateExec(ProgState prg) throws PancakeException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.print(prg.toString());
            logFile.print("============================================================================================================\n============================================================================================================");
            logFile.close();
            //System.out.println(prg.toString());
        }
        catch (IOException e)
        {
            throw new PancakeException(e.getMessage());
        }
    }

    public List<ProgState> getPrgList() {
        return myList;
    }

    public void setPrgList(List<ProgState> prgList) {
        myList = prgList;
    }
}