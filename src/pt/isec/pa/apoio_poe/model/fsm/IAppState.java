package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Filtros;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposals;

import java.util.List;

public interface IAppState  {
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
}
