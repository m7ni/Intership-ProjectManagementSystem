package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.File;
import java.io.Serializable;

public class ICEEPIState extends AppStateAdapter implements Serializable {

    public ICEEPIState(AppContext context, AppData data) {
        super(context,data);
    }


    @Override
    public boolean upload(File file) {
        return data.uploadCSV(file, Types.PROPOSAL);
    }

    @Override
    public boolean back() {
        changeState(AppState.CHOOSE_PHASE_ONE);
        return true;
    }

    @Override
    public void goInternship() {
        changeState(AppState.PONE_INTERNSHIP);
    }

    @Override
    public void goProject() {
        changeState(AppState.PONE_PROJECT);
    }

    @Override
    public void goSelfProp() {
        changeState(AppState.PONE_SELFPROP);
    }

    @Override
    public String toString() {
        return "Phase ONE";
    }

    @Override
    public AppState getState() {
        return AppState.PONE_PI;
    }
}
