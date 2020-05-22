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
        List<Item> groundItem = map.locationOf(actor).getItems();
        for (Item item : groundItem) {
            if (item instanceof Crop) {
                ((Crop) item).fertilize();
                return actor.toString() + " fertilized crops";
            }
        }
        return "No Crops to fertilized";
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
