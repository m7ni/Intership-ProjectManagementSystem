package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.AppData;
import pt.isec.pa.apoio_poe.model.data.StateBlock;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.AppStateAdapter;

public class ChoosePhaseOne extends AppStateAdapter {

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

