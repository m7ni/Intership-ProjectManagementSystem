package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.data.Types;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class ICEEStudentState extends AppStateAdapter {
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
