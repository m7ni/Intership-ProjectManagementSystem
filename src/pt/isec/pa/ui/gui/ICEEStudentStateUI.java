package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.io.File;

public class ICEEStudentStateUI extends BorderPane {
    Facade facade;
    Button btnUploadCSV, btnErase, btnConfirmInsert, btnClearInsert, btnConfirmEdit, btnClearEdit;
    Label title;
    Tab tabUploadCSV, tabInsert, tabConsult, tabErase, tabEdit;

    VBox vbInsert, vbCSV, vbErase, vbConsult, vbEdit;
    HBox hbMinor, hbInternship, hbBranch, hbBtnInsert, hbBtnEdit, hbMinorEdit, hbInternshipEdit, hbBranchEdit, hbEditStudentNumber, hbStudentNumberErase;

    TextField tfName, tfNumber, tfScore, tfEmail, tfNameEdit, tfScoreEdit, tfEmailEdit;
    ChoiceBox cbMinor, cbInternship, cbBranch, cbNameNumberStudent, cbStudentNumberEdit, cbMinorEdit, cbInternshipEdit, cbBranchEdit;
    Label lbMinorL, lbInternshipL, lbBranchL, lbEraseL, lbMinorEditL, lbInternshipEditL, lbBranchEditL, lbEditL,lbTitle, lbEditStudentNumber, lbStudentNumberErase;
    ListView lvConsult;

    long studentNEdit;
    boolean flag = false;

    //minor, internship
    public ICEEStudentStateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
        vbCSV = new VBox();
        vbCSV.setAlignment(Pos.CENTER);

        btnUploadCSV = new Button("Upload CSV File");
        btnUploadCSV.setAlignment(Pos.CENTER);
        vbCSV.getChildren().addAll(btnUploadCSV);

        //Insert Student
        lbTitle = new Label("Insert the Student Data");
        lbTitle.setId("lbTitle");
        lbTitle.setMinWidth(300);
        lbTitle.setAlignment(Pos.CENTER);
        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        hbBtnInsert = new HBox( btnConfirmInsert, btnClearInsert);
        hbBtnInsert.setAlignment(Pos.CENTER);
        hbBtnInsert.setSpacing(5);

        vbInsert = new VBox();
        vbInsert.setAlignment(Pos.CENTER);
        vbInsert.setSpacing(10);

        tfName = new TextField();
        tfName.setId("tfInsert");
        tfName.setFont(Constants.getNormalFont());
        tfName.setPromptText("Student Name");
        tfName.setMaxWidth(150);

        tfEmail = new TextField();
        tfEmail.setId("tfInsert");
        tfEmail.setFont(Constants.getNormalFont());
        tfEmail.setPromptText("Student Email");
        tfEmail.setMaxWidth(150);

        tfNumber = new TextField();
        tfNumber.setId("tfInsert");
        tfNumber.setFont(Constants.getNormalFont());
        tfNumber.setPromptText("Student Number");
        tfNumber.setMaxWidth(150);

        tfScore = new TextField();
        tfScore.setId("tfInsert");
        tfScore.setFont(Constants.getNormalFont());
        tfScore.setPromptText("Student Score");
        tfScore.setMaxWidth(150);

        lbMinorL = new Label("Student Minor ");
        lbMinorL.setAlignment(Pos.CENTER_LEFT);
        lbBranchL = new Label("Student Branch ");
        lbBranchL.setAlignment(Pos.CENTER_LEFT);
        lbInternshipL = new Label("Available for internships ? ");
        lbInternshipL.setAlignment(Pos.CENTER_LEFT);

        cbMinor = new ChoiceBox<>();
        cbMinor.getItems().addAll("LEI","LEI-PL");
        cbBranch = new ChoiceBox<>();
        cbBranch.getItems().addAll("DA","SI","RAS");
        cbInternship = new ChoiceBox<>();
        cbInternship.getItems().addAll("TRUE","FALSE");
        cbMinor.setId("ChoiceBoxL");

        hbMinor = new HBox(lbMinorL, cbMinor);
        hbBranch = new HBox(lbBranchL, cbBranch);
        hbInternship = new HBox(lbInternshipL, cbInternship);

        hbMinor.setAlignment(Pos.CENTER);
        hbBranch.setAlignment(Pos.CENTER);
        hbInternship.setAlignment(Pos.CENTER);
        hbMinor.setId(  "hBoxChoice");
        hbBranch.setId(  "hBoxChoice");
        hbInternship.setId(  "hBoxChoice");

        vbInsert.getChildren().addAll( lbTitle, tfName, tfNumber, tfEmail, tfScore, hbMinor, hbBranch, hbInternship, hbBtnInsert);

