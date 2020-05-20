package GUI;

import Controller.Service;
import Exception.PancakeException;
import Model.Containers.Heap;
import Model.ProgState;
import Model.Statement.IStmt;
import Model.Value.Value;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterpreterWindow {
    private Service serv;
    @FXML
    private ListView<String> outView;
    @FXML
    private TableView<String> symTable;
    @FXML
    private TableView<String> heapTable;
    @FXML
    private TextField numberProgStates;
    @FXML
    private ListView<String> stackView;
    @FXML
    private ListView<String> threadList;
    @FXML
    private ListView<String> fileTable;
    @FXML
    private Button runOneStep;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapAddress;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapValue;
    private Integer index;
    @FXML
    private TableView<Map.Entry<String, Value>> symTableView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symName;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symValue;


    @FXML
    public void initialize()
    {
        numberProgStates.setEditable(false);
        heapAddress.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()+ ""));
        heapValue.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().toString()));

        symName.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()+ ""));
        symValue.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().toString()));
        index = 0;
    }

    @FXML
    private void populateHeapTable()
    {
        ProgState prg = serv.getProgramList().get(index);
        List<Map.Entry<Integer, Value>> stuff = new ArrayList<>();
        for(Map.Entry<Integer, Value> entry: prg.getHeap().getContent().entrySet())
            stuff.add(entry);
        heapTableView.setItems(FXCollections.observableArrayList(stuff));
        heapTableView.refresh();
    }

    @FXML
    private void populateSymTable()
    {
        ProgState prg = serv.getProgramList().get(index);
        List<Map.Entry<String, Value>> stuff = new ArrayList<>();
        for(Map.Entry<String, Value> entry: prg.getSymTable().getContent().entrySet())
            stuff.add(entry);
        symTableView.setItems(FXCollections.observableArrayList(stuff));
        symTableView.refresh();
    }

    @FXML
    private void populateThreads()
    {
        List<ProgState> list = serv.getProgramList();
        ObservableList<String> progs = FXCollections.observableArrayList();
        for(int i = 0; i<list.size(); i++) {
            if(list.get(i).getExeStack().isEmpty() == false)
                progs.add(list.get(i).getOriginalProgram().toString());
        }
        threadList.setItems(progs);
        threadList.refresh();
    }
    @FXML
    private void populateOut()
    {
        ProgState prg = serv.getProgramList().get(index);
        ObservableList<String> progs = FXCollections.observableArrayList();
        progs.add(prg.getOut().toString());
        outView.setItems(progs);
        outView.refresh();
    }

    @FXML
    private void populateStack()
    {
        ProgState prg = serv.getProgramList().get(index);
        ObservableList<String> progs = FXCollections.observableArrayList();
        progs.add(prg.getExeStack().toString());
        stackView.setItems(progs);
        stackView.refresh();
    }

    @FXML
    private void populateFileTable()
    {
        ProgState prg = serv.getProgramList().get(index);
        ObservableList<String> progs = FXCollections.observableArrayList();
        progs.add(prg.getFileTable().toString());
        fileTable.setItems(progs);
        fileTable.refresh();
    }

    private void populateAll()
    {
        populateThreads();
        ProgState prg = serv.getProgramList().get(index);
        if(prg.getExeStack().isEmpty())
        {
            stackView.setItems(FXCollections.observableArrayList());
            stackView.refresh();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Empty Stack");
            error.setContentText("No more instructions to execute!");
            error.showAndWait();
            return;
        }
        numberProgStates.setText(String.valueOf(serv.getProgramList().size()));
        populateOut();
        populateStack();
        populateFileTable();
        populateHeapTable();
        populateSymTable();
    }
    public void setService(Service service)
    {
        serv = service;
        populateThreads();
        numberProgStates.setText(String.valueOf(serv.getProgramList().size()));
    }

    public void selectThread(MouseEvent mouseEvent) {
        index = threadList.getSelectionModel().getSelectedIndex();
        numberProgStates.setText(String.valueOf(serv.getProgramList().size()));
        populateOut();
        populateStack();
        populateFileTable();
        populateHeapTable();
        populateSymTable();
    }

    public void oneStep(MouseEvent mouseEvent) throws IOException, PancakeException {
        serv.allSteps();
        populateAll();
    }
}
