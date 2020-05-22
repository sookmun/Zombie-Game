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
    private int[][] values = {{1, -1}, {1, 0}, {1 + 1},
            {0, -1}, {0, 0}, {0, 1},
            {-1, 1}, {-1, 0}, {-1, -1}};

    private Action[] farmactions = {new SowAction(), new HarvestAction()};

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
        AroundLocation location = new AroundLocation(actor,map);

        if (map.locationOf(actor).getGround().hasCapability(CropCapability.Unripe)) {
            return new FertilizeAction();
        }

        for (Location locate : location.getLocation(actor,map)){
            if(locate.getGround().hasCapability(CropCapability.Ripe)){
                return new HarvestAction();
            }
            if(rand.nextDouble()<=0.33){
                return new SowAction();
            }
        }


        return null;

//        List<Item> groundItems = map.locationOf(actor).getItems();
//
//        for (Item item : groundItems) {
//            if (item.hasCapability(CropCapability.Ripe)) {
//                return new HarvestAction();
//            }
//            else if (item.hasCapability(CropCapability.Unripe)) {
//                return new FertilizeAction();
//            }
//
//        }
//        if (groundItems.size()==0) { // if there is no items on it sow but you can still put items on a crop
//            if (rand.nextDouble() <= 0.33) {
//                return new SowAction();
//            }
//        }
//        return null;


    }

    public ArrayList<Location> getLocation(Actor actor, GameMap map){
        int x = map.locationOf(actor).x();
        int y = map.locationOf(actor).y();
        ArrayList<Location> locations = new ArrayList<>();
        if (map.getXRange().contains(x+1)){
            locations.add(map.at(x+1,y));
        }
        if (map.getYRange().contains(y+1)){
            locations.add(map.at(x,y+1));
        }
        if (map.getYRange().contains(y+1)){
            locations.add(map.at(x,y-1));
        }
        if (map.getYRange().contains(y+1)){
            locations.add(map.at(x-1,y));
        }
        return locations;
    }

}
