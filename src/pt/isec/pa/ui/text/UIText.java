package pt.isec.pa.ui.text;


import pt.isec.pa.model.data.Branches;
import pt.isec.pa.model.data.Minor;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.utils.PAInput;
import pt.isec.pa.model.data.proposals.FinalAtribution;
import pt.isec.pa.model.data.proposals.Internship;
import pt.isec.pa.model.data.proposals.Project;
import pt.isec.pa.model.data.proposals.SelfProposed;

import java.util.*;

public class UIText {
    Facade facade;

    public UIText(Facade fsm) {
        this.facade = fsm;
    }
    private boolean finish = false;

    private boolean blockUI(){
        boolean aux;
        switch (PAInput.chooseOption("Do you wish to block this Phase ?",
                "Yes", "No")) {
            case 1 -> aux =true;
            case 2 -> aux = false;
            default -> aux = false;
        }
        return aux;
    }

    public void start() {
        while (!finish) {
            switch (facade.getState()) {
                case CHOOSE_PHASE_ONE -> ChoosePhaseOneUI();
                case PONE_STUDENT -> ICEEStateUIStudent();
                case PONE_TEACHER -> ICEEStateUITeacher();
                case PONE_PI -> ICEEStateUIPI();
                case PONE_INTERNSHIP -> InternshipManagementP1();
                case PONE_PROJECT -> ProjectsManagementP1();
                case PONE_SELFPROP -> InternshipSelfProposedProjectManagementP1();
                case PHASE_TWO -> PhaseTwoStateUI();
                case PHASE_THREE -> PhaseThreeStateUI();
                case PHASE_FOUR -> PhaseFourStateUI();
                case PHASE_FIVE -> PhaseFiveStateUI();
                case UNTIE -> UnTieStateUI();
            }
        }
    }

    private void PhaseFiveStateUI() {
        switch (PAInput.chooseOption("Phase 5 - Consult",
                "List of Student with Attributed Proposals", "List of Students without Proposals Attributed and Candidature Options", "Available Proposals", "Attributed Proposals", "Consult Mentor Attribution", "Back to Phase 4")) {
            case 1 -> System.out.println(printProposalAtributted(facade.getFA()));
            case 2 -> {
                StringBuilder sb = new StringBuilder();
                sb.append(printObject(facade.noProposalCandidature()));
                System.out.println(sb.toString());
            }
            case 3 -> {
                ArrayList<Filtros> filters;
                filters = new ArrayList<>();
                filters.add(Filtros.AVAIABLE);
                System.out.println(printObject(facade.printFiltros(filters)));
            }
            case 4 -> {
                ArrayList<Filtros> filters;
                filters = new ArrayList<>();
                filters.add(Filtros.NOTAVAIABLE);
                System.out.println(printObject(facade.printFiltros(filters)));
            }
            case 5 -> System.out.println(mentorByTeacherUI());
            case 6 -> facade.back();
        }
    }

    private void PhaseFourStateUI() {
        if (facade.getBlock(4) == StateBlock.BLOCKED) {
            switch (PAInput.chooseOption("Phase 4 - Blocked",
                    "Consult List of Students", "Consult List of Mentors", "Consult Mentor Attribution", "Back to Phase 3", "Move to Phase 5")) {
                case 1 -> ListStudentsUI();
                case 2 -> {
                    for(Teacher t : facade.getTeachers().values())
                        System.out.println(t.toString());
                }
                case 3 -> printMentorAttributionUI();
                case 4 -> facade.back();
                case 5 -> facade.next(true);
            }
        }else {
            switch (PAInput.chooseOption("Phase 4 - Attribution of Mentors",
                    "Mentor Attribution", "Consult List of Mentors", "Consult Mentor Attribution", "Back to Phase 3", "Move to Phase 5")) {
                case 1 -> {
                    if(!mentorAttribution())
                        System.out.println("There are no Students without mentor\n");
                }
                case 2 -> {
                    for(Teacher t : facade.getTeachers().values())
                        System.out.println(t.toString());
                    }
                case 3 -> printMentorAttributionUI();
                case 4 -> facade.back();
                case 5 -> facade.next(true);
            }
        }
    }

