package pt.isec.pa.model.data.personel;

import java.io.Serializable;

public abstract class Person implements Serializable {
    static final long serialVersionUID = 100L;
    private String name;
    private String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------> Name [").append(name).append("]").append("\n");
        sb.append("          Email [").append(email).append("]").append("\n");

        return sb.toString();
    }


    public String printCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(email);

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
