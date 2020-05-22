package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
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
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return action to be executed
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        //can only fertilize on ground that is standing on
        AroundLocation location = new AroundLocation(actor, map);

        if (map.locationOf(actor).getGround().hasCapability(CropCapability.Unripe)) {
            return new FertilizeAction();
        }

        for (Location locate : location.getLocation(actor, map)) {
            if (locate.getGround().hasCapability(CropCapability.Ripe)) {
                return new HarvestAction();
            }

        }
        if (rand.nextDouble() <= 0.33) {
            return new SowAction();
        }


        return null;

    }
}
