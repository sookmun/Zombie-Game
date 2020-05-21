package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class SowAction extends Action {

    public String execute(Actor actor, GameMap map){
        //making sure it is a ground
        assert map.locationOf(actor).getGround().getDisplayChar()-'.'==0;

        Item crop=new Crop();
        map.locationOf(actor).addItem(crop);
        return actor.toString() + "sowed the ground";
    }

    public String menuDescription(Actor actor){return actor.toString() + "sow the ground"; }
}
