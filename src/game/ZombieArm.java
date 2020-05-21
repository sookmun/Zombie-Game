package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A class representing ZombieArm and the damage it can do
 * Cast-off Zombie Limbs can be wielded as simple clubs
 *
 * @author Lai Ying Ying
 */
public class ZombieArm extends WeaponItem {
    public ZombieArm(){
        super("Zombie Arm", 'a', 15, "hit");
    }
}
