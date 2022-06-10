package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.io.File;

public class ICEETeacherStateUI  extends BorderPane {
    Facade facade;
    Button btnUploadCSV, btnErase, btnConfirmInsert, btnClearInsert;
    Label title,eraseL;
    Tab tabUploadCSV, tabInsert, tabConsult, tabErase, tabEdit;
    ChoiceBox nameEmailTeacher;
    VBox insert, CSVBox, erase;
    HBox minorH, internshipH,branchH , btnInsertH;
    TextField name, email;

    public ICEETeacherStateUI(Facade facade) {
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

        //Insert Teacher
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
        name.setPromptText("Teacher Name");
        name.setMaxWidth(150);

        email = new TextField();
        email.setId("tfInsert");
        email.setFont(Constants.getNormalFont());
        email.setPromptText("Teacher Number");
        email.setMaxWidth(150);

        insert.getChildren().addAll(name, email,btnInsertH);

        //erase
        erase = new VBox();
        erase.setAlignment(Pos.CENTER);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        eraseL = new Label("Choose the Student that you want to erase");
        eraseL.setAlignment(Pos.CENTER_LEFT);
        nameEmailTeacher = new ChoiceBox<>();
        erase.getChildren().addAll(eraseL, nameEmailTeacher, btnErase);

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

    }

    private void update() {
        nameEmailTeacher.getItems().clear();
        for(Teacher t : facade.getTeachers().values()) {
            nameEmailTeacher.getItems().add(t.getName()+" "+t.getEmail().toString());
        }

        if (facade.getState() != AppState.PONE_TEACHER) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
