package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.Serializable;

public class ICEEStudentState extends AppStateAdapter implements Serializable {
    public ICEEStudentState(AppContext context, AppData data) {
        super(context,data);
    }


    @Override
    public boolean upload(String nameFile) {
        return data.uploadCSV(nameFile, Types.STUDENT);
    }

    @Override
    public boolean insert(String name, String email, Long number, String minor, String branch, double score,boolean internship) {
        return data.addStudent(name, email, number, minor, branch, score,internship);
    }

    @Override
    public boolean erase(Long number) {
        return data.removeType(number, Types.STUDENT);
    }

    public AppState getState() {
        return AppState.PONE_STUDENT;
    }

    public boolean back() {
        changeState(AppState.CHOOSE_PHASE_ONE);
        return true;
    }
}
