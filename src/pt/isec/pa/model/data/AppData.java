package pt.isec.pa.model.data;

import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.*;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.utils.CSVModder;
import pt.isec.pa.utils.PAInput;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;

public class AppData implements Serializable {
    static final long serialVersionUID = 100L;
    protected HashMap<Long, Student> students;
    protected HashMap<String, Teacher> teachers;
     protected HashMap<String, SelfProposed> selfProp;
     protected HashMap<String, Project> projects;
    protected HashMap<String, Internship> internships;
     protected HashMap<Long, List<String>> candidatures; //student number + aplications
     protected List<StateBlock> block;
    protected List<FinalAtribution> FA;
     protected Proposals tieProposal;
     protected List<Student> studentsTie;
    
    public AppData() {
        initialize();
    }

    private void initialize() {
        FA = new ArrayList<>();
        selfProp = new HashMap<>();
        projects = new HashMap<>();
        internships = new HashMap<>();
        candidatures = new HashMap<>();
        students = new HashMap<>();
        teachers = new HashMap<>();
        block = Arrays.asList(StateBlock.UNLOCKED, StateBlock.UNLOCKED, StateBlock.UNLOCKED, StateBlock.UNLOCKED, StateBlock.UNLOCKED);
    }

    public void automaticOneP3(){ //automatic distribution of self-proposed
        List<Proposals> projectsPLUSselfProp = new ArrayList<>(projects.values());
        projectsPLUSselfProp.addAll(selfProp.values());
        FinalAtribution aux ;
        for(Proposals sp: projectsPLUSselfProp){
            if(sp.getHasAssignedStudent()) {
                aux = new FinalAtribution(sp, students.get(sp.getStudentNumber()));
                if(!FA.contains(aux))
                    FA.add(aux);
            }
        }
    }

    public void automaticMentorPhaseFour(){
        for(Project p : projects.values()){
            for(FinalAtribution fa: FA){
                if(fa.getFinalP().equals(p)){
                    fa.setMentor( teachers.get(p.gettEmail()));
                    teachers.get(p.gettEmail()).upMentorCount();
                }
            }
        }
    }

    public void tieFA(Proposals p, Student chooseStudent) {

        if(projects.containsKey(p.getIdCode())){
            projects.get(p.getIdCode()).setHasAssignedStudent(true);
            projects.get(p.getIdCode()).setStudentNumber(chooseStudent.getStudentNumber());
        }else{
            internships.get(p.getIdCode()).setHasAssignedStudent(true);
            internships.get(p.getIdCode()).setStudentNumber(chooseStudent.getStudentNumber());
        }
        students.get(chooseStudent.getStudentNumber()).setAssignedProposal(true);
        FA.add(new FinalAtribution(p,chooseStudent));
    }

   transient Predicate<Proposals> selfPropoposal = new Predicate<Proposals>() {
        @Override
        public boolean test(Proposals prop) {
            return prop instanceof SelfProposed;
        }
    };

    transient Predicate<Proposals> teacherProposal = new Predicate<Proposals>() {
        @Override
        public boolean test(Proposals prop) {
            return prop instanceof Project;
        }
    };

    transient Predicate<Proposals> proposalWCandiddature = new Predicate<Proposals>() {
        @Override
        public boolean test(Proposals prop) {
            for (List<String> l : candidatures.values())
                if (l.contains(prop.getIdCode()))
                    return true;
            return false;
        }
    };

    transient Predicate<Proposals> avaiableProposal = new Predicate<Proposals>() {
        @Override
        public boolean test(Proposals prop) {
            return !prop.getHasAssignedStudent();
        }
    };

    transient Predicate<Proposals> lockedProposal = new Predicate<Proposals>() {
        @Override
        public boolean test(Proposals prop) {
            return prop.getHasAssignedStudent();
        }
    };

    transient Predicate<Proposals> proposalWOCandidature = new Predicate<Proposals>() {
        @Override
        public boolean test(Proposals prop) {
            for (List<String> l : candidatures.values())
                if (l.contains(prop.getIdCode()))
                    return false;
            return true;
        }
    };

