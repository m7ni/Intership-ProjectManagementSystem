package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.data.Types;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class ICEETeacherState extends AppStateAdapter {

    public ICEETeacherState(AppContext context, AppData data) {
        super(context, data);

    }

    @Override
    public boolean insert(String name, String email) {
        return data.addTeacher(name, email);
    }

    @Override
    public boolean erase(String email) {
        return data.removeType(email, Types.TEACHER);
    }

    @Override
    public boolean upload(String nameFile) {
        return data.uploadCSV(nameFile, Types.TEACHER);
    }

    /*
    @Override
    public boolean set(String name, String email) {
        return data.editTeacherName(name, email);
    }
*/


    public AppState getState() {
        return AppState.PONE_TEACHER;
    }


    public boolean back() {
        changeState(AppState.CHOOSE_PHASE_ONE);
        return true;
    }

}
