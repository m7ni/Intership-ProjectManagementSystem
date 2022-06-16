package pt.isec.pa.model;

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

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Facade implements Serializable, IOriginator {
    AppContext context;
    CareTaker ct;
    MemoryManager mm;
    public Facade(AppContext context) {
        this.context = context;
        mm = new MemoryManager();
        ct = new CareTaker(this);
    }

    public void changeState(IAppState newState) {
        context.changeState(newState);
    }

    public boolean sameState() {
        return context.sameState();
    }

    public boolean next(Boolean block) {
        return context.next(block);
    }

    public boolean back() {
        return context.back();
    }

    public void teacher() {
        context.teacher();
    }

    public void student() {
        context.student();
    }

    public void projectInternship() {
        context.projectInternship();
    }

    public AppState getState() {
        return context.getState();
    }

    public boolean insertStudent(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
        return context.insertStudent(name, email, number, minor, branch, score, internship);
    }

    public boolean insertTeacher(String name, String email) {
        return context.insertTeacher(name, email);
    }

    public boolean insertPI(String idCode, long number, List<String> branch, String title, String local) {
        return context.insertPI(idCode, number, branch, title, local);
    }

    public boolean insertSelpProp(String idCode, long number, String title) {
        return context.insertSelpProp(idCode, number, title);
    }

    public boolean remove(Long number) {
        return context.remove(number);
    }

    public boolean remove(String email) {
        return context.remove(email);
    }

    public void enterAPP() {
        context.enterAPP();
    }

    public boolean upload(String nameFile) {
        return context.upload(nameFile);
    }

    public boolean export() {
        return context.export();
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

    public boolean manualAtribution(String[] values, List<Long> studentsNumber) {
        ct.save();
        return context.manualAtribution(values, studentsNumber);
    }

    public void automaticOneP3() {
        context.automaticOneP3();
    }

    public boolean automaticTwoP3() {
        return context.automaticTwoP3();
    }

    public boolean blockPhaseThree() {
        return context.blockPhaseThree();
    }

    public void tieFA(Proposals p, Student chooseStudent) {
        context.tieFA(p, chooseStudent);
    }

    public void automaticMentorPhaseFour() {
        context.automaticMentorPhaseFour();
    }

    public List<Student> studentsMentor() {
        return context.studentsMentor();
    }

    public List<Student> studentsNoMentor() {
        return context.studentsNoMentor();
    }

    public boolean mentorAttribution(String[] values) {
        return context.mentorAttribution(values);
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
        return context.setNameTeacher(newName, email);
    }

    public boolean setEmailTeacher(String newEmail, String email) {
        return context.setEmailTeacher(newEmail, email);
    }

    public boolean setNameStudent(String newName, long number) {
        return context.setNameStudent(newName, number);
    }

    public boolean setNumberStudent(long newNumber, long number) {
        return context.setNumberStudent(newNumber, number);
    }

    public boolean setApplicableInternshipStudent(long number) {
        return context.setApplicableInternshipStudent(number);
    }

    public boolean setCourseStudent(String newCourse, long number) {
        return context.setCourseStudent(newCourse, number);
    }

    public boolean setBranchStudent(String newBranch, long number) {
        return context.setBranchStudent(newBranch, number);
    }

    public boolean setScoreStudent(String newScore, long number) {
        return context.setScoreStudent(newScore, number);
    }

    public boolean setCandidatures(String values, String number) {
        return context.setCandidatures(values, number);
    }

    public FinalAtribution getStudentFA(long number) {
        return context.getStudentFA(number);
    }

    public boolean setMentor(String newMentor, long number) {
        return context.setMentor(newMentor, number);
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

    /*
    public boolean saveM(String fileName) {
        return mm.save(fileName, context);
    }

    public Boolean loadM(String fileName) {
        return mm.load(fileName, context);
    }


     */

    public boolean saveM(String fileName){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(fileName)))
        {
            oos.writeObject(context);
        } catch (Exception e) {
            System.err.println("Error saving data " + e );
        }
        return false;
    }

    public Boolean loadM(String fileName){
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(fileName)))
        {
            context =  (AppContext) ois.readObject();

        } catch (Exception e) {
            System.err.println("Error loading data" + e);
        }
        return false;
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