    private void printMentorAttributionUI() {
        switch (PAInput.chooseOption("Phase 4 - Attribution of Mentors",
                "Students with Mentor", "Students without Mentor", "Mentors number of Orientations")) {
            case 1 -> System.out.println(studentsMentorUI());
            case 2 -> System.out.println(studentsNoMentorUI());
            case 3 -> System.out.println(mentorByTeacherUI());
        }
    }

    private String studentsMentorUI() {
        StringBuilder sb = new StringBuilder();

        for(Student s : facade.studentsMentor())
            sb.append(s.toString()).append("\n");

        return sb.toString();
    }

    private String studentsNoMentorUI() {
        StringBuilder sb = new StringBuilder();

        for(Student s : facade.studentsNoMentor())
            sb.append(s.toString()).append("\n");

        return sb.toString();
    }

    private String mentorByTeacherUI() {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;

        switch (PAInput.chooseOption("Do you want to search for a Mentor in specific?",
                "Yes", "No")) {
            case 1 -> flag = true;
            case 2 -> flag = false;
        }

        if(flag) {
            String email = PAInput.readString("Email of Mentor: \"quit\" to exit)", true);
            if(email.equalsIgnoreCase("quit")) {
                return sb.append("User quit the action\n").toString();

            }

            sb.append("Min: "+ facade.mentorByTeacher(email).get(0)).append("\n");
            sb.append("Max: "+ facade.mentorByTeacher(email).get(1)).append("\n");
            sb.append("Teacher: "+ facade.getTeachers().get(email).toString()).append("\n");
            sb.append("Teacher Count: "+ facade.getTeachers().get(email).getMentorCount()).append("\n");
            sb.append("Average: "+ facade.mentorByTeacher(email).get(3)).append("\n");

        } else {
            sb.append("Min: "+ facade.mentorByTeacher(null).get(0)).append("\n");
            sb.append("Max: "+ facade.mentorByTeacher(null).get(1)).append("\n");
            sb.append("Average: "+ facade.mentorByTeacher(null).get(3)).append("\n");
        }

        return sb.toString();
    }

    private boolean mentorAttribution() {
        StringBuilder sb = new StringBuilder();

        if(facade.studentsMentor().isEmpty())
            return false;

        sb.append("Students without a Mentor Attributed").append("\n");
        for (Student s : facade.studentsNoMentor())
            sb.append(s).append("\n");

        System.out.println("Mentors\n");
        for(Teacher t : facade.getTeachers().values())
            sb.append(t.toString()).append("\n");

        System.out.println(sb.toString());

        String[] values;
        do {
            String delimited = PAInput.readString("Number of Student and Email o: (Write \"quit\" to exit) \n Format: <Student number> <email>", false);
            if(delimited.equalsIgnoreCase("quit"))
                break;
            values = delimited.split(" ");
        } while (!facade.mentorAttribution(values));


        System.out.println("\nSuccess");
        return true;
    }

    private void PhaseThreeStateUI() {
        if (facade.getBlock(3) == StateBlock.BLOCKED) {
            switch (PAInput.chooseOption("Phase 3 - Blocked",
                    "Consult List of Students","Consult with Filters","Back to Phase 2","Move to Phase 4")) {
                case 1 -> ListStudentsUI();
                case 2 -> FilterPhaseThreeUI();
                case 3 -> facade.back();
                case 4 -> facade.next(false);
            }
        }else {
            facade.automaticOneP3(); //TODO: mudar para o switch
            facade.automaticTwoP3();
            if(facade.getState() != AppState.UNTIE){
                switch (PAInput.chooseOption("Phase 3 - Attribution of Proposals",
                        "Manual Attribution", "Consult List of Students", "Consult with Filters","Undo","Redo", "Back to Phase 2", "Move to Phase 4")) {
                    case 1 -> {
                        if(!ManualAttributionUI(facade.getStudents(), facade.getCandidatures()))
                            System.out.println("There are no Proposals or Students available\n");
                    }
                    case 2 -> ListStudentsUI();
                    case 3 -> FilterPhaseThreeUI();
                    case 4 -> facade.undo();
                    case 5 -> facade.redo();
                    case 6 -> facade.back();
                    case 7 -> {
                        if (!facade.next(blockUI()))
                            System.out.println("The Blocking conditions where not met");
                    }
                }
            }
        }
    }

