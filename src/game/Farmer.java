package game;
import edu.monash.fit2099.engine.*;

public class Farmer extends Human {
    private Behaviour[] behaviours = {
            new FarmBehaviour(),
            new WanderBehaviour()};

    public Farmer(String name, int hitpoints) {
        super(name, 'F', hitpoints);
    }

    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display){
        for(Behaviour behaviour : behaviours){
            Action action = behaviour.getAction(this,map);
            if( action != null){
                return action;
            }
        }
        return new DoNothingAction();
    }




}
