package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    public NBLS(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnSave = new Button("SAVE");
        btnSave.setMinWidth(100);
        btnLoad  = new Button("LOAD");
        btnLoad.setMinWidth(100);
        btnNext  = new Button("NEXT");
        btnNext.setMinWidth(100);
        btnBack  = new Button("BACK");
        btnBack.setMinWidth(100);

        HBox hBox = new HBox();

        this.getChildren().addAll(btnBack,btnSave,btnLoad,btnNext);
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
        if (facade.getState() == AppState.CHOOSE_PHASE_ONE || facade.getState() == AppState.UNTIE ) {
           btnBack.setVisible(false);
            btnNext.setVisible(true);
            return;
        }

        if (facade.getState() == AppState.PONE_PI || facade.getState() == AppState.PONE_STUDENT || facade.getState() == AppState.PONE_TEACHER || facade.getState() == AppState.PONE_STUDENT || facade.getState() == AppState.PONE_PROJECT || facade.getState() == AppState.PONE_SELFPROP || facade.getState() == AppState.PONE_INTERNSHIP || facade.getState() == AppState.PHASE_FIVE) {
            btnNext.setVisible(false);
            btnBack.setVisible(true);
            return;
        }
    }

}
