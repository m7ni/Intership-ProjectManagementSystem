package pt.isec.pa.model.fsm;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

public class Constants {

    private Constants() {
    }

    public static String fonte = "Roboto";
    public static double fonteNormalTam = 13;
    public static Font getNormalFont() {
        return new Font(fonte,fonteNormalTam);
    }
   // public static Font getTitleFont(double size) { return FontManager.loadFont("Midorima-PersonalUse-Regular.ttf",size); }


    public static Color corFundo1 = Color.web("#cc99ff");
    public static Color corFundo2 = Color.web("#871bf3");

    public static LinearGradient getGradientFundo() {
        Stop[] stops = new Stop[] {
                new Stop(0, corFundo1),
                new Stop(1, corFundo2)
        };

        return new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    }

}
