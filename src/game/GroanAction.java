package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special action for saying 'Braaaaaains'
 *
 * @ Lai Ying Ying
 */
public class GroanAction extends Action {
    /**
     * A method that execute the GroanAction
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what the action does
     *
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        assert actor.hasCapability(ZombieCapability.UNDEAD);
        return (actor + " says 'Braaaaaains'");
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
