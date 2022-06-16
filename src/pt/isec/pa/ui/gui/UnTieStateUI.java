package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.fsm.AppState;

public class UnTieStateUI  extends BorderPane {
    Facade facade;
    VBox vbUntie;
    HBox hbStudentNumberTie;
    Label lbTitle, lbTIE, lbStudentNumberTie;
    ChoiceBox cbStudents;
    Button btnConfirm;



    public UnTieStateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
        vbUntie = new VBox();
        vbUntie.setSpacing(4);
        vbUntie.setAlignment(Pos.CENTER);
        lbTitle = new Label("Select the Student that you chose for the Proposal");
        lbTitle.setAlignment(Pos.CENTER);
        lbTitle.setId("lbTitle");
        lbTitle.setMinWidth(500);
        lbTIE  = new Label();
        lbTIE.setAlignment(Pos.CENTER);
        lbTIE.setId("lbTitle");
        lbTIE.setId("tfInsert");
        lbTIE.setMinWidth(300);
        btnConfirm = new Button("Confirm");
        cbStudents = new ChoiceBox<>();

        lbStudentNumberTie = new Label("Student\t");
        lbStudentNumberTie.setAlignment(Pos.CENTER_LEFT);

        hbStudentNumberTie = new HBox(lbStudentNumberTie, cbStudents);

        hbStudentNumberTie.setAlignment(Pos.CENTER);
        hbStudentNumberTie.setId(  "hBoxChoice");

        vbUntie.getChildren().addAll(lbTitle, lbTIE,hbStudentNumberTie,btnConfirm);
        this.setCenter(vbUntie);
    }


    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });
        btnConfirm.setOnAction(actionEvent -> {
            if (cbStudents.getValue() != null) {

                Label labelresponse = new Label();


                labelresponse.setText("" + cbStudents.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);

                if (!facade.studentsTie(Long.parseLong(values[1])))
                    return;

                facade.tieFA(facade.getTieProposal(), facade.getStudents().get(Long.parseLong(values[1])));


                facade.back();
            }
        });
    }

    private void update() {
        if(facade.getStudentsTie()!=null){
            cbStudents.getItems().clear();
            lbTIE.setText("");
            lbTIE.setText(facade.getTieProposal().getTitle()+" "+facade.getTieProposal().getIdCode());
            for(Student s : facade.getStudentsTie())
                cbStudents.getItems().add(s.getName()+","+s.getStudentNumber().toString());
        }




        if (facade.getState() != AppState.UNTIE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
