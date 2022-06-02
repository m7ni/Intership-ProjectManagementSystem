package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.data.Types;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class T2P1State extends AppStateAdapter {
    public T2P1State(AppContext context, AppData data) {
        super(context, data);

    }

/*
    @Override
    public boolean insert(String idCode, long number, List<String> branch, String title, String tEmail) {
        return data.addProject(idCode, number, branch, title, tEmail);
    }
*/
    @Override
    public boolean erase(String email) {
        return data.removeType(email, Types.PROJECT);
    }

/*
    @Override
    public boolean set(String idCode, long number, String branch, String title, String tEmail) {
        //return data.editProject(name, email);
        return false;
    }*/

    public AppState getState() {
        return AppState.PONE_PROJECT;
    }

    @Override
    public boolean back() {
        changeState(AppState.PONE_PI);
        return true;
    }
}
