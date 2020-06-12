package game;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private int num_of_arms;
	private int num_of_legs;

	protected Random rand = new Random();

	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		this.num_of_arms = 2;
		this.num_of_legs = 2;
	}

	/**
	 * A method that determines which IntrinsicWeapon should be used by the Zombie
	 * The probability of the action returned will depend on the number of arms left
	 *
	 * @return either a bite or punch action
	 *
	 * @author Lai Ying Ying
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		boolean bool;
		if (num_of_arms==2){			// if both arms are present, 50% chance of doing either
			bool = rand.nextBoolean();
		}
		else if (num_of_arms==1){		// if one arm left, 75% chance of biting
			bool = rand.nextInt(100)+1 <= 75;
		}
		else{
			bool = true;	// no arms are present, can only bite
		}

		if (bool){		// true --> bites
			return new IntrinsicWeapon(20, "bites");
		}
		else{		// false --> punches
			return new IntrinsicWeapon(10, "punches");
		}
	}

	@Override
	public Weapon getWeapon(){
		for (Item item : inventory) {
			if (item.asWeapon() != null && (!(item instanceof SniperRifle) &&!(item instanceof Shotgun)) ){
				return item.asWeapon();
			}

		}
		return getIntrinsicWeapon();
	}


	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (rand.nextInt(100)+1 <= 10){	// 10% chance of saying 'Braaaaaains'
			return new GroanAction();
		}

		for (Action action: actions){		// if there's a weapon on the floor, pick up the item
			if (action instanceof PickUpItemAction){
				if (num_of_arms>=1){
					return action;
				}
			}
		}
		if (rand.nextInt(100)+1 <= 10){		// 10 % chance of damaging the crop and turn them back into dirt
			if (map.locationOf(this).getGround().getDisplayChar() - 'C' == 0 ){
				return new UnsowAction();
			}
		}

		for (Behaviour behaviour : behaviours) {
			if (num_of_legs==2){
				Action action = behaviour.getAction(this, map);
				if (action != null)
					return action;
			}
			else if (num_of_legs==1){
				Action action = behaviour.getAction(this, map);
				if(behaviour instanceof AttackAction){
					if (action != null) {
						return action;
					}
				}
				else if (behaviour instanceof WanderBehaviour || behaviour instanceof HuntBehaviour) {	// if current action is to move
					if (lastAction instanceof DoNothingAction) {
						if (action != null){
							return action;
						}
					}
				}
			}
			else if (num_of_legs==0){
				Action action = behaviour.getAction(this, map);
				if (behaviour instanceof WanderBehaviour || behaviour instanceof HuntBehaviour) {    // if current action is to move
					return new DoNothingAction();    // zombie can't move at all
				}
				else{
					if (action != null)
						return action;
				}
			}
		}
		return new DoNothingAction();
	}

	/**
	 * Getter for number of arms
	 * @return the current number of arms of Zombie
	 */
	public int getNum_of_arms() {
		return num_of_arms;
	}

	/**
	 * Setter for number of arms
	 * @param num_of_arms: new number of arms
	 * @throws Exception when the number of arms is not in between 0 and 2
	 */
	public void setNum_of_arms(int num_of_arms) throws Exception {
		if (num_of_arms>=0 && num_of_arms<=2){
			this.num_of_arms = num_of_arms;
		}
		else {
			throw new Exception("Invalid Number of Zombie Arms");
		}

	}

	/**
	 * Getter for number of legs
	 * @return the current number of legs of Zombie
	 */
	public int getNum_of_legs() {
		return num_of_legs;
	}

	/**
	 * Setter for number of arms
	 * @param num_of_legs: new number of legs
	 * @throws Exception when the number of legs is not in between 0 and 2
	 */
	public void setNum_of_legs(int num_of_legs) throws Exception {
		if (num_of_legs >= 0 && num_of_legs<=2){
			this.num_of_legs = num_of_legs;
		}
		else{
			throw new Exception("Invalid Number of Zombie Legs");
		}
	}
}
