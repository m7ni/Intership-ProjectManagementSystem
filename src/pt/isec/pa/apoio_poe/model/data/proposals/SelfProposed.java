package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.Branches;

import java.util.List;

public class SelfProposed extends Proposals{

    public SelfProposed(String idCode, long number, String title) {
        super(idCode, title, number);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof SelfProposed) )
            return false;

        SelfProposed aux = (SelfProposed) obj;

        if(this.hashCode() == aux.hashCode())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return getIdCode().hashCode();
    }

    @Override
    public List<Branches> getBranch() {
        return null;
    }
}
