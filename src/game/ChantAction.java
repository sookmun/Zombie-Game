package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class ChantAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        int new_zombie_count = 0;
        while (new_zombie_count < 5) {
            Zombie new_zombie = new Zombie("Mambo Zombie");
            int x = new Random().nextInt(map.getXRange().max());
            int y = new Random().nextInt(map.getYRange().max());
            Location location = new Location(map, x, y);
            if (!map.isAnActorAt(location)) {
                map.at(location.x(), location.y()).addActor(new_zombie);
                new_zombie_count += 1;
            }
        }
        return (actor + " chanted and 5 new zombies are created");
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " chanted and 5 new zombies are created";
    }
}

