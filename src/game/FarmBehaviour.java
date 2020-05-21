package game;

import com.sun.scenario.effect.Crop;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Random;

public class FarmBehaviour implements Behaviour {
    private Random rand = new Random();
    private Boolean flah=false;

    public Action getAction(Actor actor, GameMap map) {
        List<Item> groundItems = map.locationOf(actor).getItems();

        for (Item item : groundItems) {
            if (item.hasCapability(CropCapability.Ripe)) {
                return new HarvestAction();
            }
//            else if (item.hasCapability(CropCapability.Unripe)) {
//                return new FertilizeAction();
//            }

        }

        if (groundItems.size()==0) { // if there is no items on it sow but you can still put items on a crop
            if (rand.nextDouble() < 0.80) {
                return new SowAction();
            }
        }
        return null;


    }
}
