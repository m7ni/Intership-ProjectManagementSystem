package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.Branches;

import java.util.List;

public class Project extends Proposals {
    private List<Branches> branch;
    private String tEmail;
    public Project(String idCode, long number, List<Branches> branch, String title, String tEmail) {
        super(idCode, title, number);
        this.branch = branch;
        this.tEmail = tEmail;
    }

    public void setBranch(List<Branches> branch) {
        this.branch = branch;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        String strbranch = branch.toString();
        sb.append("          Branch [").append(strbranch.substring(1,strbranch.length()-1)).append("]").append("\n");
        sb.append("          Teacher Email [").append(tEmail).append("]").append("\n");
        return sb.toString();
    }

    public String gettEmail() {
        return tEmail;
    }

    @Override
    public String printCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.printCSV());
        String strbranch = branch.toString();
        sb.append(",").append(getIdCode()).append(",").append(strbranch.substring(1,strbranch.length()-1)).append(",").append(tEmail).append(",");

        return sb.toString();
    }

    @Override
    public List<Branches> getBranch() {
        return branch;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof Project) )
            return false;

        Project aux = (Project) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return getIdCode().hashCode();
    }

}
