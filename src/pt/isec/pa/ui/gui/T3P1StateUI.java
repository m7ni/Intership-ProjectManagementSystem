package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.proposals.SelfProposed;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;

public class T3P1StateUI  extends BorderPane {
    Facade facade;
    TextField id,title, titleEdit;
    Button btnErase, btnConfirmInsert, btnClearInsert, btnConfirmEdit, btnClearEdit;
    VBox vbInsert, vbErase, vbConsult, vbEdit;
    HBox hbBtnInsertH, hbBtnEditH;
    Tab tabInsert, tabConsult, tabErase, tabEdit;
    ChoiceBox cbAllStudents, cbTitleCodeSelfProp;
    ListView lvConsult;
    Label lbEraseL, lbEditL,lbTitle;
    ChoiceBox CbcodeSelfProp;
    boolean flag = false;
    String titleCodeSelfPropStringEdit = null;
    long studentNumber = -1;

    public T3P1StateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnConfirmInsert = new Button("Insert");
        btnClearInsert = new Button("Clear");
        hbBtnInsertH = new HBox( btnConfirmInsert, btnClearInsert);
        hbBtnInsertH.setAlignment(Pos.CENTER);
        hbBtnInsertH.setSpacing(5);

        vbInsert = new VBox();
        vbInsert.setAlignment(Pos.CENTER);
        vbInsert.setSpacing(10);

        //erase
        vbErase = new VBox();
        vbErase.setAlignment(Pos.CENTER);

        btnErase = new Button("Confirm");
        btnErase.setAlignment(Pos.CENTER_RIGHT);

        lbEraseL = new Label("Choose the Self-Proposed Internship/Project that you want to erase");
        lbEraseL.setId("lbTitle");
        lbEraseL.setMinWidth(700);
        lbEraseL.setAlignment(Pos.CENTER);

        CbcodeSelfProp = new ChoiceBox<>();
        vbErase.getChildren().addAll(lbEraseL, CbcodeSelfProp, btnErase);


        lbTitle = new Label("Insert the SelfPop Data");
        lbTitle.setId("lbTitle");
        lbTitle.setMinWidth(300);
        lbTitle.setAlignment(Pos.CENTER);
        id = new TextField();
        id.setId("tfInsert");
        id.setFont(Constants.getNormalFont());
        id.setPromptText("Id");
        id.setMaxWidth(150);

        title = new TextField();
        title.setId("tfInsert");
        title.setFont(Constants.getNormalFont());
        title.setPromptText("Title");
        title.setMaxWidth(150);
        cbAllStudents = new ChoiceBox<>();

        vbConsult = new VBox();
        vbConsult.setAlignment(Pos.CENTER);
        lvConsult = new ListView();
        lvConsult.setEditable(false);
        vbConsult.getChildren().add(lvConsult);

        //Edit Project


        btnConfirmEdit = new Button("Edit");
        btnClearEdit = new Button("Clear");
        hbBtnEditH = new HBox( btnConfirmEdit, btnClearEdit);
        hbBtnEditH.setAlignment(Pos.CENTER);
        hbBtnEditH.setSpacing(5);

        vbEdit = new VBox();
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.setSpacing(10);

        lbEditL = new Label("Choose the Self-Proposed Proposal that you want to edit");
        lbEditL.setId("lbTitle");
        lbEditL.setMinWidth(600);
        lbEditL.setAlignment(Pos.CENTER);
        cbTitleCodeSelfProp = new ChoiceBox<>();

        titleEdit = new TextField();
        titleEdit.setId("tfInsert");
        titleEdit.setFont(Constants.getNormalFont());
        titleEdit.setPromptText("Title");
        titleEdit.setMaxWidth(150);

        vbEdit.getChildren().addAll(lbEditL, cbTitleCodeSelfProp,titleEdit, hbBtnEditH);


        tabInsert = new Tab("Insert SelProp" , vbInsert);
        tabInsert.setClosable(false);
        tabConsult = new Tab("Consult SelProp" , vbConsult);
        tabConsult.setClosable(false);
        tabErase = new Tab("Erase SelProp" , vbErase);
        tabErase.setClosable(false);
        tabEdit = new Tab("Edit SelProp" , vbEdit);
        tabEdit.setClosable(false);


        vbInsert.getChildren().addAll(lbTitle,id,title, cbAllStudents, hbBtnInsertH);
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

        btnClearInsert.setOnAction(actionEvent -> {
            clearInsert();
        });
        btnClearEdit.setOnAction(actionEvent -> {
            clearEdit();
        });
        btnErase.setOnAction(actionEvent -> {
            if(CbcodeSelfProp.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" +CbcodeSelfProp.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                facade.remove(values[1]);
            }

        });

        btnConfirmInsert.setOnAction(actionEvent -> {

            if(id.getText().isBlank() || title.getText().isBlank() || cbAllStudents.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are empty fields");
                alert.showAndWait();
                return;
            }

            if(cbAllStudents.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbAllStudents.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                studentNumber = Long.parseLong(values[1]);
            }

            if(!facade.addSelfProp(id.getText(),studentNumber, title.getText())){
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

            if(titleEdit.getText().isBlank() && cbTitleCodeSelfProp.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no fields to edit");
                alert.showAndWait();
                return;
            }

            if(cbTitleCodeSelfProp.getValue() != null) {
                Label labelresponse = new Label();
                labelresponse.setText("" + cbTitleCodeSelfProp.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                titleCodeSelfPropStringEdit = values[1];
            }

            if(!titleEdit.getText().isBlank()) {
                if(!facade.editTitle(facade.getSelfProp().get(titleCodeSelfPropStringEdit), titleEdit.getText()))
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
        id.setText("");
        title.setText("");
        cbAllStudents.setValue(null);
    }

    void clearEdit(){
        cbTitleCodeSelfProp.setValue(null);
        titleEdit.setText("");
    }
    private void update() {

        if(facade.getBlock(1) == StateBlock.BLOCKED){
            tabInsert.setDisable(true);
            tabErase.setDisable(true);
            tabEdit.setDisable(true);
        }

        cbAllStudents.getItems().clear();
        for(Student s : facade.getStudents().values()) {
            cbAllStudents.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }

        CbcodeSelfProp.getItems().clear();
        lvConsult.getItems().clear();
        cbTitleCodeSelfProp.getItems().clear();
        for(SelfProposed sp : facade.getSelfProp().values()) {
            CbcodeSelfProp.getItems().add(sp.getTitle()+","+sp.getIdCode());
            lvConsult.getItems().add(sp.toString());
            cbTitleCodeSelfProp.getItems().add(sp.getTitle()+","+sp.getIdCode());
        }

        if (facade.getState() != AppState.PONE_SELFPROP) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
