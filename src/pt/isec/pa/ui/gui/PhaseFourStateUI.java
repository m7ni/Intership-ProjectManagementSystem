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
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.FinalAtribution;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.util.ArrayList;

public class PhaseFourStateUI  extends BorderPane {
    Facade facade;
    Tab tabAutomatic, tabManualInsert, tabManualEdit, tabManualErase, tabConsultBtn;
    VBox vbAutomatic, vbManualInsert, vbManualEdit, vbManualErase,  vbConsultBtn,vbConsultBtnN;
    HBox hbBtnInsert, hbBtnConsultBTN,hbBtnAutomatic, hbBtnManualEdit;
    Button btnAutomatic1,btnConfirmManualInsert, btnClearManualInsert,btnConfirmManualEdit, btnClearManualEdit, btnEraseManual, btnPropAttributesMentor, btnPropAttributesNMentor, btnNOrientMentor;
    ListView lvConsultBtn;
    Label lbManualErase, lbManual, lbConsultStudents, lbAutomatic, lbManualEdit;
    ChoiceBox  cbNameEmailMentor, cbTitleCodeProposal,cbNameSIDCodePEmailTEdit, cbNameSIDCodePEmailTErase, cbtitleCodeMentor, cbNameEmailMentorEdit;
    TextField tfManualEdit;

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
        cbtitleCodeMentor =new ChoiceBox<>();
        vbConsultBtnN = new VBox();
        vbConsultBtnN.setSpacing(7);
        vbConsultBtnN.getChildren().addAll(btnNOrientMentor,cbtitleCodeMentor);

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
        cbTitleCodeProposal = new ChoiceBox<>();

        vbManualInsert.getChildren().addAll(lbManual, cbTitleCodeProposal,cbNameEmailMentor,hbBtnInsert);

        //Edit
        btnConfirmManualEdit = new Button("Edit");
        btnClearManualEdit = new Button("Clear");
        hbBtnManualEdit = new HBox( btnConfirmManualEdit, btnClearManualEdit);
        hbBtnManualEdit.setAlignment(Pos.CENTER);
        hbBtnManualEdit.setSpacing(6);
        tfManualEdit= new TextField();
        tfManualEdit.setMaxWidth(200);
        vbManualEdit = new VBox();
        vbManualEdit.setAlignment(Pos.CENTER);
        vbManualEdit.setSpacing(10);

        lbManualEdit = new Label("Choose the Proposal´s Mentor that you want to edit");
        lbManualEdit.setAlignment(Pos.CENTER);
        lbManualEdit.setId("lbTitle");
        lbManualEdit.setMinWidth(500);

        cbNameSIDCodePEmailTEdit = new ChoiceBox<>();
        cbNameEmailMentorEdit = new ChoiceBox<>();

        vbManualEdit.getChildren().addAll(lbManualEdit,cbNameSIDCodePEmailTEdit, cbNameEmailMentorEdit,tfManualEdit, hbBtnManualEdit);

        //ERASE
        lbManualErase = new Label("Choose the Student's Attribution that you want to erase");
        lbManualErase.setAlignment(Pos.CENTER);
        lbManualErase.setId("lbTitle");
        lbManualErase.setMinWidth(800);
        vbManualErase = new VBox();
        vbManualErase.setSpacing(5);
        vbManualErase.setAlignment(Pos.CENTER);
        cbNameSIDCodePEmailTErase = new ChoiceBox<>();
        btnEraseManual = new Button("Erase");
        vbManualErase.getChildren().addAll(lbManualErase, cbNameSIDCodePEmailTErase,btnEraseManual);

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
        if (facade.getState() != AppState.PHASE_FOUR) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
