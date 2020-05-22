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

	/**
	 * A method that execute an AttackAction, Zombie can use WeaponItem or IntrinsicWeapon
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what the actor does
	 *
	 * @author Lai Ying Ying
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();    // get weapon. Return WeaponItem, else Intrinsic Weapon

		if (actor.hasCapability(ZombieCapability.UNDEAD) && weapon.verb().equals("bites")){    // Zombie's bite action
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
		if (target.hasCapability(ZombieCapability.UNDEAD)){
			System.out.println(damaged_zombie((Zombie) target, map));
		}


		target.hurt(damage);
		if (!target.isConscious()) {
			Item corpse;
			if (target.hasCapability(ZombieCapability.UNDEAD)){	// if it is a zombie, it cant rise, so make it a portable item
				corpse = new PortableItem("dead " + target, '%');
			}
			else {
				corpse = new Corpse(target+"", map); 	// Only Corpse() can rise
			}

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

	/**
	 * A method that determines whether zombie should lose their limbs
	 * @param target: Zombie that will lose its limb(s)
	 * @param map: GameMap
	 * @return a description stating if the Zombie loses its linb(s)
	 */
	public String damaged_zombie(Zombie target, GameMap map) {
		if (getBooleanWithProbability(30) && (target.num_of_arms>=1 || target.num_of_legs>=1)) {	// 50% of losing 1 or 2 limbs if and only if the zombie still has limbs
			Random random = new Random();	// random generator to decide if the zombie loses 1 or 2 limbs
			int num_loseLimbs = random.nextInt(2)+1;

			boolean arms = random.nextBoolean();	// if legs, but no legs left but still has arms, then lose arms
			if (!arms && target.num_of_legs==0){
				if (target.num_of_arms>=1){
					arms = true;
				}
			}

			int ori_arm = target.num_of_arms;	// current number of arms
			int ori_leg = target.num_of_legs;	// current number of legs

			if (arms && target.num_of_arms>=1) {    // Lose arm(s)
				target.num_of_arms -= num_loseLimbs;
				if (target.num_of_arms == 1) {        // if lose one arm and one arm is left, 50% chance of dropping any weapon
					if (rand.nextBoolean()) {	// if true, 50% chance of dropping any weapon it is holding
						Actions dropActions = new Actions();
						for (Item item : target.getInventory())
							dropActions.add(item.getDropAction());
						for (Action drop : dropActions)
							drop.execute(target, map);
					}
					map.locationOf(target).addItem(new ZombieArm());        // Zombie's arm dropped to the ground
					return (target + " loses one arm and has one arm left");
				}
				if (target.num_of_arms <= 0) {    // if loses both arm, drops weapon. A negative number if zombie has one arm left and random generator outcome == 2
					target.num_of_arms = 0;
					Actions dropActions = new Actions();
					for (Item item : target.getInventory())
						dropActions.add(item.getDropAction());
					for (Action drop : dropActions)
						drop.execute(target, map);

					map.locationOf(target).addItem(new ZombieArm()); // drop one arm
					if (ori_arm==1){	// if zombie originally has 1 arm and loses another arm
						return (target + " loses one arm and has no arm left");
					} else{		// if zombie originally has both arm, and loses both arms
						map.locationOf(target).addItem(new ZombieArm());	// drop another arm
						return (target + " loses both arm and has no arms left");
					}
				}
			}
			else if (target.num_of_legs>=1) {        // Lose leg(s)
				target.num_of_legs -= num_loseLimbs;
				if (target.num_of_legs == 1) {        // if loses 1 leg, movement speed is halved
					map.locationOf(target).addItem(new ZombieLeg());
					return(target + " loses one leg and has one leg left");
				}
				if (target.num_of_legs <= 0) {    // if loses both legs, cannot move at all
					target.num_of_legs = 0;
					map.locationOf(target).addItem(new ZombieLeg());
					if (ori_leg == 1) {    // if zombie originally has 1 leg and loses another leg
						return(target + " loses one leg and has no leg left");
					} else {        // if zombie originally has both legs, and loses both legs
						map.locationOf(target).addItem(new ZombieLeg());
						return(target + " loses both legs and has no leg left");
					}
				}
			}
		}
		if (target.num_of_arms==0 &&target.num_of_legs==0){
			return (target + " has no more limbs to lose");
		}
		else {
			return (target + " did not lose its arm(s) or limb(s)");
		}
	}
}
