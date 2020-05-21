package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class GroanAction extends Action {
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
