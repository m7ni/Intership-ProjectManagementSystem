package pt.isec.pa.model.data;


import pt.isec.pa.model.data.personel.Teacher;

import java.io.Serializable;
import java.util.Comparator;

public class SortByMentorCount implements Comparator<Teacher>, Serializable {

    public int compare(Teacher a, Teacher b)
    {
        Double mentorA= (double) a.getMentorCount();
        Double mentorB= (double) b.getMentorCount();
        return mentorB.compareTo(mentorA);
    }

}
