package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

public class vehicle extends Item {
    public vehicle() {
        super("vehicle", '^', false);
    }

    public void addAction(Action action) {
        this.allowableActions.add(action);
    }
}
