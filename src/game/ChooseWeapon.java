package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Weapon;

/**
 * A class that enable the player to choose his weapon
 * @author Lai Ying Ying
 */
public class ChooseWeapon extends Action {
    private Weapon weapon;
    private String index;

    public ChooseWeapon(Weapon weapon, String index){
        this.weapon=weapon;
        this.index = index;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        return index;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " choose " + weapon + " that has " + weapon.damage() + " hit points.";
    }
}
