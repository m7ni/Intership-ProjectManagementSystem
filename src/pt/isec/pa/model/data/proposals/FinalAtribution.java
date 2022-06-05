package pt.isec.pa.model.data.proposals;

import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;

import java.io.Serializable;

public class FinalAtribution implements Serializable {
    private Teacher mentor;
    private Proposals finalP;
    private Student student;

    public FinalAtribution(Proposals finalP, Student student) {
        this.finalP = finalP;
        this.student = student;
    }

    public Teacher getMentor() {
        return mentor;
    }

    public void setMentor(Teacher mentor) {
        this.mentor = mentor;
    }

    public Proposals getFinalP() {
        return finalP;
    }

    public void setFinalP(Proposals finalP) {
        this.finalP = finalP;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Student \n").append(getStudent()).append("\n");
        sb.append("Mentor \n").append(getMentor()).append("\n");
        sb.append("Proposal \n").append(getFinalP()).append("\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof FinalAtribution) )
            return false;

        FinalAtribution aux = (FinalAtribution) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return getFinalP().hashCode();
    }
}
