package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

public class T3P1State extends AppStateAdapter {

    public T3P1State(AppContext context, AppData data) {
        super(context, data);

    }


    @Override
    public boolean insert(String idCode, long number, String title) {
        return data.addSelfProp(idCode, number, title);
    }

    @Override
    public boolean erase(String email) {
        return data.removeType(email, Types.SELPROP );
    }

/*
    @Override
    public boolean set(String idCode, long number, String title) {
        //return data.editSelfProp(idCode, number, title);
        return false;
    }
*/
    public AppState getState() {
        return AppState.PONE_SELFPROP;
    }

    @Override
    public boolean back() {
        changeState(AppState.PONE_PI);
        return true;
    }
}