    private void UnTieStateUI() {
        long studentNumber;

        StringBuilder sb = new StringBuilder();

        System.out.println("Tied Students\n");
        for(Student s : facade.getStudentsTie())
            sb.append(s.toString()).append("\n");

        System.out.println(sb.toString());

        studentNumber = PAInput.readLong("Number of choosen Student: ");

        if(!facade.studentsTie(studentNumber))

        facade.tieFA(facade.getTieProposal(), facade.getStudents().get(studentNumber));
        facade.back();
    }

    private void PhaseTwoStateUI() {
        if (facade.getBlock(2) == StateBlock.BLOCKED) {
            switch (PAInput.chooseOption("Phase 2 - Blocked",
                    "Print Candidatures","Print Students lists","Print Proposals","Back to Phase 1", "Move to Phase 3")) {
                case 1 -> System.out.println(printCandidatures(facade.getCandidatures()));
                case 2 -> System.out.println(printPhaseTwo(facade.getStudents(), facade.getTeachers(), facade.getSelfProp(), facade.getCandidatures()));
                case 3 -> FilterPhaseTwoUI();
                case 4 -> facade.back();
                case 5 -> facade.next(false);
            }
        }else {

            switch (PAInput.chooseOption("Phase 2 - Candidatures Options",
                    "Upload CSV", "Consult Candidatures", "Consult Students", "Consult with Filters", "Back to Phase 1", "Move to Phase 3")) {
                case 1 -> {
                    //String nameFile = PAInput.readString("Filename that you want to import Format Example->(filename.txt): ", true);

                    if (!facade.upload("candidaturas.txt")) {
                        System.out.println("Something did not go right");
                    }else
                        System.out.println("Success uploading data");
                }
                case 2 ->  System.out.println(printCandidatures(facade.getCandidatures()));
                case 3 -> System.out.println(printPhaseTwo(facade.getStudents(), facade.getTeachers(), facade.getSelfProp(), facade.getCandidatures()));
                case 4 -> FilterPhaseTwoUI();
                case 5 -> facade.back();
                case 6 -> {
                    if (!facade.next(blockUI()))
                        System.out.println("The Blocking conditions where not met");
                }
            }
        }
    }

    private void ListStudentsUI() {
        boolean flag = false;
        while(!flag)
        switch (PAInput.chooseOption("Phase 3 - Consult List of Students",
                "Students with Self-Proposed Candidatures", "Students with candidature already registered", "Students with Atributted Proposal", "Students without any Atributted Proposal", "Back to Phase 3 Menu")) {
            case 1 -> System.out.println(printAssociatedSelfProposal(facade.getStudents()));
            case 2 -> System.out.println(printRegisteredCandidature(facade.getCandidatures(), facade.getStudents()));
            case 3 -> System.out.println(printProposalAtributted(facade.getFA()));
            case 4 -> System.out.println(printWithoutProposalsAssociated(facade.getStudents()));
            case 5 -> flag = true;
        }
    }

    private boolean ManualAttributionUI(HashMap<Long, Student> students, HashMap<Long, List<String>> candidatures){
        StringBuilder sb = new StringBuilder();
        List<Long> studentsNumber = new ArrayList<>();
        ArrayList<Filtros> filters;
        filters = new ArrayList<>();
        filters.add(Filtros.AVAIABLE);

        if(facade.printFiltros(filters).isEmpty())
             return false;

        sb.append("Students without a Proposal Attributed").append("\n");
        for (Student s : facade.studentsWOPropAssociated()) {
            studentsNumber.add(s.getStudentNumber());
            sb.append(s.toString()).append("\n");
        }

        System.out.println(sb.toString());

        System.out.println("Available Proposals\n");

        System.out.println(printObject(facade.printFiltros(filters)));

        String[] values;
        String delimited = PAInput.readString("Number of Student and Code of Proposal: (Write \"quit\" to exit) \n Format: <Student number> <Proposal idCode>", false);
        if(delimited.equalsIgnoreCase("quit"))
            return false;
        values = delimited.split(" ");

        if (!facade.manualAtribution(values, studentsNumber))
            return false;

        System.out.println("\nSuccess");
        return true;
    }

