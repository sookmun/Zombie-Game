package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

/**
 * Special action for harvesting crops and turning into food
 * Author: Tan Sook Mun
 */
public class HarvestAction extends Action {
    /**
     * Get the all items on the ground and if there is a crop with Ripe capability it will harvest and drop food
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return description of the action that had been executed
     */
    @Override
    public String execute(Actor actor, GameMap map){
        List<Item> groundItems= map.locationOf(actor).getItems();
        for (Item item : groundItems){
            if (item.hasCapability(CropCapability.Ripe)){
                map.locationOf(actor).removeItem(item);
                map.locationOf(actor).addItem(new Food());
                return actor.toString() + " harvest crop into food";
            }
        }
        return null;

    }

    @Override
    public String menuDescription(Actor actor){return actor.toString() + " harvest the crop";}
}