    public boolean automaticTwoP3Projects() { //automatic distribution of the proposals that are left
        if(getBlock(2)== StateBlock.UNLOCKED)
            return true;
        //List<Filtros> filtros = new ArrayList<>();
        List<Student> studentsWoProposal = new ArrayList<>(); //doesn't have a proposal, and presented a candidature
        StudentsWOProposal(studentsWoProposal);
        //filtros.add(Filtros.AVAIABLE);
        List<Project> availableProjects = new ArrayList<>();
        projectNoStudent(availableProjects);
        List<Student> chooseStudent = new ArrayList<>();
        List<Student> toRemove =new ArrayList<>() ;
        List<Internship> availableInternships = new ArrayList<>();
        internshipNoStudent(availableInternships);

        if(studentsWoProposal.isEmpty())
            return true;

        for (Project ap : availableProjects) {
            for (Student s : studentsWoProposal) {
                if (!s.getInternship() && ap.getBranch().contains(s.getBranch()) && candidatures.get(s.getStudentNumber()).contains(ap.getIdCode())) {
                    chooseStudent.add(s);
                }
            }
            if (chooseStudent.size() == 1) {
                creatingFa(ap, chooseStudent, studentsWoProposal);
            } else {

                if(chooseStudent.isEmpty())
                    return true;

                chooseStudent.sort(new SortByScore()); //organized by score
                if (chooseStudent.get(0).getScore() > chooseStudent.get(1).getScore()) { // we don't have score ties
                    creatingFa(ap, chooseStudent, studentsWoProposal);
                } else {
                    for (Student s : chooseStudent) {
                        if (s.getScore() < chooseStudent.get(0).getScore()) { //we leave in the chooseStudent the students with the score tied
                            toRemove.add(s);
                        }
                    }
                    chooseStudent.removeAll(toRemove);
                    tieProposal = ap;
                    studentsTie = new ArrayList<>(chooseStudent);
                    return false;
                }
            }
        }

        for (Internship ap : availableInternships) {
            for (Student s : studentsWoProposal) {
                if (s.getInternship() && ap.getBranch().contains(s.getBranch()) && candidatures.get(s.getStudentNumber()).contains(ap.getIdCode())) {
                    chooseStudent.add(s);
                }
            }
            if (chooseStudent.size() == 1) {
                creatingFa(ap, chooseStudent, studentsWoProposal);
            } else {

                if(chooseStudent.isEmpty())
                    return true;

                chooseStudent.sort(new SortByScore()); //organized by score
                if (chooseStudent.get(0).getScore() > chooseStudent.get(1).getScore()) { // we don't have score ties
                    creatingFa(ap, chooseStudent, studentsWoProposal);
                } else {
                    for (Student s : chooseStudent) {
                        if (s.getScore() < chooseStudent.get(0).getScore()) { //we leave in the chooseStudent the students with the score tied
                            toRemove.add(s);//user needs to solve this
                        }
                    }
                    chooseStudent.removeAll(toRemove);
                    tieProposal = ap;
                    studentsTie = new ArrayList<>(chooseStudent);
                    return false;
                }
            }
        }
        return true;

    }

    private <T,S> void creatingFa(S p, List<Student> chooseStudent, List<Student> studentsWoProposal) {
        FA.add(new FinalAtribution((Proposals) p, chooseStudent.get(0)));
        students.get(chooseStudent.get(0).getStudentNumber()).setAssignedProposal(true);

        if(p instanceof Project)
            projects.get(((Proposals) p).getIdCode()).setHasAssignedStudent(true);
        else
            internships.get(((Proposals) p).getIdCode()).setHasAssignedStudent(true);

        studentsWoProposal.remove(chooseStudent.get(0));
        chooseStudent.clear();
    }

