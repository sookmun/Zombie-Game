package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that enabled MamboMarie to vanish from the map
 */
public class VanishAction extends Action {
    /**
     * An action where removes mambo marie from map so she "vanish"
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String description of mambo marie vanish
     * @author Lai Ying Ying
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return (actor + " vanished.");
    }

    /**
     * A description for the action in the submenu
     * @param actor The actor performing the action.
     * @return A menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " vanished";
    }
}
