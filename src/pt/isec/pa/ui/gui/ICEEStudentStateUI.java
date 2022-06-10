package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.io.File;

public class ICEEStudentStateUI extends BorderPane {
    Facade facade;
    Button btnUploadCSV, btnErase, btnConfirmInsert, btnClearInsert;
    Label title;
    Tab tabUploadCSV, tabInsert, tabConsult, tabErase, tabEdit;

    VBox insert, CSVBox, erase;
    HBox minorH, internshipH,branchH , btnInsertH;
    TextField name,number,score;
    ChoiceBox minor, intership, branch, nameNumberStudent;
    Label minorL, internshipL,branchL, eraseL;




    //minor, internship
    public ICEEStudentStateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
        CSVBox = new VBox();
        CSVBox.setAlignment(Pos.CENTER);

        btnUploadCSV = new Button("Upload CSV File");
        btnUploadCSV.setAlignment(Pos.CENTER);
        CSVBox.getChildren().addAll(btnUploadCSV);

        //Insert Student
        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        btnInsertH = new HBox( btnConfirmInsert, btnClearInsert);
        btnInsertH.setAlignment(Pos.CENTER);
        btnInsertH.setSpacing(5);

        insert = new VBox();
        insert.setAlignment(Pos.CENTER);
        insert.setSpacing(10);

        name = new TextField();
        name.setId("tfInsert");
        name.setFont(Constants.getNormalFont());
        name.setPromptText("Student Name");
        name.setMaxWidth(150);

        number = new TextField();
        number.setId("tfInsert");
        number.setFont(Constants.getNormalFont());
        number.setPromptText("Student Number");
        number.setMaxWidth(150);

        score = new TextField();
        score.setId("tfInsert");
        score.setFont(Constants.getNormalFont());
        score.setPromptText("Student Score");
        score.setMaxWidth(150);

        minorL = new Label("Student Minor ");
        minorL.setAlignment(Pos.CENTER_LEFT);
        branchL = new Label("Student Branch ");
        branchL.setAlignment(Pos.CENTER_LEFT);
        internshipL = new Label("Available for internships ? ");
        internshipL.setAlignment(Pos.CENTER_LEFT);


        minor = new ChoiceBox<>();
        minor.getItems().addAll("LEI","LEI-PL");
        branch = new ChoiceBox<>();
        branch.getItems().addAll("DA","SI","RAS");
        intership = new ChoiceBox<>();
        intership.getItems().addAll("TRUE","FALSE");
        minor.setId("ChoiceBoxL");

        minorH = new HBox(minorL,minor);
        branchH = new HBox(branchL,branch);
        internshipH = new HBox(internshipL,intership);

        minorH.setAlignment(Pos.CENTER);
        branchH.setAlignment(Pos.CENTER);
        internshipH.setAlignment(Pos.CENTER);
        minorH.setId(  "hBoxChoice");
        branchH.setId(  "hBoxChoice");
        internshipH.setId(  "hBoxChoice");

        insert.getChildren().addAll(name,number,score,minorH,branchH,internshipH,btnInsertH);

        //erase
        erase = new VBox();
        erase.setAlignment(Pos.CENTER);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        eraseL = new Label("Choose the Student that you want to erase");
        eraseL.setAlignment(Pos.CENTER_LEFT);
        nameNumberStudent = new ChoiceBox<>();
        erase.getChildren().addAll(eraseL,nameNumberStudent, btnErase);

        tabUploadCSV = new Tab("Upload CSV", CSVBox);
        tabUploadCSV.setClosable(false);
        tabInsert = new Tab("Insert Student"  , insert);
        tabInsert.setClosable(false);
        tabConsult = new Tab("Consult Student"  , new Label("Show all cars available"));
        tabConsult.setClosable(false);
        tabErase = new Tab("Erase Student" , erase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit Student" , new Label("Show all boats available"));
        tabEdit.setClosable(false);



        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(tabUploadCSV);
        tabPane.getTabs().add(tabInsert);
        tabPane.getTabs().add(tabConsult);
        tabPane.getTabs().add(tabErase);
        tabPane.getTabs().add(tabEdit);



        VBox VBox = new VBox(tabPane);
        this.setTop(VBox);
        VBox.setAlignment(Pos.CENTER);

    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });



        btnUploadCSV.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("File open...");
            fc.setInitialDirectory(new File("."));
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Drawing(*.text)","*.txt"));
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"));

            File hFile = fc.showOpenDialog(this.getScene().getWindow());
            Boolean result = facade.upload(hFile);
            ToastMessage.show(getScene().getWindow(), result);

        });

        btnErase.setOnAction(actionEvent -> {
            Label labelresponse = new Label();
            labelresponse.setText(""+nameNumberStudent.getValue());

            String s = labelresponse.toString();

        });

    }

    private void update() {
        nameNumberStudent.getItems().clear();
        for(Student s : facade.getStudents().values()) {
            nameNumberStudent.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }

        if (facade.getState() != AppState.PONE_STUDENT) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
