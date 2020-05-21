package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

/**
 * Special action that allow player to craft a weapon
 * Author:Tan Sook Mun
 */

public class CraftAction extends Action {
    /**
     * Checks if there is zombie limbs and craft it
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string description of what had been done
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        List<Item> inventory = actor.getInventory();
        for (Item item : inventory) {
            //if there is a zombie arm inside
            if (item.getDisplayChar() - 'a' == 0) {
                actor.removeItemFromInventory(item);
                actor.addItemToInventory(new ZombieClub());
                return "Player crafted a Zombie Club";
            }
            //if there is a zombie leg inside
            else if (item.getDisplayChar() - 'l'==0){
                actor.removeItemFromInventory(item);
                actor.addItemToInventory(new ZombieMace());
                return "Player Crafted a Zombie Mace";
            }
        }

        return "Player cant craft because there is no limb";
    }
    @Override
    public String menuDescription (Actor actor){
            return actor + " craft Zombie limbs into weapon";
        }
}
