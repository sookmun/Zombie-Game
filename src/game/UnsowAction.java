package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that enable zombie and human to damage crop and turn them back to dirt
 */
public class UnsowAction extends Action {
    /**
     * When actor is standing on a crop it may damage and turn into dirt
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String description of action
     * @author Lai Ying Ying
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (map.locationOf(actor).getGround().getDisplayChar()-'C'==0){
            map.locationOf(actor).setGround(new Dirt());
        }
        return actor + " damaged the crop and turn it back to dirt";
    }

    /**
     * Menu description for unsowAction
     * @param actor The actor performing the action.
     * @return String description of action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " damages the crop and turn it back to dirt";
    }
}
