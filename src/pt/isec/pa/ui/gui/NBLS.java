package pt.isec.pa.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.states.*;

import java.io.File;


public class NBLS extends HBox {
    Facade facade;
    Button btnSave,btnLoad,btnNext,btnBack;
    Label title;

    public NBLS(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        HBox hb = new HBox();
        title = new Label();
        title.setAlignment(Pos.BASELINE_LEFT);
        title.setId("lbTitle");
        hb.getChildren().add(title);
        hb.setPadding(new Insets(10,50,0,0));
        btnSave = new Button("SAVE");
        btnSave.setMinWidth(100);
        btnLoad  = new Button("LOAD");
        btnLoad.setMinWidth(100);
        btnNext  = new Button("NEXT");
        btnNext.setMinWidth(100);
        btnBack  = new Button("BACK");
        btnBack.setMinWidth(100);

        HBox hBox = new HBox();

        this.getChildren().addAll(hb,btnBack,btnSave,btnLoad,btnNext);
        this.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });

        btnBack.setOnAction(actionEvent -> {
           facade.back();
        });

        btnNext.setOnAction(actionEvent -> {
            int state = 0;
            switch(facade.getState()) {
                case CHOOSE_PHASE_ONE -> state = 1;
                case PHASE_TWO -> state = 2;
                case PHASE_THREE -> state = 3;
                case PHASE_FOUR -> state = 4;
                case PHASE_FIVE -> state = 5;
            }
            if(facade.getBlock(state)== StateBlock.UNLOCKED) {
                new BlockUI(getScene().getWindow(), facade);
            }
            else
                facade.next(false);
        });

        btnSave.setOnAction(actionEvent -> {
            facade.save();
        });
    }

    private void update() {
        title.setText(facade.toString());
        btnNext.setVisible(true);
        btnBack.setVisible(true);
        switch (facade.getState()) {
            case CHOOSE_PHASE_ONE, UNTIE -> btnBack.setVisible(false);
            case PONE_PI, PONE_STUDENT, PONE_TEACHER, PONE_INTERNSHIP, PONE_PROJECT, PONE_SELFPROP, PHASE_FIVE -> btnNext.setVisible(false);
        }
    }

}
