package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.ui.text.UIText;

public class Main {
    public static void main(String[] args) {
        AppContext fsm = new AppContext();
        UIText ui = new UIText(fsm);
        ui.start();
    }
}
