package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Branches;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.proposals.Internship;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;

import java.util.ArrayList;
import java.util.List;


public class T1P1StateUI  extends BorderPane {
    Facade facade;
    Button btnErase, btnConfirmInsert, btnClearInsert, btnConfirmEdit, btnClearEdit;
    Tab tabInsert, tabConsult, tabErase, tabEdit;
    VBox vbInsert, vbErase, vbConsult, vbEdit;
    HBox hbBranchH, hbBtnInsertH, hbBranchEditH, hbBtnEditH, hbStudentNumberInsert, hbStudentNumberEdit, hbInternshipCodeErase, hbInternshipCodeEdit;
    TextField tfId, tfTitle, tfPlace, tfTitleEdit, tfPlaceEdit;
    CheckBox cbRas, cbDa, cbSi, cbRasEdit, cbDaEdit, cbSiEdit;
    ChoiceBox cbAllStudents, cbAllStudentsEdit, cbTitleCodeInternship;
    ListView lvConsult;
    Label lbEraseL, lbEditL,lbTitle, lbStudentNumberInsert, lbStudentNumberEdit, lbInternshipCodeErase, lbInternshipCodeEdit;
    ChoiceBox cbCodeInternship;
    boolean flag = false;
    String titleCodeInternshipStringEdit = null;
    long numberEdit = -1;