        //erase
        vbErase = new VBox();
        vbErase.setAlignment(Pos.CENTER);
        vbErase.setSpacing(10);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        lbEraseL = new Label("Choose the Student that you want to erase");
        lbEraseL.setId("lbTitle");
        lbEraseL.setMinWidth(500);
        lbEraseL.setAlignment(Pos.CENTER);

        cbNameNumberStudent = new ChoiceBox<>();

        lbStudentNumberErase = new Label("Student\t\t");
        lbStudentNumberErase.setAlignment(Pos.CENTER_LEFT);

        hbStudentNumberErase = new HBox(lbStudentNumberErase, cbNameNumberStudent);

        hbStudentNumberErase.setAlignment(Pos.CENTER);
        hbStudentNumberErase.setId(  "hBoxChoice");

        vbErase.getChildren().addAll(lbEraseL, hbStudentNumberErase, btnErase);

        //consult
        vbConsult = new VBox();
        vbConsult.setAlignment(Pos.CENTER);
        lvConsult = new ListView();
        vbConsult.getChildren().add(lvConsult);
        lvConsult.setEditable(false);
        lvConsult.setMinHeight(600);

        //Edit Student
        btnConfirmEdit = new Button("Edit");
        btnClearEdit = new Button("Clear");
        hbBtnEdit = new HBox( btnConfirmEdit, btnClearEdit);
        hbBtnEdit.setAlignment(Pos.CENTER);
        hbBtnEdit.setSpacing(5);

        vbEdit = new VBox();
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.setSpacing(10);

        lbEditL = new Label("Choose the Student that you want to edit");
        lbEditL.setAlignment(Pos.CENTER);
        lbEditL.setId("lbTitle");
        lbEditL.setMinWidth(500);
        cbStudentNumberEdit = new ChoiceBox<>();

        lbEditStudentNumber = new Label("Student Number\t");
        lbEditStudentNumber.setAlignment(Pos.CENTER_LEFT);

        hbEditStudentNumber = new HBox(lbEditStudentNumber, cbStudentNumberEdit);

        hbEditStudentNumber.setAlignment(Pos.CENTER);
        hbEditStudentNumber.setId(  "hBoxChoice");

        tfNameEdit = new TextField();
        tfNameEdit.setId("tfInsert");
        tfNameEdit.setFont(Constants.getNormalFont());
        tfNameEdit.setPromptText("Student Name");
        tfNameEdit.setMaxWidth(150);

        tfEmailEdit = new TextField();
        tfEmailEdit.setId("tfInsert");
        tfEmailEdit.setFont(Constants.getNormalFont());
        tfEmailEdit.setPromptText("Student Email");
        tfEmailEdit.setMaxWidth(150);

        tfScoreEdit = new TextField();
        tfScoreEdit.setId("tfInsert");
        tfScoreEdit.setFont(Constants.getNormalFont());
        tfScoreEdit.setPromptText("Student Score");
        tfScoreEdit.setMaxWidth(150);

        lbMinorEditL = new Label("Student Minor ");
        lbMinorEditL.setAlignment(Pos.CENTER_LEFT);
        lbBranchEditL = new Label("Student Branch ");
        lbBranchEditL.setAlignment(Pos.CENTER_LEFT);
        lbInternshipEditL = new Label("Available for internships ? ");
        lbInternshipEditL.setAlignment(Pos.CENTER_LEFT);

        cbMinorEdit = new ChoiceBox<>();
        cbMinorEdit.getItems().addAll("LEI","LEI-PL");
        cbBranchEdit = new ChoiceBox<>();
        cbBranchEdit.getItems().addAll("DA","SI","RAS");
        cbInternshipEdit = new ChoiceBox<>();
        cbInternshipEdit.getItems().addAll("TRUE","FALSE");
        cbMinorEdit.setId("ChoiceBoxL");

        hbMinorEdit = new HBox(lbMinorEditL, cbMinorEdit);
        hbBranchEdit = new HBox(lbBranchEditL, cbBranchEdit);
        hbInternshipEdit = new HBox(lbInternshipEditL, cbInternshipEdit);

        hbMinorEdit.setAlignment(Pos.CENTER);
        hbBranchEdit.setAlignment(Pos.CENTER);
        hbInternshipEdit.setAlignment(Pos.CENTER);
        hbMinorEdit.setId(  "hBoxChoice");
        hbBranchEdit.setId(  "hBoxChoice");
        hbInternshipEdit.setId(  "hBoxChoice");

        vbEdit.getChildren().addAll(lbEditL, hbEditStudentNumber, tfNameEdit, tfEmailEdit, tfScoreEdit, hbMinorEdit, hbBranchEdit, hbInternshipEdit, hbBtnEdit);

