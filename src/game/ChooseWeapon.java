package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Weapon;

public class ChooseWeapon extends Action {
    private Weapon weapon;
    private String index;

    public ChooseWeapon(Weapon weapon){
        this.weapon=weapon;
//        this.index = index;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        return weapon.getClass().getName();
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " choose " + weapon;
    }
}