    public T1P1StateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {

        lbTitle = new Label("Insert the Internship Data");
        lbTitle.setId("lbTitle");
        lbTitle.setMinWidth(300);
        lbTitle.setAlignment(Pos.CENTER);
        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        hbBtnInsertH = new HBox( btnConfirmInsert, btnClearInsert);
        hbBtnInsertH.setAlignment(Pos.CENTER);
        hbBtnInsertH.setSpacing(5);

        cbRas = new CheckBox("RAS");
        cbDa = new CheckBox("DA");
        cbSi = new CheckBox("SI");
        cbRas.setId("CheckBoxI");
        cbDa.setId("CheckBoxI");
        cbSi.setId("CheckBoxI");
        hbBranchH = new HBox();
        hbBranchH.setAlignment(Pos.CENTER);
        hbBranchH.setSpacing(5);
        hbBranchH.getChildren().addAll(cbRas, cbDa, cbSi);

        vbInsert = new VBox();
        vbInsert.setAlignment(Pos.CENTER);
        vbInsert.setSpacing(10);

        //erase
        vbErase = new VBox();
        vbErase.setAlignment(Pos.CENTER);
        vbErase.setSpacing(10);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        lbEraseL = new Label("Choose the Internship that you want to erase");
        lbEraseL.setId("lbTitle");
        lbEraseL.setMinWidth(500);
        lbEraseL.setAlignment(Pos.CENTER);
        cbCodeInternship = new ChoiceBox<>();

        lbInternshipCodeErase = new Label("Internship Code");
        lbInternshipCodeErase.setAlignment(Pos.CENTER_LEFT);

        hbInternshipCodeErase = new HBox(lbInternshipCodeErase, cbCodeInternship);

        hbInternshipCodeErase.setAlignment(Pos.CENTER);
        hbInternshipCodeErase.setId(  "hBoxChoice");

        vbErase.getChildren().addAll(lbEraseL, hbInternshipCodeErase, btnErase);

        //consult
        vbConsult = new VBox();
        vbConsult.setAlignment(Pos.CENTER);
        lvConsult = new ListView();
        vbConsult.getChildren().add(lvConsult);
        lvConsult.setEditable(false);

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

        tfPlace = new TextField();
        tfPlace.setId("tfInsert");
        tfPlace.setFont(Constants.getNormalFont());
        tfPlace.setPromptText("Place of the Internship");
        tfPlace.setMaxWidth(150);

        cbAllStudents = new ChoiceBox<>();

        //Edit Internship
        btnConfirmEdit = new Button("Edit");
        btnClearEdit = new Button("Clear");
        hbBtnEditH = new HBox( btnConfirmEdit, btnClearEdit);
        hbBtnEditH.setAlignment(Pos.CENTER);
        hbBtnEditH.setSpacing(5);

        vbEdit = new VBox();
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.setSpacing(10);

        lbEditL = new Label("Choose the Internship that you want to edit");
        lbEditL.setId("lbTitle");
        lbEditL.setMinWidth(500);
        lbEditL.setAlignment(Pos.CENTER);
        cbTitleCodeInternship = new ChoiceBox<>();

        lbInternshipCodeEdit = new Label("Internship Code");
        lbInternshipCodeEdit.setAlignment(Pos.CENTER_LEFT);

        hbInternshipCodeEdit = new HBox(lbInternshipCodeEdit, cbTitleCodeInternship);

        hbInternshipCodeEdit.setAlignment(Pos.CENTER);
        hbInternshipCodeEdit.setId(  "hBoxChoice");

        tfTitleEdit = new TextField();
        tfTitleEdit.setId("tfInsert");
        tfTitleEdit.setFont(Constants.getNormalFont());
        tfTitleEdit.setPromptText("Title");
        tfTitleEdit.setMaxWidth(150);

        tfPlaceEdit = new TextField();
        tfPlaceEdit.setId("tfInsert");
        tfPlaceEdit.setFont(Constants.getNormalFont());
        tfPlaceEdit.setPromptText("Place of the Internship");
        tfPlaceEdit.setMaxWidth(150);

        cbRasEdit = new CheckBox("RAS");
        cbDaEdit = new CheckBox("DA");
        cbSiEdit = new CheckBox("SI");
        cbRasEdit.setId("CheckBoxI");
        cbDaEdit.setId("CheckBoxI");
        cbSiEdit.setId("CheckBoxI");
        hbBranchEditH = new HBox();
        hbBranchEditH.setAlignment(Pos.CENTER);
        hbBranchEditH.setSpacing(5);
        hbBranchEditH.getChildren().addAll(cbRasEdit, cbDaEdit, cbSiEdit);

        cbAllStudentsEdit = new ChoiceBox<>();
        lbStudentNumberEdit = new Label("Student\t");
        lbStudentNumberEdit.setAlignment(Pos.CENTER_LEFT);

        hbStudentNumberEdit = new HBox(lbStudentNumberEdit, cbAllStudentsEdit);

        hbStudentNumberEdit.setAlignment(Pos.CENTER);
        hbStudentNumberEdit.setId(  "hBoxChoice");

        vbEdit.getChildren().addAll(lbEditL, hbInternshipCodeEdit, tfTitleEdit, tfPlaceEdit, hbBranchEditH,hbStudentNumberEdit, hbBtnEditH);


        tabInsert = new Tab("Insert Internship" , vbInsert);
        tabInsert.setClosable(false);
        tabConsult = new Tab("Consult Internships"  , vbConsult);
        tabConsult.setClosable(false);
        tabErase = new Tab("Erase Internship" , vbErase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit Internship" , vbEdit);
        tabEdit.setClosable(false);

        lbStudentNumberInsert = new Label("Student\t");
        lbStudentNumberInsert.setAlignment(Pos.CENTER_LEFT);

        hbStudentNumberInsert = new HBox(lbStudentNumberInsert, cbAllStudents);

        hbStudentNumberInsert.setAlignment(Pos.CENTER);
        hbStudentNumberInsert.setId(  "hBoxChoice");

        vbInsert.getChildren().addAll(lbTitle,tfId, tfTitle, tfPlace, hbBranchH, hbStudentNumberInsert, hbBtnInsertH);
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
            if(cbCodeInternship.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbCodeInternship.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                facade.remove(values[1]);
            }
        });

