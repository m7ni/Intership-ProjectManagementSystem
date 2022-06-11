package pt.isec.pa.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.proposals.Internship;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.fsm.AppState;

public class PrintAllPhaseOne extends Stage {
    Facade facade;
    ListView lvConsult;
    VBox consult;

    public PrintAllPhaseOne(Window parent, Facade facade) {
        this.facade = facade;
        createViews();
        update();
        initOwner(parent);

        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    private void createViews() {
        VBox vBox = new VBox();

        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);

        VBox verticalBox = new VBox();
        verticalBox.setSpacing(20);
        verticalBox.setAlignment(Pos.CENTER_LEFT);

        consult = new VBox();
        consult.setAlignment(Pos.CENTER);
        lvConsult = new ListView();
        lvConsult.setMinWidth(800);
        lvConsult.setMinHeight(500);
        consult.getChildren().add(lvConsult);
        lvConsult.setEditable(false);

        verticalBox.getChildren().addAll(consult);
        hBox.getChildren().addAll(verticalBox);

        vBox.getChildren().addAll(hBox);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        Scene scene = new Scene(vBox, 800,500);
        this.setTitle("Print of All the Proposals");
        this.setScene(scene);
        setResizable(false);

    }

    private void update() {
        lvConsult.getItems().clear();
        for(Proposals p : facade.getProposalsCombined().values()) {
            lvConsult.getItems().add(p.toString());
        }
    }

}
