package game;
import edu.monash.fit2099.engine.*;

/**
 * A human actor that is a farmer
 * Author: Tan Sook Mun
 */
public class Farmer extends Human {
    private Behaviour[] behaviours = {
            new FarmBehaviour(),
            new WanderBehaviour()};

    /**
     * constructor
     * @param name name of the farmer
     */
    public Farmer(String name) {
        super(name, 'f', 50);
    }

    /**
     * Farmer has farm behavior and wander behaviour.
     * @param actions list of action
     * @param lastAction the last action the farmer made
     * @param map map of game
     * @param display display
     * @return action the farmer will execute
     */
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display){
        //make the game harder by making farmer move around more
        if(lastAction instanceof SowAction) //make the farmer move or else in the next turn it will fertilize
            return new WanderBehaviour().getAction(this,map);
        else if (lastAction instanceof FertilizeAction)
            return new WanderBehaviour().getAction(this,map);
        else {
            for (Behaviour behaviour : behaviours) {
                Action action = behaviour.getAction(this, map);
                if (action != null) {
                    return action;
                }
            }
        }
        return new DoNothingAction();
    }




}