    public List<Proposals> printFiltros(List<Filtros> filtros, AppState phase) {
        List<Proposals> proposalsCombined = new ArrayList<>(projects.values());
        proposalsCombined.addAll(internships.values());
        proposalsCombined.addAll(selfProp.values());
        List <Proposals> result = new ArrayList<>();

        if(filtros.isEmpty()){
            result.addAll(proposalsCombined);
        }else{
            if(filtros.contains(Filtros.SELFPROP)){
                result = proposalsCombined.stream().filter(selfPropoposal).toList();
            }
            if(filtros.contains(Filtros.TEACHERPROP)){
                if(result.isEmpty())
                    result = proposalsCombined.stream().filter(teacherProposal).toList();
                else
                    result = result.stream().filter(teacherProposal).toList();
            }
            switch(phase){
                case PHASE_TWO->{
                if(filtros.contains(Filtros.CANDIDATUREPROP)){
                    if(result.isEmpty())
                        result = proposalsCombined.stream().filter(proposalWCandiddature).toList();
                    else
                        result = result.stream().filter(proposalWCandiddature).toList();
                }
                    if(filtros.contains(Filtros.CANDIDATUREWOPROP)){
                        if(result.isEmpty())
                            result = proposalsCombined.stream().filter(proposalWOCandidature).toList();
                        else
                            result = result.stream().filter(proposalWOCandidature).toList();
                    }
                }
                case PHASE_THREE -> {
                    if(filtros.contains(Filtros.AVAIABLE)){
                        if(result.isEmpty())
                            result = proposalsCombined.stream().filter(avaiableProposal).toList();
                        else
                            result = result.stream().filter(avaiableProposal).toList();
                    }
                    if(filtros.contains(Filtros.NOTAVAIABLE)){
                        if(result.isEmpty())
                            result = proposalsCombined.stream().filter(lockedProposal).toList();
                        else
                            result = result.stream().filter(lockedProposal).toList();
                    }
                }

                }
            }
        return new ArrayList<Proposals>(result);
        }

    public boolean blockPhaseOne() {
        List<Branches> branches;
        List<Proposals> projectsPLUSInterships = new ArrayList<>(projects.values());
        projectsPLUSInterships.addAll(internships.values());

        int sDA = 0, sRAS = 0, sSI = 0;
        int pDA = 0, pRAS = 0, pSI = 0;
        for (Student s : students.values()) {
            switch (s.getBranch()) {
                case DA -> sDA++;
                case RAS -> sRAS++;
                case SI -> sSI++;
            }
        }

        for (Proposals p : projectsPLUSInterships) {
                switch (p.getBranch().get(0)) {
                    case DA -> pDA++;
                    case RAS -> pRAS++;
                    case SI -> pSI++;
                }
        }

        return pDA >= sDA && pRAS >= sRAS && pSI >= sSI && !students.isEmpty();
    }

    public boolean blockPhaseThree() {
       for(Long num : candidatures.keySet()){
           if(!students.get(num).isAssignedProposal())
               return false;
       }
      return true;
    }

    public boolean addStudent(String name, String email, Long number, String minor, String branch, double score, boolean internship) {
        Branches br;
        Minor mn;
        if (students.containsKey(number))
            return false;

        switch(minor){
            case "LEI" -> mn = Minor.LEI;
            case "LEI-PL" -> mn = Minor.LEIPL;
            default ->{ return false;}
        }

        switch(branch){
            case "DA" -> br = Branches.DA;
            case "RAS" -> br = Branches.RAS;
            case "SI" -> br = Branches.SI;
            default ->{ return false;}
        }


        if (score < 0.0 || score > 1.0)
            return false;

        students.put(number, new Student(name, email, number, mn, br, score, internship));
        return true;
    }

    public boolean addTeacher(String name, String email) {
        if (teachers.containsKey(email))
            return false;

        teachers.put(email, new Teacher(name, email));
        return true;
    }

    private boolean studentProposalCheck(String idCode, long number) {
        if (projects.containsKey(idCode) || internships.containsKey(idCode) || selfProp.containsKey(idCode))
            return true;

        if(number != -1){
            Student st = students.get(number);
            if(st.isAssignedProposal())
                return true;
            students.get(number).setAssignedProposal(true);
        }
        return false;
    }

    public boolean addInternship(String idCode, long number, List<Branches> branch, String title, String local) {         //T1
        Internship aux;
        if (!students.containsKey(number) && number != -1)
            return false;

        if (branch.isEmpty()) return false;

        if (studentProposalCheck(idCode, number)) return false;
        aux = new Internship(idCode, number, branch, title, local);

        if (number != -1)
            aux.setHasAssignedStudent(true);


        internships.put(idCode, aux);
        return true;
    }

    public boolean addProject(String idCode, long number, List<Branches> branch, String title, String tEmail) {      //T2
        Project aux;
        if (!students.containsKey(number) && number != -1)
            return false;

        if (branch.isEmpty()) return false;


        if (!teachers.containsKey(tEmail))
            return false;

        if (studentProposalCheck(idCode, number)) return false;

        aux = new Project(idCode, number, branch, title, tEmail);

        if (number != -1)
            aux.setHasAssignedStudent(true);



        projects.put(idCode, aux);
        return true;
    }

