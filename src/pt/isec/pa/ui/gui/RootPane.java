package pt.isec.pa.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;
import pt.isec.pa.model.fsm.states.*;
import pt.isec.pa.model.fsm.states.UnTieState;
import pt.isec.pa.ui.gui.resources.CSSManager;
import pt.isec.pa.ui.gui.resources.ImageManager;

public class RootPane extends BorderPane {
    Facade facade;

    public RootPane(Facade facade) {
        this.facade = facade;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");
        StackPane stackPane = new StackPane(
            new ICEETeacherStateUI(facade),
            new ICEEPIStateUI(facade),
            new ICEEStudentStateUI(facade),
            new T1P1StateUI(facade),
            new T2P1StateUI(facade),
            new T3P1StateUI(facade),
            new ChoosePhaseOneUI(facade),
            new PhaseTwoStateUI(facade),
            new PhaseThreeStateUI(facade),
            new PhaseFourStateUI(facade),
            new PhaseFiveStateUI(facade),
            new UnTieStateUI(facade)
        );

        /*
        stackPane.setBackground(new Background(new BackgroundImage(
                ImageManager.getImage("fundo.png"),
                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1,1,true,true,true,false)
        )));

         */
        this.setBackground(new Background(new BackgroundFill(Constants.getGradientFundo(), CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(stackPane);

        this.setBottom(
                new NBLS(facade)
        );

    }

    private void registerHandlers() {

    }

    private void update() {

    }
}
