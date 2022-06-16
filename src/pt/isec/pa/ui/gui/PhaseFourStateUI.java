package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.FinalAtribution;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.ui.gui.utils.ToastMessage;

public class PhaseFourStateUI  extends BorderPane {
    Facade facade;
    Tab tabAutomatic, tabManualInsert, tabManualEdit, tabManualErase, tabConsultBtn;
    VBox vbAutomatic, vbManualInsert, vbManualEdit, vbManualErase,  vbConsultBtn,vbConsultBtnN;
    HBox hbBtnInsert, hbBtnConsultBTN,hbBtnAutomatic, hbBtnManualEdit, hbStudentNumberPropCodeManualInsert, hbMentorEmailManualInsert, hbStudentMentorInsertAttributionEdit, hbMentorEmailEdit, hbAttributionManualErase, hbMentorEmailConsultMentors;
    Button btnAutomatic1,btnConfirmManualInsert, btnClearManualInsert,btnConfirmManualEdit, btnClearManualEdit, btnEraseManual, btnPropAttributesMentor, btnPropAttributesNMentor, btnNOrientMentor;
    ListView lvConsultBtn;
    Label lbManualErase, lbManual, lbConsultStudents, lbAutomatic, lbManualEdit, lbStudentNumberPropCodeManualInsert, lbMentorEmailManualInsert, lbStudentMentorInsertAttributionEdit, lbMentorEmailEdit, lbAttributionManualErase, lbMentorEmailConsultMentors;
    ChoiceBox  cbNameEmailMentor, cbTitleCodeProposal,cbNameSIDCodePEmailTEdit, cbNameSIDCodePEmailTErase, cbTitleCodeMentor, cbNameEmailMentorEdit;

