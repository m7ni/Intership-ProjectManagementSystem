package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.data.personel.Student;
import pt.isec.pa.apoio_poe.model.data.personel.Teacher;
import pt.isec.pa.apoio_poe.model.data.proposals.*;
import pt.isec.pa.apoio_poe.model.fsm.states.ChoosePhaseOne;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class AppContext implements Serializable {
    static final long serialVersionUID = 100L;
    private AppData app;
    transient private IAppState state;
   // private MemoryManager mm;
    public AppContext() {
        this.app = new AppData();
        this.state = new ChoosePhaseOne(this,app);
        //this.mm = new MemoryManager();
    }

    public void changeState(IAppState newState) {
        state = newState;
    }

    public boolean next(Boolean block) {return state.next(block);}

    public boolean back() {
        return state.back();
    }

    public void teacher(){
        state.teacher();
    }
    public void student(){
        state.student();
    }
    public void projectInternship(){
        state.PI();
    }


    public AppState getState() {
        if (state == null)
            return null;
        return state.getState();
    }

    public boolean insertStudent(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
       return state.insert(name, email, number, minor, branch, score,internship);
    }
    public boolean insertTeacher(String name, String email) {
        return state.insert( name, email);
    }
    public boolean insertPI(String idCode, long number, List<String> branch, String title, String local) {
        return state.insert(idCode, number, branch, title, local);
    }

    public boolean insertSelpProp(String idCode, long number, String title) {
        return state.insert(idCode, number, title);
    }

    public boolean remove(Long number) {
      return state.erase(number);
    }
    public boolean remove(String email) {
        return state.erase(email);
    }

    public void enterAPP() {
        state.enterAPP();
    }

    public boolean upload(String nameFile) {
        return state.upload(nameFile);
    }

    public boolean export() {
        return state.export();
    }

    /*
    public boolean setName(String name,String email){
        return state.set(name,email);
    }
    */
    public boolean save(String filename){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(filename)))
        {
            oos.writeObject(app);
        } catch (Exception e) {
            System.err.println("Error saving data");
        }
        return false;
    }

    public boolean load(String filename){
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(filename)))
        {
            app =(AppData) ois.readObject();

        } catch (Exception e) {
        e.printStackTrace();
            System.err.println("Error loading data");
        }

        return false;
    }
    public StateBlock getBlock(int phase){return app.getBlock(phase);}


    public List<Proposals> printFiltros(List<Filtros> filtros){
        return state.printFiltro(filtros);
    }

    public HashMap<Long, Student> getStudents() {
        return app.getStudents();
    }

    public HashMap<String, Teacher> getTeachers() {
        return app.getTeachers();
    }

    public HashMap<String, SelfProposed> getSelfProp() {
        return app.getSelfProp();
    }

    public Proposals getTieProposal() {
        return app.getTieProposal();
    }

    public List<Student> getStudentsTie() {
        return app.getStudentsTie();
    }

    public HashMap<String, Project> getProjects() {
        return app.getProjects();
    }

    public HashMap<String, Internship> getInternships() {
        return app.getInternships();
    }

    public HashMap<Long, List<String>> getCandidatures() {
        return app.getCandidatures();
    }

    public List<FinalAtribution> getFA() {
        return app.getFA();
    }

    public boolean manualAtribution(String[] values, List<Long> studentsNumber) {
        return app.manualAtribution(values, studentsNumber);
    }

    public void automaticOneP3() {
        app.automaticOneP3();
    }

    public boolean automaticTwoP3() {
        return state.unTie();
    }

    public boolean blockPhaseThree() {
        return app.blockPhaseThree();
    }

    public void tieFA(Proposals p, Student chooseStudent) {
        app.tieFA(p, chooseStudent);
    }

    public void automaticMentorPhaseFour() {
        app.automaticMentorPhaseFour();
    }

    public List<Student> studentsMentor() {
        return app.studentsMentor();
    }

    public List<Student> studentsNoMentor() {
        return app.studentsNoMentor();
    }

    public boolean mentorAttribution(String[] values) {
        return app.mentorAttribution(values);
    }

    public List<Student> noProposalCandidature() {
        return app.noProposalCandidature();
    }

    public List<Integer> mentorByTeacher(String email) {
        return app.mentorByTeacher(email);
    }

    public List<Student> studentsWOPropAssociated() {
        return app.studentsWOPropAssociated();
    }

    public List<Student> studentsSelfPropCandidature() {
        return app.studentsSelfPropCandidature();
    }

    public List<Student> studentsWOCandidature() {
        return app.studentsWOCandidature();
    }


    public boolean setNumberStudent(long newNumber, long number) {
        return app.editNumberStudent(newNumber, number);
    }

    public boolean setApplicableInternshipStudent(long number) {
        return app.editApplicableInternshipStudent(number);
    }

    public boolean setCourseStudent(String newCourse, long number) {
        return app.editCourseStudent(newCourse, number);
    }

    public boolean setBranchStudent(String newBranch, long number) {
        return app.editBranchStudent(newBranch, number);
    }

    public boolean setScoreStudent(String newScore, long number) {
        return app.editScoreStudent(newScore, number);
    }

}
