package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {

	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}

	@Override
	public void tick(Location currentLocation) {
		super.tick(currentLocation);
	}

	@Override
	public void tick(Location currentLocation, Actor actor) {
		super.tick(currentLocation, actor);
	}
}
