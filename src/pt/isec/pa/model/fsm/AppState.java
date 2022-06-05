package pt.isec.pa.model.fsm;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.fsm.states.*;
import pt.isec.pa.model.fsm.states.*;

public enum AppState {
    CHOOSE_PHASE_ONE,
    PONE_TEACHER,
    PONE_PI,
    PONE_STUDENT,
    PONE_INTERNSHIP,
    PONE_PROJECT,
    PONE_SELFPROP,
    PHASE_TWO,
    PHASE_THREE,
    UNTIE,
    PHASE_FOUR,
    PHASE_FIVE;

    public IAppState createState(AppContext context, AppData data) {
        return switch (this) {
            case PONE_TEACHER->new ICEETeacherState(context,data);
            case PONE_PI->new ICEEPIState(context,data);
            case PONE_STUDENT->new ICEEStudentState(context,data);
            case PONE_INTERNSHIP -> new T1P1State(context,data);
            case PONE_PROJECT -> new T2P1State(context,data);
            case PONE_SELFPROP -> new T3P1State(context,data);
            case CHOOSE_PHASE_ONE -> new ChoosePhaseOne(context, data);
            case PHASE_TWO -> new PhaseTwoState(context, data);
            case PHASE_THREE -> new PhaseThreeState(context, data);
            case PHASE_FOUR -> new PhaseFourState(context, data);
            case PHASE_FIVE -> new PhaseFiveState(context, data);
            case UNTIE -> new  UnTieState(context,data);
        };
    }
}
