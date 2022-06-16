package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.io.File;

public class ICEETeacherStateUI  extends BorderPane {
    Facade facade;
    Button btnUploadCSV, btnErase, btnConfirmInsert, btnClearInsert, btnConfirmEdit, btnClearEdit;
    Label lbErase, lbEdit,lbTitleI, lbTeacherEmailEdit, lbTeacherEmailErase;
    Tab tabUploadCSV, tabInsert, tabConsult, tabErase, tabEdit;
    ChoiceBox cbNameEmailTeacher, cbEmailTeacherEdit;
    VBox vbInsert, vbCSVBox, vberase, vbConsult, vbEdit;
    HBox hbBtnInsertH, hbBtnEditH, hbTeacherEmailEdit, hbTeacherEmailErase;
    TextField tfName, tfEmail, tfNameEdit, tfEmailEdit;
    ListView lvConsult;
    boolean flag = false;
    String teacherEmailStringEdit;

    public ICEETeacherStateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        vbCSVBox = new VBox();
        vbCSVBox.setAlignment(Pos.CENTER);

        btnUploadCSV = new Button("Upload CSV File");
        btnUploadCSV.setAlignment(Pos.CENTER);
        vbCSVBox.getChildren().addAll(btnUploadCSV);

        //Insert Teacher

        lbTitleI = new Label("Insert the Internship Data");
        lbTitleI.setId("lbTitle");
        lbTitleI.setMinWidth(300);
        lbTitleI.setAlignment(Pos.CENTER);
        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        hbBtnInsertH = new HBox( btnConfirmInsert, btnClearInsert);
        hbBtnInsertH.setAlignment(Pos.CENTER);
        hbBtnInsertH.setSpacing(5);

        vbInsert = new VBox();
        vbInsert.setAlignment(Pos.CENTER);
        vbInsert.setSpacing(10);

        tfName = new TextField();
        tfName.setId("tfInsert");
        tfName.setFont(Constants.getNormalFont());
        tfName.setPromptText("Teacher Name");
        tfName.setMaxWidth(150);

        tfEmail = new TextField();
        tfEmail.setId("tfInsert");
        tfEmail.setFont(Constants.getNormalFont());
        tfEmail.setPromptText("Teacher Number");
        tfEmail.setMaxWidth(150);

        vbInsert.getChildren().addAll(lbTitleI,tfName, tfEmail, hbBtnInsertH);

        //erase
        vberase = new VBox();
        vberase.setAlignment(Pos.CENTER);
        vberase.setSpacing(10);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        lbErase = new Label("Choose the Teacher that you want to erase");
        lbErase.setAlignment(Pos.CENTER_LEFT);
        lbErase.setId("lbTitle");
        lbErase.setMinWidth(500);
        lbErase.setAlignment(Pos.CENTER);
        cbNameEmailTeacher = new ChoiceBox<>();

        lbTeacherEmailErase = new Label("Teacher\t");
        lbTeacherEmailErase.setAlignment(Pos.CENTER_LEFT);

        hbTeacherEmailErase = new HBox(lbTeacherEmailErase, cbNameEmailTeacher);

        hbTeacherEmailErase.setAlignment(Pos.CENTER);
        hbTeacherEmailErase.setId(  "hBoxChoice");

        vberase.getChildren().addAll(lbErase, hbTeacherEmailErase, btnErase);

        //consult
        vbConsult = new VBox();
        vbConsult.setAlignment(Pos.CENTER);
        lvConsult = new ListView();
        vbConsult.getChildren().add(lvConsult);
        lvConsult.setEditable(false);
        lvConsult.setMinHeight(600);

        //edit Teacher
        btnConfirmEdit = new Button("Edit");
        btnClearEdit = new Button("Clear");
        hbBtnEditH = new HBox( btnConfirmEdit, btnClearEdit);
        hbBtnEditH.setAlignment(Pos.CENTER);
        hbBtnEditH.setSpacing(5);

        vbEdit = new VBox();
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.setSpacing(10);

        lbEdit = new Label("Choose the Teacher that you want to edit");
        lbEdit.setAlignment(Pos.CENTER_LEFT);
        cbEmailTeacherEdit = new ChoiceBox<>();
        lbEdit.setId("lbTitle");
        lbEdit.setMinWidth(500);
        lbEdit.setAlignment(Pos.CENTER);
        tfNameEdit = new TextField();
        tfNameEdit.setId("tfInsert");
        tfNameEdit.setFont(Constants.getNormalFont());
        tfNameEdit.setPromptText("Teacher Name");
        tfNameEdit.setMaxWidth(150);

        lbTeacherEmailEdit = new Label("Teacher\t");
        lbTeacherEmailEdit.setAlignment(Pos.CENTER_LEFT);

        hbTeacherEmailEdit = new HBox(lbTeacherEmailEdit, cbEmailTeacherEdit);

        hbTeacherEmailEdit.setAlignment(Pos.CENTER);
        hbTeacherEmailEdit.setId(  "hBoxChoice");

        vbEdit.getChildren().addAll(lbEdit, hbTeacherEmailEdit, tfNameEdit, hbBtnEditH);

        tabUploadCSV = new Tab("Upload CSV", vbCSVBox);
        tabUploadCSV.setClosable(false);
        tabInsert = new Tab("Insert Teacher"  , vbInsert);
        tabInsert.setClosable(false);
        tabConsult = new Tab("Consult Teachers"  , vbConsult);
        tabConsult.setClosable(false);
        tabErase = new Tab("Erase Teacher" , vberase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit Teacher" , vbEdit);
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
            if(cbNameEmailTeacher.getValue() != null){
                Label labelresponse = new Label();

                labelresponse.setText(""+ cbNameEmailTeacher.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0,  values[1].length() - 1);
                facade.remove(values[1]);
            }
        });

        btnConfirmInsert.setOnAction(actionEvent -> {
            if(tfName.getText().isBlank()|| tfEmail.getText().isBlank() ){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                return;
            }
            if(!facade.insertTeacher((String) tfName.getText(),(String) tfEmail.getText())){
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

            if(tfNameEdit.getText().isBlank() && tfEmailEdit.getText().isBlank()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no fields to edit");
                alert.showAndWait();
                return;
            }

            if(cbEmailTeacherEdit.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbEmailTeacherEdit.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                teacherEmailStringEdit = values[1];
            }

            if(!tfNameEdit.getText().isBlank()) {
                if(!facade.editNameTeacher(tfNameEdit.getText(), teacherEmailStringEdit))
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
    }

    void clearEdit(){
        tfNameEdit.setText("");
        cbEmailTeacherEdit.setValue(null);
    }

    private void update() {
        cbNameEmailTeacher.getItems().clear();
        lvConsult.getItems().clear();
        cbEmailTeacherEdit.getItems().clear();
        for(Teacher t : facade.getTeachers().values()) {
            cbNameEmailTeacher.getItems().add(t.getName()+","+t.getEmail());
            lvConsult.getItems().add(t.toString());
            cbEmailTeacherEdit.getItems().add(t.getName()+","+t.getEmail());
        }

        if(facade.getBlock(1) == StateBlock.BLOCKED){
            tabUploadCSV.setDisable(true);
            tabInsert.setDisable(true);
            tabErase.setDisable(true);
            tabEdit.setDisable(true);
        }

        if (facade.getState() != AppState.PONE_TEACHER) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
