package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A sow action. To sow crops
 * Author:Tan Sook Mun
 */
public class SowAction extends Action {
    /**
     * add a new item, Crop on the ground
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String description of executed action
     */
    @Override
    public String execute(Actor actor, GameMap map){
        //making sure it is a ground
        assert map.locationOf(actor).getGround().getDisplayChar()-'.'==0;

        Item crop = new Crop();
        map.locationOf(actor).addItem(crop);
        return actor.toString() + "sowed the ground";
    }

    @Override
    public String menuDescription(Actor actor){return actor.toString() + " sow the ground"; }
}
