package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class ChooseZombie extends Action {
    private Actor target;
    private String index;

    public ChooseZombie(Actor zombie, String index){
        this.target=zombie;
        this.index = index;
    }
    @Override
    public String execute(Actor actor, GameMap map){
        return index;
    }
    public String menuDescription(Actor actor){return actor + " choose "+ target;}
}
