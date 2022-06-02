package pt.isec.pa.model.fsm;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.proposals.Proposals;

import java.io.Serializable;
import java.util.List;

public class AppStateAdapter implements IAppState, Serializable {
    protected AppData data;
    protected AppContext context;
    static final long serialVersionUID = 100L;

    @Override
    public String printCandidatures() {
        return null;
    }

    public AppStateAdapter(AppContext context, AppData data) {
        this.data = data;
        this.context = context;
    }


    @Override
    public List<Proposals> printFiltro(List<Filtros> filtros) {
        return null;
    }
    protected void changeState(AppState newState) {
        context.changeState(newState.createState(context,data));
    }

    @Override
    public boolean next(Boolean block) {
        return false;
    }

    @Override
    public boolean sameState() {
        return false;
    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void enterAPP() {

    }

    @Override
    public boolean export() {
        return false;
    }

    /*
        @Override
        public boolean set(String name, String email) {
            return false;
        }

        @Override
        public boolean set(Boolean bl, String email) {
            return false;
        }

        @Override
        public boolean set(Double db, String email) {
            return false;
        }

        @Override
        public boolean set(Long lg, String email) {
            return false;
        }
    */
    @Override
    public AppState getState() {
        return null;
    }

    public AppStateAdapter() {
        super();
    }

    @Override
    public boolean erase(Long number) {
        return false;
    }

    @Override
    public boolean erase(String email) {
        return false;
    }

    @Override
    public boolean insert(String name, String email) {
        return false;
    }

    @Override
    public boolean insert(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
        return false;
    }

    @Override
    public boolean insert(String idCode, long number, List<String> branch, String title, String local) {
        return false;
    }


    @Override
    public boolean insert(String idCode, long number, String title) {
        return false;
    }

    @Override
    public boolean unTie() {
        return false;
    }

    @Override
    public void student() {

    }

    @Override
    public boolean upload(String nameFile) {
        return false;
    }

    @Override
    public void teacher() {

    }

    @Override
    public void PI() {

    }

    public void setName(String newName, String email) {

    }

    public void setEmail(String newEmail, String email)   //TODO Mudar para só setTeacher
    {

    }

    public void setName(String newName, long number) {}
    public void setApplicableInternship(long number) {}
    public void setNumber(long newNumber, long number) {}
    public void setCourse(String newCourse, long number) {}
    public void setBranch(String newBranch, long number) {}
    public void setScore(String newScore, long number) {}

}
