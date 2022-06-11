package pt.isec.pa.model.fsm.states;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.StateBlock;
import pt.isec.pa.model.fsm.AppContext;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.AppStateAdapter;

import java.io.Serializable;

public class ChoosePhaseOne extends AppStateAdapter implements Serializable {

    public ChoosePhaseOne(AppContext context, AppData data) {
        super(context, data);
    }

    @Override
    protected void changeState(AppState newState) {
        super.changeState(newState);
    }

    @Override
    public void teacher() {
        changeState(AppState.PONE_TEACHER);
    }

    @Override
    public void PI() {
        changeState(AppState.PONE_PI);
    }

    public void student(){
        changeState(AppState.PONE_STUDENT);
    }

    public AppState getState() {
        return AppState.CHOOSE_PHASE_ONE;
    }

    @Override
    public String toString() {
        return "Phase ONE";
    }

    @Override
    public boolean next(Boolean block) {

        if(block && (data.getBlock(1)== StateBlock.UNLOCKED)){ //wants to block and is not already blocked
            if(!data.blockPhaseOne()) //block conditions are not met
                return false;
            else{
                data.setBlock(1);  //block conditions are met, and we block
                data.exportCSVP1("PhaseOne.txt");
            }

        }
            changeState(AppState.PHASE_TWO);

        return true;
    }
}

