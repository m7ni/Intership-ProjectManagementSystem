
package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.data.proposals.SelfProposed;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PhaseTwoStateUI  extends BorderPane {
    Facade facade;
    Tab tabUploadCSV, tabInsert,tabConsultC, tabConsultS,tabConsultFilters, tabErase, tabEdit;
    Button btnUploadCSV, btnConfirmInsert, btnClearInsert, btnConfirmEdit, btnClearEdit,btnConfirmConsultFilters;
    Button btnStudentSelfProp, btnStudentCandidature, btnStudenNotCandidature,btnEraseCandidature;
    VBox vbInsert, vbCSV, vbErase, vbConsultC,vbConsultS,vbConsultFilters, vbEdit;
    HBox hbCandidatureFilters, hbBtnInsert, hbBtnEdit, hbConsultStudents;
    ChoiceBox cbNameNumberStudent, cbStudentNumberEdit,cbStudentNumberErase;
    TextField tfProposals,tfEdit;
    ListView lvConsultC,lvConsultS,lvConsultFilters;
    Label lbEdit, lbInsert,lbErase,lbConsultProject,lbConsultStudents;
    CheckBox chSelfProp,chTeachersProp, chPropWCandidature, chPropWOCandidature;
    ArrayList<Filtros> fl;
    public PhaseTwoStateUI(Facade facade) {
        this.facade = facade;
        fl = new ArrayList<>();
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

        //ERASE
        lbErase = new Label("Choose the Student's Candidature that you want to erase");
        lbErase.setAlignment(Pos.CENTER);
        lbErase.setId("lbTitle");
        lbErase.setMinWidth(800);
        vbErase = new VBox();
        vbErase.setSpacing(5);
        vbErase.setAlignment(Pos.CENTER);
        cbStudentNumberErase = new ChoiceBox<>();
        btnEraseCandidature = new Button("Erase");
        vbErase.getChildren().addAll(lbErase,cbStudentNumberErase,btnEraseCandidature);

        //Insert
        lbInsert = new Label("Choose the student you want to " +
                "insert the proposals [P123,P456,P789] ");
        lbInsert.setAlignment(Pos.CENTER);
        lbInsert.setId("lbTitle");
        lbInsert.setMinWidth(800);

        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        hbBtnInsert = new HBox( btnConfirmInsert, btnClearInsert);
        hbBtnInsert.setAlignment(Pos.CENTER);
        hbBtnInsert.setSpacing(5);

        vbInsert = new VBox();
        vbInsert.setAlignment(Pos.CENTER);
        vbInsert.setSpacing(10);

        cbNameNumberStudent = new ChoiceBox<>();
        tfProposals= new TextField();
        tfProposals.setMaxWidth(200);
        vbInsert.getChildren().addAll(lbInsert,cbNameNumberStudent,tfProposals,hbBtnInsert);

        //consult Candidature
        vbConsultC = new VBox();
        vbConsultC.setAlignment(Pos.CENTER);
        lvConsultC = new ListView();
        vbConsultC.getChildren().add(lvConsultC);
        lvConsultC.setEditable(false);


        //consult Candidature Student
        lbConsultStudents = new Label("Choose which group of Students you want to see");
        lbConsultStudents.setAlignment(Pos.CENTER);
        lbConsultStudents.setId("lbTitle");
        lbConsultStudents.setMinWidth(800);
        hbConsultStudents = new HBox();
        hbConsultStudents.setAlignment(Pos.CENTER);
        hbConsultStudents.setSpacing(5);
        vbConsultS = new VBox();
        vbConsultS.setSpacing(20);
        vbConsultS.setAlignment(Pos.CENTER);
        lvConsultS = new ListView();
        lvConsultS.setEditable(false);
        btnStudentSelfProp  = new Button("Self Proposed ");
        btnStudentCandidature = new Button("Registered Candidature ");
        btnStudenNotCandidature = new Button("Non Registered Candidature");
        hbConsultStudents.getChildren().addAll( btnStudentSelfProp,btnStudentCandidature,btnStudenNotCandidature);
        vbConsultS.getChildren().addAll(lbConsultStudents,lvConsultS,hbConsultStudents);

        //consult Candidature Filters
        lbConsultProject = new Label("Select which filters you want to use");
        lbConsultProject.setAlignment(Pos.CENTER);
        lbConsultProject.setId("lbTitle");
        lbConsultProject.setMinWidth(800);
        hbCandidatureFilters = new HBox();
        hbCandidatureFilters.setAlignment(Pos.CENTER);
        chSelfProp = new CheckBox("Self Proposed       ");
        chTeachersProp= new CheckBox("Proposed by Teacher       ");
        chPropWCandidature = new CheckBox("Proposals With Candidature      ");
        chPropWOCandidature = new CheckBox("Proposals Without Candidature      ");
        chPropWOCandidature.setId("checkBoxL");
        chTeachersProp.setId("checkBoxL");
        chPropWCandidature.setId("checkBoxL");
        chSelfProp.setId("checkBoxL");
        hbCandidatureFilters.getChildren().addAll(chSelfProp,chTeachersProp, chPropWCandidature, chPropWOCandidature);
        btnConfirmConsultFilters = new Button("Show");
        vbConsultFilters = new VBox();
        vbConsultFilters.setSpacing(20);
        vbConsultFilters.setAlignment(Pos.CENTER);
        lvConsultFilters = new ListView();
        vbConsultFilters.getChildren().addAll(lbConsultProject,lvConsultFilters,hbCandidatureFilters,btnConfirmConsultFilters);
        lvConsultFilters.setEditable(false);

        //Edit
        btnConfirmEdit = new Button("Edit");
        btnClearEdit = new Button("Clear");
        hbBtnEdit = new HBox( btnConfirmEdit, btnClearEdit);
        hbBtnEdit.setAlignment(Pos.CENTER);
        hbBtnEdit.setSpacing(6);
        tfEdit= new TextField();
        tfEdit.setMaxWidth(200);
        vbEdit = new VBox();
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.setSpacing(10);

        lbEdit = new Label("Choose the Student that you want to edit");
        lbEdit.setAlignment(Pos.CENTER);
        lbEdit.setId("lbTitle");
        lbEdit.setMinWidth(500);
        cbStudentNumberEdit = new ChoiceBox<>();

        vbEdit.getChildren().addAll(lbEdit, cbStudentNumberEdit,tfEdit, hbBtnEdit);

        tabUploadCSV = new Tab("Upload CSV", vbCSV);
        tabUploadCSV.setClosable(false);
        tabInsert = new Tab("Insert Candidature"  , vbInsert);
        tabInsert.setClosable(false);
        tabConsultC = new Tab("Consult Candidature"  , vbConsultC);
        tabConsultC.setClosable(false);
        tabErase = new Tab("Erase Candidature" , vbErase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit Candidature" , vbEdit);
        tabEdit.setClosable(false);
        tabConsultS = new Tab("Consult Students"  , vbConsultS);
        tabConsultS.setClosable(false);
        tabConsultFilters = new Tab("Consult Projects"  , vbConsultFilters);
        tabConsultFilters.setClosable(false);
        tabConsultC = new Tab("Consult Candidatures"  , vbConsultC);
        tabConsultC.setClosable(false);


        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(tabUploadCSV);
        tabPane.getTabs().add(tabInsert);
        tabPane.getTabs().add(tabErase);
        tabPane.getTabs().add(tabEdit);
        tabPane.getTabs().add(tabConsultC);
        tabPane.getTabs().add(tabConsultS);
        tabPane.getTabs().add(tabConsultFilters);

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

            ToastMessage.show(getScene().getWindow(), facade.upload(hFile));
        });

        btnEraseCandidature.setOnAction(actionEvent -> {
            if(cbStudentNumberErase.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbStudentNumberErase.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                facade.removeC(Long.parseLong(values[1]));
            }
        });

        btnConfirmConsultFilters.setOnAction(actionEvent -> {
            if(chSelfProp.isSelected())
                fl.add(Filtros.SELFPROP);

            if(chTeachersProp.isSelected())
                fl.add(Filtros.TEACHERPROP);

            if(chPropWCandidature.isSelected())
                fl.add(Filtros.CANDIDATUREPROP);

            if(chPropWOCandidature.isSelected())
                fl.add(Filtros.CANDIDATUREWOPROP);

            lvConsultFilters.getItems().clear();
            for(Proposals p  : facade.printFiltros(fl))
                lvConsultFilters.getItems().add(p.toString());
            fl.clear();
        });

        btnStudentSelfProp.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            if(facade.getSelfProp().values().isEmpty())
                return;

            for(SelfProposed s  : facade.getSelfProp().values())
                lvConsultS.getItems().add(facade.getStudents().get(s.getStudentNumber()).toString());

        });

        btnStudentCandidature.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            if(facade.getCandidatures().keySet().isEmpty())
                return;

            for(long c  : facade.getCandidatures().keySet())
                lvConsultS.getItems().add(facade.getStudents().get(c).toString());

        });

        btnStudenNotCandidature.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            for(Student s  : facade.studentsWOCandidature())
                lvConsultS.getItems().add(s.toString());
            fl.clear();
        });

        btnClearInsert.setOnAction(actionEvent -> {
            clearInsert();
        });

        btnClearEdit.setOnAction(actionEvent -> {
            clearEdit();
        });

        btnConfirmInsert.setOnAction(actionEvent -> {
            if(!tfProposals.getText().isBlank() && cbNameNumberStudent.getValue()!=null){
                Label labelresponse = new Label();
                labelresponse.setText("" +cbNameNumberStudent.getValue());
                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                String aux = values[1] + "," +
                        tfProposals.getText();
                String[] sFinal;
                sFinal = aux.split(",");

                if(!facade.addCandidature(sFinal)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Error adding Candidature");
                    alert.showAndWait();
                    clearInsert();
                    return;
                }
            }

            clearInsert();

        });

        btnConfirmEdit.setOnAction(actionEvent -> {

            if(tfEdit.getText().isBlank() && cbStudentNumberEdit.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no fields to edit");
                alert.showAndWait();
                return;
            }

            if(!tfEdit.getText().isBlank() && cbStudentNumberEdit.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" +cbStudentNumberEdit.getValue());
                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);

                if(!facade.editCandidatures(tfEdit.getText(),values[1])){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Error editing Candidature");
                    alert.showAndWait();
                    clearEdit();
                    return;
                }
            }

            clearEdit();

        });


    }

    void clearInsert() {
        tfProposals.setText("");
    }
    void clearEdit() {
        tfEdit.setText("");
    }

    private void update() {
        if(facade.getBlock(2) == StateBlock.BLOCKED){
            tabUploadCSV.setDisable(true);
            tabInsert.setDisable(true);
            tabErase.setDisable(true);
            tabEdit.setDisable(true);
        }

        cbNameNumberStudent.getItems().clear();
        for(Student  s : facade.studentsWOCandidature()) {
            cbNameNumberStudent.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }

        cbStudentNumberEdit.getItems().clear();
        cbStudentNumberErase.getItems().clear();
        for(Student s : facade.studentsWCandidature()) {
            cbStudentNumberEdit.getItems().add(s.getName()+","+s.getStudentNumber().toString());
            cbStudentNumberErase.getItems().add(s.getName()+","+s.getStudentNumber().toString());

        }

        StringBuilder sb = new StringBuilder();
        lvConsultC.getItems().clear();
        for (long c : facade.getCandidatures().keySet()) {
            sb.append("          Student Number [").append(c).append("]");
            sb.append("          Proposals Codes [").append(facade.getCandidatures().get(c).toString(), 1, facade.getCandidatures().get(c).toString().length() - 1).append("]");
            lvConsultC.getItems().add(sb.toString());
        }

        if (facade.getState() != AppState.PHASE_TWO) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}

//