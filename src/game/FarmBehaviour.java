package game;

import com.sun.scenario.effect.Crop;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Random;

public class FarmBehaviour implements Behaviour{
    private Random rand= new Random();

    public Action getAction(Actor actor, GameMap map){
        List<Item> groundItems =  map.locationOf(actor).getItems();

        for(Item item : groundItems){
            if(item.hasCapability(CropCapability.Ripe)){
                //return a harvest action
                return null;
            }

        if(map.locationOf(actor).getItems() == null && map.locationOf(actor).getGround().hasCapability(DirtCapability.NORMALDIRT)){ // if there is no items on it
            if (rand.nextDouble()<0.33) {
                return new SowAction();
            }
        }

        else if (map.locationOf(actor).getGround().hasCapability(DirtCapability.CROPSDIRT)){
            //return fertilize action
            return null;
        }

        }
        else{
            return null;
        }

    }
}
