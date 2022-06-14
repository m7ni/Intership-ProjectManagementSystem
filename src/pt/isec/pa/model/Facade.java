package pt.isec.pa.model;

import pt.isec.pa.model.data.Branches;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.data.memento.CareTaker;
import pt.isec.pa.model.data.memento.IMemento;
import pt.isec.pa.model.data.memento.IOriginator;
import pt.isec.pa.model.data.memento.Memento;
import pt.isec.pa.model.memory.MemoryManager;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.*;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.IAppState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Facade implements Serializable, IOriginator {
    private AppContext context;
    private CareTaker ct;
    private MemoryManager mm;
    PropertyChangeSupport pcs;

    public int getBranchProposalsDA() {
        return context.getBranchProposalsDA();
    }

    public int getBranchProposalsSI() {
        return context.getBranchProposalsSI();
    }

    public int getBranchProposalsRAS() {
        return context.getBranchProposalsRAS();
    }

    public int getAvailablePropNumber() {
        return context.getAvailablePropNumber();
    }

    public int getNotAvailablePropNumber() {
        return context.getNotAvailablePropNumber();
    }

    public Facade(AppContext context) {
        this.context = context;
        mm = new MemoryManager();
        ct = new CareTaker(this);
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void changeState(IAppState newState) {
        context.changeState(newState);
        pcs.firePropertyChange(null,null,null);
    }

    public boolean sameState() {
        var ret = context.sameState();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean next(Boolean block) {
        var ret = context.next(block);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public void goInternship() {
        context.goInternship();
        pcs.firePropertyChange(null,null,null);
    }

    public void goProject() {
        context.goProject();
        pcs.firePropertyChange(null,null,null);
    }

    public void goSelfProp() {
        context.goSelfProp();
        pcs.firePropertyChange(null,null,null);
    }

    public boolean back() {
        var ret = context.back();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public void teacher() {
        context.teacher();
        pcs.firePropertyChange(null,null,null);
    }

    public void student() {
        context.student();
        pcs.firePropertyChange(null,null,null);
    }

    public void projectInternship() {
        context.projectInternship();
        pcs.firePropertyChange(null,null,null);
    }

    public AppState getState() {
        return context.getState();
    }

    public HashMap<String, Proposals> getProposalsCombined() {
        var ret = context.getProposalsCombined();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean insertStudent(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
        var ret = context.insertStudent(name, email, number, minor, branch, score, internship);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean insertTeacher(String name, String email) {
        var ret = context.insertTeacher(name, email);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public ArrayList<Student> studentsWCandidature() {
        return context.studentsWCandidature();
    }

    public boolean insertPI(String idCode, long number, List<String> branch, String title, String local) {
        var ret = context.insertPI(idCode, number, branch, title, local);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean automaticTwoP3Projects() {
        var ret = context.automaticTwoP3Projects();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public List<Proposals> printFiltros(List<Filtros> filtros, AppState phase) {

        return context.printFiltros(filtros, phase);
    }

    public boolean blockPhaseOne() {
        var ret = context.blockPhaseOne();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean addStudent(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
        var ret = context.addStudent(name, email, number, minor, branch, score, internship);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean addTeacher(String name, String email) {
        var ret = context.addTeacher(name, email);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean addInternship(String idCode, long number, List<Branches> branch, String title, String local) {
        var ret =  context.addInternship(idCode, number, branch, title, local);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean addProject(String idCode, long number, List<Branches> branch, String title, String tEmail) {
        var ret = context.addProject(idCode, number, branch, title, tEmail);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean addSelfProp(String idCode, long number, String title) {
        var ret = context.addSelfProp(idCode, number, title);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    @Override
    public String toString() {
        return context.toString();
    }

    public boolean addCandidature(String[] values) {
        var ret = context.addCandidature(values);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean removeType(Object obj, Types type) {
        var ret = context.removeType(obj, type);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean uploadCSV(File file, Types type) {
        return context.uploadCSV(file, type);
    }

    public boolean exportCSVP1(String nametxt) {
        return context.exportCSVP1(nametxt);
    }

    public boolean exportCSVP2(String nametxt) {
        return context.exportCSVP2(nametxt);
    }

    public boolean exportCSVP34(String nametxt, AppState State) {
        return context.exportCSVP34(nametxt, State);
    }

    public List<Proposals> getAvailableProposals() {
        return context.getAvailableProposals();
    }

    public void setBlock(int phase) {
        context.setBlock(phase);
        pcs.firePropertyChange(null,null,null);
    }

    public boolean removeC(long studentNumber) {
        var ret = context.removeC(studentNumber);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean removeFA(long studentNumber) {
        var ret = context.removeFA(studentNumber);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean removeAllFA() {
        pcs.firePropertyChange(null,null,null);
        return context.removeAllFA();
    }

    public boolean removeMentor(String idCodePropOfMentor) {
        var ret = context.removeMentor(idCodePropOfMentor);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editNameTeacher(String newName, String email) {
        var ret = context.editNameTeacher(newName, email);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editEmailTeacher(String newEmail, String email) {
        var ret = context.editEmailTeacher(newEmail, email);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editNameStudent(String newName, long number) {
        var ret = context.editNameStudent(newName, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editEmailStudent(String newEmail, long number) {
        var ret = context.editEmailStudent(newEmail, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editNumberStudent(long newNumber, long number) {
        var ret = context.editNumberStudent(newNumber, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editApplicableInternshipStudent(long number) {
        var ret = context.editApplicableInternshipStudent(number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editCourseStudent(String newCourse, long number) {
        var ret = context.editCourseStudent(newCourse, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editBranchStudent(String newBranch, long number) {
        var ret = context.editBranchStudent(newBranch, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editScoreStudent(String newScore, long number) {
        var ret = context.editScoreStudent(newScore, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editBranch(Proposals prop, List<String> branch) {
        var ret = context.editBranch(prop, branch);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editTitle(Proposals prop, String title) {
        var ret = context.editTitle(prop, title);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editLocal(Proposals prop, String local) {
        var ret = context.editLocal(prop, local);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editTeacherProject(String email, Proposals prop) {
        var ret = context.editTeacherProject(email, prop);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editNumberStudentProposals(long studentNumber, Proposals prop) {
        var ret = context.editNumberStudentProposals(studentNumber, prop);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editID(Proposals prop, String newID) {
        var ret = context.editID(prop, newID);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean editCandidatures(String newCandidatures, String number) {
        var ret = context.editCandidatures(newCandidatures, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean insertSelpProp(String idCode, long number, String title) {
        var ret = context.insertSelpProp(idCode, number, title);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean remove(Long number) {
        var ret = context.remove(number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean remove(String email) {
        var ret = context.remove(email);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public void enterAPP() {
        context.enterAPP();
        pcs.firePropertyChange(null,null,null);
    }

    public boolean upload(File nameFile) {
        var ret = context.upload(nameFile);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean export() {
        var ret = context.export();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public StateBlock getBlock(int phase) {
        return context.getBlock(phase);
    }

    public List<Proposals> printFiltros(List<Filtros> filtros) {
        return context.printFiltros(filtros);
    }

    public HashMap<Long, Student> getStudents() {
        return context.getStudents();
    }

    public HashMap<String, Teacher> getTeachers() {
        return context.getTeachers();
    }

    public HashMap<String, SelfProposed> getSelfProp() {
        return context.getSelfProp();
    }

    public Proposals getTieProposal() {
        return context.getTieProposal();
    }

    public List<Student> getStudentsTie() {
        return context.getStudentsTie();
    }

    public HashMap<String, Project> getProjects() {
        return context.getProjects();
    }

    public HashMap<String, Internship> getInternships() {
        return context.getInternships();
    }

    public HashMap<Long, List<String>> getCandidatures() {
        return context.getCandidatures();
    }

    public List<FinalAtribution> getFA() {
        return context.getFA();
    }

    public boolean manualAtribution(long studentNumber, String idCode) {
        ct.save();
        var ret = context.manualAtribution(studentNumber, idCode);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public void automaticOneP3() {
        context.automaticOneP3();
        pcs.firePropertyChange(null,null,null);
    }

    public boolean automaticTwoP3() {
        var ret = context.automaticTwoP3();
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean blockPhaseThree() {
        return context.blockPhaseThree();
    }

    public void tieFA(Proposals p, Student chooseStudent) {
        context.tieFA(p, chooseStudent);
    }

    public void automaticMentorPhaseFour() {
        context.automaticMentorPhaseFour();
        pcs.firePropertyChange(null,null,null);
    }

    public List<Student> studentsMentor() {
        return context.studentsMentor();
    }

    public List<Student> studentsNoMentor() {
        return context.studentsNoMentor();
    }

    public ArrayList<String> getCandidaturesStudentPropCode() {
        return context.getCandidaturesStudentPropCode();
    }

    public List<Student> studentsWFA() {
        return context.studentsWFA();
    }

    public boolean setMentor(String newMentor, String idCode) {
        return context.setMentor(newMentor, idCode);
    }

    public List<FinalAtribution> proposalsNoMentor(){
        return context.proposalsNoMentor();
    }

    public List<FinalAtribution> proposalsWithMentor(){
        return context.proposalsWithMentor();
    }

    public boolean mentorAttribution(String idCode, String mentorEmail) {
        var ret = context.mentorAttribution(idCode, mentorEmail);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public List<Student> noProposalCandidature() {
        return context.noProposalCandidature();
    }

    public List<Integer> mentorByTeacher(String email) {
        return context.mentorByTeacher(email);
    }

    public List<Student> studentsWOPropAssociated() {
        return context.studentsWOPropAssociated();
    }

    public List<Student> studentsSelfPropCandidature() {
        return context.studentsSelfPropCandidature();
    }

    public List<Student> studentsWOCandidature() {
        return context.studentsWOCandidature();
    }

    public boolean studentsTie(long studentNumber) {
        return context.studentsTie(studentNumber);
    }

    public boolean setNameTeacher(String newName, String email) {
        var ret = context.setNameTeacher(newName, email);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean setEmailTeacher(String newEmail, String email) {
        var ret =  context.setEmailTeacher(newEmail, email);
        pcs.firePropertyChange(null,null,null);
        return context.setEmailTeacher(newEmail, email);
    }

    public boolean setNameStudent(String newName, long number) {
        var ret =context.setNameStudent(newName, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean setNumberStudent(long newNumber, long number) {
        var ret =context.setNumberStudent(newNumber, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean setApplicableInternshipStudent(long number) {
        var ret = context.setApplicableInternshipStudent(number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean setCourseStudent(String newCourse, long number) {
        var ret =context.setCourseStudent(newCourse, number);
        pcs.firePropertyChange(null,null,null);
         return ret;
    }

    public boolean setBranchStudent(String newBranch, long number) {
        var ret = context.setBranchStudent(newBranch, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean setScoreStudent(String newScore, long number) {
        var ret = context.setScoreStudent(newScore, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public boolean setCandidatures(String values, String number) {
        var ret =context.setCandidatures(values, number);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    public FinalAtribution getStudentFA(long number) {
        return context.getStudentFA(number);
    }

    public ArrayList<Teacher> getMapCountMentor() {
        return context.getMapCountMentor();
    }

    public boolean editMentor(String newMentor, String idCode) {
        var ret =context.setMentor(newMentor, idCode);
        pcs.firePropertyChange(null,null,null);
        return ret;
    }

    @Override
    public IMemento save() {
        return new Memento(this);
    }

    public void undo() {
        if(ct.hasUndo())
            ct.undo();
    }

    public void redo() {
        if(ct.hasRedo())
            ct.redo();
    }

    public void reset() {
        ct.reset();
    }

    public boolean saveM(String fileName) {
        return mm.save(fileName, context);
    }

    public Boolean loadM(String fileName) {
        return mm.load(fileName, context);
    }

    public boolean hasUndo() {
        return ct.hasUndo();
    }

    public boolean hasRedo() {
        return ct.hasRedo();
    }

    @Override
    public void restore(IMemento memento) {
        Object obj = memento.getSnapshot();
        if (obj instanceof Facade m)
           context = m.context;
    }
}

