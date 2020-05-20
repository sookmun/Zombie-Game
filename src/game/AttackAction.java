package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;

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
		// if Zombie is attacked
		if (target.getClass().getName().equals("game.Zombie")){
			System.out.println(damaged_zombie((Zombie) target, map));
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

	public String damaged_zombie(Zombie target, GameMap map) {
		if (getBooleanWithProbability(50)) {        // 50% of losing 1 or 2 limbs
			Random random = new Random();	// random generator to decide if the zombie loses 1 or 2 limbs
			int rand;
			rand = random.nextInt(3);

			int ori_arm = target.num_of_arms;	// current number of arms
			int ori_leg = target.num_of_legs;	// current number of legs

			if (getBooleanWithProbability(50)) {    // Lose arm(s)
				target.num_of_arms -= rand;
				if (target.num_of_arms == 1) {        // if lose one arm and one arm is left, 50% chance of dropping any weapon
					if (getBooleanWithProbability(50)) {
						if (target.getWeapon().getClass().getName().equals("game.WeaponItem")){		// drop weapon
							WeaponItem weapon = (WeaponItem) target.getWeapon();
							System.out.println(weapon.getDropAction().execute(target, map));
						}
						map.locationOf(target).addItem(new PortableItem("ZombieArm",'a'));		// Zombie's arm dropped to the ground
						return (target + " loses one arm and has one arm left");
					}
					if (target.num_of_arms <= 0) {    // if loses both arm, drops weapon. A negative number if zombie has one arm left and random generator outcome == 2
						target.num_of_arms = 0;
						for (Item w : target.getInventory()){	// go through inventory and drop all WeaponItem
							if (w.getClass().getName().equals("game.WeaponItem")) {
								WeaponItem weapon = (WeaponItem) w;
								System.out.println(weapon.getDropAction().execute(target, map));
							}
						}
						if (ori_arm==1){	// if zombie originally has 1 arm and loses another arm
							map.locationOf(target).addItem(new PortableItem("ZombieArm",'a'));
							return (target + " loses one arm and has no arm left");
						} else{		// if zombie originally has both arm, and loses both arms
							map.locationOf(target).addItem(new PortableItem("ZombieArm",'a'));	//drop both arms
							map.locationOf(target).addItem(new PortableItem("ZombieArm",'a'));
							return (target + " loses boh arm and has no arms left");
						}
					}
				}
			}
			else {        // Lose leg(s)
				target.num_of_legs -= rand;
				if (target.num_of_legs == 1) {        // if loses 1 leg, movement speed is halved
					map.locationOf(target).addItem(new PortableItem("ZombieLeg", 'l'));
					return(target + " loses one leg and has one leg left");
				}
				if (target.num_of_legs <= 0) {    // if loses both legs, cannot move at all
					target.num_of_legs = 0;
					if (ori_leg == 1) {    // if zombie originally has 1 leg and loses another leg
						map.locationOf(target).addItem(new PortableItem("ZombieLeg", 'l'));
						return(target + " loses one leg and has one leg left");
					} else {        // if zombie originally has both legs, and loses both legs
						map.locationOf(target).addItem(new PortableItem("ZombieLeg", 'l'));
						map.locationOf(target).addItem(new PortableItem("ZombieLeg", 'l'));
						return(target + " loses both legs and has no leg left");
					}
				}
			}
		}
		return(target + " did not lose its arm(s) or limb(s)");
	}
}