    public boolean addSelfProp(String idCode, long number, String title) {      //T3
        SelfProposed aux;
        if (projects.containsKey(idCode) || internships.containsKey(idCode) || selfProp.containsKey(idCode))
            return false;

        if (!students.containsKey(number) && number != -1)
            return false;

        aux = new SelfProposed(idCode, number, title);

        if(number != -1){
            Student st = students.get(number);
            if(st.isAssignedProposal())
                return false;
            students.get(number).setAssignedProposal(true);
        }

        aux.setHasAssignedStudent(true);


        selfProp.put(idCode, aux);
        return true;
    }

    public boolean addCandidature(String[] values) {      //T3
        List<String> aux = new ArrayList<>();
        long studentNumber = Long.parseLong(values[0]);

        if (!students.containsKey(studentNumber) || students.get(studentNumber).isAssignedProposal())  //student doesn't exist or already as a proposal assigned
            return false;

        if (values.length == 1)  //se só tiver um valor
            return false;

        if (candidatures.containsKey(studentNumber))  //ver se já existe alguma candidatura com este numero de estudante
            return false;

        for (int i = 1; i < values.length; i++) {
            if (!(projects.containsKey(values[i]) || internships.containsKey(values[i]) || selfProp.containsKey(values[i])))
                return false;

            aux.add(values[i]);
        }

        candidatures.put(studentNumber, aux);

        return true;
    }

    public boolean removeType(Object obj, Types type){
        switch (type){
            case STUDENT -> {   if (!students.containsKey((long)obj))
                return false;

                students.remove((long)obj);
                return true;
            }
            case TEACHER ->{  if (!teachers.containsKey((String) obj))
                return false;

                teachers.remove((String) obj);
                return true;
            }
            case PROJECT -> {   if (!projects.containsKey((String) obj))
                return false;

                projects.remove((String) obj);
                return true;
            }
            case INTERNSHIP -> {  if (!internships.containsKey((String) obj))
                return false;

                internships.remove((String) obj);
                return true;
            }
            case SELPROP -> {
                if (!selfProp.containsKey((String) obj))
                    return false;

                teachers.remove((String) obj);
                return true;
            }
        }
        return false;
    }

    public boolean uploadCSV(String nametxt, Types type) {
        CSVModder csv = new CSVModder(this);

        switch (type){
            case STUDENT -> { return csv.SUpload(nametxt); }
            case TEACHER ->{  return csv.TUpload(nametxt); }
            case PROPOSAL -> {  return csv.PIUpload(nametxt); }
            case CANDIDATURE -> { return csv.CUpload(nametxt); }
        }
        return false;
    }

    public boolean exportCSVP1(String nametxt) {
        CSVModder csv = new CSVModder(this);
        StringBuilder sb = new StringBuilder();

        for(Student s : students.values())
            sb.append(s.printCSV()).append("\n");
        for(Teacher t : teachers.values())
            sb.append(t.printCSV()).append("\n");
        for(Internship i : internships.values())
            sb.append(i.printCSV()).append("\n");
        for(Project p : projects.values())
            sb.append(p.printCSV()).append("\n");
        for(SelfProposed sp : selfProp.values())
            sb.append(sp.printCSV()).append("\n");

        return csv.ExportPhase(nametxt, sb.toString());
    }

    public boolean exportCSVP2(String nametxt) {
        CSVModder csv = new CSVModder(this);
        StringBuilder sb = new StringBuilder();

        for(long c : candidatures.keySet()) {
            sb.append(Long.toString(c)).append(",");
            sb.append(candidatures.get(c).toString().substring(1,  candidatures.get(c).toString().length() - 1)).append("\n");
        }

        return csv.ExportPhase(nametxt, sb.toString());
    }

    public boolean exportCSVP34(String nametxt,AppState State) {
        CSVModder csv = new CSVModder(this);
        StringBuilder sb = new StringBuilder();
        for(Student s: students.values()){
            sb.append(s.printCSV()).append("\n");
            if(candidatures.containsKey(s.getStudentNumber()))
                sb.append(candidatures.get(s.getStudentNumber()).toString().substring(1,  candidatures.get(s.getStudentNumber()).toString().length() - 1)).append("\n");
            for(FinalAtribution fa : FA) {
                if(fa.getStudent().equals(s)) {
                    sb.append(fa.getFinalP().getIdCode()).append("\n");
                    if(State == AppState.PHASE_FOUR)
                        if(fa.getMentor()!=null){
                            sb.append(fa.getMentor().getEmail()).append("\n");
                        }
                }
            }



        }
        return csv.ExportPhase(nametxt, sb.toString());
    }

