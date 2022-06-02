package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.util.List;

public class PhaseThreeState extends AppStateAdapter {

    public PhaseThreeState(AppContext context, AppData data) {
        super(context,data);
    }

    public AppState getState() {
        return AppState.PHASE_THREE;
    }

    @Override
    public List<Proposals> printFiltro(List<Filtros> filtros) {
        return data.printFiltros(filtros,AppState.PHASE_THREE);
    }

    @Override
    public boolean unTie() {
        if(!data.automaticTwoP3Projects()){
            changeState(AppState.UNTIE);
        }
        return true;
    }

    @Override
    public boolean next(Boolean block) {

        if(block && (data.getBlock(3)== StateBlock.UNLOCKED)){ //wants to block and is not already blocked
            if(!data.blockPhaseThree()) //block conditions are not met
                return false;
            else{
                data.setBlock(3);  //block conditions are met, and we block
                data.exportCSVP34("PhaseThree.txt",AppState.PHASE_THREE);
            }
        }

        data.automaticMentorPhaseFour();
        changeState(AppState.PHASE_FOUR);
        return true;
    }

    @Override
    public boolean back() {
        changeState(AppState.PHASE_TWO);
        return true;
    }
}