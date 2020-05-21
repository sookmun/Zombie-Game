package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import java.util.List;
import java.util.Random;

/**
 * A class that will check if it is standing new to a bare ground, unriped crop or ripe crop and creates the
 * appropriate action.
 * Author: Tan Sook Mun
 */
public class FarmBehaviour implements Behaviour {
    private Random rand = new Random();

    /**
     * If farmer stand on a ripe crop it will return a new HarvestAction. If farmer stands on Unripe Crop it will fertilize
     * If farmer stands on bare earth it will hv 33% chance of sowing
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return action to be executed
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        List<Item> groundItems = map.locationOf(actor).getItems();

        for (Item item : groundItems) {
            if (item.hasCapability(CropCapability.Ripe)) {
                return new HarvestAction();
            }
            else if (item.hasCapability(CropCapability.Unripe)) {
                return new FertilizeAction();
            }

        }
        if (groundItems.size()==0) { // if there is no items on it sow but you can still put items on a crop
            if (rand.nextDouble() < 0.33) {
                return new SowAction();
            }
        }
        return null;


    }
}