    public boolean manualAtribution(String[] values, List<Long> studentsNumber) {
        if(getBlock(2)== StateBlock.UNLOCKED)
            return false;

        HashMap<String, Proposals> proposalsCombinedMap = new HashMap<>(projects);
        proposalsCombinedMap.putAll(internships);
        List<String> availableProposals = new ArrayList<>();

        if(values.length !=2)
            return false;

        for (Proposals f: proposalsCombinedMap.values())
            if (!f.getHasAssignedStudent())
                availableProposals.add(f.getIdCode());

        if(!proposalsCombinedMap.get(values[1]).getBranch().contains(students.get(Long.parseLong(values[0])).getBranch()))
            return false;

        if(!students.get(Long.parseLong(values[0])).getInternship() && internships.containsKey(values[1]))
            return false;

        /*
        for(Proposals p: proposalsCombinedMap.values()) {
            if(!p.getBranch().contains(students.get(Long.parseLong(values[0])).getBranch()))
                return false;
        }
        */

        if(studentsNumber.contains(Long.parseLong(values[0]))) {
            if(availableProposals.contains(values[1])) {
                students.get(Long.parseLong(values[0])).setAssignedProposal(true);
                proposalsCombinedMap.get(values[1]).setHasAssignedStudent(true);
                FA.add(new FinalAtribution(proposalsCombinedMap.get(values[1]), students.get(Long.parseLong(values[0]))));
                return true;
            }
        }

        return false;
    }

    public HashMap<Long, Student> getStudents() {
        return new HashMap<>(students);
    }

    public HashMap<String, Teacher> getTeachers() {
        return  new HashMap<>(teachers);
    }

    public HashMap<String, SelfProposed> getSelfProp() {
        return new HashMap<>(selfProp);
    }

    public HashMap<String, Project> getProjects() {
        return new HashMap<>(projects);
    }

    public HashMap<String, Internship> getInternships() {
        return new HashMap<>(internships);
    }

    public HashMap<Long, List<String>> getCandidatures() {
        return new HashMap<>(candidatures);
    }

    public List<FinalAtribution> getFA() {
        return new ArrayList<>(FA);
    }

    public StateBlock getBlock(int phase) {
        return block.get(phase-1);
    }

    public void setBlock(int phase) {
        block.set(phase - 1, StateBlock.BLOCKED);
    }

    public Proposals getTieProposal() {
        return tieProposal;
    }

    public List<Student> getStudentsTie() {
        return studentsTie;
    }

    public List<Student> studentsMentor(){
        List<Student> wm = new ArrayList<>();
        for(FinalAtribution fa: FA){
            if(fa.getMentor()!=null)
                wm.add(fa.getStudent());
        }
        return wm;
    }

    public List<Student> studentsNoMentor(){
        List<Student> nm = new ArrayList<>();
        for(FinalAtribution fa: FA){
            if(fa.getMentor()==null)
                nm.add(fa.getStudent());
        }
        return nm;
    }

    public List<Student> studentsWOPropAssociated(){
        List<Student> st = new ArrayList<>();
        for (Student s : students.values()) {
            if(!s.isAssignedProposal())
                st.add(s);

        }
        return st;
    }

    public List<Student> studentsSelfPropCandidature(){
        List<Student> st = new ArrayList<>();
        for (SelfProposed sp : selfProp.values()) {
            if(students.containsKey(sp.getStudentNumber()))
                st.add(students.get(sp.getStudentNumber()));
        }
        return st;
    }

    public List<Student> studentsWOCandidature(){
        List<Student> st = new ArrayList<>();
        for (long c : candidatures.keySet()) {
            if (!students.containsKey(c))
                st.add(students.get(c));
        }
        return st;
    }

    public boolean studentsTie(long studentNumber) {
        return getStudentsTie().contains(getStudents().get(studentNumber));
    }

    public boolean mentorAttribution(String[] values) {
        if(values.length !=2)
            return false;

        if(!students.containsKey(Long.parseLong(values[0])))
            return false;

        if(!teachers.containsKey(values[1]))
            return false;

        for(FinalAtribution fa : FA) {
            if(fa.getStudent().getStudentNumber() == Long.parseLong(values[0])) {
                fa.setMentor(teachers.get(values[1]));
                teachers.get(values[1]).upMentorCount();
                return true;
            }
        }

        return false;
    }

