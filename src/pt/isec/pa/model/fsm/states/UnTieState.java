package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.Serializable;

public class UnTieState extends AppStateAdapter implements Serializable {
    public UnTieState(AppContext context, AppData data) {
        super(context, data);
    }

    @Override
    public boolean back() {
        changeState(AppState.PHASE_THREE);
        return true;
    }
    public AppState getState() {
        return AppState.UNTIE;
    }
    public boolean sameState(){
        changeState(AppState.UNTIE);
        return true;
    }
    @Override
    public String toString() {
        return "UNTIE";
    }
}
