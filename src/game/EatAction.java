package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

public class EatAction extends Action {

    public String execute(Actor actor, GameMap map){
        List<Item> inventory = actor.getInventory();
        for(Item item : inventory){
            if (item.getDisplayChar() - 'F'==0){
                actor.removeItemFromInventory(item); // remove the food
                actor.heal(5); //eating it will add health points
                return actor.toString() + " eat food and 5 health points restored";
            }
        }
        return null;
    }

    public String menuDescription(Actor actor){return actor.toString() + " eats food";}
}
