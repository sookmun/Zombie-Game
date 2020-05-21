package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

public class HarvestAction extends Action {

    public String execute(Actor actor, GameMap map){
        List<Item> groundItems= map.locationOf(actor).getItems();
        for (Item item : groundItems){
            if (item.hasCapability(CropCapability.Ripe)){
                map.locationOf(actor).removeItem(item);
                map.locationOf(actor).addItem(new Food());
                return actor.toString() + "harvest crop into food";
            }
        }
        return null;

    }

    public String menuDescription(Actor actor){return actor.toString() + "harvest the crop";}
}
