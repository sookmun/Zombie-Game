package game;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.monash.fit2099.engine.*;
import java.util.List;
import java.util.Random;

public class TradeAction extends Action {
    /**
     * The Actor to trade with
     */
    protected Actor target;
    protected boolean food;
    protected Random rand = new Random();

    /**
     * A constuctor for trade action
     * @param target the actor to trade with
     * @param Food  true is there is food and flase if not
     */
    public TradeAction(Actor target, boolean Food) {
        this.target = target;
        this.food = Food;
    }


    /**
     * Checks the probability of the actor trading with Player. if human has at least a weapon it will be able to trade with human
     * Human is able to refuse trade. When player trade with weapon and food the probability is higher
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String statment of trad action
     * @author Lai Ying Ying
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int probability = 0;
        if (food){
            probability = 75;
        }else{
            probability = 50;
        }
        boolean trade = (rand.nextInt(100)+1 <= probability);   // probability of human agreeing to trade

        if (trade){
            Item actor_weapon = null;
            Item target_weapon = null;

            List<Item> actor_inventory = actor.getInventory();
            List<Item> target_inventory = target.getInventory();
            for (Item item : actor_inventory) {  // remove weapon from actor's inventory
                if (item instanceof WeaponItem) {
                    actor.removeItemFromInventory(item);
                    actor_weapon = item;
                    break;
                }
            }
            for (Item item : target_inventory) {     // remove weapon from target's inventory
                if (item instanceof WeaponItem) {
                    target.removeItemFromInventory(item);
                    target_weapon = item;
                    break;
                }
            }
            actor.addItemToInventory(target_weapon); // add target's weapon to actor's inventory
            target.addItemToInventory(actor_weapon);  // add actor's weapon to target's inventory

            if (food){
                for (Item item : actor_inventory) {     // remove food
                    if (item instanceof Food) {
                        actor.removeItemFromInventory(item);    // remove food from target's inventory
                        break;
                    }
                }
                target.addItemToInventory(new Food());
                return (actor + " traded " + actor_weapon + " and food with " + target + "'s " + target_weapon);
            }
            return (actor + " traded " + actor_weapon + " with " + target + "'s " + target_weapon);
        }
        return (target + " refuses to trade with " + actor);
    }

    /**
     *
     * @param actor The actor performing the action.
     * @return String description of action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " requests to trade with " + target;
    }
}
