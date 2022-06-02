package pt.isec.pa.model;

import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.ui.text.UIText;

public class Main {
    public static void main(String[] args) {
        AppContext fsm = new AppContext();
        UIText ui = new UIText(fsm);
        ui.start();
    }
}
