package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

/**
 * An action for actors to eat food
 */

public class EatAction extends Action {
    /**
     * Eating the food will restore 5 health points
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String description of executed action
     * @author Tan Sook Mun
     */
    @Override
    public String execute(Actor actor, GameMap map){
        List<Item> inventory = actor.getInventory();
        for(Item item : inventory){
            if (item.getDisplayChar() - 'F' == 0){
                actor.removeItemFromInventory(item); // remove the food
                actor.heal(5); //eating it will add health points
                return actor.toString() + " eat food and 5 health points restored";
            }
        }
        return null;
    }

    /**
     * Action description
     * @param actor The actor performing the action.
     * @return String description of action
     */
    @Override
    public String menuDescription(Actor actor){
        return actor.toString() + " eats food";
    }
}
