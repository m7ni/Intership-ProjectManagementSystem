package pt.isec.pa.model;

import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.ui.text.UIText;

public class Main {
    public static void main(String[] args) {
        AppContext fsm = new AppContext();
        Facade fc = new Facade(fsm);
        UIText ui = new UIText(fc);
        ui.start();
    }
}