    private void FilterPhaseThreeUI() {
        ArrayList<Filtros> filters;
        filters = new ArrayList<>();
        boolean flag = false;
        while (!flag) {
            switch (PAInput.chooseOption("Phase 3 - Choose your filters and then quit",
                    "Self Proposed Proposals","Teachers Proposals", "Proposals available","Proposals already attributed","Quit")) {
                case 1 -> filters.add(Filtros.SELFPROP);
                case 2 -> filters.add(Filtros.TEACHERPROP);
                case 3 -> filters.add(Filtros.AVAIABLE);
                case 4 -> filters.add(Filtros.NOTAVAIABLE);
                case 5 -> flag = true;
            }
        }
        System.out.println(printObject(facade.printFiltros(filters)));
        PhaseThreeStateUI();
    }

    private void FilterPhaseTwoUI() {
        ArrayList<Filtros> filters;
        filters = new ArrayList<>();
        boolean flag = false;
        while (!flag) {
            switch (PAInput.chooseOption("Phase 2 - Choose your filters and then quit",
                    "Self Proposed Students","Teachers Proposals", "Candidatures Proposals","Candidatures without Proposals","Quit")) {
                case 1 -> filters.add(Filtros.SELFPROP);
                case 2 -> filters.add(Filtros.TEACHERPROP);
                case 3 -> filters.add(Filtros.CANDIDATUREPROP);
                case 4 -> filters.add(Filtros.CANDIDATUREWOPROP);
                case 5 -> flag = true;
            }
        }
        System.out.println(printObject(facade.printFiltros(filters)));
        PhaseTwoStateUI();
    }

    protected void ChoosePhaseOneUI() {
        if (facade.getBlock(1) == StateBlock.BLOCKED) {
            switch (PAInput.chooseOption("Phase 1 - Blocked",
                    "Print", "Move to Phase 2")) {
                case 1 -> System.out.println(printPhaseOne(facade.getStudents(), facade.getTeachers(), facade.getSelfProp(), facade.getProjects(), facade.getInternships()));
                case 2 -> facade.next(false);
            }
        }else{
            switch (PAInput.chooseOption("Phase 1",
                    "Student Management", "Teacher Management", "PI Management","load","Save",
                    "Advance to Phase 2")) {
                case 1 -> facade.student();
                case 2 -> facade.teacher();
                case 3 -> facade.projectInternship();
                case 4 -> facade.loadM("load.dat");
                case 5 -> facade.saveM("save.dat");
                case 6 -> {
                    if (!facade.next(blockUI()))
                        System.out.println("The Blocking conditions where not met");
                }

            }
        }
    }

    private void ICEEStateUIPI() {
        switch (PAInput.chooseOption("Proposals Management","Upload CSV",
                "Internship Management", "Projects Management", "Self proposed Internship/Project Management","Print All",
                "Back to Phase 1 Menu")) {
            case 1 -> {
              //  String nameFile = PAInput.readString("Filename that you want to import Format Example->(filename.txt): ", true);

                if(!facade.upload("PI.txt"))
                    System.out.println("Something did not go right");
                        else
                            System.out.println("Success uploading data");
            }
            case 2 -> InternshipManagementP1();
            case 3 -> ProjectsManagementP1();
            case 4 -> InternshipSelfProposedProjectManagementP1();
            case 5 -> System.out.println(printPI(facade.getSelfProp(), facade.getProjects(), facade.getInternships()));
            case 6 -> facade.back();
        }
    }

    private void InternshipSelfProposedProjectManagementP1() {
        switch (PAInput.chooseOption("Internship/Self proposed Project Management", "Insert", "Consult", "Erase", "Back to Phase 1 Menu")) {
            case 1 -> InsertSPP();
            case 2 -> System.out.println(printMap(facade.getSelfProp()));
            case 3 -> RemoveIPSP();
            case 4 ->  facade.back();
        }
    }

    private void InsertSPP() {
        String code = PAInput.readString("Identification Code: ", false);
        String title = PAInput.readString("Title: ", true);
        long sNumber = PAInput.readLong("Student identification number: ");

        if(!facade.insertSelpProp(code,sNumber, title))
            System.out.println("Error adding self proposed project");
        else
            System.out.println("self proposed project successfully added");
    }

    private void RemoveIPSP() {
        String code = PAInput.readString("Identification code of the project that you wish to remove: ",true);
        if (facade.remove(code))
            System.out.println("Self-Proposal successfully removed");
        else
            System.out.println("Error removing Self-Proposal");
    }

