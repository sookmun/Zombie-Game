package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import java.util.List;

/**
 * An action to fertilize the crops
 */
public class FertilizeAction extends Action {
    /**
     * Get the crop from the ground and call fertilize method in crop class to reduce number of turn to ripe
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String description of executed action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (map.locationOf(actor).getGround().hasCapability(CropCapability.Unripe)) {
            if (map.locationOf(actor).getGround() instanceof Crop) {
                ((Crop) map.locationOf(actor).getGround()).fertilize();
                return actor.toString() + " fertilized crops";
            }
        }
        return null;
    }

    /**
     * Menu Description for the action
     * @param actor The actor performing the action.
     * @return String description of action
     */
    @Override
    public String menuDescription(Actor actor){
        return actor.toString() + "fertilize the crop";
    }


}