    public PhaseFourStateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }
    private void createViews() {

        //AUTOMATIC
        vbAutomatic = new VBox();
        vbAutomatic.setAlignment(Pos.CENTER);
        vbAutomatic.setSpacing(5);
        hbBtnAutomatic = new HBox();
        hbBtnAutomatic.setAlignment(Pos.CENTER);
        hbBtnAutomatic.setSpacing(5);
        lbAutomatic = new Label("Automatic Attribution of Mentors To the Projects they are associated");
        lbAutomatic.setId("lbTitle");
        lbAutomatic.setMinWidth(800);
        lbAutomatic.setAlignment(Pos.CENTER);
        btnAutomatic1 = new Button("Automatic Association");
        hbBtnAutomatic.getChildren().add(btnAutomatic1);
        vbAutomatic.getChildren().addAll(lbAutomatic,hbBtnAutomatic);

        //ConsultM
        lbConsultStudents = new Label("Choose which group of People you want to see");
        lbConsultStudents.setAlignment(Pos.CENTER);
        lbConsultStudents.setId("lbTitle");
        lbConsultStudents.setMinWidth(800);
        hbBtnConsultBTN = new HBox();
        hbBtnConsultBTN.setAlignment(Pos.CENTER);
        hbBtnConsultBTN.setSpacing(5);
        vbConsultBtn = new VBox();
        vbConsultBtn.setSpacing(20);
        vbConsultBtn.setAlignment(Pos.CENTER);
        lvConsultBtn = new ListView();
        lvConsultBtn.setEditable(false);
        btnPropAttributesMentor  = new Button("Students with Proposal with Mentor         ");
        btnPropAttributesNMentor = new Button("Students with Proposal with no Mentor       ");
        btnNOrientMentor = new Button("Number of Proposals a Teacher Mentors      ");
        cbTitleCodeMentor =new ChoiceBox<>();

        lbMentorEmailConsultMentors = new Label("Mentor\t");
        lbMentorEmailConsultMentors.setAlignment(Pos.CENTER_LEFT);

        hbMentorEmailConsultMentors = new HBox(lbMentorEmailConsultMentors, cbTitleCodeMentor);

        hbMentorEmailConsultMentors.setAlignment(Pos.CENTER);
        hbMentorEmailConsultMentors.setId(  "hBoxChoice");

        vbConsultBtnN = new VBox();
        vbConsultBtnN.setSpacing(7);
        vbConsultBtnN.getChildren().addAll(btnNOrientMentor, hbMentorEmailConsultMentors);

        hbBtnConsultBTN.getChildren().addAll( btnPropAttributesMentor,btnPropAttributesNMentor,vbConsultBtnN);
        vbConsultBtn.getChildren().addAll(lbConsultStudents,lvConsultBtn, hbBtnConsultBTN);


        //Manual attribution
        lbManual = new Label("Manual Attribution of Mentor");
        lbManual.setAlignment(Pos.CENTER);
        lbManual.setId("lbTitle");
        lbManual.setMinWidth(400);

        btnConfirmManualInsert = new Button("Insert");
        btnClearManualInsert = new Button("Clear");
        hbBtnInsert = new HBox( btnConfirmManualInsert, btnClearManualInsert);
        hbBtnInsert.setAlignment(Pos.CENTER);
        hbBtnInsert.setSpacing(5);

        vbManualInsert = new VBox();
        vbManualInsert.setAlignment(Pos.CENTER);
        vbManualInsert.setSpacing(10);

        cbNameEmailMentor = new ChoiceBox<>();

        lbMentorEmailManualInsert = new Label("Mentor\t");
        lbMentorEmailManualInsert.setAlignment(Pos.CENTER_LEFT);

        hbMentorEmailManualInsert = new HBox(lbMentorEmailManualInsert, cbNameEmailMentor);

        hbMentorEmailManualInsert.setAlignment(Pos.CENTER);
        hbMentorEmailManualInsert.setId(  "hBoxChoice");


        cbTitleCodeProposal = new ChoiceBox<>();

        lbStudentNumberPropCodeManualInsert = new Label("Student Proposal");
        lbStudentNumberPropCodeManualInsert.setAlignment(Pos.CENTER_LEFT);

        hbStudentNumberPropCodeManualInsert = new HBox(lbStudentNumberPropCodeManualInsert, cbTitleCodeProposal);

        hbStudentNumberPropCodeManualInsert.setAlignment(Pos.CENTER);
        hbStudentNumberPropCodeManualInsert.setId(  "hBoxChoice");

        vbManualInsert.getChildren().addAll(lbManual, hbStudentNumberPropCodeManualInsert,hbMentorEmailManualInsert,hbBtnInsert);

        //Edit
        btnConfirmManualEdit = new Button("Edit");
        btnClearManualEdit = new Button("Clear");
        hbBtnManualEdit = new HBox( btnConfirmManualEdit, btnClearManualEdit);
        hbBtnManualEdit.setAlignment(Pos.CENTER);
        hbBtnManualEdit.setSpacing(6);
        vbManualEdit = new VBox();
        vbManualEdit.setAlignment(Pos.CENTER);
        vbManualEdit.setSpacing(10);

        lbManualEdit = new Label("Choose the Proposal´s Mentor that you want to edit");
        lbManualEdit.setAlignment(Pos.CENTER);
        lbManualEdit.setId("lbTitle");
        lbManualEdit.setMinWidth(500);

        cbNameSIDCodePEmailTEdit = new ChoiceBox<>();

        lbStudentMentorInsertAttributionEdit = new Label("Student Proposal and Mentor");
        lbStudentMentorInsertAttributionEdit.setAlignment(Pos.CENTER_LEFT);

        hbStudentMentorInsertAttributionEdit = new HBox(lbStudentMentorInsertAttributionEdit, cbNameSIDCodePEmailTEdit);

        hbStudentMentorInsertAttributionEdit.setAlignment(Pos.CENTER);
        hbStudentMentorInsertAttributionEdit.setId(  "hBoxChoice");


        cbNameEmailMentorEdit = new ChoiceBox<>();

        lbMentorEmailEdit = new Label("Mentor\t");
        lbMentorEmailEdit.setAlignment(Pos.CENTER_LEFT);

        hbMentorEmailEdit = new HBox(lbMentorEmailEdit, cbNameEmailMentorEdit);

        hbMentorEmailEdit.setAlignment(Pos.CENTER);
        hbMentorEmailEdit.setId(  "hBoxChoice");

        vbManualEdit.getChildren().addAll(lbManualEdit,hbStudentMentorInsertAttributionEdit, hbMentorEmailEdit, hbBtnManualEdit);

        //ERASE
        lbManualErase = new Label("Choose the Student's Attribution that you want to erase");
        lbManualErase.setAlignment(Pos.CENTER);
        lbManualErase.setId("lbTitle");
        lbManualErase.setMinWidth(800);
        vbManualErase = new VBox();
        vbManualErase.setSpacing(5);
        vbManualErase.setAlignment(Pos.CENTER);
        cbNameSIDCodePEmailTErase = new ChoiceBox<>();

        lbAttributionManualErase = new Label("Mentor Attribution\t");
        lbAttributionManualErase.setAlignment(Pos.CENTER_LEFT);

        hbAttributionManualErase = new HBox(lbAttributionManualErase, cbNameSIDCodePEmailTErase);

        hbAttributionManualErase.setAlignment(Pos.CENTER);
        hbAttributionManualErase.setId(  "hBoxChoice");

        btnEraseManual = new Button("Erase");
        vbManualErase.getChildren().addAll(lbManualErase, hbAttributionManualErase,btnEraseManual);

        tabAutomatic = new Tab("Automatic Attribution", vbAutomatic);
        tabAutomatic.setClosable(false);
        tabManualInsert = new Tab("Manual Insert"  , vbManualInsert);
        tabManualInsert.setClosable(false);
        tabManualErase = new Tab("Manual Erase"  , vbManualErase);
        tabManualErase.setClosable(false);
        tabManualEdit = new Tab("Edit Mentor", vbManualEdit);
        tabManualEdit.setClosable(false);
        tabConsultBtn = new Tab("Consult Mentors" , vbConsultBtn);
        tabConsultBtn.setClosable(false);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(tabAutomatic);
        tabPane.getTabs().add(tabManualInsert);
        tabPane.getTabs().add(tabManualEdit);
        tabPane.getTabs().add(tabManualErase);
        tabPane.getTabs().add(tabConsultBtn);

        VBox VBox = new VBox(tabPane);
        this.setTop(VBox);
        VBox.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });

        btnConfirmManualInsert.setOnAction(actionEvent -> {
            if(cbNameEmailMentor.getValue() == null || cbTitleCodeProposal.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                clearManualInsert();
                return;
            }

            String idCode = null;
            if(cbTitleCodeProposal.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbTitleCodeProposal.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                idCode = values[1];
            }

            String emailMentor = null;
            if(cbNameEmailMentor.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbNameEmailMentor.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                emailMentor = values[1];
            }

            if(!facade.mentorAttribution(idCode, emailMentor)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are fields that do not match the requirements");
                alert.showAndWait();
                clearManualInsert();
                return;
            }
            clearManualInsert();
        });

        btnConfirmManualEdit.setOnAction(actionEvent -> {
            if(cbNameSIDCodePEmailTEdit.getValue() == null && cbNameEmailMentorEdit.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                clearManualEdit();
                return;
            }

            String idCode = null;
            if(cbNameSIDCodePEmailTEdit.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbNameSIDCodePEmailTEdit.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                idCode = values[1];
            }

            String emailMentor = null;
            if(cbNameEmailMentorEdit.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbNameEmailMentorEdit.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                emailMentor = values[1];
            }

            if(!facade.editMentor(emailMentor, idCode)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are fields that do not match the requirements");
                alert.showAndWait();
                clearManualEdit();
                return;
            }
            clearManualEdit();
        });


        btnAutomatic1.setOnAction(actionEvent -> {
            facade.automaticMentorPhaseFour();
            ToastMessage.show(getScene().getWindow(), true);
        });

        btnPropAttributesMentor.setOnAction(actionEvent -> {
            lvConsultBtn.getItems().clear();
            if(facade.studentsMentor().isEmpty())
                return;

            for(Student s  :  facade.studentsMentor())
                lvConsultBtn.getItems().add(s.toString());
        });

        btnPropAttributesNMentor.setOnAction(actionEvent -> {
            lvConsultBtn.getItems().clear();
            if(facade.studentsMentor().isEmpty())
                return;

            for(Student s : facade.studentsNoMentor())
                lvConsultBtn.getItems().add(s.toString());
        });

        btnNOrientMentor.setOnAction(actionEvent -> {
            lvConsultBtn.getItems().clear();
            StringBuilder sb = new StringBuilder();
            if(cbTitleCodeMentor.getValue() == null){
                for(String email : facade.getTeachers().keySet()){
                    sb.append("Teacher: "+ facade.getTeachers().get(email).toString()).append("\n");
                    sb.append("Min: "+ facade.mentorByTeacher(email).get(0)).append("\n");
                    sb.append("Max: "+ facade.mentorByTeacher(email).get(1)).append("\n");
                    sb.append("Average: "+ facade.mentorByTeacher(email).get(3)).append("\n");
                    lvConsultBtn.getItems().add(sb.toString());
                    sb = new StringBuilder();
                }

            }else{
                Label labelresponse = new Label();
                labelresponse.setText("" + cbTitleCodeMentor.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                String email = values[1];


                sb.append("Min: "+ facade.mentorByTeacher(email).get(0)).append("\n");
                sb.append("Max: "+ facade.mentorByTeacher(email).get(1)).append("\n");
                sb.append("Teacher: "+ facade.getTeachers().get(email).toString()).append("\n");
                sb.append("Teacher Count: "+ facade.getTeachers().get(email).getMentorCount()).append("\n");
                sb.append("Average: "+ facade.mentorByTeacher(email).get(3)).append("\n");

                lvConsultBtn.getItems().add(sb.toString());
            }

            cbTitleCodeMentor.setValue(null);
        });

        btnEraseManual.setOnAction(actionEvent -> {
            if(cbNameSIDCodePEmailTErase.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbNameSIDCodePEmailTErase.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                facade.removeMentor(values[1]);
            }
        });

    }

    void clearManualInsert() {
        cbNameEmailMentor.setValue(null);
        cbTitleCodeProposal.setValue(null);
    }

    void clearManualEdit() {
        cbNameEmailMentorEdit.setValue(null);
        cbNameSIDCodePEmailTEdit.setValue(null);
    }


    private void update() {
        if(facade.getBlock(4) == StateBlock.BLOCKED){
            tabAutomatic.setDisable(true);
            tabManualErase.setDisable(true);
            tabManualInsert.setDisable(true);
            tabManualEdit.setDisable(true);
        }

        cbTitleCodeProposal.getItems().clear();
        for (FinalAtribution fa : facade.proposalsNoMentor()) {
            cbTitleCodeProposal.getItems().add(fa.getStudent().getStudentNumber()+","+fa.getFinalP().getIdCode());
        }

        cbNameSIDCodePEmailTErase.getItems().clear();
        cbNameSIDCodePEmailTEdit.getItems().clear();
        for(FinalAtribution fa : facade.proposalsWithMentor()) {
            cbNameSIDCodePEmailTErase.getItems().add(fa.getStudent().getName()+","+fa.getFinalP().getIdCode()+","+fa.getMentor().getEmail());
            cbNameSIDCodePEmailTEdit.getItems().add(fa.getStudent().getName()+","+fa.getFinalP().getIdCode()+","+fa.getMentor().getEmail());

        }

        cbNameEmailMentor.getItems().clear();
        cbTitleCodeMentor.getItems().clear();
        cbNameEmailMentorEdit.getItems().clear();
        for(Teacher t : facade.getTeachers().values()) {
            cbNameEmailMentor.getItems().add(t.getName()+","+t.getEmail());
            cbTitleCodeMentor.getItems().add(t.getName()+","+t.getEmail());
            cbNameEmailMentorEdit.getItems().add(t.getName()+","+t.getEmail());
        }

        if (facade.getState() != AppState.PHASE_FOUR) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
