package pt.isec.pa.model.data.proposals;

import pt.isec.pa.model.data.Branches;

import java.io.Serializable;
import java.util.List;

public abstract class Proposals implements Serializable {
    static final long serialVersionUID = 100L;
    private String idCode;
    private String title;
    private long studentNumber; //Student number that got the proposal(only valid to sel-prop and projects)
    private boolean hasAssignedStudent; //if true, then the proposal already has a student and the "studentNumber" has the number of the student

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setID(String newID) {
        this.idCode = newID;
    }

    public Proposals(String idCode, String title, long number) {
        this.idCode = idCode;
        this.title = title;
        this.studentNumber = number;
    }

    public void setHasAssignedStudent(boolean hasAssignedStudent) {
        this.hasAssignedStudent = hasAssignedStudent;
    }

    public boolean getHasAssignedStudent() {
        return hasAssignedStudent;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------> Id Code [").append(idCode).append("]").append("\n");
        sb.append("          Title [").append(title).append("]").append("\n");
        if(studentNumber!=-1)
            sb.append("          Number of Student [").append(studentNumber).append("]").append("\n");
        return sb.toString();
    }

    public String printCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(idCode).append(",").append(title).append(",").append(studentNumber);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof Project) )
            return false;

        Proposals aux = (Proposals) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return idCode.hashCode();
    }

    public abstract List<Branches> getBranch();

    public void setTitle(String title) {
        this.title = title;
    }
}
