package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Branches;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.Project;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;

import java.util.ArrayList;
import java.util.List;

public class T2P1StateUI extends BorderPane {
    Facade facade;
    Button btnErase, btnConfirmInsert, btnClearInsert, btnConfirmEdit, btnClearEdit;
    Tab tabInsert, tabConsult, tabErase, tabEdit;
    VBox vbInsert, vbErase, vbConsult, vbEdit;
    HBox hbBranch, hbBtnInsert, hbBranchEdit, hbBtnEdit;
    TextField tfId, tfTitle, tfTitleEdit;
    CheckBox checkBRas, checkBDa, checkBSi, checkBRasEdit, checkBDaEdit, checkBSiEdit;
    ChoiceBox cbBAllStudents, cbAllTeachers, cbTitleCodeProject, cbAllStudentsEdit, cbAllTeachersEdit;
    ListView lvConsult;
    Label lbErase, lbEdit,lbTitle;
    ChoiceBox cbCodeProject;
    boolean flag = false;
    String titleCodeProjectStringEdit = null, emailEdit;
    long numberEdit = -1;
    
    public T2P1StateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        lbTitle = new Label("Insert the Project Data");
        lbTitle.setId("lbTitle");
        lbTitle.setMinWidth(300);
        lbTitle.setAlignment(Pos.CENTER);

        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        hbBtnInsert = new HBox( btnConfirmInsert, btnClearInsert);
        hbBtnInsert.setAlignment(Pos.CENTER);
        hbBtnInsert.setSpacing(5);

        checkBRas = new CheckBox("RAS");
        checkBDa = new CheckBox("DA");
        checkBSi = new CheckBox("SI");
        checkBRas.setId("CheckBoxI");
        checkBDa.setId("CheckBoxI");
        checkBSi.setId("CheckBoxI");
        hbBranch = new HBox();
        hbBranch.setAlignment(Pos.CENTER);
        hbBranch.setSpacing(5);
        hbBranch.getChildren().addAll(checkBRas, checkBDa, checkBSi);

        vbInsert = new VBox();
        vbInsert.setAlignment(Pos.CENTER);
        vbInsert.setSpacing(10);

        tfId = new TextField();
        tfId.setId("tfInsert");
        tfId.setFont(Constants.getNormalFont());
        tfId.setPromptText("Id");
        tfId.setMaxWidth(150);

        tfTitle = new TextField();
        tfTitle.setId("tfInsert");
        tfTitle.setFont(Constants.getNormalFont());
        tfTitle.setPromptText("Title");
        tfTitle.setMaxWidth(150);

        btnErase = new Button("Erase");

        cbBAllStudents = new ChoiceBox<>();
        cbAllTeachers = new ChoiceBox<>();

        //consult
        vbConsult = new VBox();
        vbConsult.setAlignment(Pos.CENTER);
        lvConsult = new ListView();
        lvConsult.setEditable(false);
        vbConsult.getChildren().add(lvConsult);
        
        //erase
        vbErase = new VBox();
        vbErase.setAlignment(Pos.CENTER);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        lbErase = new Label("Choose the Project that you want to erase");
        lbErase.setId("lbTitle");
        lbErase.setMinWidth(500);
        lbErase.setAlignment(Pos.CENTER);
        cbCodeProject = new ChoiceBox<>();
        vbErase.getChildren().addAll(lbErase, cbCodeProject, btnErase);

        //Edit Project
        btnConfirmEdit = new Button("Edit");
        btnClearEdit = new Button("Clear");
        hbBtnEdit = new HBox( btnConfirmEdit, btnClearEdit);
        hbBtnEdit.setAlignment(Pos.CENTER);
        hbBtnEdit.setSpacing(5);

        vbEdit = new VBox();
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.setSpacing(10);

        lbEdit = new Label("Choose the Project that you want to edit");
        lbEdit.setId("lbTitle");
        lbEdit.setMinWidth(500);
        lbEdit.setAlignment(Pos.CENTER);
        cbTitleCodeProject = new ChoiceBox<>();

        tfTitleEdit = new TextField();
        tfTitleEdit.setId("tfInsert");
        tfTitleEdit.setFont(Constants.getNormalFont());
        tfTitleEdit.setPromptText("Title");
        tfTitleEdit.setMaxWidth(150);

        checkBRasEdit = new CheckBox("RAS");
        checkBDaEdit = new CheckBox("DA");
        checkBSiEdit = new CheckBox("SI");
        checkBRasEdit.setId("CheckBoxI");
        checkBDaEdit.setId("CheckBoxI");
        checkBSiEdit.setId("CheckBoxI");
        hbBranchEdit = new HBox();
        hbBranchEdit.setAlignment(Pos.CENTER);
        hbBranchEdit.setSpacing(5);
        hbBranchEdit.getChildren().addAll(checkBRasEdit, checkBDaEdit, checkBSiEdit);

        cbAllTeachersEdit = new ChoiceBox<>();
        cbAllStudentsEdit = new ChoiceBox<>();

        vbEdit.getChildren().addAll(lbEdit, cbTitleCodeProject, tfTitleEdit, cbAllTeachersEdit, cbAllStudentsEdit, hbBranchEdit, hbBtnEdit);


