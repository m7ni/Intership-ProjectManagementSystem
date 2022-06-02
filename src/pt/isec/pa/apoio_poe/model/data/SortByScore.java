package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.personel.Student;

import java.util.Comparator;

public class SortByScore implements Comparator<Student> {

    public int compare(Student a, Student b)
    {
        return b.getScore().compareTo(a.getScore());
    }

}
