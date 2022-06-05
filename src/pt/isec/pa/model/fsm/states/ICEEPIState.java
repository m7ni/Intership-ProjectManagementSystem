package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.Serializable;

public class ICEEPIState extends AppStateAdapter implements Serializable {

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
