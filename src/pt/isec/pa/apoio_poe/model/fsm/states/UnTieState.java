package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class UnTieState extends AppStateAdapter {
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
}
