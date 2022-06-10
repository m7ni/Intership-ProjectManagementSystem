package pt.isec.pa.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.fsm.AppState;


public class ChoosePhaseOneUI extends BorderPane {
    Facade facade;
    Button btnStudent,btnTeacher,btnPI;
    Label title;
    public ChoosePhaseOneUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnStudent = new Button("Student Edition");
        btnStudent.setMinWidth(100);
        btnTeacher  = new Button("Teacher Edition");
        btnTeacher.setMinWidth(100);
        btnPI  = new Button("Proposal Edition");
        btnTeacher.setMinWidth(100);
        title = new Label("PHASE ONE MENU");
        title.setMinWidth(100);
        title.setMinHeight(30);
        title.setId("lbTitle");
        VBox Vbox = new VBox(title,btnStudent,btnTeacher,btnPI);
        Vbox.setSpacing(4);
        this.setCenter(Vbox);
        Vbox.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });
        btnStudent.setOnAction( event -> {
            facade.student();
        });

        btnTeacher.setOnAction( event -> {
            facade.teacher();
        });

        btnPI.setOnAction( event -> {
            facade.projectInternship();
        });
    }

    private void update() {
        if (facade.getState() != AppState.CHOOSE_PHASE_ONE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
