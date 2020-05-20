package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	public static boolean getBooleanWithProbability(int probabilityOfTrue) {
		if (probabilityOfTrue <= 0) {
			return false;
		}
		else {
			return new Random().nextInt(100) + 1 <= probabilityOfTrue;
		}
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();    // get weapon. Return WeaponItem, else Intrinsic Weapon

		if (actor.getClass().getName().equals("game.Zombie") && weapon.verb().equals("bites")){    // Zombie's bite action
			if (!getBooleanWithProbability(30)){   // only 30% change of biting. if false, means do not bite
				return actor + " misses " + target + ".";    //    misses the target
			}
		}
		else {    // if not Zombie or the Zombie's IntrinsicWeapon is 'punches'
			if (rand.nextBoolean()) {            // Generate a random generator. 50% probability of attacking
				return actor + " misses " + target + ".";    //    misses the target
			}
		}
		

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		// if a successful zombie bite attack add 5 health points
		if(actor.hasCapability(ZombieCapability.UNDEAD) && weapon.verb().equals("bites")){
			actor.heal(5);
		}


		target.hurt(damage);
		if (!target.isConscious()) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}


		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
