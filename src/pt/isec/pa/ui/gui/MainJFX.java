package pt.isec.pa.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.fsm.AppContext;

public class MainJFX extends Application {
    Facade facade;

    @Override
    public void init() throws Exception {
        super.init();
        facade = new Facade(new AppContext()); // here or in the constructor
    }

    @Override
    public void start(Stage stage) throws Exception {
        RootPane root = new RootPane(facade);
        Scene scene = new Scene(root,1200,900);
        stage.setScene(scene);
        stage.setTitle("APP MANAGEMENT");
        stage.setMinWidth(700);
        stage.setMinHeight(400);
        stage.show();
    }
}
