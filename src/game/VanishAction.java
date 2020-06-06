package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class VanishAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return (actor + " vanished.");
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " vanished";
    }
}
