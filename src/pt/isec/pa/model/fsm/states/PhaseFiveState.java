package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

public class PhaseFiveState extends AppStateAdapter {

    public PhaseFiveState(AppContext context, AppData data) {
        super(context, data);

    }


    public AppState getState() {
        return AppState.PHASE_FIVE;
    }

    @Override
    public boolean back() {

        changeState(AppState.PHASE_FOUR);
        return true;
    }
}
