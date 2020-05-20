package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;


public class CraftAction extends Action {

    public String execute(Actor actor, GameMap map) {
        List<Item> inventory = actor.getInventory();
        for (Item item : inventory) {
            //if there is a zombie arm inside
            if (item.getDisplayChar() - 'a' == 0) {
                item.getDropAction().execute(actor, map);
                map.locationOf(actor).removeItem(item);
                Item zombieClub = new ZombieClub();
                zombieClub.getPickUpAction().execute(actor, map);
                return "Player crafted a Zombie Club";
            }
            //if there is a zombie leg inside
            else if (item.getDisplayChar() - 'l'==0){
                item.getDropAction().execute(actor, map);
                map.locationOf(actor).removeItem(item);
                Item zombieMace = new ZombieMace();
                zombieMace.getPickUpAction().execute(actor, map);
                return "Player Crafted a Zombie Mace";
            }
        }
        return "Player cant craft because there is no limbs";
    }

    public String menuDescription (Actor actor){
            return actor + " craft Zombie limbs into weapon";
        }
}