    public List<Integer> mentorByTeacher(String email){
        List<Integer> values= new ArrayList<>();
        int sum = 0,count = 0;
        if(!teachers.containsKey(email)) // name of the teacher doesn't exist
         return null;

        Teacher minByMentor = teachers.values() //min
                .stream()
                .min(Comparator.comparing(Teacher::getMentorCount))
                .orElseThrow(NoSuchElementException::new);
        values.add(minByMentor.getMentorCount());

        Teacher maxByMentor = teachers.values() //max
                .stream()
                .max(Comparator.comparing(Teacher::getMentorCount))
                .orElseThrow(NoSuchElementException::new);
        values.add(maxByMentor.getMentorCount());

        values.add(teachers.get(email).getMentorCount());//teacher

       for(Teacher t : teachers.values()){
           sum += t.getMentorCount();
           count++;
       }
        values.add((int) Math.ceil(sum/count));

       return values;
    }
   
    private void StudentsWOProposal(List<Student> studentsWoProposal) {
        for(Student s : students.values())
            if(!s.isAssignedProposal() && candidatures.containsKey(s.getStudentNumber())){ //doesn't have a proposal, and presented a candidature
                studentsWoProposal.add(s);
            }
    }
    
    private void projectNoStudent(List<Project> prop) {
        for(Project p : projects.values())
            if(!p.getHasAssignedStudent()){ //doesn't have a proposal, and presented a candidature
                prop.add(p);
            }
    }

    private void internshipNoStudent(List<Internship> prop) {
        for(Internship p : internships.values())
            if(!p.getHasAssignedStudent()){ //doesn't have a proposal, and presented a candidature
                prop.add(p);
            }
    }

    public FinalAtribution getStudentFA(long number) {
        for (FinalAtribution finalAtribution : FA) {
            if (finalAtribution.getStudent().getStudentNumber() == number)
                return finalAtribution;
        }

        return null;
    }

    public List<Student> noProposalCandidature() {
        List<Student> aux = new ArrayList<>();
       for(Student s: students.values()){
           if(!s.isAssignedProposal() && candidatures.containsKey(s.getStudentNumber()))
               aux.add(s);
       }
       return aux;
    }

    public boolean removeStudent(long code) {
        if (!students.containsKey(code))
            return false;

        students.remove(code);
        return true;
    }

    public boolean removeTeacher(String email) {
        if (!teachers.containsKey(email))
            return false;

        teachers.remove(email);
        return true;
    }

    public boolean removeP(String idCode) {
        if (!projects.containsKey(idCode))
            return false;

        projects.remove(idCode);
        return true;
    }

    public boolean removeI(String idCode) {
        if (!internships.containsKey(idCode))
            return false;

        internships.remove(idCode);
        return true;
    }

    public boolean removeSP(String idCode) {
        if (!selfProp.containsKey(idCode))
            return false;

        teachers.remove(idCode);
        return true;
    }

    public boolean removeC(long studentNumber) {
        if (!candidatures.containsKey(studentNumber))
            return false;

        candidatures.remove(studentNumber);
        return true;
    }

    public boolean removeFA(long studentNumber) {
        for (FinalAtribution finalAtribution : FA) {
            if (finalAtribution.getStudent().getStudentNumber() == studentNumber) {
                FA.remove(finalAtribution);
                return true;
            }
        }
        return false;
    }

    public boolean removeAllFA() {
        for(long s : students.keySet())
            removeFA(s);
        return true;
    }

    public boolean removeMentor(String emailMentor) {

        for (FinalAtribution finalAtribution : FA) {
            if (finalAtribution.getMentor().getEmail().equals(emailMentor)) {
                finalAtribution.setMentor(null);
                return true;
            }
        }

        return false;
    }

    //editTeacher
    
    public boolean editNameTeacher(String newName, String email) {
        if(!teachers.containsKey(email))
            return false;

        teachers.get(email).setName(newName);

        return true;
    }

    public boolean editEmailTeacher(String newEmail, String email) {
        if(!teachers.containsKey(email))
            return false;

        teachers.get(email).setEmail(newEmail);

        return true;
    }

    //editStudent

    public boolean editNameStudent(String newName, long number) {
        if(!students.containsKey(number))
            return false;

        students.get(number).setName(newName);

        return true;
    }

    public boolean editNumberStudent(long newNumber, long number) {
        if(!students.containsKey(number))
            return false;

        if(!students.containsKey(newNumber))
            students.get(number).setStudentNumber(newNumber);

        return true;
    }

