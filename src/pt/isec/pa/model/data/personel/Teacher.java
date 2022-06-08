package pt.isec.pa.model.data.personel;

public class Teacher extends Person {
    private int mentorCount;

    public Teacher(String name, String email) {
        super(name, email);
        mentorCount =0;
    }

    public void upMentorCount() {
        this.mentorCount ++;
    }
    public void downMentorCount() {
        this.mentorCount ++;
    }

    public int getMentorCount() {
        return mentorCount;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof Teacher) )
            return false;

        Teacher aux = (Teacher) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
