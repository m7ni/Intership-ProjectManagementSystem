package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.Serializable;

public class PhaseFourState extends AppStateAdapter implements Serializable {

    public PhaseFourState(AppContext context, AppData data) {
        super(context,data);
    }



    public AppState getState() {
        return AppState.PHASE_FOUR;
    }


    @Override
    public boolean next(Boolean block) {

        if(block){ //wants to block and is not already blocked
            data.setBlock(4);  //block conditions are met, and we block
            data.exportCSVP34("PhaseFour.txt",AppState.PHASE_FOUR);
        }
        changeState(AppState.PHASE_FIVE);
        return true;
    }
    @Override
    public boolean back() {

        changeState(AppState.PHASE_THREE);
        return true;
    }
/*
    @Override
    public boolean next(Boolean block) {
        if(data.getBlock(1))
            changeState(AppState.PHASE_THREE);
        else
            return false;

        return true;
    }
    */

}
