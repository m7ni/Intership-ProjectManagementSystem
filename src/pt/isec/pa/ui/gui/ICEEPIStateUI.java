package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.ui.gui.utils.ToastMessage;

import java.io.File;

public class ICEEPIStateUI extends BorderPane {
    Facade facade;
    Button btnCSVPI,btnInternshipManagement, btnProjectManagement, btnSelfPropManagement;
    Button btnPrintAll;
    Label title;

    public ICEEPIStateUI(Facade facade) {
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnCSVPI = new Button("Load CSV File");
        btnCSVPI.setMinWidth(100);
        btnInternshipManagement = new Button("Internships Management");
        btnInternshipManagement.setMinWidth(100);
        btnProjectManagement = new Button("Projects Management");
        btnProjectManagement.setMinWidth(100);
        btnSelfPropManagement = new Button("Self-Proposed Projects/Internships Management");
        btnSelfPropManagement.setMinWidth(100);
        btnPrintAll = new Button("Print All");
        btnPrintAll.setMinWidth(100);
        title = new Label("PI Management");

        VBox VBox = new VBox(title,btnCSVPI,btnInternshipManagement,btnProjectManagement,btnSelfPropManagement,btnPrintAll);
        this.setCenter(VBox);
        VBox.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });
        btnCSVPI.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("File open...");
            fc.setInitialDirectory(new File("."));
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Drawing(*.text)","*.txt"));
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"));

            File hFile = fc.showOpenDialog(this.getScene().getWindow());

            ToastMessage.show(getScene().getWindow(), facade.upload(hFile));

        });
        btnInternshipManagement.setOnAction(actionEvent -> {
            facade.
        });
    }

    private void update() {
        if (facade.getState() != AppState.PONE_PI) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
