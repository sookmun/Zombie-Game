package game;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class FarmBehaviour implements Behaviour {
    private String dummy;
    public FarmBehaviour(String crop){
        dummy= crop; //ignore this
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        return null;
    }
}
