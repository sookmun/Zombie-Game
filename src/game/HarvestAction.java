package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Special action for harvesting crops and turning into food
 * Author: Tan Sook Mun
 */
public class HarvestAction extends Action {
    private int[][] values ={{1,-1},{1,0},{1+1},{0,-1},{0,0},{0,1},{-1,1},{-1,0},{-1,-1}};
    /**
     * Get the all items on the ground and if there is a crop with Ripe capability it will harvest and drop food
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return description of the action that had been executed
     */
    @Override
    public String execute(Actor actor, GameMap map){
        AroundLocation location = new AroundLocation(actor,map);
        for(Location loca : location.getLocation(actor,map) ){
            if(loca.getGround().hasCapability(CropCapability.Ripe)){
                loca.setGround(new Dirt());
                if(actor instanceof Farmer) // if is a farmer drop to the ground
                    loca.addItem(new Food());
                else // if is a player add into inventory
                    actor.addItemToInventory(new Food());
                return actor.toString() + " harvest crop into food";
            }

        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor){return actor.toString() + " harvest the crop";}


}