    public boolean editApplicableInternshipStudent(long number) {
        if(!students.containsKey(number))
            return false;

        students.get(number).setInternship();

        return true;
    }

    public boolean editCourseStudent(String newCourse, long number) {
        if(!students.containsKey(number))
            return false;

        Minor mn;
        switch(newCourse){
            case "LEI" -> mn = Minor.LEI;
            case "LEI-PL" -> mn = Minor.LEIPL;
            default ->{ return false; }
        }
        students.get(number).setMinor(mn);

        return true;
    }

    public boolean editBranchStudent(String newBranch, long number) {
        if(!students.containsKey(number))
            return false;

        Branches br;
        switch(newBranch){
            case "DA" -> br = Branches.DA;
            case "RAS" -> br = Branches.RAS;
            case "SI" -> br = Branches.SI;
            default -> { return false;}
        }
        students.get(number).setBranch(br);

        return true;
    }

    public boolean editScoreStudent(String newScore, long number) {
        if(!students.containsKey(number))
            return false;

        Double sc = Double.parseDouble(newScore);
        if (sc < 0.0 || sc > 1.0)
            return false;
        students.get(number).setScore(sc);

        return true;
    }

    //editPI

    public boolean editBranch(Proposals prop, List<String> branch){
        List<Branches> aux = new ArrayList<>();

        if(branch.isEmpty())
            return false;

        for(String br:branch){
            switch(br.toUpperCase(Locale.ROOT)){
                case "DA" -> aux.add(Branches.DA);
                case "RAS" ->aux.add(Branches.RAS);
                case "SI" ->aux.add(Branches.SI);
                default ->{return false;}
            }
        }

        if(prop instanceof Project){
            projects.get(prop.getIdCode()).setBranch(aux);
            return true;
        }else if(prop instanceof Internship) {
            internships.get(prop.getIdCode()).setBranch(aux);
            return true;
        }
        return false;
    }

    public boolean editTitle(Proposals prop,String title){
        if(title.isEmpty())
            return false;

        if(prop instanceof Project){
            projects.get(prop.getIdCode()).setTitle(title);
            return true;
        }else if(prop instanceof Internship) {
            internships.get(prop.getIdCode()).setTitle(title);
            return true;
        }else if (prop instanceof SelfProposed){
            selfProp.get(prop.getIdCode()).setTitle(title);
            return true;
        }
        return false;
    }

    public boolean editLocal(Proposals prop,String local) {
        if (local.isEmpty())
            return false;

        if (prop instanceof Internship) {
            internships.get(prop.getIdCode()).setLocal(local);
            return true;
        }
        return false;
    }

    public boolean editTeacherProject(String email,Proposals prop) {

        if(!teachers.containsKey(email))
            return false;

        if (prop instanceof Internship) {
            projects.get(prop.getIdCode()).settEmail(email);
            return true;
        }
        return false;
    }

    public boolean editID(Proposals prop,String newID){
        if(newID.isEmpty())
            return false;

        if(prop instanceof Project){
            if(projects.containsKey(newID))
                return false;
            projects.get(prop.getIdCode()).setID(newID);
            return true;
        }else if(prop instanceof Internship) {
            if(internships.containsKey(newID))
                return false;
            internships.get(prop.getIdCode()).setID(newID);
            return true;
        }else if (prop instanceof SelfProposed){
            if(selfProp.containsKey(newID))
                return false;
            selfProp.get(prop.getIdCode()).setID(newID);
            return true;
        }
        return false;
    }

    //editCandidatures

    public boolean editCandidatures(String newCandidatures, String number) {
        if(!candidatures.containsKey(Long.parseLong(number)))
            return false;

        if(newCandidatures.isEmpty())
            return false;

        String[] values;
        values = newCandidatures.split(" ");

        removeC(Long.parseLong(number));

        for(int i = values.length + 1 ; i > 0 ; i++) {
            values[i] = values[i-1];
        }
        values[0] = number;

        return addCandidature(values);
    }

    //editMentor

    public boolean editMentor(String newMentor, long number) {

        if(!teachers.containsKey(newMentor))
            return false;

        for (FinalAtribution finalAtribution : FA) {
            if (finalAtribution.getStudent().getStudentNumber() == number) {
                finalAtribution.setMentor(teachers.get(newMentor));
                return true;
            }
        }

        return false;
    }


}