    private void ProjectsManagementP1() {
        switch (PAInput.chooseOption("Projects Management", "Insert", "Consult", "Erase", "Back to Phase 1 Menu")) {

            //case 2 -> InsertProj();
            case 2 -> System.out.println(printMap(facade.getProjects()));
            case 3 -> RemoveIPSP();
            case 4 -> facade.back();
        }
    }

    /*
    private void InsertProj() {
        String code = PAInput.readString("Identification Code: ", false);
        String branch;
        do{
            branch = PAInput.readString("Minor Branch: ", true);
        }while(!branch.equalsIgnoreCase("DA") && !branch.equalsIgnoreCase("RAS") && !branch.equalsIgnoreCase("SI"));

        String title = PAInput.readString("Title: ", false);
        String email = PAInput.readString("Head Teacher Email: ", true);
        long number = -1;
        switch (PAInput.chooseOption("Do you wish to associate a Student to the project ?",
                "Yes", "No")) {
            case 1 -> number = PAInput.readLong("Student Number: ");
            case 2 -> number = -1;
        }

        if(!fsm.insertPI(code,number,branch.toUpperCase(),title,email))
            System.out.println("Error adding Project");
        else
            System.out.println("Project successfully added");
    }
*/

    private void InternshipManagementP1() {
        switch (PAInput.chooseOption("Internship Management",
                "Insert", "Consult", "Erase", "Back to Phase 1 Menu")) {

           // case 2 -> InsertIntern();
            case 2 -> System.out.println(printMap(facade.getInternships()));
            case 3 -> RemoveIPSP();
            case 4 ->  facade.back();
        }
    }
    /*
    private void InsertIntern() {
        String code = PAInput.readString("Identification Code: ", false);
        String branch;
        do{
            branch = PAInput.readString("Minor Branch: ", true);
        }while(!branch.equalsIgnoreCase("DA") && !branch.equalsIgnoreCase("RAS") && !branch.equalsIgnoreCase("SI"));

        String title = PAInput.readString("Title: ", false);
        String local = PAInput.readString("Internship Location: ", false);
        long number = -1;
        switch (PAInput.chooseOption("Do you wish to associate a Student to the project ?",
                "Yes", "No")) {
            case 1 -> number = PAInput.readLong("Student Number: ");
            case 2 -> number = -1;
        }

        if(!fsm.insertPI(code,number,branch.toUpperCase(Locale.ROOT),title,local))
            System.out.println("Error adding Internship");
        else
            System.out.println("Internship successfully added");
    }
*/

    private void ICEEStateUITeacher() {
        switch (PAInput.chooseOption("Teacher Management",
                "Upload CSV", "Insert", "Consult", "Erase","Edit", "Back to Phase 1 Menu")) {
            case 1 -> {
                //String nameFile = PAInput.readString("Filename that you want to import Format Example->(filename.txt): ", true);

                if (!facade.upload("professores.txt")) {
                    System.out.println("Something did not go right");
                }else
                    System.out.println("Success uploading data");
            }
            case 2 -> InsertTeacher();
            case 3 -> System.out.println(printMap(facade.getTeachers()));
            case 4 -> RemoveTeacher();
            case 5 -> editTeacher();
            case 6 -> facade.back();
        }
    }

    private void RemoveTeacher() {
        String email = PAInput.readString("Email of the Teacher that you wish to remove: ",true);
        if (facade.remove(email))
            System.out.println("Teacher successfully removed");
        else
            System.out.println("Error removing Teacher");
    }

    private void InsertTeacher() {
        String name = PAInput.readString("Teacher Name: ", false);
        String email = PAInput.readString("Teacher Email: ", true);
        if(!facade.insertTeacher(name, email))
            System.out.println("Error adding Teacher");
        else
            System.out.println("Teacher successfully added");
    }

    private void editTeacher() {

        String email = PAInput.readString("Email of the teacher that you wish to edit: ", true);

        if(!facade.getTeachers().containsKey(email)) {
            System.out.println("There is no Teacher with that email\n");
            return;
        }

        switch (PAInput.chooseOption("What do you wish to edit ?",
                "Name", "Email")) {
            case 1 -> facade.setNameTeacher(PAInput.readString("New name of the teacher: ", true),email);
            case 2 -> facade.setEmailTeacher(PAInput.readString("New email of the teacher: ", true),email);

        }
    }

