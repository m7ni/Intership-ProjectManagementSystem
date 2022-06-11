package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.File;
import java.io.Serializable;

public class ICEETeacherState extends AppStateAdapter implements Serializable {

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
    public boolean upload(File file) {
        return data.uploadCSV(file, Types.TEACHER);
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

    @Override
    public String toString() {
        return "Phase ONE";
    }

    public boolean back() {
        changeState(AppState.CHOOSE_PHASE_ONE);
        return true;
    }

}
