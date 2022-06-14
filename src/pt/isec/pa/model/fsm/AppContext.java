package pt.isec.pa.model.fsm;

import pt.isec.pa.model.data.*;
import pt.isec.pa.model.data.memento.IMemento;
import pt.isec.pa.model.data.memento.IOriginator;
import pt.isec.pa.model.data.memento.Memento;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.*;
import pt.isec.pa.model.fsm.states.ChoosePhaseOne;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppContext implements Serializable {
    static final long serialVersionUID = 100L;
    private AppData app;
    private IAppState state;

    public AppContext() {
        this.app = new AppData();
        this.state = new ChoosePhaseOne(this,app);
    }


    public void changeState(IAppState newState) {
        state = newState;
    }

    public boolean sameState() { return false; }

    public boolean next(Boolean block) {return state.next(block);}

    public boolean back() {
        return state.back();
    }

    public void teacher(){
        state.teacher();
    }

    public int getBranchProposalsDA() {
        return app.getBranchProposalsDA();
    }

    public int getAvailablePropNumber() {
        return app.getAvailablePropNumber();
    }

    public int getNotAvailablePropNumber() {
        return app.getNotAvailablePropNumber();
    }

    public int getBranchProposalsSI() {
        return app.getBranchProposalsSI();
    }

    public int getBranchProposalsRAS() {
        return app.getBranchProposalsRAS();
    }

    public void student(){
        state.student();
    }

    public void projectInternship(){
        state.PI();
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public void goInternship() {
        state.goInternship();
    }

    public void goProject() {
        state.goProject();
    }

    public void goSelfProp() {
        state.goSelfProp();
    }

    public ArrayList<Student> studentsWCandidature() {
        return app.studentsWCandidature();
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

    public boolean upload(File file) {
        return state.upload(file);
    }

    public boolean export() {
        return state.export();
    }

    /*
    public boolean setName(String name,String email){
        return state.set(name,email);
    }
    */
  /*  public boolean save(String filename){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(filename)))
        {
            oos.writeObject(app);
        } catch (Exception e) {
            System.err.println("Error saving data");
        }
        return false;
    }*/
/*
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

 */
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

    public HashMap<String, Proposals> getProposalsCombined() {
        return app.getProposalsCombined();
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

    public boolean automaticTwoP3Projects() {
        return app.automaticTwoP3Projects();
    }

    public List<Proposals> printFiltros(List<Filtros> filtros, AppState phase) {
        return app.printFiltros(filtros, phase);
    }

    public boolean blockPhaseOne() {
        return app.blockPhaseOne();
    }

    public boolean addStudent(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
        return app.addStudent(name, email, number, minor, branch, score, internship);
    }

    public boolean addTeacher(String name, String email) {
        return app.addTeacher(name, email);
    }

    public boolean addInternship(String idCode, long number, List<Branches> branch, String title, String local) {
        return app.addInternship(idCode, number, branch, title, local);
    }

    public boolean addProject(String idCode, long number, List<Branches> branch, String title, String tEmail) {
        return app.addProject(idCode, number, branch, title, tEmail);
    }

    public boolean addSelfProp(String idCode, long number, String title) {
        return app.addSelfProp(idCode, number, title);
    }

    public boolean addCandidature(String[] values) {
        return app.addCandidature(values);
    }

    public boolean removeType(Object obj, Types type) {
        return app.removeType(obj, type);
    }

    public boolean uploadCSV(File file, Types type) {
        return app.uploadCSV(file, type);
    }

    public boolean exportCSVP1(String nametxt) {
        return app.exportCSVP1(nametxt);
    }

    public boolean exportCSVP2(String nametxt) {
        return app.exportCSVP2(nametxt);
    }

    public boolean exportCSVP34(String nametxt, AppState State) {
        return app.exportCSVP34(nametxt, State);
    }

    public void setBlock(int phase) {
        app.setBlock(phase);
    }

    public boolean removeC(long studentNumber) {
        return app.removeC(studentNumber);
    }

    public boolean removeFA(long studentNumber) {
        return app.removeFA(studentNumber);
    }

    public boolean removeAllFA() {
        return app.removeAllFA();
    }

    public boolean removeMentor(String emailMentor) {
        return app.removeMentor(emailMentor);
    }

    public boolean editNameTeacher(String newName, String email) {
        return app.editNameTeacher(newName, email);
    }

    public boolean editEmailTeacher(String newEmail, String email) {
        return app.editEmailTeacher(newEmail, email);
    }

    public boolean editNameStudent(String newName, long number) {
        return app.editNameStudent(newName, number);
    }

    public boolean editEmailStudent(String newEmail, long number) {
        return app.editEmailStudent(newEmail, number);
    }

    public boolean editNumberStudent(long newNumber, long number) {
        return app.editNumberStudent(newNumber, number);
    }

    public boolean editApplicableInternshipStudent(long number) {
        return app.editApplicableInternshipStudent(number);
    }

    public boolean editCourseStudent(String newCourse, long number) {
        return app.editCourseStudent(newCourse, number);
    }

    public boolean editBranchStudent(String newBranch, long number) {
        return app.editBranchStudent(newBranch, number);
    }

    public boolean editScoreStudent(String newScore, long number) {
        return app.editScoreStudent(newScore, number);
    }

    public boolean editBranch(Proposals prop, List<String> branch) {
        return app.editBranch(prop, branch);
    }

    public boolean editTitle(Proposals prop, String title) {
        return app.editTitle(prop, title);
    }

    public boolean editLocal(Proposals prop, String local) {
        return app.editLocal(prop, local);
    }

    public boolean editTeacherProject(String email, Proposals prop) {
        return app.editTeacherProject(email, prop);
    }

    public boolean editNumberStudentProposals(long studentNumber,Proposals prop) {
        return app.editNumberStudentProposals(studentNumber, prop);
    }

    public boolean editID(Proposals prop, String newID) {
        return app.editID(prop, newID);
    }

    public boolean editCandidatures(String newCandidatures, String number) {
        return app.editCandidatures(newCandidatures, number);
    }

    public boolean editMentor(String newMentor, long number) {
        return app.editMentor(newMentor, number);
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

    public boolean studentsTie(long studentNumber) {
        return app.studentsTie(studentNumber);
    }

    public boolean setNameTeacher(String newName, String email) {
        return app.editNameTeacher(newName, email);
    }

    public boolean setEmailTeacher(String newEmail, String email) {
        return app.editEmailTeacher(newEmail, email);
    }

    public boolean setNameStudent(String newName, long number) {
        return app.editNameStudent(newName, number);
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

    public boolean setCandidatures(String values, String number) {
        return app.editCandidatures(values, number);
    }

    public boolean setMentor(String newMentor, long number) {
        return app.editMentor(newMentor, number);
    }

    public FinalAtribution getStudentFA(long number) {
        return app.getStudentFA(number);
    }

}
