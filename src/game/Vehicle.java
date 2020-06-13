package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

/**
 * Allows Actor to travel between worlds
 */
public class Vehicle extends Item {
    /**A non portable item
     * constructor
     */
    public Vehicle() {
        super("Vehicle", '^', false);
    }

    /**
     *To add move actor action
     * @param action special actions that can be done with item
     */

    public void addAction(Action action) {
        this.allowableActions.add(action);
    }


}
