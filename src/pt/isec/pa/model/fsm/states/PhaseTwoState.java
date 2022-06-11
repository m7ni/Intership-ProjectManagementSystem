package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.data.Types;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;
import pt.isec.pa.model.data.Filtros;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class PhaseTwoState extends AppStateAdapter implements Serializable {
    public PhaseTwoState(AppContext context, AppData data) {
        super(context,data);
    }

    @Override
    public boolean upload(File file) {
        return data.uploadCSV(file, Types.CANDIDATURE);
    }

    public AppState getState() {
        return AppState.PHASE_TWO;
    }

    @Override
    public boolean next(Boolean block) {

        if(block && (data.getBlock(2)== StateBlock.UNLOCKED)){ //wants to block and is not already blocked
            if(data.getBlock(1)== StateBlock.UNLOCKED) //block conditions are not met
                return false;
            else{
                data.setBlock(2);  //block conditions are met, and we block
                data.exportCSVP2("PhaseTwo.txt");
            }
        }
        changeState(AppState.PHASE_THREE);
        return true;
    }
    @Override
    public String toString() {
        return "Phase TWO";
    }
    @Override
    public List<Proposals> printFiltro(List<Filtros> filtros) {
        return data.printFiltros(filtros,AppState.PHASE_TWO);
    }

    @Override
    public boolean back() {

         changeState(AppState.CHOOSE_PHASE_ONE);
         return true;
    }
}
