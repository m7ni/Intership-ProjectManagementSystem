package pt.isec.pa.model;

import javafx.application.Application;
import pt.isec.pa.ui.gui.MainJFX;


public class Main {
   /* public static void main(String[] args) {
        AppContext fsm = new AppContext();
        Facade fc = new Facade(fsm);
        UIText ui = new UIText(fc);
        ui.start();
    }

    */
   public static void main(String[] args) {
       Application.launch(MainJFX.class,args);
   }
}