        tabInsert = new Tab("Insert Project" , vbInsert);
        tabInsert.setClosable(false);
        tabConsult = new Tab("Consult Project" , vbConsult);
        tabConsult.setClosable(false);
        tabErase = new Tab("Erase Project" , vbErase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit Project" , vbEdit);
        tabEdit.setClosable(false);

        vbInsert.getChildren().addAll(lbTitle, tfId, tfTitle, hbBranch, cbAllTeachers, cbBAllStudents, hbBtnInsert);
        TabPane tabPane = new TabPane();
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

        btnErase.setOnAction(actionEvent -> {
            if(cbCodeProject.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbCodeProject.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                facade.remove(values[1]);
            }
        });
        btnClearInsert.setOnAction(actionEvent -> {
            clearInsert();
        });
        btnClearEdit.setOnAction(actionEvent -> {
            clearEdit();
        });

        btnConfirmInsert.setOnAction(actionEvent -> {

            if(tfId.getText().isBlank() || tfTitle.getText().isBlank() || cbBAllStudents.getValue() == null || (!checkBRas.isSelected() && !checkBDa.isSelected() && !checkBSi.isSelected())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                return;
            }

            List<Branches> br = new ArrayList<>();
            if(checkBRas.isSelected()){
                br.add(Branches.RAS);
            }
            if(checkBDa.isSelected()){
                br.add(Branches.DA);
            }
            if(checkBSi.isSelected()){
                br.add(Branches.SI);
            }

            String email = null;
            if(cbAllTeachers.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbAllTeachers.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                email = values[1];
            }

            long number;
            if(cbBAllStudents.getValue() == null){
                number = Long.valueOf(-1);
            } else{
                Label labelresponse = new Label();
                labelresponse.setText("" + cbBAllStudents.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                number = Long.valueOf(values[1]);
            }

            if(!facade.addProject(tfId.getText(),number, br, tfTitle.getText(), email)){
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

            if(tfTitleEdit.getText().isBlank() && (!checkBRasEdit.isSelected() && !checkBDaEdit.isSelected()  && !checkBSiEdit.isSelected()) && cbAllTeachersEdit.getValue() == null && cbAllStudentsEdit.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no fields to edit");
                alert.showAndWait();
                return;
            }

            if(cbTitleCodeProject.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbTitleCodeProject.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                titleCodeProjectStringEdit = values[1];
            }

            if(!tfTitleEdit.getText().isBlank()) {
                if(!facade.editTitle(facade.getProjects().get(titleCodeProjectStringEdit), tfTitleEdit.getText()))
                    flag = true;
            }

            List<String> brEdit = new ArrayList<>();

            if(checkBRasEdit.isSelected()){
                brEdit.add("RAS");
            }
            if(checkBDaEdit.isSelected()){
                brEdit.add("DA");
            }
            if(checkBSiEdit.isSelected()){
                brEdit.add("SI");
            }

            if(!brEdit.isEmpty()) {
                if(!facade.editBranch(facade.getProjects().get(titleCodeProjectStringEdit), brEdit))
                    flag = true;
            }

            if(cbAllStudentsEdit.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbAllStudentsEdit.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                numberEdit = Long.valueOf(values[1]);

                if(!facade.editNumberStudentProposals(numberEdit, facade.getProjects().get(titleCodeProjectStringEdit)))
                    flag = true;
            }

            if(cbAllTeachersEdit.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbAllTeachersEdit.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                emailEdit = values[1];

                if(!facade.editTeacherProject(emailEdit, facade.getProjects().get(titleCodeProjectStringEdit)))
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
        tfId.setText("");
        tfTitle.setText("");
        cbBAllStudents.setValue(null);
        cbAllTeachers.setValue(null);
        checkBRas.setSelected(false);
        checkBDa.setSelected(false);
        checkBSi.setSelected(false);
    }

    void clearEdit(){
        tfTitleEdit.setText("");
        cbAllStudentsEdit.setValue(null);
        cbAllTeachersEdit.setValue(null);
        checkBRasEdit.setSelected(false);
        checkBDaEdit.setSelected(false);
        checkBSiEdit.setSelected(false);
    }

    private void update() {
        if(facade.getBlock(1) == StateBlock.BLOCKED){
            tabInsert.setDisable(true);
            tabErase.setDisable(true);
            tabEdit.setDisable(true);
        }

        cbCodeProject.getItems().clear();
        lvConsult.getItems().clear();
        cbTitleCodeProject.getItems().clear();
        for(Project p : facade.getProjects().values()) {
            cbCodeProject.getItems().add(p.getTitle()+","+p.getIdCode());
            lvConsult.getItems().add(p.toString());
            cbTitleCodeProject.getItems().add(p.getTitle()+","+p.getIdCode());
        }

        cbBAllStudents.getItems().clear();
        cbAllStudentsEdit.getItems().clear();
        for(Student s : facade.getStudents().values()) {
            cbBAllStudents.getItems().add(s.getName()+","+s.getStudentNumber().toString());
            cbAllStudentsEdit.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }

        cbAllTeachers.getItems().clear();
        cbAllTeachersEdit.getItems().clear();
        for(Teacher t : facade.getTeachers().values()) {
            cbAllTeachers.getItems().add(t.getName()+","+t.getEmail());
            cbAllTeachersEdit.getItems().add(t.getName()+","+t.getEmail());
        }

        if (facade.getState() != AppState.PONE_PROJECT) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
