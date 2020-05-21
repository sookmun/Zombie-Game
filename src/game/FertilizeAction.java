package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class FertilizeAction extends Action {

    public String execute(Actor actor, GameMap map){
        return null;
    }

    public String menuDescription(Actor actor){
        return actor.toString() + "fertilize the crop";
    }
}
