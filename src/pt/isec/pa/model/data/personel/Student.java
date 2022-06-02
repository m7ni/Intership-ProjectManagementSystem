package pt.isec.pa.model.data.personel;

import pt.isec.pa.model.data.Branches;
import pt.isec.pa.model.data.Minor;

import java.util.ArrayList;

public class Student extends Person {
    private Long studentNumber;
    private Minor minor;
    private Branches branch;
    private Double score;
    private Boolean internship;  //true if the student is able to access internships
    private ArrayList<String> proposals;
    private boolean assignedProposal; //true if a self-prop, project or internship  is submitted with this student's number

    public boolean isAssignedProposal() {
        return assignedProposal;
    }

    public void setAssignedProposal(boolean assignedProposal) {
        this.assignedProposal = assignedProposal;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public Student(String name, String email, long studentNumber, Minor minor, Branches branch, Double score, Boolean internship) {
        super(name, email);
        this.studentNumber = studentNumber;
        this.minor = minor;
        this.branch = branch;
        this.score = score;
        this.internship = internship;
        assignedProposal = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append("          Student Number [").append(studentNumber).append("]").append("\n");
        sb.append("          Course [").append(minor).append("]").append("\n");
        sb.append("          Branch [").append(branch).append("]").append("\n");
        sb.append("          Score [").append(score).append("]").append("\n");
        sb.append("          Applicable to internships ? [").append(internship).append("]").append("\n");

        return sb.toString();
    }

    @Override
    public String printCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.printCSV());

        sb.append(",").append(studentNumber).append(",").append(minor).append(",").append(branch).append(",").append(score).append(",").append(internship);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof Student) )
            return false;

        Student aux = (Student) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Minor getMinor() {
        return minor;
    }

    public void setMinor(Minor minor) {
        this.minor = minor;
    }

    public Branches getBranch() {
        return branch;
    }

    public void setBranch(Branches branch) {
        this.branch = branch;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getInternship() {
        return internship;
    }

    public void setInternship() {
        this.internship = !this.internship;
    }
    @Override
    public int hashCode() {
        return studentNumber.hashCode();
    }

}
