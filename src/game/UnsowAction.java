package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that enable zombie and human to damage crop and turn them back to dirt
 */
public class UnsowAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        if (map.locationOf(actor).getGround().getDisplayChar()-'C'==0){
            map.locationOf(actor).setGround(new Dirt());
        }
        return actor + " damaged the crop and turn it back to dirt";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " damages the crop and turn it back to dirt";
    }
}
