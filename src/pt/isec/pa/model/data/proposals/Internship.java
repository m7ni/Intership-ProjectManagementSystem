package pt.isec.pa.model.data.proposals;

import pt.isec.pa.model.data.Branches;

import java.util.List;

public class Internship extends Proposals {
    private List<Branches> branch;
    private String local;

    public List<Branches> getBranch() {
        return branch;
    }

    public Internship(String idCode, long number, List<Branches> branch, String title, String local) {
        super(idCode, title, number);
        this.branch = branch;
        this.local = local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setBranch(List<Branches> branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        String strbranch = branch.toString();
        sb.append("          Branch [").append(strbranch.substring(1,strbranch.length()-1)).append("]").append("\n");
        sb.append("          Local [").append(local).append("]").append("\n");
        return sb.toString();
    }

    @Override
    public String printCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.printCSV());
        String strbranch = branch.toString();
        sb.append(",").append(strbranch.substring(1,strbranch.length()-1)).append(",").append(getTitle()).append(",").append(local);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof Internship) )
            return false;

        Internship aux = (Internship) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return getIdCode().hashCode();
    }
}
