package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

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
