package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.data.Types;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class T1P1State extends AppStateAdapter {

    public T1P1State(AppContext context, AppData data) {
        super(context, data);
    }

/*
    @Override
    public boolean insert(String idCode, long number, List<Branches> branch, String title, String local) {
        return data.addInternship(idCode, number, branch, title, local);
    }
*/
    @Override
    public boolean erase(String email) {
        return data.removeType(email, Types.INTERNSHIP);
    }

/*
    @Override
    public boolean set(String idCode, long number, String branch, String title, String local) {
        //return data.editInternship(name, email);
        return false;
    }
*/
    public AppState getState() {
        return AppState.PONE_INTERNSHIP;
    }

    @Override
    public boolean back() {
        changeState(AppState.PONE_PI);
        return true;
    }
}
