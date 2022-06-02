package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.data.Types;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class ICEEPIState extends AppStateAdapter {

    public ICEEPIState(AppContext context, AppData data) {
        super(context,data);
    }


    @Override
    public boolean upload(String namFile) {
        return data.uploadCSV(namFile, Types.PROPOSAL);
    }

    @Override
    public boolean back() {
        changeState(AppState.CHOOSE_PHASE_ONE);
        return true;
    }


    @Override
    public AppState getState() {
        return AppState.PONE_PI;
    }
}