    private void editStudent() {

        long number = PAInput.readLong("Number of the student that you wish to edit: ");

        if(!facade.getStudents().containsKey(number)) {
            System.out.println("There is no Student with that number\n");
            return;
        }

        switch (PAInput.chooseOption("What do you wish to edit ?",
                "Name", "Number")) {
            case 1 -> facade.setNameStudent(PAInput.readString("New name of the student: ", true), number);
            case 2 -> facade.setNumberStudent(PAInput.readLong("New number of the student: "), number);
            case 3 -> {
                facade.setCourseStudent(PAInput.readString("New course of the student: ", true), number);
            }
            case 4 -> {
                facade.setBranchStudent(PAInput.readString("New branch of the student: ", true), number);
            }
            case 5 -> {
                facade.setScoreStudent(PAInput.readString("New score of the student: ", true), number);
            }
            case 6 -> facade.setApplicableInternshipStudent(number);

        }
    }

    private void editCandidatures() {

        String number = PAInput.readString("Number of the student that you wish to edit: ", true);

        if(!facade.getCandidatures().containsKey(Long.parseLong( number))) {
            System.out.println("There is no Student with that number\n");
            return;
        }

        System.out.println("These are the actual candidatures of that student\n\n"+ facade.getCandidatures().get(Long.parseLong(number)).toString());

        String values = PAInput.readString("Write the new code(s) of candidature that you wish - use this sintaxe <P010 P011 P012>", false);

        facade.setCandidatures(values, number);

    }

    private void editMentor() {

        long number = PAInput.readLong("Number of the student that you wish to edit: ");

        if(!facade.getCandidatures().containsKey(number)) {
            System.out.println("There is no Student with that number\n");
            return;
        }

        System.out.println("These is the actual mentor of that student\n\n"+ facade.getStudentFA(number).toString());

        facade.setMentor(PAInput.readString("New Mentor - introduce the email of the new Mentor: ", true), number);

    }


    protected void ICEEStateUIStudent() {
        switch (PAInput.chooseOption("Student Management",
                "Upload CSV", "Insert", "Consult", "Erase","Edit", "Back to Phase 1 Menu")) {
            case 1 -> {
                //String nameFile = PAInput.readString("Filename that you want to import Format Example->(filename.txt): ", true);

                if (!facade.upload("alunos.txt")) {
                    System.out.println("Something did not go right");
                }else
                    System.out.println("Success uploading data");
            }
            case 2 -> InsertStudent();
            case 3 -> System.out.println(printMap(facade.getStudents()));
            case 4 -> RemoveStudent();
            case 5 -> editStudent();
            case 6 -> facade.back();
        }
    }


    protected void InsertStudent() {
        String name = PAInput.readString("Student Name: ", false);
        String email = PAInput.readString("Student Email: ", true);
        Long number = PAInput.readLong("Student Number: ");
        String minor;
        String branch;
        Minor mn ;
        Branches br;
        double score;

        minor = PAInput.readString("Student Minor: ", true);

        branch = PAInput.readString("Minor Branch: ", true);

        score= PAInput.readNumber("Student Score: ");

        boolean internship;
        switch (PAInput.chooseOption("Does the Student has access to Internships ?",
                "Yes", "No")) {
            case 1 -> internship = true;
            case 2 -> internship = false;
            default -> internship = false;
        }



       if(!facade.insertStudent(name, email, number, minor, branch, score,internship))
           System.out.println("Error adding Student");
       else
           System.out.println("Student successfully added");
    }

    protected void RemoveStudent(){
        Long number = PAInput.readLong("Number of the Student that you wish to remove: ");
        if (facade.remove(number))
            System.out.println("Student successfully removed");
        else
            System.out.println("Error removing Student");
    }

    protected void exit(){
        finish = true;
    }


    public <E, T> String printMap(HashMap<E, T> map) {
        StringBuilder sb = new StringBuilder();
        for (T t : map.values())
            sb.append(t.toString()).append("\n");

        return sb.toString();
    }
    public <T> String printObject(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (T t : list)
            sb.append(t.toString()).append("\n");

        return sb.toString();
    }

