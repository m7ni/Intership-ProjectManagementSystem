package pt.isec.pa.model.fsm;

import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.proposals.Proposals;

import java.io.Serializable;
import java.util.List;

public interface IAppState extends Serializable {
    void student();
    void teacher();
    void PI();
    boolean next(Boolean block);
    boolean back();
    void enterAPP();

    boolean erase(Long number);
    boolean erase(String email);
    boolean sameState();
    boolean upload(String nameFile);
    AppState getState();
    String toString();
    boolean export();

    boolean unTie();
    List<Proposals> printFiltro(List<Filtros> filtros);

    /*
    boolean set(String name,String email);
    boolean set(Boolean bl,String email);
    boolean set(Double db,String email);
    boolean set(Long lg,String email);
    */

    boolean insert(String name, String email);
    boolean insert(String name, String email, Long number, String minor, String branch, double score, boolean internship);
    boolean insert(String idCode, long number, List<String> branch, String title, String local);
    boolean insert(String idCode, long number, String title);

    String printCandidatures();


    void setName(String newName, String email);
    void setEmail(String newEmail, String email);   //TODO Mudar para só setTeacher

    void setName(String newName, long number);
    void setApplicableInternship(long number);
    void setNumber(long newNumber, long number);
    void setCourse(String newCourse, long number);
    void setBranch(String newBranch, long number);
    void setScore(String newScore, long number);
}