        tabUploadCSV = new Tab("Upload CSV", vbCSV);
        tabUploadCSV.setClosable(false);
        tabInsert = new Tab("Insert Student"  , vbInsert);
        tabInsert.setClosable(false);
        tabConsult = new Tab("Consult Student"  , vbConsult);
        tabConsult.setClosable(false);
        tabErase = new Tab("Erase Student" , vbErase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit Student" , vbEdit);
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
        btnClearInsert.setOnAction(actionEvent -> {
           clearInsert();
        });
        btnClearEdit.setOnAction(actionEvent -> {
            clearEdit();
        });
        btnErase.setOnAction(actionEvent -> {
            if(cbNameNumberStudent.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbNameNumberStudent.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                facade.remove(Long.parseLong(values[1]));
            }
        });

        btnConfirmInsert.setOnAction(actionEvent -> {
            if(tfName.getText().isBlank()|| tfEmail.getText().isBlank() || tfNumber.getText().isBlank() || tfScore.getText().isBlank() || cbMinor.getValue() == null || cbBranch.getValue() == null || cbInternship.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                return;
            }
            if(!facade.insertStudent((String) tfName.getText(),(String) tfEmail.getText() ,Long.parseLong(tfNumber.getText()),(String) cbMinor.getValue(),(String) cbBranch.getValue(),Double.parseDouble(tfScore.getText()),Boolean.parseBoolean( (String) cbInternship.getValue()))){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are fields that do not match the requirements");
                alert.showAndWait();
                clearInsert();
                return;
            }
            clearInsert();
        });

        btnConfirmEdit.setOnAction(actionEvent -> {

            if(tfNameEdit.getText().isBlank() && tfEmailEdit.getText().isBlank() && tfScoreEdit.getText().isBlank() && cbMinorEdit.getValue() == null && cbBranchEdit.getValue() == null && cbInternshipEdit.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no fields to edit");
                alert.showAndWait();
                return;
            }

            if(cbStudentNumberEdit.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbStudentNumberEdit.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                studentNEdit = Long.parseLong(values[1]);
            }

            if(!tfNameEdit.getText().isBlank()) {
                if(!facade.editNameStudent(tfNameEdit.getText(), studentNEdit))
                    flag = true;
            }
            if(!tfEmailEdit.getText().isBlank()) {
                if(!facade.editEmailStudent(tfEmailEdit.getText(), studentNEdit))
                    flag = true;
            }
            if(!tfScoreEdit.getText().isBlank()) {
                if(!facade.editScoreStudent(tfScoreEdit.getText(), studentNEdit))
                    flag = true;
            }
            if(cbMinorEdit.getValue() != null) {
                if(!facade.editCourseStudent((String) cbMinorEdit.getValue(), studentNEdit))
                    flag = true;
            }
            if(cbBranchEdit.getValue() != null) {
                if(!facade.editBranchStudent((String) cbBranchEdit.getValue(), studentNEdit))
                    flag = true;
            }
            if(cbInternshipEdit.getValue() != null) {
                if(!facade.editApplicableInternshipStudent(studentNEdit))
                    flag = true;
            }

            if(flag) {
                flag = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("Some data was incorrect to be able to edit");
                alert.showAndWait();
                clearEdit();
                return;
            }

            clearEdit();
        });

    }

    void clearInsert(){
        tfName.setText("");
        tfEmail.setText("");
        tfNumber.setText("");
        tfScore.setText("");
        cbMinor.setValue(null);
        cbBranch.setValue(null);
        cbInternship.setValue(null);
    }

    void clearEdit(){
        tfNameEdit.setText("");
        tfEmailEdit.setText("");
        tfScoreEdit.setText("");
        cbMinorEdit.setValue(null);
        cbBranchEdit.setValue(null);
        cbInternshipEdit.setValue(null);
        cbStudentNumberEdit.setValue(null);
    }

    private void update() {
        cbNameNumberStudent.getItems().clear();
        lvConsult.getItems().clear();
        cbStudentNumberEdit.getItems().clear();
        for(Student s : facade.getStudents().values()) {
            cbNameNumberStudent.getItems().add(s.getName()+","+s.getStudentNumber().toString());
            lvConsult.getItems().add(s.toString());
            cbStudentNumberEdit.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }

        if(facade.getBlock(1) ==StateBlock.BLOCKED){
            tabUploadCSV.setDisable(true);
            tabInsert.setDisable(true);
            tabErase.setDisable(true);
            tabEdit.setDisable(true);

        }

        if (facade.getState() != AppState.PONE_STUDENT) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
