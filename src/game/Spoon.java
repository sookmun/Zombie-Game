package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.WeaponItem;

import java.util.Random;

/**
 * A handy spoon
 */
public class Spoon extends WeaponItem {
    private int count = 0;
    private final int max = 15;
    private Random rand = new Random();

    /**
     * A spoon constructor
     */
    public Spoon(){
        super("A Spoon", 'p',80, "wallop");
    }

    /**
     * Overide tick method to count the number of turns. After every 15 turns, probability of 30% of adding food into inventory
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     * @author Tan Sook Mun
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        count+=1;
        if (count>=max){
            if (rand.nextDouble() <0.3){
                actor.addItemToInventory(new Food());
                count=0;
            }
        }
    }


}