    public String printPI(HashMap<String, SelfProposed> selfProp, HashMap<String, Project> projects, HashMap<String, Internship> internships) {
        StringBuilder sb = new StringBuilder();

        sb.append(printMap(selfProp)).append(printMap(projects)).append(printMap(internships));
        return sb.toString();
    }

    public String printPhaseOne(HashMap<Long, Student> students,HashMap<String, Teacher> teachers,HashMap<String, SelfProposed> selfProp,HashMap<String, Project> projects,HashMap<String, Internship> internships) {
        StringBuilder sb = new StringBuilder();
        sb.append("Students").append("\n");
        sb.append(printMap(students));
        sb.append("Teacher").append("\n");
        sb.append(printMap(teachers));
        sb.append("Proposals").append("\n");
        sb.append(printMap(selfProp)).append(printMap(projects)).append(printMap(internships));

        return sb.toString();
    }

/*
    public String printCSVP1(HashMap<Long, Student> students,HashMap<String, Teacher> teachers,HashMap<String, SelfProposed> selfProp,HashMap<String, Project> projects,HashMap<String, Internship> internships) {
        StringBuilder sb = new StringBuilder();
        for (Student s : students.values())
            sb.append(s.printCSV()).append("\n");

        for (Teacher s : teachers.values())
            sb.append(s.printCSV()).append("\n");

        for (Project s : projects.values())
            sb.append(s.printCSV()).append("\n");

        for (Internship s : internships.values())
            sb.append(s.printCSV()).append("\n");

        for (SelfProposed s : selfProp.values())
            sb.append(s.printCSV()).append("\n");

        return sb.toString();
    }
*/
    public String printPhaseTwo(HashMap<Long, Student> students,HashMap<String, Teacher> teachers,HashMap<String, SelfProposed> selfProp,HashMap<Long, List<String>> candidatures) {
        StringBuilder sb = new StringBuilder();

        if(!selfProp.isEmpty()) {
            sb.append("Sel Proposed Students").append("\n");
            for (SelfProposed sp : selfProp.values())
                sb.append(students.get(sp.getStudentNumber())).append("\n");
        }
        if(!candidatures.isEmpty()) {
            sb.append("Students with candidature already registered").append("\n");
            for (long c : candidatures.keySet())
                sb.append(students.get(c)).append("\n");
        }

        if(!facade.studentsWOCandidature().isEmpty()) {
            sb.append("Students without candidature").append("\n");
            for (Student s : facade.studentsWOCandidature()) {
                sb.append(s).append("\n");
            }
        }
        return sb.toString();
    }

    public String printCandidatures(HashMap<Long, List<String>> candidatures) {
        StringBuilder sb = new StringBuilder();
        for (long c : candidatures.keySet()) {
            sb.append("          Student Number [").append(c).append("]");
            sb.append("          Proposals Codes [").append( candidatures.get(c).toString().substring(1,  candidatures.get(c).toString().length() - 1)).append("]").append("\n");
        }
        return sb.toString();

    }

    public String printAssociatedSelfProposal(HashMap<Long, Student> students) {
        StringBuilder sb = new StringBuilder();
        sb.append("Students with Self-Proposed Candidatures").append("\n");
        for (Student s : facade.studentsSelfPropCandidature()) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public String printRegisteredCandidature(HashMap<Long, List<String>> candidatures, HashMap<Long, Student> students) {
        StringBuilder sb = new StringBuilder();
        sb.append("Students with candidature already registered").append("\n");
        for (long c : candidatures.keySet())
            sb.append(students.get(c)).append("\n");

        return sb.toString();
    }

    public String printProposalAtributted(List<FinalAtribution> FA) {
        StringBuilder sb = new StringBuilder();
        sb.append("Students with Atributted Proposal").append("\n");
        for (FinalAtribution fa : FA)
            sb.append(fa.getStudent()).append("\n");

        return sb.toString();
    }

    public String printWithoutProposalsAssociated(HashMap<Long, Student> students) {
        StringBuilder sb = new StringBuilder();
        sb.append("Students without any Atributted Proposal").append("\n");
        for (Student s : facade.studentsWOPropAssociated())
            sb.append(s.toString()).append("\n");

        return sb.toString();
    }

}