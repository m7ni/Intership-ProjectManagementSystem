package pt.isec.pa.model.data;

import pt.isec.pa.model.data.personel.Student;

import java.io.Serializable;
import java.util.Comparator;

public class SortByScore implements Comparator<Student>, Serializable {

    public int compare(Student a, Student b)
    {
        return b.getScore().compareTo(a.getScore());
    }

}