        btnConfirmInsert.setOnAction(actionEvent -> {
            if(tfId.getText().isBlank()|| tfTitle.getText().isBlank() || tfPlace.getText().isBlank() || (!cbRas.isSelected() && !cbDa.isSelected() && !cbSi.isSelected()) ){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                return;
            }
            long number;
            List<Branches> br = new ArrayList<>();

            if(cbAllStudents.getValue() == null){
               number = Long.valueOf(-1);
            } else{
                Label labelresponse = new Label();
                labelresponse.setText("" + cbAllStudents.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                number = Long.valueOf(values[1]);
            }

            if(cbRas.isSelected()){
                br.add(Branches.RAS);
            }
            if(cbDa.isSelected()){
                br.add(Branches.DA);
            }
            if(cbSi.isSelected()){
                br.add(Branches.SI);
            }

            if(!facade.addInternship(tfId.getText(),number,br, tfTitle.getText(), tfPlace.getText())){
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
        btnClearInsert.setOnAction(actionEvent -> {
            clearInsert();
        });
        btnClearEdit.setOnAction(actionEvent -> {
            clearEdit();
        });
        btnConfirmEdit.setOnAction(actionEvent -> {

            if(tfTitleEdit.getText().isBlank() && tfPlaceEdit.getText().isBlank() && (!cbRasEdit.isSelected() && !cbDaEdit.isSelected()  && !cbSiEdit.isSelected()) && cbAllStudentsEdit.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no fields to edit");
                alert.showAndWait();
                return;
            }

            if(cbTitleCodeInternship.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbTitleCodeInternship.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                titleCodeInternshipStringEdit = values[1];
            }

            if(!tfTitleEdit.getText().isBlank()) {
                if(!facade.editTitle(facade.getInternships().get(titleCodeInternshipStringEdit), tfTitleEdit.getText()))
                    flag = true;
            }
            if(!tfPlaceEdit.getText().isBlank()) {
                if(!facade.editLocal(facade.getInternships().get(titleCodeInternshipStringEdit), tfPlaceEdit.getText()))
                    flag = true;
            }

            List<String> brEdit = new ArrayList<>();

            if(cbRasEdit.isSelected()){
                brEdit.add("RAS");
            }
            if(cbDaEdit.isSelected()){
                brEdit.add("DA");
            }
            if(cbSiEdit.isSelected()){
                brEdit.add("SI");
            }

            if(!brEdit.isEmpty()) {
                if(!facade.editBranch(facade.getInternships().get(titleCodeInternshipStringEdit), brEdit))
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

                if(!facade.editNumberStudentProposals(numberEdit, facade.getInternships().get(titleCodeInternshipStringEdit)))
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
        tfPlace.setText("");
        cbAllStudents.setValue(null);
        cbRas.setSelected(false);
        cbDa.setSelected(false);
        cbSi.setSelected(false);
    }

    void clearEdit(){
        cbTitleCodeInternship.setValue(null);
        tfTitleEdit.setText("");
        tfPlaceEdit.setText("");
        cbAllStudentsEdit.setValue(null);
        cbRasEdit.setSelected(false);
        cbDaEdit.setSelected(false);
        cbSiEdit.setSelected(false);
    }

    private void update() {
        if(facade.getBlock(1) == StateBlock.BLOCKED){
            tabInsert.setDisable(true);
            tabErase.setDisable(true);
            tabEdit.setDisable(true);
        }

        cbCodeInternship.getItems().clear();
        lvConsult.getItems().clear();
        cbTitleCodeInternship.getItems().clear();
        for(Internship i : facade.getInternships().values()) {
            cbCodeInternship.getItems().add(i.getTitle()+","+i.getIdCode());
            lvConsult.getItems().add(i.toString());
            cbTitleCodeInternship.getItems().add(i.getTitle()+","+i.getIdCode());
        }

        cbAllStudents.getItems().clear();
        cbAllStudentsEdit.getItems().clear();
        for(Student s : facade.getStudents().values()) {
            cbAllStudents.getItems().add(s.getName()+","+s.getStudentNumber().toString());
            cbAllStudentsEdit.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }

        if (facade.getState() != AppState.PONE_INTERNSHIP) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
