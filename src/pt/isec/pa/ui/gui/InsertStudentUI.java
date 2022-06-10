package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.fsm.AppState;

public class InsertStudentUI extends BorderPane {
    Facade facade;
    Button btnUploadCSV, btnInsert, btnConsult, btnErase, btnEdit;
    Label title;

    public InsertStudentUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnUploadCSV = new Button("Uwer34t5y6u7iop9V");
        btnUploadCSV.setMinWidth(100);
        btnInsert = new Button("Insert Teacher");
        btnInsert.setMinWidth(100);
        btnConsult = new Button("Consult Teachers");
        btnConsult.setMinWidth(100);
        btnErase = new Button("Erase Teacher");
        btnErase.setMinWidth(100);
        btnEdit = new Button("Edit Teacher");
        btnEdit.setMinWidth(100);
        title = new Label("Teacher Management");

        VBox VBox = new VBox(title,btnUploadCSV, btnInsert, btnConsult, btnErase, btnEdit);
        this.setCenter(VBox);
        VBox.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {
        if (facade.getState() != AppState.PONE_STUDENT) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

}
