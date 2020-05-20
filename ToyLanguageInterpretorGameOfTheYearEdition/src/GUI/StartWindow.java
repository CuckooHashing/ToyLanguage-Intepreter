package GUI;
import Controller.*;
import Model.*;
import Model.Containers.MyList;
import Model.Containers.MyMap;
import Model.Containers.MyStack;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repo.Repo;
import Repo.RepoInter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import Exception.PancakeException;

public class StartWindow {
    private ArrayList<IStmt> options = null;
    private Integer index = null;
    private Stage startWin = null;

    @FXML
    private ListView<String> programList;

    @FXML
    private Label textLabel;

    @FXML
    public void initialize()
    {
        textLabel.setFont(new Font(18));
        textLabel.setText("Please select a program to run\nand then press the button");
        textLabel.setTextAlignment(TextAlignment.CENTER);
        generatePrograms();
        populate();
    }

    public void itemClicked(MouseEvent mouseEvent)
    {
        index = programList.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void executeClicked(MouseEvent mouseEvent) throws IOException {
        if(index == null)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("No program was selected!");
            error.setContentText("A program must be selected from the list before hitting the button!");
            error.showAndWait();
            return;
        }
        createOneTimeService(options.get(index), index);
        index = null;
    }

    public void setStartWindow(Stage startPage){
        this.startWin = startPage;
    }

    @FXML
    private void populate()
    {
        ObservableList<String> progs = FXCollections.observableArrayList();
        progs.addAll(options.stream().map(Object::toString).collect(Collectors.toList()));
        programList.setItems(progs);
    }

    private void createOneTimeService(IStmt program, Integer ind) throws IOException {
        try{
            program.typecheck(new MyMap<String, Type>());
        }
        catch (PancakeException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Type Check");
            error.setContentText(e.getMessage());
            error.showAndWait();
            return;
        }
        ProgState prg = new ProgState(new MyStack<IStmt>(),  new MyMap<String, Value>(), new MyList<Value>(), program);
        RepoInter repo = new Repo("log"+ind+".in");
        repo.add(prg);
        Service ctrl = new Service(repo);
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("ex.fxml"));
        Parent root = loader.load();
        InterpreterWindow ctrl2 = loader.getController();
        ctrl2.setService(ctrl);
        Stage stage = new Stage();
        stage.setTitle("Interpreter");
        stage.setScene(new Scene(root, 1000, 750));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void generatePrograms()
    {
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("Test.txt"))),
                        new CompStmt(new OpenStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseStmt(new VarExp("varf"))))
                                                )
                                        )))));
        IStmt ex5= new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("v", new ValueExp(new IntValue(80))),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))
                                )
                        )
                )
        );
        IStmt ex6 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new HeapReadingExp(new HeapReadingExp(new VarExp("a"))), new ValueExp(new IntValue(5)))
                                                )
                                        )
                                )
                        )
                ));
        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                new CompStmt(
                                        new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+',new HeapReadingExp(new VarExp("v")), new ValueExp(new IntValue(5))))
                                )
                        )
                )
        );
        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v",  new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v"))
                        )
                )

        );
        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))))
                                        )
                                )
                        )
                )

        );
        IStmt ex10 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(new CompStmt(
                                                        new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(
                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                                )
                                                        )
                                                )),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                )
                                        )

                                )
                        )
                )
        );
        IStmt ex11 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(new CompStmt(
                                                        new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(
                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                                )
                                                        )
                                                )),
                                                new ForkStmt(new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                ))
                                        )

                                )
                        )
                )
        );
        options = new ArrayList<IStmt>();
        options.add(ex1);
        options.add(ex2);
        options.add(ex3);
        options.add(ex4);
        options.add(ex5);
        options.add(ex6);
        options.add(ex7);
        options.add(ex8);
        options.add(ex9);
        options.add(ex10);
        options.add(ex11);
    }
}
