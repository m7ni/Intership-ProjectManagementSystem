
package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.proposals.FinalAtribution;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.util.ArrayList;

public class PhaseThreeStateUI  extends BorderPane {
    Facade facade;
    Tab tabAutomatic, tabManualInsert, tabManualErase, tabConsultStudents, tabConsultFilters;
    VBox vbAutomatic, vbManualInsert, vbManualErase, vbConsultS, vbConsultFilters;
    HBox hbBtnAutomatic,hbBtnInsert, hbBtnConsultS,hbBtnConsultC;
    Button btnAutomatic1, btnAutomatic2,btnConfirmManualInsert, btnClearManualInsert, btnEraseManual, btnSelfPropAssociatedS, btnCandidatureRegiS, btnPropAttributedS,btnNoPropS;
    ListView lvConsultS,lvConsultP;
    Button btnShowProject;
    CheckBox chSelfProp,chTeachersProp, chPropAvailable, chPropNotAvailable;
    Label lbManualErase, lbManual, lbConsultStudents, lbAutomatic, lbConsultC;
    ChoiceBox cbStudentNumberErase, cbNameNumberStudent, cbtitleCodeStudent;
    ArrayList<Filtros> fl;

    public PhaseThreeStateUI(Facade facade) {
        fl = new ArrayList<>();
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        //consultS
        lbConsultStudents = new Label("Choose which group of Students you want to see");
        lbConsultStudents.setAlignment(Pos.CENTER);
        lbConsultStudents.setId("lbTitle");
        lbConsultStudents.setMinWidth(800);
        hbBtnConsultS = new HBox();
        hbBtnConsultS.setAlignment(Pos.CENTER);
        hbBtnConsultS.setSpacing(5);
        vbConsultS = new VBox();
        vbConsultS.setSpacing(20);
        vbConsultS.setAlignment(Pos.CENTER);
        lvConsultS = new ListView();
        lvConsultS.setEditable(false);
        btnSelfPropAssociatedS  = new Button("Self Proposed Associated");
        btnCandidatureRegiS = new Button("Registered Candidature ");
        btnPropAttributedS = new Button("Attributed Proposal");
        btnNoPropS = new Button("Non Attributed Proposal");
        hbBtnConsultS.getChildren().addAll( btnSelfPropAssociatedS,btnCandidatureRegiS,btnPropAttributedS,btnNoPropS);
        vbConsultS.getChildren().addAll(lbConsultStudents,lvConsultS, hbBtnConsultS);


        //FILTERS
        lbConsultC = new Label("Select which filters you want to use");
        lbConsultC.setAlignment(Pos.CENTER);
        lbConsultC.setId("lbTitle");
        lbConsultC.setMinWidth(800);
        hbBtnConsultC = new HBox();
        hbBtnConsultC.setAlignment(Pos.CENTER);
        chSelfProp = new CheckBox("Self Proposed       ");
        chTeachersProp= new CheckBox("Proposed by Teacher       ");
        chPropAvailable = new CheckBox("Available Proposals      ");
        chPropNotAvailable = new CheckBox("Non Available Proposals      ");
        chPropNotAvailable.setId("checkBoxL");
        chTeachersProp.setId("checkBoxL");
        chPropAvailable.setId("checkBoxL");
        chSelfProp.setId("checkBoxL");
        hbBtnConsultC.getChildren().addAll(chSelfProp,chTeachersProp, chPropAvailable, chPropNotAvailable);
        btnShowProject = new Button("Show");
        vbConsultFilters = new VBox();
        vbConsultFilters.setSpacing(20);
        vbConsultFilters.setAlignment(Pos.CENTER);
        lvConsultP = new ListView();
        vbConsultFilters.getChildren().addAll(lbConsultC,lvConsultP,hbBtnConsultC,btnShowProject);
        lvConsultP.setEditable(false);

        //AUTOMATIC
        vbAutomatic = new VBox();
        vbAutomatic.setAlignment(Pos.CENTER);
        vbAutomatic.setSpacing(5);
        hbBtnAutomatic = new HBox();
        hbBtnAutomatic.setAlignment(Pos.CENTER);
        hbBtnAutomatic.setSpacing(5);
        lbAutomatic = new Label("Automatic Atribution of Proposals");
        lbAutomatic.setId("lbTitle");
        lbAutomatic.setMinWidth(500);
        lbAutomatic.setAlignment(Pos.CENTER);
        btnAutomatic1 = new Button("Self-Prop & Teacher Prop");
        btnAutomatic2 = new Button("Available Proposals");
        hbBtnAutomatic.getChildren().addAll(btnAutomatic1,btnAutomatic2);
        vbAutomatic.getChildren().addAll(lbAutomatic,hbBtnAutomatic);

        //Manual attribution
        lbManual = new Label("Manual Attribution of Proposals");
        lbManual.setAlignment(Pos.CENTER);
        lbManual.setId("lbTitle");
        lbManual.setMinWidth(800);

        btnConfirmManualInsert = new Button("Insert");
        btnClearManualInsert = new Button("Clear");
        hbBtnInsert = new HBox( btnConfirmManualInsert, btnClearManualInsert);
        hbBtnInsert.setAlignment(Pos.CENTER);
        hbBtnInsert.setSpacing(5);

        vbManualInsert = new VBox();
        vbManualInsert.setAlignment(Pos.CENTER);
        vbManualInsert.setSpacing(10);

        cbNameNumberStudent = new ChoiceBox<>();
        cbtitleCodeStudent = new ChoiceBox<>();

        vbManualInsert.getChildren().addAll(lbManual,cbNameNumberStudent,cbtitleCodeStudent,hbBtnInsert);

        //ERASE
        lbManualErase = new Label("Choose the Student's Attribution that you want to erase");
        lbManualErase.setAlignment(Pos.CENTER);
        lbManualErase.setId("lbTitle");
        lbManualErase.setMinWidth(800);
        vbManualErase = new VBox();
        vbManualErase.setSpacing(5);
        vbManualErase.setAlignment(Pos.CENTER);
        cbStudentNumberErase = new ChoiceBox<>();
        btnEraseManual = new Button("Erase");
        vbManualErase.getChildren().addAll(lbManualErase,cbStudentNumberErase,btnEraseManual);


        tabAutomatic = new Tab("Automatic Attribution", vbAutomatic);
        tabAutomatic.setClosable(false);
        tabManualInsert = new Tab("Manual Insert"  , vbManualInsert);
        tabManualInsert.setClosable(false);
        tabManualErase = new Tab("Manual Erase"  , vbManualErase);
        tabManualErase.setClosable(false);
        tabConsultStudents = new Tab("Consult Students" , vbConsultS);
        tabConsultStudents.setClosable(false);
        tabConsultFilters = new Tab("Consult Proposals" , vbConsultFilters);
        tabConsultFilters.setClosable(false);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(tabAutomatic);
        tabPane.getTabs().add(tabManualInsert);
        tabPane.getTabs().add(tabManualErase);
        tabPane.getTabs().add(tabConsultStudents);
        tabPane.getTabs().add(tabConsultFilters);

        VBox VBox = new VBox(tabPane);
        this.setTop(VBox);
        VBox.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });

        btnSelfPropAssociatedS.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            if(facade.studentsSelfPropCandidature().isEmpty())
                return;

            for(Student s  : facade.studentsSelfPropCandidature())
                lvConsultS.getItems().add(s.toString());

        });

        btnCandidatureRegiS.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            if(facade.getCandidatures().keySet().isEmpty())
                return;

            for(long c  : facade.getCandidatures().keySet())
                lvConsultS.getItems().add(facade.getStudents().get(c).toString());

        });

        btnPropAttributedS.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            if(facade.getFA().isEmpty())
                return;

            for(FinalAtribution fa  : facade.getFA())
                lvConsultS.getItems().add(fa.getStudent().toString());

        });

        btnNoPropS.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            if(facade.studentsWOPropAssociated().isEmpty())
                return;

            for(Student s  : facade.studentsWOPropAssociated())
                lvConsultS.getItems().add(s.toString());

        });

        btnShowProject.setOnAction(actionEvent -> {
            if(chSelfProp.isSelected())
                fl.add(Filtros.SELFPROP);

            if(chTeachersProp.isSelected())
                fl.add(Filtros.TEACHERPROP);

            if(chPropAvailable.isSelected())
                fl.add(Filtros.AVAIABLE);

            if(chPropNotAvailable.isSelected())
                fl.add(Filtros.NOTAVAIABLE);

            lvConsultP.getItems().clear();
            for(Proposals p  : facade.printFiltros(fl))
                lvConsultP.getItems().add(p.toString());
            fl.clear();
        });
        btnAutomatic1.setOnAction(actionEvent -> {
           facade.automaticOneP3();
            ToastMessage.show(getScene().getWindow(), true);
        });
        btnAutomatic2.setOnAction(actionEvent -> {
            facade.automaticTwoP3();
            ToastMessage.show(getScene().getWindow(), true);

        });

        btnClearManualInsert.setOnAction(actionEvent -> {
            clearManual();
        });

        btnConfirmManualInsert.setOnAction(actionEvent -> {
            if(cbNameNumberStudent.getValue() == null || cbtitleCodeStudent.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                clearManual();
                return;
            }

            String idCode = null;
            if(cbtitleCodeStudent.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbtitleCodeStudent.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                idCode = values[1];
            }

            long number = -1;
            if(cbNameNumberStudent.getValue() != null){
                Label labelresponse = new Label();
                labelresponse.setText("" + cbNameNumberStudent.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                number = Long.valueOf(values[1]);
            }

            if(!facade.manualAtribution(number, idCode)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are fields that do not match the requirements");
                alert.showAndWait();
                clearManual();
                return;
            }
            clearManual();
        });

        btnEraseManual.setOnAction(actionEvent -> {
            if(cbStudentNumberErase.getValue() == null) {
                facade.removeAllFA();
            }
                else {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbStudentNumberErase.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[2] = values[2].substring(0, values[2].length() - 1); //se der erro mudar o 0 para 1
                facade.removeFA(Long.parseLong(values[2]));
            }
        });

    }

    void clearManual() {
        cbNameNumberStudent.setValue(null);
        cbtitleCodeStudent.setValue(null);
    }

    private void update() {
        btnAutomatic2.setDisable(false);
        if(facade.getBlock(2) == StateBlock.UNLOCKED){
            btnAutomatic2.setDisable(true);
        }

        if(facade.getBlock(3) == StateBlock.BLOCKED){
            tabAutomatic.setDisable(true);
            tabManualErase.setDisable(true);
            tabManualInsert.setDisable(true);
        }
        cbStudentNumberErase.getItems().clear();
        for(FinalAtribution fa : facade.getFA()) {
            cbStudentNumberErase.getItems().add(fa.getFinalP().getIdCode()+","+fa.getStudent().getName()+","+fa.getStudent().getStudentNumber());
        }

        cbNameNumberStudent.getItems().clear();
        for(Student s : facade.studentsWOPropAssociated()) {
            cbNameNumberStudent.getItems().add(s.getName()+","+s.getStudentNumber());
        }

        cbtitleCodeStudent.getItems().clear();
        for(Proposals p : facade.getAvailableProposals()) {
            cbtitleCodeStudent.getItems().add(p.getTitle()+","+p.getIdCode());
        }

        if (facade.getState() != AppState.PHASE_THREE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
